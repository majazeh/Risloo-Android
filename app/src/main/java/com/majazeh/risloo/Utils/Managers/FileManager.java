package com.majazeh.risloo.Utils.Managers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class FileManager {

    /*
    ---------- Create ----------
    */

    @SuppressLint("SimpleDateFormat")
    public static File createImageFile(Activity activity) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(new Date());
            String fileName = "IMG_" + timeStamp + "_";
            String fileSuffix = ".jpg";

            File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            return File.createTempFile(fileName, fileSuffix, storageDir);
        } catch (IOException e) {
            return new File("");
        }
    }

    /*
    ---------- Write ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeObjectToCache(Context context, JSONObject jsonObject, String fileName) {
        try {
            File file = new File(context.getCacheDir(), fileName);
            if (!Objects.requireNonNull(file.getParentFile()).exists())
                file.getParentFile().mkdirs();
            if (!file.exists())
                file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(jsonObject.toString());
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeArrayToCache(Context context, JSONArray jsonArray, String fileName) {
        try {
            File file = new File(context.getCacheDir(), fileName);
            if (!Objects.requireNonNull(file.getParentFile()).exists())
                file.getParentFile().mkdirs();
            if (!file.exists())
                file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(jsonArray.toString());
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeBitmapToCache(Context context, Bitmap bitmap, String fileName) {
        try {
            File file = new File(context.getCacheDir(), fileName);
            if (!Objects.requireNonNull(file.getParentFile()).exists())
                file.getParentFile().mkdirs();
            if (!file.exists())
                file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeUriToCache(Context context, Uri uri, String fileName) {
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            is = context.getContentResolver().openInputStream(uri);
            fos = new FileOutputStream(fileName, false);
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

    /*
    ---------- Read ----------
    */

    public static JSONObject readObjectFromCache(Context context, String fileName) {
        try {
            File file = new File(context.getCacheDir(), fileName);
            if (!Objects.requireNonNull(file.getParentFile()).exists())
                return null;
            if (!file.exists())
                return null;

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            JSONObject jsonObject = new JSONObject(ois.readObject().toString());
            ois.close();
            return jsonObject;
        } catch (IOException | JSONException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray readArrayFromCache(Context context, String fileName) {
        try {
            File file = new File(context.getCacheDir(), fileName);
            if (!Objects.requireNonNull(file.getParentFile()).exists())
                return null;
            if (!file.exists())
                return null;

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            JSONArray jsonArray = new JSONArray(ois.readObject().toString());
            ois.close();
            return jsonArray;
        } catch (IOException | JSONException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap readBitmapFromCache(Context context, String fileName) {
        try {
            File file = new File(context.getCacheDir(), fileName);
            if (!Objects.requireNonNull(file.getParentFile()).exists())
                return null;
            if (!file.exists())
                return null;

            FileInputStream fis = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File readFileFromCache(Context context, String fileName) {
        File file = new File(context.getCacheDir(), fileName);
        if (!Objects.requireNonNull(file.getParentFile()).exists())
            return null;
        if (!file.exists())
            return null;

        return file;
    }

    /*
    ---------- Check ----------
    */

    public static boolean hasFileInCache(Context context, String fileName) {
        File file = new File(context.getCacheDir(), fileName);
        return file.exists();
    }

    /*
    ---------- Delete ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteFileFromCache(Context context, String fileName) {
        File file = new File(context.getCacheDir(), fileName);
        if (file.exists())
            file.delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteFolderFromCache(Context context, String fileName) {
        File file = new File(context.getCacheDir(), fileName);
        if (file.exists()) {
            String[] children = file.list();
            if (children != null) {
                for (String child : children) {
                    File subFile = new File(file, child);
                    if (subFile.exists())
                        subFile.delete();
                }
            }
            file.delete();
        }
    }

}