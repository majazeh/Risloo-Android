package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.majazeh.risloo.Views.Dialogs.LoadingDialog;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;

public class DialogManager {

    private static LoadingDialog loadingDialog = null;
    private static SearchableDialog searchableDialog = null;

    /*
    ---------- Funcs ----------
    */

    public static void showLoadingDialog(Activity activity) {
        loadingDialog = new LoadingDialog();
        if (activity instanceof AppCompatActivity)
            loadingDialog.show(((AppCompatActivity) activity).getSupportFragmentManager(), "loadingDialog");
        else if (activity instanceof FragmentActivity)
            loadingDialog.show(((FragmentActivity) activity).getSupportFragmentManager(), "loadingDialog");
    }

    public static void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isVisible())
            loadingDialog.dismiss();
    }

    public static void showSearchableDialog(Activity activity, String method) {
        searchableDialog = new SearchableDialog();
        if (activity instanceof AppCompatActivity)
            searchableDialog.show(((AppCompatActivity) activity).getSupportFragmentManager(), "searchableDialog");
        else if (activity instanceof FragmentActivity)
            searchableDialog.show(((FragmentActivity) activity).getSupportFragmentManager(), "searchableDialog");
        searchableDialog.setData(method);
    }

    public static void dismissSearchableDialog() {
        if (searchableDialog != null && searchableDialog.isVisible())
            searchableDialog.dismiss();
    }

}