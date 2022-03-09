package com.majazeh.risloo.utils.managers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.majazeh.risloo.databinding.ToastDefaultBinding;
import com.majazeh.risloo.databinding.ToastErrorBinding;
import com.majazeh.risloo.databinding.ToastSuccesBinding;

public class ToastManager {

    // Widgets
    private static Toast toast;

    /*
    ---------- Show's ----------
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

    public static void showToastSucces(Activity activity, String value) {
        try {
            toast.getView().isShown();
            toast.setText(value);
        } catch (Exception e) {
            ToastSuccesBinding binding = ToastSuccesBinding.inflate(LayoutInflater.from(activity));
            View view = binding.getRoot();

            TextView textView = binding.getRoot();
            textView.setText(value);

            toast = new Toast(activity.getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(view);
        }

        toast.show();
    }

    public static void showToastError(Activity activity, String value) {
        try {
            toast.getView().isShown();
            toast.setText(value);
        } catch (Exception e) {
            ToastErrorBinding binding = ToastErrorBinding.inflate(LayoutInflater.from(activity));
            View view = binding.getRoot();

            TextView textView = binding.getRoot();
            textView.setText(value);

            toast = new Toast(activity.getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(view);
        }

        toast.show();
    }

    public static void showToastDefault(Activity activity, String value) {
        try {
            toast.getView().isShown();
            toast.setText(value);
        } catch (Exception e) {
            ToastDefaultBinding binding = ToastDefaultBinding.inflate(LayoutInflater.from(activity));
            View view = binding.getRoot();

            TextView textView = binding.getRoot();
            textView.setText(value);

            toast = new Toast(activity.getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(view);
        }

        toast.show();
    }

}