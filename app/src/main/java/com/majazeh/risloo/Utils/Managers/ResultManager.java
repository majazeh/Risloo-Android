package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultManager {

    // Vars
    public static Bitmap bitmap = null;
    public static String path = "";

    /*
    ---------- Funcs ----------
    */

    public static void fileResult(Activity activity, Intent data, TextView textView) {
        Uri fileUri = data.getData();

        path = PathManager.localPath(activity, fileUri);

        if (path != null && textView != null)
            textView.setText(StringManager.substring(path, '/'));
    }

    public static void galleryResult(Activity activity, Intent data) {
        try {
            Uri imageUri = data.getData();
            InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
            Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);

            path = PathManager.localPath(activity, imageUri);
            bitmap = BitmapManager.scaleToCenter(imageBitmap);

            if (path != null && bitmap != null)
                IntentManager.crop(activity, imageUri);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void cameraResult(Activity activity, String selectedPath) {
        File imageFile = new File(selectedPath);
        IntentManager.mediaScan(activity, Uri.fromFile(imageFile));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = Math.max(1, 2);

        Bitmap imageBitmap = BitmapFactory.decodeFile(selectedPath, options);

        path = selectedPath;
        bitmap = BitmapManager.scaleToCenter(imageBitmap);

        if (ResultManager.path != null && bitmap != null)
            IntentManager.crop(activity, Uri.fromFile(imageFile));
    }

    public static void cropResult(Intent data, CircleImageView circleImageView, TextView textView) {
        Bundle extras = data.getExtras();

        bitmap = extras.getParcelable("data");

        if (path != null && bitmap != null)
            circleImageView.setImageBitmap(BitmapManager.modifyOrientation(bitmap, path));

        if (textView != null)
            textView.setVisibility(View.GONE);
    }

}