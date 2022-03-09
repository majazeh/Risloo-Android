package com.majazeh.risloo.utils.managers;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;

public class SelectionManager {

    /*
    ---------- Funcs ----------
    */

    public static String getAcceptation(Activity activity, String local, String value) {
        return getSelection(activity, "Acceptations.json", local, value);
    }

    public static String getBillType(Activity activity, String local, String value) {
        return getSelection(activity, "BillTypes.json", local, value);
    }

    public static String getCaseStatus(Activity activity, String local, String value) {
        return getSelection(activity, "CaseStatus.json", local, value);
    }

    public static String getCaseStatus2(Activity activity, String local, String value) {
        return getSelection2(activity, "CaseStatus.json", local, value);
    }

    public static String getCenterStatus(Activity activity, String local, String value) {
        return getSelection(activity, "CenterStatus.json", local, value);
    }

    public static String getClientType(Activity activity, String local, String value) {
        return getSelection(activity, "ClientTypes.json", local, value);
    }

    public static String getEncryption(Activity activity, String local, String value) {
        return getSelection(activity, "Encryptions.json", local, value);
    }

    public static String getIbanStatus(Activity activity, String local, String value) {
        return getSelection(activity, "IbanStatus.json", local, value);
    }

    public static String getIbanType(Activity activity, String local, String value) {
        return getSelection(activity, "IbanTypes.json", local, value);
    }

    public static String getPaymentStatus(Activity activity, String local, String value) {
        return getSelection(activity, "PaymentStatus.json", local, value);
    }

    public static String getPaymentType(Activity activity, String local, String value) {
        return getSelection(activity, "PaymentTypes.json", local, value);
    }

    public static String getPlatformIdentifier(Activity activity, String local, String value) {
        return getSelection(activity, "PlatformIdentifiers.json", local, value);
    }

    public static String getPlatformLevel(Activity activity, String local, String value) {
        return getSelection(activity, "PlatformLevels.json", local, value);
    }

    public static String getPlatformSession(Activity activity, String local, String value) {
        return getSelection(activity, "PlatformSessions.json", local, value);
    }

    public static String getProfileExtras(Activity activity, String local, String value) {
        return getSelection(activity, "ProfileExtras.json", local, value);
    }

    public static String getReferencePosition(Activity activity, String local, String value) {
        return getSelection(activity, "ReferencePositions.json", local, value);
    }

    public static String getRoomStatus(Activity activity, String local, String value) {
        return getSelection(activity, "RoomStatus.json", local, value);
    }

    public static String getSampleStatus(Activity activity, String local, String value) {
        return getSelection(activity, "SampleStatus.json", local, value);
    }

    public static String getSampleStatus2(Activity activity, String local, String value) {
        return getSelection2(activity, "SampleStatus.json", local, value);
    }

    public static String getSelectionType(Activity activity, String local, String value) {
        return getSelection(activity, "SelectionTypes.json", local, value);
    }

    public static String getSessionStatus(Activity activity, String local, String value) {
        return getSelection(activity, "SessionStatus.json", local, value);
    }

    public static String getSessionStatus2(Activity activity, String local, String value) {
        return getSelection2(activity, "SessionStatus.json", local, value);
    }

    public static String getUserPosition(Activity activity, String local, String value) {
        return getSelection(activity, "UserPositions.json", local, value);
    }

    public static String getUserStatus(Activity activity, String local, String value) {
        return getSelection(activity, "UserStatus.json", local, value);
    }

    public static String getUserType(Activity activity, String local, String value) {
        return getSelection(activity, "UserTypes.json", local, value);
    }

    /*
    ---------- Locals ----------
    */

    private static String getSelection(Activity activity, String asset, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, asset));

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
        }

        return value;
    }

    private static String getSelection2(Activity activity, String asset, String local, String value) {
        try {
            JSONArray list = new JSONArray(JsonManager.getJson(activity, asset));

            for (int i = 0; i < list.length(); i++) {
                if (local.equals("en")) {
                    if (value.equals(list.getJSONObject(i).getString("fa_title_2")))
                        return list.getJSONObject(i).getString("en_title");
                } else {
                    if (value.equals(list.getJSONObject(i).getString("en_title")))
                        return list.getJSONObject(i).getString("fa_title_2");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return value;
    }

}