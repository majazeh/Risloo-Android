package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PaymentManager {

    public static void request(Context context, String url, String param1, String param2) {
        String uri = Uri.parse(url)
                .buildUpon()
                .appendQueryParameter("param1", param1)
                .appendQueryParameter("param2", param2)
                .build().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);
    }

    public static void callback(Activity activity) {
        Uri data = activity.getIntent().getData();

        String tmpData = data.getQueryParameter("data");
        String tmpStatus = data.getQueryParameter("status");
    }

}
