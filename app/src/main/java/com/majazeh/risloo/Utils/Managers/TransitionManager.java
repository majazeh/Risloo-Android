package com.majazeh.risloo.utils.managers;

import android.graphics.drawable.TransitionDrawable;
import android.view.View;

public class TransitionManager {

    /*
    ---------- Funcs ----------
    */

    public static void startTransition(View view, int duration) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
        transitionDrawable.startTransition(duration);
    }

    public static void reverseTransition(View view, int duration) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
        transitionDrawable.reverseTransition(duration);
    }

    public static void resetTransition(View view) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
        transitionDrawable.resetTransition();
    }

}