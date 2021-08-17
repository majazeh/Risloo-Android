package com.majazeh.risloo.Utils.Widgets;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Activities.TestActivity;

import java.util.Objects;

public class ControlEditText {

    // Widgets
    private EditText editText;

    public EditText input() {
        return editText;
    }

    public void select(Activity activity, EditText editText) {
        this.editText = editText;

        editText.requestFocus();
        if (activity instanceof AuthActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_blue500);
        else if (activity instanceof MainActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_blue500);
        else if (activity instanceof TestActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_blue500);
    }

    public void clear(Activity activity, EditText editText) {
        editText.clearFocus();
        if (activity instanceof AuthActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
        else if (activity instanceof MainActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
        else if (activity instanceof TestActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray300);

        hideKeyboard(activity, editText);
    }

    public void error(Activity activity, EditText widget, ConstraintLayout errorLayout, TextView errorTextView, String value) {
        if (widget != null) {
            widget.clearFocus();
            if (activity instanceof AuthActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_red500);
            else if (activity instanceof MainActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
            else if (activity instanceof TestActivity)
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);

            hideKeyboard(activity, widget);
        }

        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(value);
    }

    public void error(Activity activity, TextView widget, ConstraintLayout errorLayout, TextView errorTextView, String value) {
        if (widget != null) {
            if (activity instanceof AuthActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_red500);
            else if (activity instanceof MainActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
            else if (activity instanceof TestActivity)
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
        }

        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(value);
    }

    public void error(Activity activity, Spinner widget, ConstraintLayout errorLayout, TextView errorTextView, String value) {
        if (widget != null) {
            if (activity instanceof AuthActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_red500);
            else if (activity instanceof MainActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
            else if (activity instanceof TestActivity)
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
        }

        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(value);
    }

    public void error(Activity activity, RecyclerView widget, ConstraintLayout errorLayout, TextView errorTextView, String value) {
        if (widget != null) {
            if (activity instanceof AuthActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_red500);
            else if (activity instanceof MainActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
            else if (activity instanceof TestActivity)
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
        }

        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(value);
    }

    public void error(Activity activity, LinearLayout widget, ConstraintLayout errorLayout, TextView errorTextView, String value) {
        if (widget != null) {
            if (activity instanceof AuthActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_red500);
            else if (activity instanceof MainActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
            else if (activity instanceof TestActivity)
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
        }

        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(value);
    }

    public void error(Activity activity, ConstraintLayout widget, ConstraintLayout errorLayout, TextView errorTextView, String value) {
        if (widget != null) {
            if (activity instanceof AuthActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_red500);
            else if (activity instanceof MainActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
            else if (activity instanceof TestActivity)
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
        }

        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(value);
    }

    public void check(Activity activity, EditText widget, ConstraintLayout errorLayout, TextView errorTextView) {
        if (widget != null) {
            widget.clearFocus();
            if (activity instanceof AuthActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
            else if (activity instanceof MainActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
            else if (activity instanceof TestActivity)
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray300);

            hideKeyboard(activity, widget);
        }

        errorLayout.setVisibility(View.GONE);
        errorTextView.setText("");
    }

    public void check(Activity activity, TextView widget, ConstraintLayout errorLayout, TextView errorTextView) {
        if (widget != null) {
            if (activity instanceof AuthActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
            else if (activity instanceof MainActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
            else if (activity instanceof TestActivity)
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray300);
        }

        errorLayout.setVisibility(View.GONE);
        errorTextView.setText("");
    }

    public void check(Activity activity, Spinner widget, ConstraintLayout errorLayout, TextView errorTextView) {
        if (widget != null) {
            if (activity instanceof AuthActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
            else if (activity instanceof MainActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
            else if (activity instanceof TestActivity)
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray300);
        }

        errorLayout.setVisibility(View.GONE);
        errorTextView.setText("");
    }

    public void check(Activity activity, RecyclerView widget, ConstraintLayout errorLayout, TextView errorTextView) {
        if (widget != null) {
            if (activity instanceof AuthActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
            else if (activity instanceof MainActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
            else if (activity instanceof TestActivity)
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray300);
        }

        errorLayout.setVisibility(View.GONE);
        errorTextView.setText("");
    }

    public void check(Activity activity, LinearLayout widget, ConstraintLayout errorLayout, TextView errorTextView) {
        if (widget != null) {
            if (activity instanceof AuthActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
            else if (activity instanceof MainActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
            else if (activity instanceof TestActivity)
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray300);
        }

        errorLayout.setVisibility(View.GONE);
        errorTextView.setText("");
    }

    public void check(Activity activity, ConstraintLayout widget, ConstraintLayout errorLayout, TextView errorTextView) {
        if (widget != null) {
            if (activity instanceof AuthActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
            else if (activity instanceof MainActivity)
                widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
            else if (activity instanceof TestActivity)
                editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray300);
        }

        errorLayout.setVisibility(View.GONE);
        errorTextView.setText("");
    }

    private void hideKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}