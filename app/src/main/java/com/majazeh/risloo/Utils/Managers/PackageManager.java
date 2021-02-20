package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.content.pm.PackageInfo;

public class PackageManager {

    public static String versionCode(Activity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            return String.valueOf(packageInfo.versionCode);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } return "";
    }

    public static String versionName(Activity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } return "";
    }

}