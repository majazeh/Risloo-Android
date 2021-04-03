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

    public static void fileResult(Activity activity, Intent data, String path, TextView textView) {
        Uri fileUri = data.getData();

        path = PathManager.localPath(activity, fileUri);

        if (textView != null) {
            textView.setText(StringManager.substring(path, '/'));
        }
    }

    public static void galleryResult(Activity activity, Intent data, String path, Bitmap bitmap, CircleImageView circleImageView, TextView textView) {
        try {
            Uri imageUri = data.getData();
            InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
            Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);

            path = PathManager.localPath(activity, imageUri);
            bitmap = BitmapManager.scaleToCenter(imageBitmap);

            circleImageView.setImageBitmap(BitmapManager.modifyOrientation(bitmap, path));
            if (textView != null) {
                textView.setVisibility(View.GONE);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void cameraResult(Activity activity, String path, Bitmap bitmap, CircleImageView circleImageView, TextView textView) {
        File imageFile = new File(path);
        IntentManager.mediaScan(activity, imageFile);

        int scaleFactor = Math.max(1, 2);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        Bitmap imageBitmap = BitmapFactory.decodeFile(path, options);

        bitmap = BitmapManager.scaleToCenter(imageBitmap);

        circleImageView.setImageBitmap(BitmapManager.modifyOrientation(bitmap, path));
        if (textView != null) {
            textView.setVisibility(View.GONE);
        }
    }

}