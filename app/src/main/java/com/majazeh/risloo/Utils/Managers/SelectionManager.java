package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;

public class SelectionManager {

    public static String getAcceptation(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "Acceptations.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getCenterStatus(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "CenterStatus.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getClientType(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "ClientType.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getPaymentStatus(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "PaymentStatus.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getPosition(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "Positions.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getPosition2(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "Positions2.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getRoomStatus(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "RoomStatus.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getSampleStatus(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "SampleStatus.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getSampleStatus2(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "SampleStatus2.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getSelectionType(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "SelectionType.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getSessionStatus(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "SessionStatus.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getSessionType(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "SessionType.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getUserStatus(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "UserStatus.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getUserStatus2(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "UserStatus2.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

    public static String getUserType(Activity activity, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, "UserTypes.json"));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return value;
    }

}