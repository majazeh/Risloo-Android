package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

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

    public void login(AuthModel authModel) {
        UserModel userModel = authModel.getUser();

        if (authModel.getToken() != null)
            setToken(authModel.getToken());

        if (authModel.getToken() != null)
            setAuthorization("Bearer " + authModel.getToken());

        if (userModel != null)
            setUserModel(userModel);
    }

    public void update(UserModel userModel) {
        if (userModel != null)
            setUserModel(userModel);
    }

    public void logout() {
        editor.remove("token");
        editor.remove("authorization");
        editor.remove("usermodel");
        editor.apply();
    }

    public void setToken(String value) {
        editor.putString("token", value);
        editor.apply();
    }

    public void setAuthorization(String value) {
        editor.putString("authorization", value);
        editor.apply();
    }

    public void setUserModel(UserModel userModel) {
        editor.putString("usermodel", new Gson().toJson(userModel));
        editor.apply();
    }

    public String getToken() {
        if (!sharedPreferences.getString("token", "").equals("")) {
            return sharedPreferences.getString("token", "");
        }
        return "";
    }

    public String getAuthorization() {
        if (!sharedPreferences.getString("authorization", "").equals("")) {
            return sharedPreferences.getString("authorization", "");
        }
        return "";
    }

    public UserModel getUserModel() {
        if (!sharedPreferences.getString("usermodel", "").equals("")) {
            return new Gson().fromJson(sharedPreferences.getString("usermodel", ""), UserModel.class);
        }
        return null;
    }

    public String getId() {
        if (getUserModel().getId() != null) {
            return getUserModel().getId();
        }
        return "";
    }

    public String getName() {
        if (getUserModel().getName() != null) {
            return getUserModel().getName();
        }
        return "";
    }

    public String getUsername() {
        if (getUserModel().getUsername() != null) {
            return getUserModel().getUsername();
        }
        return "";
    }

    public String getBirthday() {
        if (getUserModel().getBirthday() != null) {
            return getUserModel().getBirthday();
        }
        return "";
    }

    public String getEmail() {
        if (getUserModel().getEmail() != null) {
            return getUserModel().getEmail();
        }
        return "";
    }

    public String getMobile() {
        if (getUserModel().getMobile() != null) {
            return getUserModel().getMobile();
        }
        return "";
    }

    public String getStatus() {
        if (getUserModel().getUserStatus() != null) {
            return getUserModel().getUserStatus();
        }
        return "";
    }

    public String getType() {
        if (getUserModel().getUserType() != null) {
            return getUserModel().getUserType();
        }
        return "";
    }

    public String getGender() {
        if (getUserModel().getGender() != null) {
            return getUserModel().getGender();
        }
        return "";
    }

    public String getAvatar() {
        if (getUserModel().getAvatar() != null && getUserModel().getAvatar().getMedium() != null && getUserModel().getAvatar().getMedium().getUrl() != null) {
            return getUserModel().getAvatar().getMedium().getUrl();
        }
        return "";
    }

    public String getPublicKey() {
        if (getUserModel().getPublic_key() != null) {
            return getUserModel().getPublic_key();
        }
        return "";
    }

    public String getMoney() {
//        if (getUserModel().getTreasuries() != null) {
//            try {
//                int money = 0;
//                for (int i = 0; i < getUserModel().getTreasuries().length(); i++) {
//                    if (getUserModel().getTreasuries().getJSONObject(i).getString("symbol").equals("gift") || getUserModel().getTreasuries().getJSONObject(i).getString("symbol").equals("wallet") )
//                        money += getUserModel().getTreasuries().getJSONObject(i).getInt("balance");
//                }
//                return String.valueOf(money);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        return "";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getDescription() {
        if (!sharedPreferences.getString("description", "").equals("")) {
            return sharedPreferences.getString("description", "");
        }
        return "";
    }

    public String getStartDate() {
        if (!sharedPreferences.getString("start_date", "").equals("")) {
            return sharedPreferences.getString("start_date", "");
        }
        return "";
    }

    public String getStartTime() {
        if (!sharedPreferences.getString("start_time", "").equals("")) {
            return sharedPreferences.getString("start_time", "");
        }
        return "";
    }

    public String getDuration() {
        if (!sharedPreferences.getString("duration", "").equals("")) {
            return sharedPreferences.getString("duration", "");
        }
        return "";
    }

    public String getAddress() {
        if (!sharedPreferences.getString("address", "").equals("")) {
            return sharedPreferences.getString("address", "");
        }
        return "";
    }

    public int getProgress() {
        if (sharedPreferences.getInt("progress", 0) != -1) {
            return sharedPreferences.getInt("address", 0);
        }
        return -1;
    }

    public boolean getBulkSession() {
        return sharedPreferences.getBoolean("bulkSession", false);
    }

    public boolean getEndScheduleTime() {
        return sharedPreferences.getBoolean("endScheduleTime", false);
    }

}