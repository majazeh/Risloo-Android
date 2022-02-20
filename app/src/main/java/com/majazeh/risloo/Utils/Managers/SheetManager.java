package com.majazeh.risloo.utils.managers;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.majazeh.risloo.Views.sheets.AuthBottomSheet;
import com.majazeh.risloo.Views.sheets.BulkSampleBottomSheet;
import com.majazeh.risloo.Views.sheets.DateBottomSheet;
import com.majazeh.risloo.Views.sheets.ImageBottomSheet;
import com.majazeh.risloo.Views.sheets.LogoutBottomSheet;
import com.majazeh.risloo.Views.sheets.TimeBottomSheet;
import com.majazeh.risloo.Views.sheets.VersionBottomSheet;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.ClientModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

public class SheetManager {

    // Widgets
    private static AuthBottomSheet authBottomSheet = null;
    private static BulkSampleBottomSheet bulkSampleBottomSheet = null;
    private static DateBottomSheet dateBottomSheet = null;
    private static ImageBottomSheet imageBottomSheet = null;
    private static LogoutBottomSheet logoutBottomSheet = null;
    private static TimeBottomSheet timeBottomSheet = null;
    private static VersionBottomSheet versionBottomSheet = null;

    /*
    ---------- Show ----------
    */

    public static void showAuthBottomSheet(Activity activity, String key, UserModel userModel) {
        authBottomSheet = new AuthBottomSheet();
        authBottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "authBottomSheet");
        authBottomSheet.setData(key, userModel);
    }

    public static void showBulkSampleBottomSheet(Activity activity, String key, UserModel userModel, BulkSampleModel bulkSampleModel) {
        bulkSampleBottomSheet = new BulkSampleBottomSheet();
        bulkSampleBottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "bulkSampleBottomSheet");
        bulkSampleBottomSheet.setData(key, userModel, bulkSampleModel);
    }

    public static void showDateBottomSheet(Activity activity, String timestamp, String method) {
        dateBottomSheet = new DateBottomSheet();
        dateBottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "dateBottomSheet");
        dateBottomSheet.setDate(timestamp, method);
    }

    public static void showImageBottomSheet(Activity activity) {
        imageBottomSheet = new ImageBottomSheet();
        imageBottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "imageBottomSheet");
    }

    public static void showLogoutBottomSheet(Activity activity, UserModel userModel) {
        logoutBottomSheet = new LogoutBottomSheet();
        logoutBottomSheet.show(((AppCompatActivity) activity).getSupportFragmentManager(), "logoutBottomSheet");
        logoutBottomSheet.setData(userModel);
    }

    public static void showTimeBottomSheet(Activity activity, String timestamp, String method) {
        timeBottomSheet = new TimeBottomSheet();
        timeBottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "timeBottomSheet");
        timeBottomSheet.setTime(timestamp, method);
    }

    public static void showVersionBottomSheet(Activity activity, ClientModel clientModel, String method) {
        versionBottomSheet = new VersionBottomSheet();
        versionBottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "versionBottomSheet");
        versionBottomSheet.setData(clientModel, method);
    }

    /*
    ---------- Dismiss ----------
    */

    public static void dismissAuthBottomSheet() {
        if (authBottomSheet != null) {
            authBottomSheet.dismiss();
            authBottomSheet = null;
        }
    }

    public static void dismissBulkSampleBottomSheet() {
        if (bulkSampleBottomSheet != null) {
            bulkSampleBottomSheet.dismiss();
            bulkSampleBottomSheet = null;
        }
    }

    public static void dismissDateBottomSheet() {
        if (dateBottomSheet != null) {
            dateBottomSheet.dismiss();
            dateBottomSheet = null;
        }
    }

    public static void dismissImageBottomSheet() {
        if (imageBottomSheet != null) {
            imageBottomSheet.dismiss();
            imageBottomSheet = null;
        }
    }

    public static void dismissLogoutBottomSheet() {
        if (logoutBottomSheet != null) {
            logoutBottomSheet.dismiss();
            logoutBottomSheet = null;
        }
    }

    public static void dismissTimeBottomSheet() {
        if (timeBottomSheet != null) {
            timeBottomSheet.dismiss();
            timeBottomSheet = null;
        }
    }

    public static void dismissVersionBottomSheet() {
        if (versionBottomSheet != null) {
            versionBottomSheet.dismiss();
            versionBottomSheet = null;
        }
    }

    /*
    ---------- Getter ----------
    */

    public static AuthBottomSheet getAuthBottomSheet() {
        if (authBottomSheet != null && authBottomSheet.isVisible())
            return authBottomSheet;

        return null;
    }

    public static BulkSampleBottomSheet getBulkSampleBottomSheet() {
        if (bulkSampleBottomSheet != null && bulkSampleBottomSheet.isVisible())
            return bulkSampleBottomSheet;

        return null;
    }

    public static DateBottomSheet getDateBottomSheet() {
        if (dateBottomSheet != null && dateBottomSheet.isVisible())
            return dateBottomSheet;

        return null;
    }

    public static ImageBottomSheet getImageBottomSheet() {
        if (imageBottomSheet != null && imageBottomSheet.isVisible())
            return imageBottomSheet;

        return null;
    }

    public static LogoutBottomSheet getLogoutBottomSheet() {
        if (logoutBottomSheet != null && logoutBottomSheet.isVisible())
            return logoutBottomSheet;

        return null;
    }

    public static TimeBottomSheet getTimeBottomSheet() {
        if (timeBottomSheet != null && timeBottomSheet.isVisible())
            return timeBottomSheet;

        return null;
    }

    public static VersionBottomSheet getVersionBottomSheet() {
        if (versionBottomSheet != null && versionBottomSheet.isVisible())
            return versionBottomSheet;

        return null;
    }

}