package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;

public class JsonManager {

    public static String getJson(Activity activity, String fileName) {
        String json = "";

        try {
            InputStream inputStream = activity.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

}