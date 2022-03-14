package com.majazeh.risloo.utils.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.majazeh.risloo.R;

public class SingleNumberPicker extends NumberPicker {

    /*
    ---------- Intialize ----------
    */

    public SingleNumberPicker(@NonNull Context context) {
        super(context);
    }

    public SingleNumberPicker(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleNumberPicker(@NonNull Context context, @NonNull AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*
    ---------- Method's ----------
    */

    @Override
    public void addView(@NonNull View view) {
        super.addView(view);
        updateView(getContext(), view);
    }

    @Override
    public void addView(@NonNull View view, @NonNull ViewGroup.LayoutParams params) {
        super.addView(view, params);
        updateView(getContext(), view);
    }

    @Override
    public void addView(@NonNull View view, int index, @NonNull ViewGroup.LayoutParams params) {
        super.addView(view, index, params);
        updateView(getContext(), view);
    }

    /*
    ---------- Custom's ----------
    */

    private void updateView(@NonNull Context context, @NonNull View view) {
        if (view instanceof EditText) {
            ((EditText) view).setTypeface(ResourcesCompat.getFont(context, R.font.dana_bold));
            ((EditText) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._11ssp));
            ((EditText) view).setTextColor(getResources().getColor(R.color.coolGray700));
        }
    }

}