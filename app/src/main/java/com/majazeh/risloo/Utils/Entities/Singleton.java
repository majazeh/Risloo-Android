package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class Singleton {

    // Objects
    private final Activity activity;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public Singleton(@NonNull Activity activity) {
        this.activity = activity;

        sharedPreferences = activity.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();
        editor.apply();
    }

    public boolean getIntro() {
        return sharedPreferences.getBoolean("intro", true);
    }

    public boolean getCallUs() {
        return sharedPreferences.getBoolean("callUs", true);
    }

    public void setIntro(boolean bool) {
        editor.putBoolean("intro", bool);
        editor.apply();
    }

    public void setCallUs(boolean bool) {
        editor.putBoolean("callUs", bool);
        editor.apply();
    }

}