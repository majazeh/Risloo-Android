package com.majazeh.risloo.utils.managers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.majazeh.risloo.BuildConfig;

import java.io.File;

public class GadgetManager {

    /*
    ---------- Request ----------
    */

    public static void requestDocument(Activity activity) {
        if (permissionDocument(activity)) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            int requestCode = 100;

            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setType("*/*");

            activity.startActivityForResult(intent, requestCode);
        }
    }

    public static void requestSendTo(Activity activity, String number, String name, String value) {
        if (permissionSendTo(activity)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            int requestCode = 200;

            intent.setData(Uri.parse("smsto:" + number));
            intent.putExtra(name, value);

            activity.startActivityForResult(intent, requestCode);
        }
    }

    public static void requestGallery(Activity activity) {
        if (permissionGallery(activity)) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            int requestCode = 300;

            intent.setType("image/*");

            activity.startActivityForResult(intent, requestCode);
        }
    }

    public static String requestCamera(Activity activity) {
        if (permissionCamera(activity)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            int requestCode = 400;

            File file = FileManager.createImageExternalFilesTempPath(activity, Environment.DIRECTORY_PICTURES);
            if (file != null) {
                Uri uri;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", file);
                else
                    uri = Uri.fromFile(file);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                activity.startActivityForResult(intent, requestCode);

                return file.getAbsolutePath();
            }

            return "";
        }

        return "";
    }

    /*
    ---------- Result ----------
    */





    /*
    ---------- Permission ----------
    */

    private static boolean permissionDocument(Activity activity) {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        int requestCode = 100;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permissions[0]) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                return false;
            }
        } else {
            return true;
        }
    }

    private static boolean permissionSendTo(Activity activity) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int requestCode = 200;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permissions[0]) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                return false;
            }
        } else {
            return true;
        }
    }

    private static boolean permissionGallery(Activity activity) {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        int requestCode = 300;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permissions[0]) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                return false;
            }
        } else {
            return true;
        }
    }

    private static boolean permissionCamera(Activity activity) {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int requestCode = 400;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permissions[0]) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(activity, permissions[1]) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    return true;
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{permissions[1]}, requestCode);
                    return false;
                }
            } else if (ContextCompat.checkSelfPermission(activity, permissions[1]) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(activity, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
                    return true;
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{permissions[0]}, requestCode);
                    return false;
                }
            } else {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                return false;
            }
        } else {
            return true;
        }
    }

}