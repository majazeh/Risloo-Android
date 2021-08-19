package com.majazeh.risloo.Utils.Widgets;

import android.content.Context;
import android.util.AttributeSet;

public class CutCopyPasteEditText extends androidx.appcompat.widget.AppCompatEditText {

    private OnCutCopyPasteListener onCutCopyPasteListener;

    public CutCopyPasteEditText(Context context) {
        super(context);
    }

    public CutCopyPasteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CutCopyPasteEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (onCutCopyPasteListener != null) {
            switch (id) {
                case android.R.id.cut:
                    onCutCopyPasteListener.onCut();
                    break;
                case android.R.id.copy:
                    onCutCopyPasteListener.onCopy();
                    break;
                case android.R.id.paste:
                    onCutCopyPasteListener.onPaste();
                    break;
            }
        } return super.onTextContextMenuItem(id);
    }

    public void setOnCutCopyPasteListener(OnCutCopyPasteListener onCutCopyPasteListener) {
        this.onCutCopyPasteListener = onCutCopyPasteListener;
    }

    public interface OnCutCopyPasteListener {
        void onCut();
        void onCopy();
        void onPaste();
    }

}