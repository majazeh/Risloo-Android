package com.majazeh.risloo.utils.managers;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.majazeh.risloo.BuildConfig;

import java.io.File;

public class UriManager {

    /*
    ---------- Func's ----------
    */

    public static Uri uri(Activity activity, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", file);
        else
            return Uri.fromFile(file);
    }

    public static Uri uri(Activity activity, File file, String path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", new File(path));
        else
            return Uri.fromFile(file);
    }

}