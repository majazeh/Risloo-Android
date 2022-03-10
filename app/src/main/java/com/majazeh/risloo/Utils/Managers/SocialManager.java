package com.majazeh.risloo.utils.managers;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SocialManager {

    /*
    ---------- Func's ----------
    */

    public static void facebook(Context context, String facebookID) {
        Intent intent;

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);

            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + facebookID));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + facebookID));
        }

        context.startActivity(intent);
    }

    public static void twitter(Context context, String twitterID) {
        Intent intent;

        try {
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);

            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=" + twitterID));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + twitterID));
        }

        context.startActivity(intent);
    }

    public static void telegram(Context context, String telegramID) {
        Intent intent;

        try {
            context.getPackageManager().getPackageInfo("org.telegram.messenger", 0);

            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=" + telegramID));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/" + telegramID));
        }

        context.startActivity(intent);
    }

    public static void instagram(Context context, String instagramID) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/" + instagramID));

        intent.setPackage("com.instagram.android");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/" + instagramID));
            context.startActivity(intent);
        }
    }

}