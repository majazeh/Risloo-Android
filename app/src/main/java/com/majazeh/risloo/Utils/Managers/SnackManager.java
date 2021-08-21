package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.view.ViewCompat;

import com.google.android.material.snackbar.Snackbar;
import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.SnackDefaultBinding;
import com.majazeh.risloo.databinding.SnackErrorBinding;
import com.majazeh.risloo.databinding.SnackSuccesBinding;

public class SnackManager {

    /*
    ---------- Funcs ----------
    */

    public static void showSuccesSnack(Activity activity, String value) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();

        snackbarView.setBackgroundColor(Color.TRANSPARENT);
        ViewCompat.setLayoutDirection(snackbarView,ViewCompat.LAYOUT_DIRECTION_RTL);

        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.setPadding((int) activity.getResources().getDimension(R.dimen._6sdp), 0, (int) activity.getResources().getDimension(R.dimen._6sdp), (int) activity.getResources().getDimension(R.dimen._6sdp));

        SnackSuccesBinding binding = SnackSuccesBinding.inflate(LayoutInflater.from(activity));
        View bindingView = binding.getRoot();

        binding.messageTextView.setText(value);
        binding.callbackImageView.setOnClickListener(v -> snackbar.dismiss());

        snackbarLayout.addView(bindingView, 0);
        snackbar.show();
    }

    public static void showErrorSnack(Activity activity, String value) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();

        snackbarView.setBackgroundColor(Color.TRANSPARENT);
        ViewCompat.setLayoutDirection(snackbarView,ViewCompat.LAYOUT_DIRECTION_RTL);

        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.setPadding((int) activity.getResources().getDimension(R.dimen._6sdp), 0, (int) activity.getResources().getDimension(R.dimen._6sdp), (int) activity.getResources().getDimension(R.dimen._6sdp));

        SnackErrorBinding binding = SnackErrorBinding.inflate(LayoutInflater.from(activity));
        View bindingView = binding.getRoot();

        binding.messageTextView.setText(value);
        binding.callbackImageView.setOnClickListener(v -> snackbar.dismiss());

        snackbarLayout.addView(bindingView, 0);
        snackbar.show();
    }

    public static void showDefaultSnack(Activity activity, String value) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();

        snackbarView.setBackgroundColor(Color.TRANSPARENT);
        ViewCompat.setLayoutDirection(snackbarView,ViewCompat.LAYOUT_DIRECTION_RTL);

        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.setPadding((int) activity.getResources().getDimension(R.dimen._6sdp), 0, (int) activity.getResources().getDimension(R.dimen._6sdp), (int) activity.getResources().getDimension(R.dimen._6sdp));

        SnackDefaultBinding binding = SnackDefaultBinding.inflate(LayoutInflater.from(activity));
        View bindingView = binding.getRoot();

        binding.messageTextView.setText(value);
        binding.callbackImageView.setOnClickListener(v -> snackbar.dismiss());

        snackbarLayout.addView(bindingView, 0);
        snackbar.show();
    }

}