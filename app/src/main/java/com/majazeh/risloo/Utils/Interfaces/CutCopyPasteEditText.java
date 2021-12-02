package com.majazeh.risloo.Utils.Interfaces;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

public class CutCopyPasteEditText extends androidx.appcompat.widget.AppCompatEditText {

    // Interfaces
    private CutCopyPasteListener editText;

    // Void
    public void setOnCutCopyPasteListener(CutCopyPasteListener editText) {
        this.editText = editText;
    }

    public CutCopyPasteEditText(@NonNull Context context) {
        super(context);
    }

    public CutCopyPasteEditText(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    public CutCopyPasteEditText(@NonNull Context context, @NonNull AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (editText != null) {
            switch (id) {
                case android.R.id.cut:
                    editText.onCutListener();
                    break;
                case android.R.id.copy:
                    editText.onCopyListener();
                    break;
                case android.R.id.paste:
                    editText.onPasteListener();
                    break;
            }
        }

        return super.onTextContextMenuItem(id);
    }

}