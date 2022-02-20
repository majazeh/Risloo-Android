package com.majazeh.risloo.utils.managers;

import android.app.Activity;
import android.content.pm.PackageInfo;

import com.majazeh.risloo.R;

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

    public static String versionNameSuffix(Activity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } return "";
    }

    public static String versionNameNoSuffix(Activity activity) {
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

    public static String versionNameWithText(Activity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            if (packageInfo.versionName.contains("-"))
                return activity.getResources().getString(R.string.AppVersion) + " " + packageInfo.versionName.substring(0, packageInfo.versionName.indexOf("-"));
            else
                return activity.getResources().getString(R.string.AppVersion) + " " + packageInfo.versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } return "";
    }

}