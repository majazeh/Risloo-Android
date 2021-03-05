package com.majazeh.risloo.Utils.Widgets;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.DisplayMetrics;

import com.majazeh.risloo.R;

public class CustomizeDialog {

    public static Dialog bottomSheet(Activity activity, Dialog dialog) {
        DisplayMetrics metrics = new DisplayMetrics();
        dialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        GradientDrawable dimDrawable = new GradientDrawable();

        GradientDrawable navigationBarDrawable = new GradientDrawable();
        navigationBarDrawable.setShape(GradientDrawable.RECTANGLE);
        navigationBarDrawable.setColor(activity.getResources().getColor(R.color.White));

        Drawable[] layers = {dimDrawable, navigationBarDrawable};

        LayerDrawable windowBackground = new LayerDrawable(layers);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            windowBackground.setLayerInsetTop(1, metrics.heightPixels);
        }

        dialog.getWindow().setBackgroundDrawable(windowBackground);

        return dialog;
    }

}
