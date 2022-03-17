package com.majazeh.risloo.utils.entities;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.majazeh.risloo.R;

import java.util.Objects;

public class Inputon {

    // Objects
    private final Activity activity;

    // Widgets
    private EditText editText;
    private Drawable background;

    /*
    ---------- Intialize ----------
    */

    public Inputon(@NonNull Activity activity) {
        this.activity = activity;
    }

    /*
    ---------- Func's ----------
    */

    public void select(EditText editText) {
        this.editText = editText;
        background = editText.getBackground();

        editText.requestFocus();
        editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_risloo500);
    }

    public void clear() {
        if (editText != null && editText.hasFocus()) {
            editText.clearFocus();
            editText.setBackground(background);

            hideKeyboard(activity, editText);
        }
    }

    public void dispatch(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            View view = activity.getCurrentFocus();

            if (view instanceof EditText) {
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);

                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();

                if (!rect.contains(rawX, rawY)) {
                    clear();
                }
            }
        }
    }

    /*
    ---------- Private's ----------
    */

    private void hideKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}