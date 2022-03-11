package com.majazeh.risloo.utils.managers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.majazeh.risloo.BuildConfig;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultManager {

    // Vars
    public static File file = null;
    public static String path = "";

    /*
    ---------- Result's ----------
    */

    public static void resultDocument(Activity activity, Intent data, TextView textView) {
        Uri uri = data.getData();

        path = PathManager.localPath(activity, uri);

        if (path != null && textView != null) {
            textView.setText(StringManager.suffix(path, '/'));
        }
    }

    public static void resultGallery(Activity activity, Intent data) {
        Uri uri = data.getData();
        Bitmap bitmap = BitmapManager.uriToBitmap(activity, uri);

        path = PathManager.localPath(activity, uri);

        if (path != null && bitmap != null) {
            File f = FileManager.createInternalCachePath(activity, "image");
            Bitmap b = BitmapManager.imageModify(bitmap, path);

            StreamManager.saveBitmapToStream(b, f);

            file = f;
            IntentManager.crop(activity, Uri.fromFile(file));
        }
    }

    public static void resultCamera(Activity activity, String selectedPath) {
        Uri uri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", new File(selectedPath));
        else
            uri = Uri.fromFile(file);

        Bitmap bitmap = BitmapManager.pathToBitmap(selectedPath);

        IntentManager.mediaScan(activity, uri);

        path = selectedPath;

        if (path != null && bitmap != null) {
            File f = FileManager.createInternalCachePath(activity, "image");
            Bitmap b = BitmapManager.imageModify(bitmap, path);

            StreamManager.saveBitmapToStream(b, f);

            file = f;
            IntentManager.crop(activity, Uri.fromFile(file));
        }

    }

    public static void resultCrop(Activity activity, Intent data, CircleImageView circleImageView, TextView textView) {
        Uri uri = UCrop.getOutput(data);
        Bitmap bitmap = BitmapManager.uriToBitmap(activity, uri);

        path = PathManager.localPath(activity, uri);

        if (path != null && bitmap != null) {
            File f = FileManager.createInternalCachePath(activity, "image");
            Bitmap b = BitmapManager.imageModify(bitmap, path);

            StreamManager.saveBitmapToStream(b, f);

            file = f;
            circleImageView.setImageBitmap(BitmapManager.imageModify(bitmap, path));

            if (textView != null) {
                textView.setVisibility(View.GONE);
            }
        }
    }

}