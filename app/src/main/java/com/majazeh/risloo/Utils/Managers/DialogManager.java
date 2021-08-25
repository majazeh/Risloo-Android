package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.majazeh.risloo.Views.Dialogs.LoadingDialog;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.Views.Dialogs.SelectedDialog;

public class DialogManager {

    // Widgets
    private static LoadingDialog loadingDialog = null;
    private static SearchableDialog searchableDialog = null;
    private static SelectedDialog selectedDialog = null;

    /*
    ---------- Show ----------
    */

    public static void showLoadingDialog(Activity activity) {
        loadingDialog = new LoadingDialog();
        if (activity instanceof AppCompatActivity)
            loadingDialog.show(((AppCompatActivity) activity).getSupportFragmentManager(), "loadingDialog");
        else if (activity instanceof FragmentActivity)
            loadingDialog.show(((FragmentActivity) activity).getSupportFragmentManager(), "loadingDialog");
    }

    public static void showSearchableDialog(Activity activity, String method) {
        searchableDialog = new SearchableDialog();
        if (activity instanceof AppCompatActivity)
            searchableDialog.show(((AppCompatActivity) activity).getSupportFragmentManager(), "searchableDialog");
        else if (activity instanceof FragmentActivity)
            searchableDialog.show(((FragmentActivity) activity).getSupportFragmentManager(), "searchableDialog");
        searchableDialog.setData(method);
    }

    public static void showSelectedDialog(Activity activity, String method) {
        selectedDialog = new SelectedDialog();
        if (activity instanceof AppCompatActivity)
            selectedDialog.show(((AppCompatActivity) activity).getSupportFragmentManager(), "selectedDialog");
        else if (activity instanceof FragmentActivity)
            selectedDialog.show(((FragmentActivity) activity).getSupportFragmentManager(), "selectedDialog");
        selectedDialog.setData(method);
    }

    /*
    ---------- Dismiss ----------
    */

    public static void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isVisible())
            loadingDialog.dismiss();
    }

    public static void dismissSearchableDialog() {
        if (searchableDialog != null && searchableDialog.isVisible())
            searchableDialog.dismiss();
    }

    public static void dismissSelectedDialog() {
        if (selectedDialog != null && selectedDialog.isVisible())
            selectedDialog.dismiss();
    }

    /*
    ---------- Getter ----------
    */

    public static LoadingDialog getLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isVisible())
            return loadingDialog;

        return null;
    }

    public static SearchableDialog getSearchableDialog() {
        if (searchableDialog != null && searchableDialog.isVisible())
            return searchableDialog;

        return null;
    }

    public static SelectedDialog getSelectedDialog() {
        if (selectedDialog != null && selectedDialog.isVisible())
            return selectedDialog;

        return null;
    }

}