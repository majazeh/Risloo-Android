package com.majazeh.risloo.utils.managers;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.majazeh.risloo.R;

public class VersionManager {

    /*
    ---------- Func's ----------
    */

    public static int versionCode(Activity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);

            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static String versionName(Activity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);

            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String versionNamePrefix(Activity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);

            if (packageInfo.versionName.contains("-"))
                return StringManager.prefix(packageInfo.versionName, '-');
            else
                return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String versionNameDesc(Activity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);

            if (packageInfo.versionName.contains("-"))
                return activity.getResources().getString(R.string.AppVersion) + " " + StringManager.prefix(packageInfo.versionName, '-');
            else
                return activity.getResources().getString(R.string.AppVersion) + " " + packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

}