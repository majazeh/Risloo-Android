package com.majazeh.risloo.Utils.Managers;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;

import com.google.android.material.appbar.AppBarLayout;

public class AnimateManager {

    /*
    ---------- Funcs ----------
    */

    public static void animateStatusBarColor(Activity activity, int duration, int fromColor, int toColor) {
        ValueAnimator colorAnimation = ValueAnimator.ofArgb(fromColor, toColor);
        colorAnimation.setDuration(duration);
        colorAnimation.addUpdateListener(animator -> activity.getWindow().setStatusBarColor((Integer) animator.getAnimatedValue()));
        colorAnimation.start();
    }

    public static void animateAppBarColor(AppBarLayout appBarLayout, int duration, int fromColor, int toColor) {
        ValueAnimator colorAnimation = ValueAnimator.ofArgb(fromColor, toColor);
        colorAnimation.setDuration(duration);
        colorAnimation.addUpdateListener(animator -> appBarLayout.setBackgroundColor((Integer) animator.getAnimatedValue()));
        colorAnimation.start();
    }

    public static void animateViewAlpha(View view, int duration, float fromAlpha, float toAlpha) {
        ObjectAnimator fadeAnimation = ObjectAnimator.ofFloat(view, "alpha", fromAlpha, toAlpha);
        fadeAnimation.setDuration(duration);
        fadeAnimation.start();
    }

    public static void animateProgressValue(ProgressBar progressBar, int duration, int maxProgress, int toProgress) {
        progressBar.setMax(maxProgress * 100);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), toProgress * 100);
        animation.setDuration(duration);
        animation.setAutoCancel(true);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();
    }

}