package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

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

}