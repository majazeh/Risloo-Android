package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.majazeh.risloo.Views.Dialogs.LoadingDialog;

public class DialogManager {

    private static LoadingDialog loadingDialog = null;

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

}