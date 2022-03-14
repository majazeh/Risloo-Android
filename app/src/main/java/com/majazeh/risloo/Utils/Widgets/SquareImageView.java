package com.majazeh.risloo.utils.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

public class SquareImageView extends androidx.appcompat.widget.AppCompatImageView {

    /*
    ---------- Intialize ----------
    */

    public SquareImageView(@NonNull Context context) {
        super(context);
    }

    public SquareImageView(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(@NonNull Context context, @NonNull AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*
    ---------- Method's ----------
    */

    @Override
    protected void onMeasure(int measuredWidth, int measuredHeight) {
        super.onMeasure(measuredWidth, measuredHeight);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

}