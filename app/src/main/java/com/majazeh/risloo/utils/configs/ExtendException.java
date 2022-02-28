package com.majazeh.risloo.utils.configs;

import android.app.Activity;

import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.ToastManager;

import com.mre.ligheh.API.Response;
import com.mre.ligheh.API.onFailureException;

import java.util.HashMap;

public class ExtendException extends onFailureException {

    // Objects
    public static Activity activity;

    /*
    ---------- Intialize ----------
    */

    public ExtendException(Response callback, Object object) {
        super(callback, object);
    }

    /*
    ---------- Funcs ----------
    */

    @Override
    public void onClient(String message) {
        logError("onClient: " + message);
        dismissDialog();
        snackMessage(message);
    }

    @Override
    public void onServerFail(String message) {
        logError("onServerFail: " + message);
        dismissDialog();
        snackMessage(message);
    }

    @Override
    public void onValidation(HashMap<String, Object> map) {
        logError("onValidation: " + map);
        dismissDialog();
    }

    /*
    ---------- Voids ----------
    */

    private void logError(Object object) {
        System.out.println(object);
    }

    private void snackMessage(String message) {
        if (!message.equals("POVERTY"))
            activity.runOnUiThread(() -> SnackManager.showDefaultSnack(activity, message));
    }

    private void toastMessage(String message) {
        activity.runOnUiThread(() -> ToastManager.showDefaultToast(activity, message));
    }

    private void dismissDialog() {
        activity.runOnUiThread(DialogManager::dismissDialogLoading);
    }

}