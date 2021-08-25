package com.majazeh.risloo.Utils.Config;

import android.app.Activity;

import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.API.onFailureException;

import java.util.HashMap;

public class ExtendException extends onFailureException {

    public static Activity activity;

    public ExtendException(Response callback, Object object) {
        super(callback,object);
    }

    @Override
    public void onClient(String s) {
        logError("onClient: " + s);
        dismissDialog();
        snackMessage(s);
    }

    @Override
    public void onServerFail(String s) {
        logError("onServerFail: " + s);
        dismissDialog();
        snackMessage(s);
    }

    @Override
    public void onValidation(HashMap<String, Object> map) {
        logError("onValidation: " + map);
        dismissDialog();
    }

    public void logError(Object object) {
        System.out.println(object);
    }

    public void snackMessage(String message) {
        activity.runOnUiThread(() -> SnackManager.showDefaultSnack(activity, message));
    }

    public void toastMessage(String message) {
        activity.runOnUiThread(() -> ToastManager.showDefaultToast(activity, message));
    }

    public void dismissDialog() {
        activity.runOnUiThread(DialogManager::dismissLoadingDialog);
    }

}