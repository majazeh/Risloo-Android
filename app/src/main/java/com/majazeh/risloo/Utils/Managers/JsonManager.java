package com.majazeh.risloo.utils.managers;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class JsonManager {

    /*
    ---------- Func's ----------
    */

    public static String getAcceptation(Activity activity, String local, String value) {
        return getAsset(activity, "Acceptations.json", local, value);
    }

    public static String getBillType(Activity activity, String local, String value) {
        return getAsset(activity, "BillTypes.json", local, value);
    }

    public static String getCaseStatus(Activity activity, String local, String value) {
        return getAsset(activity, "CaseStatus.json", local, value);
    }

    public static String getCaseStatus2(Activity activity, String local, String value) {
        return getAsset2(activity, "CaseStatus.json", local, value);
    }

    public static String getCenterStatus(Activity activity, String local, String value) {
        return getAsset(activity, "CenterStatus.json", local, value);
    }

    public static String getClientType(Activity activity, String local, String value) {
        return getAsset(activity, "ClientTypes.json", local, value);
    }

    public static String getEncryption(Activity activity, String local, String value) {
        return getAsset(activity, "Encryptions.json", local, value);
    }

    public static String getIbanStatus(Activity activity, String local, String value) {
        return getAsset(activity, "IbanStatus.json", local, value);
    }

    public static String getIbanType(Activity activity, String local, String value) {
        return getAsset(activity, "IbanTypes.json", local, value);
    }

    public static String getPaymentStatus(Activity activity, String local, String value) {
        return getAsset(activity, "PaymentStatus.json", local, value);
    }

    public static String getPaymentType(Activity activity, String local, String value) {
        return getAsset(activity, "PaymentTypes.json", local, value);
    }

    public static String getPlatformIdentifier(Activity activity, String local, String value) {
        return getAsset(activity, "PlatformIdentifiers.json", local, value);
    }

    public static String getPlatformLevel(Activity activity, String local, String value) {
        return getAsset(activity, "PlatformLevels.json", local, value);
    }

    public static String getPlatformSession(Activity activity, String local, String value) {
        return getAsset(activity, "PlatformSessions.json", local, value);
    }

    public static String getProfileExtras(Activity activity, String local, String value) {
        return getAsset(activity, "ProfileExtras.json", local, value);
    }

    public static String getReferencePosition(Activity activity, String local, String value) {
        return getAsset(activity, "ReferencePositions.json", local, value);
    }

    public static String getRoomStatus(Activity activity, String local, String value) {
        return getAsset(activity, "RoomStatus.json", local, value);
    }

    public static String getSampleStatus(Activity activity, String local, String value) {
        return getAsset(activity, "SampleStatus.json", local, value);
    }

    public static String getSampleStatus2(Activity activity, String local, String value) {
        return getAsset2(activity, "SampleStatus.json", local, value);
    }

    public static String getSelectionType(Activity activity, String local, String value) {
        return getAsset(activity, "SelectionTypes.json", local, value);
    }

    public static String getSessionStatus(Activity activity, String local, String value) {
        return getAsset(activity, "SessionStatus.json", local, value);
    }

    public static String getSessionStatus2(Activity activity, String local, String value) {
        return getAsset2(activity, "SessionStatus.json", local, value);
    }

    public static String getUserPosition(Activity activity, String local, String value) {
        return getAsset(activity, "UserPositions.json", local, value);
    }

    public static String getUserStatus(Activity activity, String local, String value) {
        return getAsset(activity, "UserStatus.json", local, value);
    }

    public static String getUserType(Activity activity, String local, String value) {
        return getAsset(activity, "UserTypes.json", local, value);
    }

    /*
    ---------- Assets's ----------
    */

    private static String getAsset(Activity activity, String asset, String local, String value) {
        try {
            JSONArray list = new JSONArray(getJson(activity, asset));

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

    private static String getAsset2(Activity activity, String asset, String local, String value) {
        try {
            JSONArray list = new JSONArray(getJson(activity, asset));

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

    /*
    ---------- Local's ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static String getJson(Activity activity, String file) {
        try {
            InputStream inputStream = activity.getAssets().open(file);

            int size = inputStream.available();
            byte[] buffer = new byte[size];
            String charset = "UTF-8";

            inputStream.read(buffer);
            inputStream.close();

            return new String(buffer, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}