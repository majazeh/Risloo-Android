package com.majazeh.risloo.Utils.Managers;

import android.graphics.drawable.TransitionDrawable;
import android.view.View;

public class TransitionManager {

    /*
    ---------- Funcs ----------
    */

    public static void startTransition(View view) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
        transitionDrawable.startTransition(500);
    }

    public static void reverseTransition(View view) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
        transitionDrawable.reverseTransition(500);
    }

    public static void resetTransition(View view) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
        transitionDrawable.resetTransition();
    }

}