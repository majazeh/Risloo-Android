package com.majazeh.risloo.utils.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.majazeh.risloo.utils.interfaces.CutCopyPasteListener;

public class CutCopyPasteEditText extends androidx.appcompat.widget.AppCompatEditText {

    // Interfaces
    private CutCopyPasteListener listener;

    /*
    ---------- Intialize's ----------
    */

    public CutCopyPasteEditText(@NonNull Context context) {
        super(context);
    }

    public CutCopyPasteEditText(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    public CutCopyPasteEditText(@NonNull Context context, @NonNull AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*
    ---------- Method's ----------
    */

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (listener != null) {
            switch (id) {
                case android.R.id.cut:
                    listener.onCutListener();
                    break;
                case android.R.id.copy:
                    listener.onCopyListener();
                    break;
                case android.R.id.paste:
                    listener.onPasteListener();
                    break;
            }
        }

        return super.onTextContextMenuItem(id);
    }

    /*
    ---------- Custom's ----------
    */

    public void setOnCutCopyPasteListener(CutCopyPasteListener listener) {
        this.listener = listener;
    }

}