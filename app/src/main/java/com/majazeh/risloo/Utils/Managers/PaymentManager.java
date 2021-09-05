package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.majazeh.risloo.R;

public class PaymentManager {

    public static void request(Activity activity, String url) {
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        } catch (ActivityNotFoundException e) {
            ToastManager.showDefaultToast(activity, activity.getResources().getString(R.string.ToastActivityException));
        }
        activity.startActivity(intent);
    }

    public static void callback(Activity activity) {
        Uri data = activity.getIntent().getData();

        if (data != null)
            Log.e("uri", data.toString());
    }

}
