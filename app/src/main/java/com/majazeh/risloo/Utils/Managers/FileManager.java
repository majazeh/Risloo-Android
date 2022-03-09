package com.majazeh.risloo.utils.managers;

import android.app.Activity;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class FileManager {

    /*
    ---------- Create's ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createInternalCachePath(Activity activity, String title) {
        File file = new File(activity.getCacheDir(), title);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createInternalFilesPath(Activity activity, String title) {
        File file = new File(activity.getFilesDir(), title);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createExternalCachePath(Activity activity, String title) {
        File file = new File(activity.getExternalCacheDir(), title);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createExternalFilesPath(Activity activity, String type, String title) {
        File file = new File(activity.getExternalFilesDir(type), title);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createExternalStoragePath(String title) {
        File file = new File(Environment.getExternalStorageDirectory(), title);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createExternalStoragePublicPath(String type, String title) {
        File file = new File(Environment.getExternalStoragePublicDirectory(type), title);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    public static File createImageExternalFilesTempPath(Activity activity, String type) {
        try {
            File parent = activity.getExternalFilesDir(type);

            String prefix = "JPEG" + "_" + System.currentTimeMillis() + "_";
            String suffix = ".jpg";

            return File.createTempFile(prefix, suffix, parent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    ---------- Has's ----------
    */

    public static boolean hasInternalCachePath(Activity activity, String title) {
        File file = new File(activity.getCacheDir(), title);

        return file.exists();
    }

    public static boolean hasInternalFilesPath(Activity activity, String title) {
        File file = new File(activity.getFilesDir(), title);

        return file.exists();
    }

    public static boolean hasExternalCachePath(Activity activity, String title) {
        File file = new File(activity.getExternalCacheDir(), title);

        return file.exists();
    }

    public static boolean hasExternalFilesPath(Activity activity, String type, String title) {
        File file = new File(activity.getExternalFilesDir(type), title);

        return file.exists();
    }

    public static boolean hasExternalStoragePath(String title) {
        File file = new File(Environment.getExternalStorageDirectory(), title);

        return file.exists();
    }

    public static boolean hasExternalStoragePublicPath(String type, String title) {
        File file = new File(Environment.getExternalStoragePublicDirectory(type), title);

        return file.exists();
    }

    /*
    ---------- Delete's ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteInternalCachePath(Activity activity, String title) {
        File file = new File(activity.getCacheDir(), title);

        if (file.exists())
            file.delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteInternalFilesPath(Activity activity, String title) {
        File file = new File(activity.getFilesDir(), title);

        if (file.exists())
            file.delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteExternalCachePath(Activity activity, String title) {
        File file = new File(activity.getExternalCacheDir(), title);

        if (file.exists())
            file.delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteExternalFilesPath(Activity activity, String type, String title) {
        File file = new File(activity.getExternalFilesDir(type), title);

        if (file.exists())
            file.delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteExternalStoragePath(String title) {
        File file = new File(Environment.getExternalStorageDirectory(), title);

        if (file.exists())
            file.delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteExternalStoragePublicPath(String type, String title) {
        File file = new File(Environment.getExternalStoragePublicDirectory(type), title);

        if (file.exists())
            file.delete();
    }


    /*
    ---------- Clear's ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void clearInternalCachePath(Activity activity, String title) {
        File file = new File(activity.getCacheDir(), title);

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
    public static void clearInternalFilesPath(Activity activity, String title) {
        File file = new File(activity.getFilesDir(), title);

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
    public static void clearExternalCachePath(Activity activity, String title) {
        File file = new File(activity.getExternalCacheDir(), title);

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
    public static void clearExternalFilesPath(Activity activity, String type, String title) {
        File file = new File(activity.getExternalFilesDir(type), title);

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
    public static void clearExternalStoragePath(String title) {
        File file = new File(Environment.getExternalStorageDirectory(), title);

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
    public static void clearExternalStoragePublicPath(String type, String title) {
        File file = new File(Environment.getExternalStoragePublicDirectory(type), title);

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
    ---------- List's ----------
    */

    public static File[] listFilesInternalCachePath(Activity activity, String title) {
        File file = new File(activity.getCacheDir(), title);

        if (file.exists())
            return file.listFiles();
        else
            return null;
    }

    public static File[] listFilesInternalFilesPath(Activity activity, String title) {
        File file = new File(activity.getFilesDir(), title);

        if (file.exists())
            return file.listFiles();
        else
            return null;
    }

    public static File[] listFilesExternalCachePath(Activity activity, String title) {
        File file = new File(activity.getExternalCacheDir(), title);

        if (file.exists())
            return file.listFiles();
        else
            return null;
    }

    public static File[] listFilesExternalFilesPath(Activity activity, String type, String title) {
        File file = new File(activity.getExternalFilesDir(type), title);

        if (file.exists())
            return file.listFiles();
        else
            return null;
    }

    public static File[] listFilesExternalStoragePath(String title) {
        File file = new File(Environment.getExternalStorageDirectory(), title);

        if (file.exists())
            return file.listFiles();
        else
            return null;
    }

    public static File[] listFilesExternalStoragePublicPath(String type, String title) {
        File file = new File(Environment.getExternalStoragePublicDirectory(type), title);

        if (file.exists())
            return file.listFiles();
        else
            return null;
    }

}