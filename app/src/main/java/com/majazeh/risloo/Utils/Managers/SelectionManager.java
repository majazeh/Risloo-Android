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

    public static String getPosition2(Activity activity, String local, String value) {
        try {
            JSONArray positions = new JSONArray(JsonManager.getJson(activity, "Positions2.json"));

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
            JSONArray acceptations = new JSONArray(JsonManager.getJson(activity, "Acceptations.json"));

            for (int i = 0; i < acceptations.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(acceptations.getJSONObject(i).getString("fa_title")))
                        return acceptations.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(acceptations.getJSONObject(i).getString("en_title")))
                        return acceptations.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getUserType(Activity activity, String local, String value) {
        try {
            JSONArray types = new JSONArray(JsonManager.getJson(activity, "UserTypes.json"));

            for (int i = 0; i < types.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(types.getJSONObject(i).getString("fa_title")))
                        return types.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(types.getJSONObject(i).getString("en_title")))
                        return types.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getUserStatus(Activity activity, String local, String value) {
        try {
            JSONArray status = new JSONArray(JsonManager.getJson(activity, "UserStatus.json"));

            for (int i = 0; i < status.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(status.getJSONObject(i).getString("fa_title")))
                        return status.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(status.getJSONObject(i).getString("en_title")))
                        return status.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getSessionStatus(Activity activity, String local, String value) {
        try {
            JSONArray status = new JSONArray(JsonManager.getJson(activity, "SessionStatus.json"));

            for (int i = 0; i < status.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(status.getJSONObject(i).getString("fa_title")))
                        return status.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(status.getJSONObject(i).getString("en_title")))
                        return status.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getSampleStatus(Activity activity, String local, String value) {
        try {
            JSONArray status = new JSONArray(JsonManager.getJson(activity, "SampleStatus.json"));

            for (int i = 0; i < status.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(status.getJSONObject(i).getString("fa_title")))
                        return status.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(status.getJSONObject(i).getString("en_title")))
                        return status.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getSampleStatus2(Activity activity, String local, String value) {
        try {
            JSONArray status = new JSONArray(JsonManager.getJson(activity, "SampleStatus2.json"));

            for (int i = 0; i < status.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(status.getJSONObject(i).getString("fa_title")))
                        return status.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(status.getJSONObject(i).getString("en_title")))
                        return status.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getCenterStatus(Activity activity, String local, String value) {
        try {
            JSONArray status = new JSONArray(JsonManager.getJson(activity, "CenterStatus.json"));

            for (int i = 0; i < status.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(status.getJSONObject(i).getString("fa_title")))
                        return status.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(status.getJSONObject(i).getString("en_title")))
                        return status.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getRoomStatus(Activity activity, String local, String value) {
        try {
            JSONArray status = new JSONArray(JsonManager.getJson(activity, "RoomStatus.json"));

            for (int i = 0; i < status.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(status.getJSONObject(i).getString("fa_title")))
                        return status.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(status.getJSONObject(i).getString("en_title")))
                        return status.getJSONObject(i).getString("fa_title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

}