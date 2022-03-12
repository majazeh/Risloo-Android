package com.majazeh.risloo.utils.managers;

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
    ---------- Func's ----------
    */

    public static String localPath(Activity activity, Uri uri) {
        if (isDocumentUri(activity, uri)) {

            if (isLocalStorageDocumentUri(uri))
                return getLocalStorageDocument(uri);

            else if (isExternalStorageDocumentUri(uri))
                return getExternalStorageDocument(uri);

            else if (isDownloadsDocumentUri(uri))
                return getDownloadsDocument(activity, uri);

            else if (isMediaDocumentUri(uri))
                return getMediaDocument(activity, uri);

        }

        else if (isMediaStoreUri(uri))
            return getMediaStore(activity, uri);

        else if (isFileUri(uri))
            return getFile(uri);

        return null;
    }

    /*
    ---------- Getter's ----------
    */

    // -------------------- Document

    private static String getLocalStorageDocument(Uri uri) {
        return DocumentsContract.getDocumentId(uri);
    }

    private static String getExternalStorageDocument(Uri uri) {
        String docId = DocumentsContract.getDocumentId(uri);
        String[] split = docId.split(":");
        String type = split[0];

        if (type.equalsIgnoreCase("primary"))
            return Environment.getExternalStorageDirectory() + "/" + split[1];
        else if (type.equalsIgnoreCase("home"))
            return Environment.getExternalStorageDirectory() + "/documents/" + split[1];
        else
            return "";
    }

    private static String getDownloadsDocument(Activity activity, Uri uri) {
        String docId = DocumentsContract.getDocumentId(uri);

        if (docId != null && docId.startsWith("raw:"))
            return docId.substring(4);

        String[] contentUriPrefixesToTry = new String[]{
                "content://downloads/public_downloads",
                "content://downloads/my_downloads"
        };

        for (String contentUriPrefix : contentUriPrefixesToTry) {
            String[] split = Objects.requireNonNull(docId).split(":");

            Uri contentUri;

            if (Objects.requireNonNull(docId).startsWith("msf:"))
                contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.parseLong(split[1]));
            else
                contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.parseLong(docId));

            return getDateColumn(activity, contentUri, null, null);
        }

        String fileName = getFileName(activity, uri);
        File cacheDir = FileManager.createInternalCachePath(activity, "documents");
        File file = new File(cacheDir, fileName);
        String path = "";

        if (fileName != null) {
            path = file.getAbsolutePath();
            saveUriToInternalCache(activity, uri, path);
        }

        return path;
    }

    private static String getMediaDocument(Activity activity, Uri uri) {
        String docId = DocumentsContract.getDocumentId(uri);
        String[] split = docId.split(":");
        String type = split[0];

        Uri contentUri = null;

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
        }

        String selection = "_id=?";
        String[] selectionArgs = new String[]{
                split[1]
        };

        return getDateColumn(activity, contentUri, selection, selectionArgs);
    }

    // -------------------- MediaStore

    private static String getMediaStore(Activity activity, Uri uri) {
        if (isGooglePhotosUri(uri))
            return uri.getLastPathSegment();
        else
            return getDateColumn(activity, uri, null, null);
    }

    // -------------------- File

    private static String getFile(Uri uri) {
        return uri.getPath();
    }

    /*
    ---------- Bool's ----------
    */

    // -------------------- Document

    private static boolean isDocumentUri(Activity activity, Uri uri) {
        return DocumentsContract.isDocumentUri(activity, uri);
    }

    private static boolean isLocalStorageDocumentUri(Uri uri) {
        return (BuildConfig.APPLICATION_ID + ".provider").equals(uri.getAuthority());
    }

    private static boolean isExternalStorageDocumentUri(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocumentUri(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocumentUri(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    // -------------------- MediaStore

    private static boolean isMediaStoreUri(Uri uri) {
        return "content".equalsIgnoreCase(uri.getScheme());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    // -------------------- File

    private static boolean isFileUri(Uri uri) {
        return "file".equalsIgnoreCase(uri.getScheme());
    }

    /*
    ---------- Save's ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void saveUriToInternalCache(Context context, Uri uri, String name) {
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
                if (is != null)
                    is.close();
                if (fos != null)
                    fos.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    ---------- Private's ----------
    */

    private static String getFileName(Context context, Uri uri) {
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

    private static String getDateColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
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

}