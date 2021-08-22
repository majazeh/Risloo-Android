package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultManager {

    // Vars
    public static Bitmap bitmap = null;
    public static String filePath = "";

    /*
    ---------- Funcs ----------
    */

    public static void fileResult(Activity activity, Intent data, TextView textView) {
        Uri fileUri = data.getData();

        filePath = PathManager.localPath(activity, fileUri);

        if (filePath != null && textView != null)
            textView.setText(StringManager.substring(filePath, '/'));
    }

    public static void galleryResult(Activity activity, Intent data, CircleImageView circleImageView, TextView textView) {
        try {
            Uri imageUri = data.getData();
            InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
            Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);

            filePath = PathManager.localPath(activity, imageUri);
            bitmap = BitmapManager.scaleToCenter(imageBitmap);

            if (filePath != null && bitmap != null)
                circleImageView.setImageBitmap(BitmapManager.modifyOrientation(bitmap, filePath));

            if (textView != null)
                textView.setVisibility(View.GONE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void cameraResult(Activity activity, String path, CircleImageView circleImageView, TextView textView) {
        File imageFile = new File(path);
        IntentManager.mediaScan(activity, imageFile);

        int scaleFactor = Math.max(1, 2);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        Bitmap imageBitmap = BitmapFactory.decodeFile(path, options);

        filePath = path;
        bitmap = BitmapManager.scaleToCenter(imageBitmap);

        if (filePath != null && bitmap != null)
            circleImageView.setImageBitmap(BitmapManager.modifyOrientation(bitmap, filePath));

        if (textView != null)
            textView.setVisibility(View.GONE);
    }

}