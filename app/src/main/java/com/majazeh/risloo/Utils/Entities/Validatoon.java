package com.majazeh.risloo.Utils.Entities;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Validatoon {

    public Validatoon() {
        // TODO : Place Code If Needed
    }

    /*
    ---------- Methods ----------
    */

    public void showValid(ConstraintLayout errorLayout, TextView errorTextView, String value) {
        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(value);
    }

    public void showValid(LinearLayout errorLayout, TextView errorTextView, String value) {
        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(value);
    }

    public void hideValid(ConstraintLayout errorLayout, TextView errorTextView) {
        errorLayout.setVisibility(View.GONE);
        errorTextView.setText("");
    }

    public void hideValid(LinearLayout errorLayout, TextView errorTextView) {
        errorLayout.setVisibility(View.GONE);
        errorTextView.setText("");
    }

}