package com.majazeh.risloo.utils.managers;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;

public class AnimateManager {

    /*
    ---------- Window ----------
    */

    public static void animateStatusBarColor(Window window, int duration, int fromColor, int toColor) {
        ValueAnimator animater = ValueAnimator.ofArgb(fromColor, toColor);
        animater.setDuration(duration);
        animater.addUpdateListener(animator -> window.setStatusBarColor((Integer) animator.getAnimatedValue()));
        animater.start();
    }

    public static void animateNavigationBarColor(Window window, int duration, int fromColor, int toColor) {
        ValueAnimator animater = ValueAnimator.ofArgb(fromColor, toColor);
        animater.setDuration(duration);
        animater.addUpdateListener(animator -> window.setNavigationBarColor((Integer) animator.getAnimatedValue()));
        animater.start();
    }

    /*
    ---------- View ----------
    */

    public static void animateViewColor(View view, int duration, int fromColor, int toColor) {
        ValueAnimator animater = ValueAnimator.ofArgb(fromColor, toColor);
        animater.setDuration(duration);
        animater.addUpdateListener(animator -> view.setBackgroundColor((Integer) animator.getAnimatedValue()));
        animater.start();
    }

    public static void animateViewAlpha(View view, int duration, float fromAlpha, float toAlpha) {
        ObjectAnimator animater = ObjectAnimator.ofFloat(view, "alpha", fromAlpha, toAlpha);
        animater.setDuration(duration);
        animater.start();
    }

    /*
    ---------- ProgressBar ----------
    */

    public static void animateProgressBarValue(ProgressBar progressBar, int duration, int maxProgress, int toProgress) {
        progressBar.setMax(maxProgress * 100);

        ObjectAnimator animater = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), toProgress * 100);
        animater.setDuration(duration);
        animater.setAutoCancel(true);
        animater.setInterpolator(new AccelerateDecelerateInterpolator());
        animater.start();
    }

}