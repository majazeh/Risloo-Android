package com.majazeh.risloo.Utils.Managers;

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
import java.util.Objects;

public class FileManager {

    /*
    ---------- Create ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createInternalCachePath(Activity activity, String name) {
        File file = new File(activity.getCacheDir(), name);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createInternalFilesPath(Activity activity, String name) {
        File file = new File(activity.getFilesDir(), name);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createExternalCachePath(Activity activity, String name) {
        File file = new File(activity.getExternalCacheDir(), name);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createExternalFilesPath(Activity activity, String type, String name) {
        File file = new File(activity.getExternalFilesDir(type), name);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    /*
    ---------- Has ----------
    */

    public static boolean hasInternalCachePath(Activity activity, String name) {
        File file = new File(activity.getCacheDir(), name);
        return file.exists();
    }

    public static boolean hasInternalFilesPath(Activity activity, String name) {
        File file = new File(activity.getFilesDir(), name);
        return file.exists();
    }

    public static boolean hasExternalCachePath(Activity activity, String name) {
        File file = new File(activity.getExternalCacheDir(), name);
        return file.exists();
    }

    public static boolean hasExternalFilesPath(Activity activity, String type, String name) {
        File file = new File(activity.getExternalFilesDir(type), name);
        return file.exists();
    }

    /*
    ---------- Delete ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteInternalCachePath(Activity activity, String name) {
        File file = new File(activity.getCacheDir(), name);
        if (file.exists())
            file.delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteInternalFilesPath(Activity activity, String name) {
        File file = new File(activity.getFilesDir(), name);
        if (file.exists())
            file.delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteExternalCachePath(Activity activity, String name) {
        File file = new File(activity.getExternalCacheDir(), name);
        if (file.exists())
            file.delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteExternalFilesPath(Activity activity, String type, String name) {
        File file = new File(activity.getExternalFilesDir(type), name);
        if (file.exists())
            file.delete();
    }

    /*
    ---------- Clear ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void clearInternalCachePath(Activity activity, String name) {
        File file = new File(activity.getCacheDir(), name);
        if (file.exists()) {
            String[] children = file.list();
            if (children != null) {
                for (String child : children) {
                    File sub = new File(file, child);
                    if (sub.exists())
                        sub.delete();
                }
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void clearInternalFilesPath(Activity activity, String name) {
        File file = new File(activity.getFilesDir(), name);
        if (file.exists()) {
            String[] children = file.list();
            if (children != null) {
                for (String child : children) {
                    File sub = new File(file, child);
                    if (sub.exists())
                        sub.delete();
                }
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void clearExternalCachePath(Activity activity, String name) {
        File file = new File(activity.getExternalCacheDir(), name);
        if (file.exists()) {
            String[] children = file.list();
            if (children != null) {
                for (String child : children) {
                    File sub = new File(file, child);
                    if (sub.exists())
                        sub.delete();
                }
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void clearExternalFilesPath(Activity activity, String type, String name) {
        File file = new File(activity.getExternalFilesDir(type), name);
        if (file.exists()) {
            String[] children = file.list();
            if (children != null) {
                for (String child : children) {
                    File sub = new File(file, child);
                    if (sub.exists())
                        sub.delete();
                }
            }
        }
    }

    /*
    ---------- List ----------
    */

    public static String[] listInternalCachePath(Activity activity, String name) {
        File file = new File(activity.getCacheDir(), name);
        if (file.exists())
            return file.list();
        else
            return null;
    }

    public static String[] listInternalFilesPath(Activity activity, String name) {
        File file = new File(activity.getFilesDir(), name);
        if (file.exists())
            return file.list();
        else
            return null;
    }

    public static String[] listExternalCachePath(Activity activity, String name) {
        File file = new File(activity.getExternalCacheDir(), name);
        if (file.exists())
            return file.list();
        else
            return null;
    }

    public static String[] listExternalFilesPath(Activity activity, String name) {
        File file = new File(activity.getExternalCacheDir(), name);
        if (file.exists())
            return file.list();
        else
            return null;
    }

    /*
    ---------- ListFiles ----------
    */

    public static File[] listFilesInternalCachePath(Activity activity, String name) {
        File file = new File(activity.getCacheDir(), name);
        if (file.exists())
            return file.listFiles();
        else
            return null;
    }

    public static File[] listFilesInternalFilesPath(Activity activity, String name) {
        File file = new File(activity.getFilesDir(), name);
        if (file.exists())
            return file.listFiles();
        else
            return null;
    }

    public static File[] listFilesExternalCachePath(Activity activity, String name) {
        File file = new File(activity.getExternalCacheDir(), name);
        if (file.exists())
            return file.listFiles();
        else
            return null;
    }

    public static File[] listFilesExternalFilesPath(Activity activity, String type, String name) {
        File file = new File(activity.getExternalFilesDir(type), name);
        if (file.exists())
            return file.listFiles();
        else
            return null;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////










































    /*
    ---------- Write ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File writeBitmapToInternalCache(Activity activity, Bitmap bitmap, String name) {
        try {
            File file = new File(activity.getCacheDir(), name);
            if (!Objects.requireNonNull(file.getParentFile()).exists())
                file.getParentFile().mkdirs();
            if (!file.exists())
                file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeObjectToInternalCache(Activity activity, JSONObject object, String name) {
        try {
            File file = new File(activity.getCacheDir(), name);
            if (!Objects.requireNonNull(file.getParentFile()).exists())
                file.getParentFile().mkdirs();
            if (!file.exists())
                file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object.toString());
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeArrayToInternalCache(Activity activity, JSONArray array, String name) {
        try {
            File file = new File(activity.getCacheDir(), name);
            if (!Objects.requireNonNull(file.getParentFile()).exists())
                file.getParentFile().mkdirs();
            if (!file.exists())
                file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(array.toString());
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    ---------- Read ----------
    */

    public static Bitmap readBitmapFromInternalCache(Activity activity, String name) {
        try {
            File file = new File(activity.getCacheDir(), name);
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

    public static JSONObject readObjectFromInternalCache(Activity activity, String name) {
        try {
            File file = new File(activity.getCacheDir(), name);
            if (!Objects.requireNonNull(file.getParentFile()).exists())
                return null;
            if (!file.exists())
                return null;

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            JSONObject object = new JSONObject(ois.readObject().toString());
            ois.close();
            return object;
        } catch (IOException | JSONException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray readArrayFromInternalCache(Activity activity, String name) {
        try {
            File file = new File(activity.getCacheDir(), name);
            if (!Objects.requireNonNull(file.getParentFile()).exists())
                return null;
            if (!file.exists())
                return null;

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            JSONArray array = new JSONArray(ois.readObject().toString());
            ois.close();
            return array;
        } catch (IOException | JSONException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static File createImageFile(Activity activity) {
        try {
            File directory = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            String prefix = "JPEG_" + System.currentTimeMillis() + "_";
            String suffix = ".jpg";

            return File.createTempFile(prefix, suffix, directory);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeUriToInternalCache(Context context, Uri uri, String name) {
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

    public static File readFileFromInternalCache(Context context, String name) {
        File file = new File(context.getCacheDir(), name);
        if (!Objects.requireNonNull(file.getParentFile()).exists())
            return null;
        if (!file.exists())
            return null;

        return file;
    }

}