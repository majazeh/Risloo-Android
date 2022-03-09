package com.majazeh.risloo.utils.managers;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import androidx.core.content.FileProvider;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.views.activities.ActivityAuth;
import com.majazeh.risloo.views.activities.ActivityDisplay;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.activities.ActivityTest;

import java.io.File;

public class IntentManager {

    /*
    ---------- Activities ----------
    */

    public static void main(Activity activity) {
        Intent intent = new Intent(activity, ActivityMain.class);

        activity.startActivity(intent);
        activity.finish();
    }

    public static void auth(Activity activity, String theory) {
        Intent intent = new Intent(activity, ActivityAuth.class);
        intent.putExtra("theory", theory);

        activity.startActivity(intent);
        activity.finish();
    }

    public static void test(Activity activity, String id) {
        Intent intent = new Intent(activity, ActivityTest.class);
        intent.putExtra("id", id);

        activity.startActivity(intent);
    }

    public static void display(Activity activity, String title, String path) {
        Intent intent = new Intent(activity, ActivityDisplay.class);
        intent.putExtra("title", title);
        intent.putExtra("path", path);

        activity.startActivity(intent);
    }

    public static void finish(Activity activity) {
        activity.finish();
    }

    /*
    ---------- Local ----------
    */

    public static void internet(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    public static void phone(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.fromParts("tel", number, null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    public static void sms(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.fromParts("sms", number, null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    public static void email(Context context, String[] emails, String subject, String message, String chooser) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, emails);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("message/rfc822");

        context.startActivity(Intent.createChooser(intent, chooser));
    }

    public static void share(Context context, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");

        context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.AppChooserShare)));
    }

    public static void mediaScan(Activity activity, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);

        activity.sendBroadcast(intent);
    }

    public static void clipboard(Context context, String value) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("saved", value);
        manager.setPrimaryClip(clip);
    }

    public static void download(Activity activity, String title, String url) {
        Uri uri = Uri.parse(url);

        String file = url.substring(url.lastIndexOf('/') + 1);
        String folder = file.substring(0, file.indexOf("."));

        String path;

        if (file.startsWith("X1"))
            path = File.separator + "Risloo" + File.separator + title + " " + StringManager.bracing(folder) + File.separator + file;
        else
            path = File.separator + "Risloo" + File.separator + file;

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, path);
        request.allowScanningByMediaScanner();

        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public static void browser(Activity activity, String url) {
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        } catch (ActivityNotFoundException e) {
            ToastManager.showToastDefault(activity, activity.getResources().getString(R.string.ToastActivityPaymentException));
        }
        activity.startActivity(intent);
    }

    public static void file(Activity activity, File file) {
        Intent intent;

        try {
            Uri uri;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", file);
            else
                uri = Uri.fromFile(file);

            intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R.string.AppChooserFile)));
        } catch (ActivityNotFoundException e) {
            ToastManager.showToastDefault(activity, activity.getResources().getString(R.string.ToastActivityEmptyException));
        }
    }

    public static void risloo(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.majazeh.risloo");

        if (intent != null) {
            context.startActivity(intent);
        } else {
            googlePlay(context);
        }
    }

    /*
    ---------- Social ----------
    */

    public static void googlePlay(Context context) {
        Intent intent;

        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.majazeh.risloo"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + "com.majazeh.risloo"));
        }

        context.startActivity(intent);
    }

}