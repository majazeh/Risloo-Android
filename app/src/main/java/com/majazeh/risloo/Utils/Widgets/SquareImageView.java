package com.majazeh.risloo.Utils.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

public class SquareImageView extends androidx.appcompat.widget.AppCompatImageView {

    public SquareImageView(@NonNull Context context) {
        super(context);
    }

    public SquareImageView(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(@NonNull Context context, @NonNull AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

}