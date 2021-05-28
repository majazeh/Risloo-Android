package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;

public class SelectionManager {

    public static String getPosition(Activity activity, String local, String value) {
        try {
            JSONArray positions = new JSONArray(JsonManager.getJson(activity, "Positions.json"));

            for (int i = 0; i < positions.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(positions.getJSONObject(i).getString("fa_title")))
                        return positions.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(positions.getJSONObject(i).getString("en_title")))
                        return positions.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getAcceptation(Activity activity, String local, String value) {
        try {
            JSONArray positions = new JSONArray(JsonManager.getJson(activity, "Acceptations.json"));

            for (int i = 0; i < positions.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(positions.getJSONObject(i).getString("fa_title")))
                        return positions.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(positions.getJSONObject(i).getString("en_title")))
                        return positions.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getUserType(Activity activity, String local, String value) {
        try {
            JSONArray positions = new JSONArray(JsonManager.getJson(activity, "UserTypes.json"));

            for (int i = 0; i < positions.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(positions.getJSONObject(i).getString("fa_title")))
                        return positions.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(positions.getJSONObject(i).getString("en_title")))
                        return positions.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getUserStatus(Activity activity, String local, String value) {
        try {
            JSONArray positions = new JSONArray(JsonManager.getJson(activity, "UserStatus.json"));

            for (int i = 0; i < positions.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(positions.getJSONObject(i).getString("fa_title")))
                        return positions.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(positions.getJSONObject(i).getString("en_title")))
                        return positions.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

}