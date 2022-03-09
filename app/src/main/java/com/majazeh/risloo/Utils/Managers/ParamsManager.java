package com.majazeh.risloo.utils.managers;

import android.app.Dialog;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.appbar.AppBarLayout;

public class ParamsManager {

    /*
    ---------- Window's ----------
    */

    public static WindowManager.LayoutParams windowWrapContent(Dialog dialog) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        return layoutParams;
    }

    public static WindowManager.LayoutParams windowMatchParent(Dialog dialog) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

        return layoutParams;
    }

    public static WindowManager.LayoutParams windowMatchWrap(Dialog dialog) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        return layoutParams;
    }

    /*
    ---------- AppBar's ----------
    */

    public static void appbarScrollEnterAlways(ViewGroup.LayoutParams viewParams) {
        AppBarLayout.LayoutParams appBarParams = (AppBarLayout.LayoutParams) viewParams;
        appBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
    }

    public static void appbarNoScroll(ViewGroup.LayoutParams viewParams) {
        AppBarLayout.LayoutParams appBarParams = (AppBarLayout.LayoutParams) viewParams;
        appBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL);
    }

}