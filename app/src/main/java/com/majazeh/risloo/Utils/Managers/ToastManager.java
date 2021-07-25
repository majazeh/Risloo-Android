package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.widget.Toast;

public class ToastManager {

    private static Toast toast;

    public static void showToast(Activity activity, String value) {
        try {
            toast.getView().isShown();
            toast.setText(value);
        } catch (Exception e) {
            toast = Toast.makeText(activity, value, Toast.LENGTH_SHORT);
        }

        toast.show();
    }

}