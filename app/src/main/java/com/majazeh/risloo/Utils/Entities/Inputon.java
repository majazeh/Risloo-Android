package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.majazeh.risloo.R;

import java.util.Objects;

public class Inputon {

    // Widgets
    public EditText editText;

    // Objects
    private Drawable background;

    /*
    ---------- Intialize ----------
    */

    public Inputon() {
        // TODO : Place Code If Needed
    }

    /*
    ---------- Methods ----------
    */

    public void select(Activity activity, EditText editText) {
        this.editText = editText;
        background = editText.getBackground();

        editText.requestFocus();
        editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_risloo500);
    }

    public void clear(Activity activity, EditText editText) {
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