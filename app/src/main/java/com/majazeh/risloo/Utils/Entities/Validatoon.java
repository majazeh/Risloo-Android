package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.majazeh.risloo.R;

public class Validatoon {

    // Objects
    private final Activity activity;

    /*
    ---------- Intialize ----------
    */

    public Validatoon(@NonNull Activity activity) {
        this.activity = activity;
    }

    /*
    ---------- Methods ----------
    */

    public void emptyValid(LinearLayout errorLayout, TextView errorTextView) {
        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(activity.getResources().getString(R.string.AppInputEmpty));
    }

    public void showValid(LinearLayout errorLayout, TextView errorTextView, String value) {
        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(value);
    }

    public void hideValid(LinearLayout errorLayout, TextView errorTextView) {
        errorLayout.setVisibility(View.GONE);
        errorTextView.setText("");
    }

}