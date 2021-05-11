package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.widget.Toast;

import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.mre.ligheh.API.onFailureException;

import java.util.HashMap;

public class ExtendOnFailureException extends onFailureException {
    public static Activity activity;

    public ExtendOnFailureException(Object object) {
        super(object);
    }

    @Override
    public void onValidation(HashMap<String, Object> map) {
        log("onValidation: " + map);
        String text = "";
        for (String s : map.keySet()) {
            text += map.get(s) + " ";
        }
        dismissLoading();
        show(text);
    }

    @Override
    public void onClient(String s) {
        log("onClient: " + s);
        dismissLoading();
        show(s);
    }

    @Override
    public void onServerFail(String s) {
        log("onServerFail: " + s);
        dismissLoading();
        show(s);
    }


    public void log(Object object) {
        System.out.println(object);
    }

    public void show(String error) {
        activity.runOnUiThread(() -> Toast.makeText(activity.getApplicationContext(), error, Toast.LENGTH_SHORT).show());
    }

    public void dismissLoading() {
        activity.runOnUiThread(() -> {
            if (activity instanceof AuthActivity) {
                if (((AuthActivity) activity).loadingDialog.isVisible())
                    ((AuthActivity) activity).loadingDialog.dismiss();
            } else if (activity instanceof MainActivity) {
                if (((AuthActivity) activity).loadingDialog.isVisible())
                    ((AuthActivity) activity).loadingDialog.dismiss();
            }
        });
    }

}
