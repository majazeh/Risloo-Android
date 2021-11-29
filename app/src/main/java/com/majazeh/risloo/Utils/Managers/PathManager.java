package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import com.majazeh.risloo.BuildConfig;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class PathManager {

    /*
    ---------- Funcs ----------
    */

    public static String localPath(Activity activity, Uri uri) {

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(activity, uri)) {

            // Local
            if (isLocalStorageDocument(uri)) {
                return DocumentsContract.getDocumentId(uri);
            }

            // External
            else if (isExternalStorageDocument(uri)) {
                String documentId = DocumentsContract.getDocumentId(uri);
                String[] split = documentId.split(":");
                String type = split[0];

                if ("primary".equalsIgnoreCase(type))
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                else if ("home".equalsIgnoreCase(type))
                    return Environment.getExternalStorageDirectory() + "/documents/" + split[1];
            }

            // Downloads
            else if (isDownloadsDocument(uri)) {
                String documentId = DocumentsContract.getDocumentId(uri);

                if (documentId != null && documentId.startsWith("raw:"))
                    return documentId.substring(4);

                String[] contentUriPrefixesToTry = new String[] {
                        "content://downloads/public_downloads",
                        "content://downloads/my_downloads"
                };

                for (String contentUriPrefix : contentUriPrefixesToTry) {
                    String[] split = Objects.requireNonNull(documentId).split(":");

                    Uri contentUri;
                    if (Objects.requireNonNull(documentId).startsWith("msf:"))
                        contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.parseLong(split[1]));
                    else
                        contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.parseLong(documentId));

                    String path = fileColumn(activity, contentUri, null, null);
                    if (path != null)
                        return path;
                }

                String fileName = fileName(activity, uri);
                if (fileName != null) {
                    File file = new File(FileManager.createInternalCachePath(activity, "documents"), fileName);
                    String path = file.getAbsolutePath();
                    writeUriToInternalCache(activity, uri, path);
                    return path;
                }
            }

            // Media
            else if (isMediaDocument(uri)) {
                String documentId = DocumentsContract.getDocumentId(uri);
                String[] split = documentId.split(":");
                String type = split[0];

                Uri contentUri;
                switch (type) {
                    case "image":
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "video":
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "audio":
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        break;
                    default:
                        contentUri = null;
                        break;
                }

                String selection = "_id=?";
                String[] selectionArgs = new String[] {split[1]};

                return fileColumn(activity, contentUri, selection, selectionArgs);
            }

        }

        // MediaStore
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            else
                return fileColumn(activity, uri, null, null);
        }

        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /*
    ---------- Access ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void writeUriToInternalCache(Context context, Uri uri, String name) {
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            is = context.getContentResolver().openInputStream(uri);
            fos = new FileOutputStream(name, false);
            bos = new BufferedOutputStream(fos);

            byte[] buf = new byte[1024];
            is.read(buf);

            do bos.write(buf);
            while (is.read(buf) != -1);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String fileName(Context context, Uri uri) {
        Cursor cursor = null;

        try {
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                return cursor.getString(nameIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    private static String fileColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;

        String column = MediaStore.Files.FileColumns.DATA;
        String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    /*
    ---------- Bools ----------
    */

    private static boolean isLocalStorageDocument(Uri uri) {
        return (BuildConfig.APPLICATION_ID + ".provider").equals(uri.getAuthority());
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}