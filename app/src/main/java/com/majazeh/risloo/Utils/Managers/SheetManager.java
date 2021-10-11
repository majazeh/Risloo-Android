package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.majazeh.risloo.Views.BottomSheets.AuthBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.BulkSampleBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.ImageBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.LogoutBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.TimeBottomSheet;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

public class SheetManager {

    // Widgets
    private static AuthBottomSheet authBottomSheet = null;
    private static BulkSampleBottomSheet bulkSampleBottomSheet = null;
    private static DateBottomSheet dateBottomSheet = null;
    private static ImageBottomSheet imageBottomSheet = null;
    private static LogoutBottomSheet logoutBottomSheet = null;
    private static TimeBottomSheet timeBottomSheet = null;

    /*
    ---------- Show ----------
    */

    public static void showAuthBottomSheet(Activity activity, String key, UserModel userModel) {
        authBottomSheet = new AuthBottomSheet();
        authBottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "authBottomSheet");
        authBottomSheet.setData(key, userModel);
    }

    public static void showBulkSampleBottomSheet(Activity activity, String key, String name, String avatar, BulkSampleModel model) {
        bulkSampleBottomSheet = new BulkSampleBottomSheet();
        bulkSampleBottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "bulkSampleBottomSheet");
        bulkSampleBottomSheet.setData(key, name, avatar, model);
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

    /*
    ---------- Dismiss ----------
    */

    public static void dismissAuthBottomSheet() {
        if (authBottomSheet != null && authBottomSheet.isVisible())
            authBottomSheet.dismiss();
    }

    public static void dismissBulkSampleBottomSheet() {
        if (bulkSampleBottomSheet != null && bulkSampleBottomSheet.isVisible())
            bulkSampleBottomSheet.dismiss();
    }

    public static void dismissDateBottomSheet() {
        if (dateBottomSheet != null && dateBottomSheet.isVisible())
            dateBottomSheet.dismiss();
    }

    public static void dismissImageBottomSheet() {
        if (imageBottomSheet != null && imageBottomSheet.isVisible())
            imageBottomSheet.dismiss();
    }

    public static void dismissLogoutBottomSheet() {
        if (logoutBottomSheet != null && logoutBottomSheet.isVisible())
            logoutBottomSheet.dismiss();
    }

    public static void dismissTimeBottomSheet() {
        if (timeBottomSheet != null && timeBottomSheet.isVisible())
            timeBottomSheet.dismiss();
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

}