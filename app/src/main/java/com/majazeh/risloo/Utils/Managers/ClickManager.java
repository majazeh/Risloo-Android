package com.majazeh.risloo.Utils.Managers;

import android.os.Handler;
import android.view.View;

public class ClickManager {

    public static CustomClickListener onClickListener(CustomExtraCode customExtraCode){
        return view -> view.setOnClickListener((View.OnClickListener) v -> {
            customExtraCode.code();
        });
    }

    public static CustomClickListener onDelayedClickListener(CustomExtraCode customExtraCode){
        return view -> view.setOnClickListener((View.OnClickListener) v -> {
            view.setClickable(false);
            new Handler().postDelayed(() -> view.setClickable(true), 300);

            customExtraCode.code();
        });
    }

    public interface CustomClickListener {
        void widget(View view);
    }

    public interface CustomExtraCode {
        void code();
    }

}