package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InitManager {

    public static void txtTextColor(TextView txt, String txtValue, int txtColor) {
        txt.setText(txtValue);
        txt.setTextColor(txtColor);
    }

    public static void imgResTint(Activity activity, ImageView img, int imgRes, int imgColor) {
        img.setImageResource(imgRes);
        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, imgColor));
    }

    public static void imgResTintRotate(Activity activity, ImageView img, int imgRes, int imgColor, int degree) {
        img.setImageResource(imgRes);
        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, imgColor));
        img.setRotation(img.getRotation() + degree);
    }

    public static void recyclerView(RecyclerView recyclerView, RecyclerView.ItemDecoration itemDecoration, LinearLayoutManager layoutManager) {
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
    }

}