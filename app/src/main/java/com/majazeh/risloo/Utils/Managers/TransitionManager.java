package com.majazeh.risloo.utils.managers;

import android.graphics.drawable.TransitionDrawable;
import android.view.View;

public class TransitionManager {

    /*
    ---------- View's ----------
    */

    public static void transitionViewStart(View view, int duration) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
        transitionDrawable.startTransition(duration);
    }

    public static void transitionViewReverse(View view, int duration) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
        transitionDrawable.reverseTransition(duration);
    }

    public static void transitionViewReset(View view) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
        transitionDrawable.resetTransition();
    }

}