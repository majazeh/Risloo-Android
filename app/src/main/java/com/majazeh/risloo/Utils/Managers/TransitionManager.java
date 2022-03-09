package com.majazeh.risloo.utils.managers;

import android.graphics.drawable.TransitionDrawable;
import android.view.View;

public class TransitionManager {

    /*
    ---------- Funcs ----------
    */

    public static void transitionStart(View view, int duration) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
        transitionDrawable.startTransition(duration);
    }

    public static void transitionReverse(View view, int duration) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
        transitionDrawable.reverseTransition(duration);
    }

    public static void transitionReset(View view) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
        transitionDrawable.resetTransition();
    }

}