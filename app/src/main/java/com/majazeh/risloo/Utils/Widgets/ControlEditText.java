package com.majazeh.risloo.Utils.Widgets;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.Views.Activities.MainActivity;

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
        if (activity instanceof AuthActivity) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_blue500);
        } else if (activity instanceof MainActivity) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_blue500);
        }
    }

    public void clear(Activity activity, EditText editText) {
        editText.clearFocus();
        if (activity instanceof AuthActivity) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
        } else if (activity instanceof MainActivity) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
        }

        hideKeyboard(activity, editText);
    }

    public void error(Activity activity, EditText editText, ImageView imageView, TextView textView, String value) {
        editText.clearFocus();
        if (activity instanceof AuthActivity) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_red500);
        } else if (activity instanceof MainActivity) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
        }

        imageView.setVisibility(View.VISIBLE);

        textView.setVisibility(View.VISIBLE);
        textView.setText(value);

        hideKeyboard(activity, editText);
    }

    public void error(Activity activity, TextView widget, ImageView imageView, TextView textView, String value) {
        if (activity instanceof AuthActivity) {
            widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_red500);
        } else if (activity instanceof MainActivity) {
            widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
        }

        imageView.setVisibility(View.VISIBLE);

        textView.setVisibility(View.VISIBLE);
        textView.setText(value);
    }

    public void error(Activity activity, RecyclerView widget, ImageView imageView, TextView textView, String value) {
        if (activity instanceof AuthActivity) {
            widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_red500);
        } else if (activity instanceof MainActivity) {
            widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
        }

        imageView.setVisibility(View.VISIBLE);

        textView.setVisibility(View.VISIBLE);
        textView.setText(value);
    }

    public void check(Activity activity, EditText editText, ImageView imageView, TextView textView) {
        editText.clearFocus();
        if (activity instanceof AuthActivity) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
        } else if (activity instanceof MainActivity) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
        }

        imageView.setVisibility(View.GONE);

        textView.setVisibility(View.GONE);
        textView.setText("");

        hideKeyboard(activity, editText);
    }

    public void check(Activity activity, TextView widget, ImageView imageView, TextView textView) {
        if (activity instanceof AuthActivity) {
            widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
        } else if (activity instanceof MainActivity) {
            widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
        }

        imageView.setVisibility(View.GONE);

        textView.setVisibility(View.GONE);
        textView.setText("");
    }

    public void check(Activity activity, RecyclerView widget, ImageView imageView, TextView textView) {
        if (activity instanceof AuthActivity) {
            widget.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
        } else if (activity instanceof MainActivity) {
            widget.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
        }

        imageView.setVisibility(View.GONE);

        textView.setVisibility(View.GONE);
        textView.setText("");
    }

    private void hideKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}