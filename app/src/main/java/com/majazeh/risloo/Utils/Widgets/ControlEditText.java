package com.majazeh.risloo.Utils.Widgets;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.majazeh.risloo.R;

import java.util.Objects;

public class ControlEditText {

    // Widgets
    private EditText editText;

    public EditText input() {
        return editText;
    }

    public void focus(EditText editText) {
        this.editText = editText;
    }

    public void select(EditText editText) {
        editText.requestFocus();
        editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_blue500);
    }

    public void clear(Activity activity, EditText editText) {
        editText.clearFocus();
        editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);

        hideKeyboard(activity, editText);
    }

    public void error(Activity activity, EditText editText, ImageView imageView, TextView textView, String value) {
        editText.clearFocus();
        editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_red500);

        imageView.setVisibility(View.VISIBLE);

        textView.setVisibility(View.VISIBLE);
        textView.setText(value);

        hideKeyboard(activity, editText);
    }

    public void check(Activity activity, EditText editText, ImageView imageView, TextView textView) {
        editText.clearFocus();
        editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);

        imageView.setVisibility(View.GONE);

        textView.setVisibility(View.GONE);
        textView.setText("");

        hideKeyboard(activity, editText);
    }

    private void hideKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}