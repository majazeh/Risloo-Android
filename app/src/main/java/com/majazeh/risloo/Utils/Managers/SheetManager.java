package com.majazeh.risloo.utils.managers;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.majazeh.risloo.views.sheets.SheetAuth;
import com.majazeh.risloo.views.sheets.SheetBulkSample;
import com.majazeh.risloo.views.sheets.SheetDate;
import com.majazeh.risloo.views.sheets.SheetImage;
import com.majazeh.risloo.views.sheets.SheetLogout;
import com.majazeh.risloo.views.sheets.SheetTime;
import com.majazeh.risloo.views.sheets.SheetVersion;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.ClientModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

public class SheetManager {

    // Widgets
    private static SheetAuth sheetAuth = null;
    private static SheetBulkSample sheetBulkSample = null;
    private static SheetDate sheetDate = null;
    private static SheetImage sheetImage = null;
    private static SheetLogout sheetLogout = null;
    private static SheetTime sheetTime = null;
    private static SheetVersion sheetVersion = null;

    /*
    ---------- Show ----------
    */

    public static void showAuthBottomSheet(Activity activity, String key, UserModel userModel) {
        sheetAuth = new SheetAuth();
        sheetAuth.show(((FragmentActivity) activity).getSupportFragmentManager(), "authBottomSheet");
        sheetAuth.setData(key, userModel);
    }

    public static void showBulkSampleBottomSheet(Activity activity, String key, UserModel userModel, BulkSampleModel bulkSampleModel) {
        sheetBulkSample = new SheetBulkSample();
        sheetBulkSample.show(((FragmentActivity) activity).getSupportFragmentManager(), "bulkSampleBottomSheet");
        sheetBulkSample.setData(key, userModel, bulkSampleModel);
    }

    public static void showDateBottomSheet(Activity activity, String timestamp, String method) {
        sheetDate = new SheetDate();
        sheetDate.show(((FragmentActivity) activity).getSupportFragmentManager(), "dateBottomSheet");
        sheetDate.setDate(timestamp, method);
    }

    public static void showImageBottomSheet(Activity activity) {
        sheetImage = new SheetImage();
        sheetImage.show(((FragmentActivity) activity).getSupportFragmentManager(), "imageBottomSheet");
    }

    public static void showLogoutBottomSheet(Activity activity, UserModel userModel) {
        sheetLogout = new SheetLogout();
        sheetLogout.show(((AppCompatActivity) activity).getSupportFragmentManager(), "logoutBottomSheet");
        sheetLogout.setData(userModel);
    }

    public static void showTimeBottomSheet(Activity activity, String timestamp, String method) {
        sheetTime = new SheetTime();
        sheetTime.show(((FragmentActivity) activity).getSupportFragmentManager(), "timeBottomSheet");
        sheetTime.setTime(timestamp, method);
    }

    public static void showVersionBottomSheet(Activity activity, ClientModel clientModel, String method) {
        sheetVersion = new SheetVersion();
        sheetVersion.show(((FragmentActivity) activity).getSupportFragmentManager(), "versionBottomSheet");
        sheetVersion.setData(clientModel, method);
    }

    /*
    ---------- Dismiss ----------
    */

    public static void dismissAuthBottomSheet() {
        if (sheetAuth != null) {
            sheetAuth.dismiss();
            sheetAuth = null;
        }
    }

    public static void dismissBulkSampleBottomSheet() {
        if (sheetBulkSample != null) {
            sheetBulkSample.dismiss();
            sheetBulkSample = null;
        }
    }

    public static void dismissDateBottomSheet() {
        if (sheetDate != null) {
            sheetDate.dismiss();
            sheetDate = null;
        }
    }

    public static void dismissImageBottomSheet() {
        if (sheetImage != null) {
            sheetImage.dismiss();
            sheetImage = null;
        }
    }

    public static void dismissLogoutBottomSheet() {
        if (sheetLogout != null) {
            sheetLogout.dismiss();
            sheetLogout = null;
        }
    }

    public static void dismissTimeBottomSheet() {
        if (sheetTime != null) {
            sheetTime.dismiss();
            sheetTime = null;
        }
    }

    public static void dismissVersionBottomSheet() {
        if (sheetVersion != null) {
            sheetVersion.dismiss();
            sheetVersion = null;
        }
    }

    /*
    ---------- Getter ----------
    */

    public static SheetAuth getAuthBottomSheet() {
        if (sheetAuth != null && sheetAuth.isVisible())
            return sheetAuth;

        return null;
    }

    public static SheetBulkSample getBulkSampleBottomSheet() {
        if (sheetBulkSample != null && sheetBulkSample.isVisible())
            return sheetBulkSample;

        return null;
    }

    public static SheetDate getDateBottomSheet() {
        if (sheetDate != null && sheetDate.isVisible())
            return sheetDate;

        return null;
    }

    public static SheetImage getImageBottomSheet() {
        if (sheetImage != null && sheetImage.isVisible())
            return sheetImage;

        return null;
    }

    public static SheetLogout getLogoutBottomSheet() {
        if (sheetLogout != null && sheetLogout.isVisible())
            return sheetLogout;

        return null;
    }

    public static SheetTime getTimeBottomSheet() {
        if (sheetTime != null && sheetTime.isVisible())
            return sheetTime;

        return null;
    }

    public static SheetVersion getVersionBottomSheet() {
        if (sheetVersion != null && sheetVersion.isVisible())
            return sheetVersion;

        return null;
    }

}