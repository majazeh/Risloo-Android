package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class PaymentManager {

    public static void request(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    public static void callback(Activity activity) {
        Uri data = activity.getIntent().getData();

        if (data != null)
            Log.e("uri", data.toString());
    }

}
