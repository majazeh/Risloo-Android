package com.majazeh.risloo.Utils.Managers;

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
    ---------- Funcs ----------
    */

    public static void fileResult(Activity activity, Intent data, TextView textView) {
        Uri uri = data.getData();

        path = PathManager.localPath(activity, uri);

        if (path != null && textView != null) {
            textView.setText(StringManager.substring(path, '/'));
        }
    }

    public static void galleryResult(Activity activity, Intent data) {
        Uri uri = data.getData();
        Bitmap bitmap = BitmapManager.uriToBitmap(activity, uri);

        path = PathManager.localPath(activity, uri);

        if (path != null && bitmap != null) {
            File f = FileManager.createInternalCachePath(activity, "image");
            Bitmap b = BitmapManager.modifyOrientation(bitmap, path);

            StreamManager.saveBitmapToStream(b, f);

            file = f;
            IntentManager.crop(activity, Uri.fromFile(file));
        }
    }

    public static void cameraResult(Activity activity, String selectedPath) {
        Uri uri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileprovider", new File(selectedPath));
        else
            uri = Uri.fromFile(file);

        Bitmap bitmap = BitmapManager.pathToBitmap(selectedPath);

        IntentManager.mediaScan(activity, uri);

        path = selectedPath;

        if (path != null && bitmap != null) {
            File f = FileManager.createInternalCachePath(activity, "image");
            Bitmap b = BitmapManager.modifyOrientation(bitmap, path);

            StreamManager.saveBitmapToStream(b, f);

            file = f;
            IntentManager.crop(activity, Uri.fromFile(file));
        }

    }

    public static void cropResult(Activity activity, Intent data, CircleImageView circleImageView, TextView textView) {
        Uri uri = UCrop.getOutput(data);
        Bitmap bitmap = BitmapManager.uriToBitmap(activity, uri);

        path = PathManager.localPath(activity, uri);

        if (path != null && bitmap != null) {
            File f = FileManager.createInternalCachePath(activity, "image");
            Bitmap b = BitmapManager.modifyOrientation(bitmap, path);

            StreamManager.saveBitmapToStream(b, f);

            file = f;
            circleImageView.setImageBitmap(BitmapManager.modifyOrientation(bitmap, path));

            if (textView != null) {
                textView.setVisibility(View.GONE);
            }
        }
    }

}