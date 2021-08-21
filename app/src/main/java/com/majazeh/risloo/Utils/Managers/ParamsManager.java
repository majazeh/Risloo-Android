package com.majazeh.risloo.Utils.Managers;

import android.app.Dialog;
import android.view.WindowManager;

public class ParamsManager {

    /*
    ---------- Funcs ----------
    */

    public static WindowManager.LayoutParams applyWrapContent(Dialog dialog) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        return layoutParams;
    }

    public static WindowManager.LayoutParams applyMatchParent(Dialog dialog) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

        return layoutParams;
    }

}