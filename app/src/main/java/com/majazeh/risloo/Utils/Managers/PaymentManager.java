package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PaymentManager {

    public static void request(Context context, String url, String authorization) {
        String uri = Uri.parse(url)
                .buildUpon()
                .appendQueryParameter("Authorization", authorization)
                .build().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);
    }

    public static void callback(Activity activity) {
        Uri data = activity.getIntent().getData();

        // TODO : Place Code Here
    }

}
