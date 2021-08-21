package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.content.pm.PackageInfo;

public class PackageManager {

    /*
    ---------- Funcs ----------
    */

    public static int versionCode(Activity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } return -1;
    }

    public static String versionName(Activity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } return "";
    }

    public static String versionNameWithoutSuffix(Activity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            if (packageInfo.versionName.contains("-"))
                return packageInfo.versionName.substring(0, packageInfo.versionName.indexOf("-"));
            else
                return packageInfo.versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } return "";
    }

}