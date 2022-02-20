package com.majazeh.risloo.Utils.entities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.majazeh.risloo.R;

import java.util.Objects;

public class Inputon {

    // Objects
    private final Activity activity;
    private Drawable background;

    // Widgets
    public EditText editText;

    /*
    ---------- Intialize ----------
    */

    public Inputon(@NonNull Activity activity) {
        this.activity = activity;
    }

    /*
    ---------- Methods ----------
    */

    public void select(EditText editText) {
        this.editText = editText;
        background = editText.getBackground();

        editText.requestFocus();
        editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_risloo500);
    }

    public void clear(EditText editText) {
        editText.clearFocus();
        editText.setBackground(background);

        hideKeyboard(activity, editText);
    }

    /*
    ---------- Voids ----------
    */

    private void hideKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}