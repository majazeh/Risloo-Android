package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Activities.TestActivity;

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
        if (activity instanceof AuthActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_risloo500);
        else if (activity instanceof MainActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_lightblue500);
        else if (activity instanceof TestActivity)
            editText.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_lightblue500);
    }

    public void clear(Activity activity, EditText editText) {
        editText.clearFocus();
        editText.setBackground(background);

        hideKeyboard(activity, editText);
    }

    private void hideKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}