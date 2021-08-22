package com.majazeh.risloo.Utils.Config;

import android.app.Activity;

import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.Views.Activities.MainActivity;
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
        snackError(s);
    }

    @Override
    public void onServerFail(String s) {
        logError("onServerFail: " + s);
        dismissDialog();
        snackError(s);
    }

    @Override
    public void onValidation(HashMap<String, Object> map) {
        logError("onValidation: " + map);
        dismissDialog();
    }

    public void logError(Object object) {
        System.out.println(object);
    }

    public void snackError(String message) {
        activity.runOnUiThread(() -> SnackManager.showDefaultSnack(activity, message));
    }

    public void dismissDialog() {
        activity.runOnUiThread(() -> {
            if (activity instanceof AuthActivity) {
                if (((AuthActivity) activity).loadingDialog != null && ((AuthActivity) activity).loadingDialog.isVisible()) {
                    ((AuthActivity) activity).loadingDialog.dismiss();
                }
            } else if (activity instanceof MainActivity) {
                if (((MainActivity) activity).loadingDialog != null && ((MainActivity) activity).loadingDialog.isVisible()) {
                    ((MainActivity) activity).loadingDialog.dismiss();
                }
            }
        });
    }

}