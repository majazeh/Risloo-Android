package com.majazeh.risloo.Utils.managers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.majazeh.risloo.databinding.ToastDefaultBinding;
import com.majazeh.risloo.databinding.ToastErrorBinding;
import com.majazeh.risloo.databinding.ToastSuccesBinding;

public class ToastManager {

    // Widgets
    private static Toast toast;

    /*
    ---------- Funcs ----------
    */

    public static void showToast(Activity activity, String value) {
        try {
            toast.getView().isShown();
            toast.setText(value);
        } catch (Exception e) {
            toast = Toast.makeText(activity, value, Toast.LENGTH_LONG);
        }

        toast.show();
    }

    public static void showSuccesToast(Activity activity, String value) {
        try {
            toast.getView().isShown();
            toast.setText(value);
        } catch (Exception e) {
            ToastSuccesBinding binding = ToastSuccesBinding.inflate(LayoutInflater.from(activity));

            View layout = binding.getRoot();
            binding.getRoot().setText(value);

            toast = new Toast(activity.getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
        }

        toast.show();
    }

    public static void showErrorToast(Activity activity, String value) {
        try {
            toast.getView().isShown();
            toast.setText(value);
        } catch (Exception e) {
            ToastErrorBinding binding = ToastErrorBinding.inflate(LayoutInflater.from(activity));

            View layout = binding.getRoot();
            binding.getRoot().setText(value);

            toast = new Toast(activity.getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
        }

        toast.show();
    }

    public static void showDefaultToast(Activity activity, String value) {
        try {
            toast.getView().isShown();
            toast.setText(value);
        } catch (Exception e) {
            ToastDefaultBinding binding = ToastDefaultBinding.inflate(LayoutInflater.from(activity));

            View layout = binding.getRoot();
            binding.getRoot().setText(value);

            toast = new Toast(activity.getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
        }

        toast.show();
    }

}