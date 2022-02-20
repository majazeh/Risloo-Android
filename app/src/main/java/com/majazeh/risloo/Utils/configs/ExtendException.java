package com.majazeh.risloo.Utils.configs;

import android.app.Activity;

import com.majazeh.risloo.Utils.managers.DialogManager;
import com.majazeh.risloo.Utils.managers.SnackManager;
import com.majazeh.risloo.Utils.managers.ToastManager;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.API.onFailureException;

import java.util.HashMap;

public class ExtendException extends onFailureException {

    public static Activity activity;

    public ExtendException(Response callback, Object object) {
        super(callback, object);
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
        activity.runOnUiThread(DialogManager::dismissLoadingDialog);
    }

}