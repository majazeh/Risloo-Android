package com.majazeh.risloo.Utils.Widgets;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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

    public void select(EditText editText, String theory) {
        editText.requestFocus();
        if (theory.equals("auth")) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_blue500);
        } else if (theory.equals("main")) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_blue500);
        }
    }

    public void error(Activity activity, EditText editText, String theory) {
        editText.clearFocus();
        if (theory.equals("auth")) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_red500);
        } else if (theory.equals("main")) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_red500);
        }

        hideKeyboard(activity, editText);
    }

    public void clear(Activity activity, EditText editText, String theory) {
        editText.clearFocus();
        if (theory.equals("auth")) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
        } else if (theory.equals("main")) {
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray300);
        }

        hideKeyboard(activity, editText);
    }

    private void hideKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}