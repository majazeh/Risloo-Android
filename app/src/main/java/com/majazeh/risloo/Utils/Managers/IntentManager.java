package com.majazeh.risloo.Utils.Managers;

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
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.core.content.FileProvider;

import com.majazeh.risloo.BuildConfig;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.Views.Activities.DisplayActivity;
import com.majazeh.risloo.Views.Activities.IntroActivity;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class IntentManager {

    /*
    ---------- Activities ----------
    */

    public static void intro(Activity activity) {
        Intent intent = new Intent(activity, IntroActivity.class);

        activity.startActivity(intent);
        activity.finish();
    }

    public static void main(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);

        activity.startActivity(intent);
        activity.finish();
    }

    public static void auth(Activity activity, String theory) {
        Intent intent = new Intent(activity, AuthActivity.class);
        intent.putExtra("theory", theory);

        activity.startActivity(intent);
        activity.finish();
    }

    public static void test(Activity activity, String id) {
        Intent intent = new Intent(activity, TestActivity.class);
        intent.putExtra("id", id);

        activity.startActivity(intent);
    }

    public static void display(Activity activity, String title, String path) {
        Intent intent = new Intent(activity, DisplayActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("path", path);

        activity.startActivity(intent);
    }

    public static void finish(Activity activity) {
        activity.finish();
    }

    /*
    ---------- Requests ----------
    */

    public static void file(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("*/*");

        activity.startActivityForResult(intent, 100);
    }

    public static void sendTo(Activity activity, String number, String name, String value) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + number));
        intent.putExtra(name, value);

        activity.startActivityForResult(intent, 200);
    }

    public static void gallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        activity.startActivityForResult(intent, 300);
    }

    public static String camera(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = FileManager.createImageFile(activity);
        if (file != null) {
            Uri uri;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileprovider", file);
            else
                uri = Uri.fromFile(file);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            activity.startActivityForResult(intent, 400);

            return file.getAbsolutePath();
        }

        return "";
    }

    public static void crop(Activity activity, Uri uri) {
        UCrop.of(uri, uri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(512, 512)
                .start(activity);
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

    public static void share(Context context, String content, String chooser) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");

        context.startActivity(Intent.createChooser(intent, chooser));
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

    public static void download(Context context, String url) {
        String savePath;
        String fullName = url.substring(url.lastIndexOf('/') + 1);
        String subName = fullName.substring(0, fullName.indexOf("."));

        if (fullName.startsWith("X1")) {
            savePath = File.separator + "Risloo" + File.separator + subName + File.separator + fullName;
            FileManager.createExternalFile(File.separator + "Risloo" + File.separator + subName);
        } else {
            savePath = File.separator + "Risloo" + File.separator + fullName;
            FileManager.createExternalFile(File.separator + "Risloo");
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, savePath);

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public static void browser(Activity activity, String url) {
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        } catch (ActivityNotFoundException e) {
            ToastManager.showDefaultToast(activity, activity.getResources().getString(R.string.ToastActivityException));
        }
        activity.startActivity(intent);
    }

    /*
    ---------- Social ----------
    */

    public static void googlePlay(Context context) {
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName()));
        }
        context.startActivity(intent);
    }

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
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/" + instagramID));
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