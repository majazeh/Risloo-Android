package com.majazeh.risloo.utils.managers;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.widgets.RecyclerViewDecoration;

public class InitManager {

    /*
    ---------- TextView's ----------
    */

    // -------------------- Two

    public static void txtTextColor(TextView txt, String value, int color) {
        txt.setText(value);
        txt.setTextColor(color);
    }

    public static void txtColorBackground(TextView txt, int color, int background) {
        txt.setTextColor(color);
        txt.setBackgroundResource(background);
    }

    public static void txtTextAppearance(Activity activity, TextView txt, String value, int style) {
        txt.setText(value);
        txt.setTextAppearance(activity, style);
    }

    public static void txtColorAppearance(Activity activity, TextView txt, int color, int style) {
        txt.setTextColor(color);
        txt.setTextAppearance(activity, style);
    }

    // -------------------- Three

    public static void txtTextColorBackground(TextView txt, String value, int color, int background) {
        txt.setText(value);
        txt.setTextColor(color);
        txt.setBackgroundResource(background);
    }

    /*
    ---------- ImageView's ----------
    */

    // -------------------- Two

    public static void imgResTint(Activity activity, ImageView img, int res, int tint) {
        img.setImageResource(res);
        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, tint));
    }

    // -------------------- Three

    public static void imgResTintBackground(Activity activity, ImageView img, int res, int tint, int background) {
        img.setImageResource(res);
        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, tint));
        img.setBackgroundResource(background);
    }

    public static void imgResTintTag(Activity activity, ImageView img, int res, int tint, String tag) {
        img.setImageResource(res);
        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, tint));
        img.setTag(tag);
    }

    // -------------------- Four

    public static void imgResTintBackgroundRotate(Activity activity, ImageView img, int res, int tint, int background, int degree) {
        img.setImageResource(res);
        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, tint));
        img.setBackgroundResource(background);
        img.setRotation(img.getRotation() + degree);
    }

    /*
    ---------- Layout's ----------
    */

    public static void lytTextColorResTintBackground(Activity activity, LinearLayout lyt, TextView txt, ImageView img, String txtValue, int txtColor, int imgRes, int imgTint, int lytBackground) {
        txt.setText(txtValue);
        txt.setTextColor(txtColor);

        img.setImageResource(imgRes);
        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, imgTint));

        lyt.setBackgroundResource(lytBackground);
    }

    /*
    ---------- Spinner's ----------
    */

    // -------------------- Rectangle

    public static void spRectDropdown(Activity activity, Spinner sp, TextView txt, ImageView img, String txtValue, int txtColor, int imgTint, int spBackground) {
        txt.setText(txtValue);
        txt.setTextColor(txtColor);

        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, imgTint));

        sp.setBackgroundResource(spBackground);
    }

    // -------------------- Oval

    public static void spOvalDropdown(Activity activity, Spinner sp, ImageView img, int imgRes, int imgTint, int spBackground) {
        img.setImageResource(imgRes);
        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, imgTint));
        img.setPadding((int) activity.getResources().getDimension(R.dimen._6sdp), (int) activity.getResources().getDimension(R.dimen._6sdp), (int) activity.getResources().getDimension(R.dimen._6sdp), (int) activity.getResources().getDimension(R.dimen._6sdp));

        img.setClickable(false);
        img.setBackgroundResource(0);

        sp.setEnabled(true);
        sp.setBackgroundResource(spBackground);
    }

    public static void spOvalSingle(Activity activity, Spinner sp, ImageView img, int imgRes, int imgTint, int imgBackground, String imgTag) {
        img.setImageResource(imgRes);
        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, imgTint));
        img.setPadding((int) activity.getResources().getDimension(R.dimen._7sdp), (int) activity.getResources().getDimension(R.dimen._7sdp), (int) activity.getResources().getDimension(R.dimen._7sdp), (int) activity.getResources().getDimension(R.dimen._7sdp));
        img.setTag(imgTag);

        img.setClickable(true);
        img.setBackgroundResource(imgBackground);

        sp.setEnabled(false);
        sp.setBackgroundResource(0);
    }

    /*
    ---------- RecyclerView's ----------
    */

    // -------------------- GridLayoutManager

    public static void rcvGridFixed(Activity activity, RecyclerView rcv, float marginTop, float marginBottom, float marginInner, float marginSide) {
        rcv.addItemDecoration(new RecyclerViewDecoration("gridLayout", (int) marginTop, (int) marginBottom, (int) marginInner, (int) marginSide));
        rcv.setLayoutManager(new GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false));
        rcv.setNestedScrollingEnabled(false);
        rcv.setHasFixedSize(true);
        rcv.setItemAnimator(null);
    }

    public static void rcvGridUnfixed(Activity activity, RecyclerView rcv, float marginTop, float marginBottom, float marginInner, float marginSide) {
        rcv.addItemDecoration(new RecyclerViewDecoration("gridLayout", (int) marginTop, (int) marginBottom, (int) marginInner, (int) marginSide));
        rcv.setLayoutManager(new GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false));
        rcv.setNestedScrollingEnabled(false);
        rcv.setHasFixedSize(false);
        rcv.setItemAnimator(null);
    }

    // -------------------- LinearLayoutManager - Horizontal

    public static void rcvHorizontalFixed(Activity activity, RecyclerView rcv, float marginTop, float marginBottom, float marginInner, float marginSide) {
        rcv.addItemDecoration(new RecyclerViewDecoration("horizontalLayout", (int) marginTop, (int) marginBottom, (int) marginInner, (int) marginSide));
        rcv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        rcv.setNestedScrollingEnabled(false);
        rcv.setHasFixedSize(true);
        rcv.setItemAnimator(null);
    }

    public static void rcvHorizontalUnfixed(Activity activity, RecyclerView rcv, float marginTop, float marginBottom, float marginInner, float marginSide) {
        rcv.addItemDecoration(new RecyclerViewDecoration("horizontalLayout", (int) marginTop, (int) marginBottom, (int) marginInner, (int) marginSide));
        rcv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        rcv.setNestedScrollingEnabled(false);
        rcv.setHasFixedSize(false);
        rcv.setItemAnimator(null);
    }

    // -------------------- LinearLayoutManager - Vertical

    public static void rcvVerticalFixedNested(Activity activity, RecyclerView rcv, float marginTop, float marginBottom, float marginInner, float marginSide) {
        rcv.addItemDecoration(new RecyclerViewDecoration("verticalLayout", (int) marginTop, (int) marginBottom, (int) marginInner, (int) marginSide));
        rcv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rcv.setNestedScrollingEnabled(true);
        rcv.setHasFixedSize(true);
        rcv.setItemAnimator(null);
    }

    public static void rcvVerticalFixedUnnested(Activity activity, RecyclerView rcv, float marginTop, float marginBottom, float marginInner, float marginSide) {
        rcv.addItemDecoration(new RecyclerViewDecoration("verticalLayout", (int) marginTop, (int) marginBottom, (int) marginInner, (int) marginSide));
        rcv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rcv.setNestedScrollingEnabled(false);
        rcv.setHasFixedSize(true);
        rcv.setItemAnimator(null);
    }

    public static void rcvVerticalUnfixed(Activity activity, RecyclerView rcv, float marginTop, float marginBottom, float marginInner, float marginSide) {
        rcv.addItemDecoration(new RecyclerViewDecoration("verticalLayout", (int) marginTop, (int) marginBottom, (int) marginInner, (int) marginSide));
        rcv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rcv.setNestedScrollingEnabled(false);
        rcv.setHasFixedSize(false);
        rcv.setItemAnimator(null);
    }

}