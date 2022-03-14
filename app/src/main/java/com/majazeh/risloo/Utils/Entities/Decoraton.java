package com.majazeh.risloo.utils.entities;

import static android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class Decoraton {

    // Objects
    private final Activity activity;

    // Vars
    private boolean lightStatus, lightNav;

    /*
    ---------- Intialize ----------
    */

    public Decoraton(@NonNull Activity activity) {
        this.activity = activity;

        // Note: Making The Ui Not Jumping When HIding And Showing The System UI
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            activity.getWindow().getAttributes().layoutInDisplayCutoutMode = LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
    }

    /*
    ---------- Show's ----------
    */

    public void showSystemUI(boolean lightStatus, boolean lightNav) {
        this.lightStatus = lightStatus;
        this.lightNav = lightNav;

        if (lightStatus && lightNav) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                // Codes For Making The Status And Navigation Bars Icons Light.
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                                | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                // Codes For Making The Status Bars Icons Light.
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }

        } else if (lightNav) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                // Codes For Making Navigation Bars Icons Light.
                                | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }

        } else if (lightStatus) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                // Codes For Making The Status Bars Icons Light.
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }

        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    // Codes For Making The Content Appear Under System Bars.
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public void navShowSystemUI(boolean lightStatus, boolean lightNav) {
        this.lightStatus = lightStatus;
        this.lightNav = lightNav;

        if (lightStatus && lightNav) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                // Codes For Making The Status And Navigation Bars Icons Light.
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                                | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                // Codes For Making The Status Bars Icons Light.
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }

        } else if (lightNav) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                // Codes For Making Navigation Bars Icons Light.
                                | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }

        } else if (lightStatus) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                // Codes For Making The Status Bars Icons Light.
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        // Codes For Making The Content Appear Under System Bars.
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }

        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    // Codes For Making The Content Appear Under System Bars.
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /*
    ---------- Color's ----------
    */

    public void colorSystemUI(int statusColor, int navColor) {
        if (lightStatus && lightNav) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity.getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

                activity.getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                activity.getWindow().setStatusBarColor(statusColor);
                activity.getWindow().setNavigationBarColor(navColor);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                activity.getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                activity.getWindow().setStatusBarColor(statusColor);
                activity.getWindow().setNavigationBarColor(Color.BLACK);
            } else {
                activity.getWindow().setStatusBarColor(Color.BLACK);
                activity.getWindow().setNavigationBarColor(Color.BLACK);
            }

        } else if (lightNav) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity.getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

                activity.getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                activity.getWindow().setNavigationBarColor(navColor);
            } else {
                activity.getWindow().setNavigationBarColor(Color.BLACK);
            }

            activity.getWindow().setStatusBarColor(statusColor);

        } else if (lightStatus) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                activity.getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                activity.getWindow().setStatusBarColor(statusColor);
            } else {
                activity.getWindow().setStatusBarColor(Color.BLACK);
            }

            activity.getWindow().setNavigationBarColor(navColor);

        } else {
            activity.getWindow().setStatusBarColor(statusColor);
            activity.getWindow().setNavigationBarColor(navColor);
        }
    }

    /*
    ---------- Extra's ----------
    */

    public void normalShowSystemUI() {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                // Codes For Making The Content Appear Under System Bars.
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    public void leanBackHideSystemUI() {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                // Codes For Making The Content Appear Under System Bars.
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        // Codes For Hiding Status And Navigation.
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void immersiveHideSystemUI() {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                // Codes For Making The Content Appear Under System Bars.
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        // Codes For Hiding Status And Navigation.
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        // Codes For Enabling Immersive Mode.
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    public void immersiveStickyHideSystemUI() {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                // Codes For Making The Content Appear Under System Bars.
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        // Codes For Hiding Status And Navigation.
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        // Codes For Enabling Immersive Sticky Mode.
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

}