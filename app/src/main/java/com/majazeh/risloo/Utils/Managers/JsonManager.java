package com.majazeh.risloo.utils.managers;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;

public class JsonManager {

    /*
    ---------- Funcs ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String getJson(Activity activity, String file) {
        try {
            InputStream inputStream = activity.getAssets().open(file);

            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        } return "";
    }

}