package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createExternalStoragePath(String name) {
        File file = new File(Environment.getExternalStorageDirectory(), name);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createExternalStoragePublicPath(String type, String name) {
        File file = new File(Environment.getExternalStoragePublicDirectory(type), name);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    public static File createImageExternalFilesTempPath(Activity activity, String type) {
        try {
            File parent = activity.getExternalFilesDir(type);

            String prefix = "JPEG_" + System.currentTimeMillis() + "_";
            String suffix = ".jpg";

            return File.createTempFile(prefix, suffix, parent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

    public static boolean hasExternalStoragePath(String name) {
        File file = new File(Environment.getExternalStorageDirectory(), name);
        return file.exists();
    }

    public static boolean hasExternalStoragePublicPath(String type, String name) {
        File file = new File(Environment.getExternalStoragePublicDirectory(type), name);
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteExternalStoragePath(String name) {
        File file = new File(Environment.getExternalStorageDirectory(), name);
        if (file.exists())
            file.delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteExternalStoragePublicPath(String type, String name) {
        File file = new File(Environment.getExternalStoragePublicDirectory(type), name);
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void clearExternalStoragePath(String name) {
        File file = new File(Environment.getExternalStorageDirectory(), name);
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
    public static void clearExternalStoragePublicPath(String type, String name) {
        File file = new File(Environment.getExternalStoragePublicDirectory(type), name);
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

    public static String[] listExternalFilesPath(Activity activity, String type, String name) {
        File file = new File(activity.getExternalFilesDir(type), name);
        if (file.exists())
            return file.list();
        else
            return null;
    }

    public static String[] listExternalStoragePath(String name) {
        File file = new File(Environment.getExternalStorageDirectory(), name);
        if (file.exists())
            return file.list();
        else
            return null;
    }

    public static String[] listExternalStoragePublicPath(String type, String name) {
        File file = new File(Environment.getExternalStoragePublicDirectory(type), name);
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

    public static File[] listFilesExternalStoragePath(String name) {
        File file = new File(Environment.getExternalStorageDirectory(), name);
        if (file.exists())
            return file.listFiles();
        else
            return null;
    }

    public static File[] listFilesExternalStoragePublicPath(String type, String name) {
        File file = new File(Environment.getExternalStoragePublicDirectory(type), name);
        if (file.exists())
            return file.listFiles();
        else
            return null;
    }

    /*
    ---------- Save To Stream ----------
    */

    public static File saveBitmapToStream(Bitmap bitmap, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return file;
        }
    }

    public static File saveObjectToStream(JSONObject object, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object.toString());
            oos.flush();
            oos.close();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return file;
        }
    }

    public static File saveArrayToStream(JSONArray array, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(array.toString());
            oos.flush();
            oos.close();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return file;
        }
    }

    /*
    ---------- Load From Stream ----------
    */

    public static Bitmap loadBitmapFromStream(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            fis.close();

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject loadObjectFromStream(File file) {
        try {
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

    public static JSONArray loadArrayFromStream(File file) {
        try {
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

}