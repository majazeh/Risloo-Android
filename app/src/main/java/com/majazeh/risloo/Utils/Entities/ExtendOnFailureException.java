package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.widget.Toast;

import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.API.onFailureException;

import java.util.HashMap;

public class ExtendOnFailureException extends onFailureException {

    public static Activity activity;

    public ExtendOnFailureException(Response callback,Object object) {
        super(callback,object);
    }

//    @Override
//    public void onValidation(HashMap<String, Object> map) {
//        logError("onValidation: " + map);
//        String text = "";
//        for (String s : map.keySet()) {
//            text += map.get(s) + " ";
//        }
//        dismissDialog();
//        widgetError(text);
//    }

    @Override
    public void onClient(String s) {
        logError("onClient: " + s);
        dismissDialog();
        toastError(s);
    }

    @Override
    public void onServerFail(String s) {
        logError("onServerFail: " + s);
        dismissDialog();
        toastError(s);
    }

    public void logError(Object object) {
        System.out.println(object);
    }

    public void toastError(String message) {
        activity.runOnUiThread(() -> {
            Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        });
    }

    public void widgetError(String message) {
        activity.runOnUiThread(() -> {
            if (activity instanceof AuthActivity) {

            } else if (activity instanceof MainActivity) {

            }
        });
    }

    public void dismissDialog() {
        activity.runOnUiThread(() -> {
            if (activity instanceof AuthActivity) {
                if (((AuthActivity) activity).loadingDialog.isVisible())
                    ((AuthActivity) activity).loadingDialog.dismiss();
            } else if (activity instanceof MainActivity) {
                if (((MainActivity) activity).loadingDialog.isVisible())
                    ((MainActivity) activity).loadingDialog.dismiss();
            }
        });
    }

}