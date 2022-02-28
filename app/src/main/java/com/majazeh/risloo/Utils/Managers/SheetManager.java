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

    public static void showSheetAuth(Activity activity, String key, UserModel userModel) {
        sheetAuth = new SheetAuth();

        if (activity instanceof AppCompatActivity)
            sheetAuth.show(((AppCompatActivity) activity).getSupportFragmentManager(), "sheetAuth");
        else if (activity instanceof FragmentActivity)
            sheetAuth.show(((FragmentActivity) activity).getSupportFragmentManager(), "sheetAuth");

        sheetAuth.setData(key, userModel);
    }

    public static void showSheetBulkSample(Activity activity, String key, UserModel userModel, BulkSampleModel bulkSampleModel) {
        sheetBulkSample = new SheetBulkSample();

        if (activity instanceof AppCompatActivity)
            sheetBulkSample.show(((AppCompatActivity) activity).getSupportFragmentManager(), "sheetBulkSample");
        else if (activity instanceof FragmentActivity)
            sheetBulkSample.show(((FragmentActivity) activity).getSupportFragmentManager(), "sheetBulkSample");

        sheetBulkSample.setData(key, userModel, bulkSampleModel);
    }

    public static void showSheetDate(Activity activity, String timestamp, String method) {
        sheetDate = new SheetDate();

        if (activity instanceof AppCompatActivity)
            sheetDate.show(((AppCompatActivity) activity).getSupportFragmentManager(), "sheetDate");
        else if (activity instanceof FragmentActivity)
            sheetDate.show(((FragmentActivity) activity).getSupportFragmentManager(), "sheetDate");

        sheetDate.setDate(method, timestamp);
    }

    public static void showSheetImage(Activity activity) {
        sheetImage = new SheetImage();

        if (activity instanceof AppCompatActivity)
            sheetImage.show(((AppCompatActivity) activity).getSupportFragmentManager(), "sheetImage");
        else if (activity instanceof FragmentActivity)
            sheetImage.show(((FragmentActivity) activity).getSupportFragmentManager(), "sheetImage");
    }

    public static void showSheetLogout(Activity activity, UserModel userModel) {
        sheetLogout = new SheetLogout();

        if (activity instanceof AppCompatActivity)
            sheetLogout.show(((AppCompatActivity) activity).getSupportFragmentManager(), "sheetLogout");
        else if (activity instanceof FragmentActivity)
            sheetLogout.show(((FragmentActivity) activity).getSupportFragmentManager(), "sheetLogout");

        sheetLogout.setData(userModel);
    }

    public static void showSheetTime(Activity activity, String timestamp, String method) {
        sheetTime = new SheetTime();

        if (activity instanceof AppCompatActivity)
            sheetTime.show(((AppCompatActivity) activity).getSupportFragmentManager(), "sheetTime");
        else if (activity instanceof FragmentActivity)
            sheetTime.show(((FragmentActivity) activity).getSupportFragmentManager(), "sheetTime");

        sheetTime.setTime(method, timestamp);
    }

    public static void showSheetVersion(Activity activity, ClientModel clientModel, String method) {
        sheetVersion = new SheetVersion();

        if (activity instanceof AppCompatActivity)
            sheetVersion.show(((AppCompatActivity) activity).getSupportFragmentManager(), "sheetVersion");
        else if (activity instanceof FragmentActivity)
            sheetVersion.show(((FragmentActivity) activity).getSupportFragmentManager(), "sheetVersion");

        sheetVersion.setData(method, clientModel);
    }

    /*
    ---------- Dismiss ----------
    */

    public static void dismissSheetAuth() {
        if (sheetAuth != null) {
            sheetAuth.dismiss();
            sheetAuth = null;
        }
    }

    public static void dismissSheetBulkSample() {
        if (sheetBulkSample != null) {
            sheetBulkSample.dismiss();
            sheetBulkSample = null;
        }
    }

    public static void dismissSheetDate() {
        if (sheetDate != null) {
            sheetDate.dismiss();
            sheetDate = null;
        }
    }

    public static void dismissSheetImage() {
        if (sheetImage != null) {
            sheetImage.dismiss();
            sheetImage = null;
        }
    }

    public static void dismissSheetLogout() {
        if (sheetLogout != null) {
            sheetLogout.dismiss();
            sheetLogout = null;
        }
    }

    public static void dismissSheetTime() {
        if (sheetTime != null) {
            sheetTime.dismiss();
            sheetTime = null;
        }
    }

    public static void dismissSheetVersion() {
        if (sheetVersion != null) {
            sheetVersion.dismiss();
            sheetVersion = null;
        }
    }

    /*
    ---------- Getter ----------
    */

    public static SheetAuth getSheetAuth() {
        if (sheetAuth != null && sheetAuth.isVisible())
            return sheetAuth;

        return null;
    }

    public static SheetBulkSample getSheetBulkSample() {
        if (sheetBulkSample != null && sheetBulkSample.isVisible())
            return sheetBulkSample;

        return null;
    }

    public static SheetDate getSheetDate() {
        if (sheetDate != null && sheetDate.isVisible())
            return sheetDate;

        return null;
    }

    public static SheetImage getSheetImage() {
        if (sheetImage != null && sheetImage.isVisible())
            return sheetImage;

        return null;
    }

    public static SheetLogout getSheetLogout() {
        if (sheetLogout != null && sheetLogout.isVisible())
            return sheetLogout;

        return null;
    }

    public static SheetTime getSheetTime() {
        if (sheetTime != null && sheetTime.isVisible())
            return sheetTime;

        return null;
    }

    public static SheetVersion getSheetVersion() {
        if (sheetVersion != null && sheetVersion.isVisible())
            return sheetVersion;

        return null;
    }

}