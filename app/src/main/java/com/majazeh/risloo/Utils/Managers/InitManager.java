package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;

public class InitManager {

    public static void imageView(Activity activity, ImageView img, int imgRes, int imgColor) {
        img.setImageResource(imgRes);
        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, imgColor));
    }

}
