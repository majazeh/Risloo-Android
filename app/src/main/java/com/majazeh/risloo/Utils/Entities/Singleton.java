package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.majazeh.risloo.Utils.Managers.DateManager;

public class Singleton {

    // Objects
    private final Activity activity;
    private final SharedPreferences sharedPreferences;
    public final SharedPreferences.Editor editor;

    public Singleton(@NonNull Activity activity) {
        this.activity = activity;

        sharedPreferences = activity.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void setToken(String value) {
        editor.putString("token", value);
        editor.apply();
    }

    public String getToken() {
        if (!sharedPreferences.getString("token", "").equals("")) {
            return sharedPreferences.getString("token", "");
        }
        return "";
    }

    public void setAuthorization(String value) {
        editor.putString("authorization", value);
        editor.apply();
    }

    public String getAuthorization() {
        if (!sharedPreferences.getString("authorization", "").equals("")) {
            return sharedPreferences.getString("authorization", "");
        }
        return "";
    }

    public void setUserId(String value) {
        editor.putString("userId", value);
        editor.apply();
    }

    public String getUserId() {
        if (!sharedPreferences.getString("userId", "").equals("")) {
            return sharedPreferences.getString("userId", "");
        }
        return "";
    }

    public void setName(String value) {
        editor.putString("name", value);
        editor.apply();
    }

    public String getName() {
        if (!sharedPreferences.getString("name", "").equals("")) {
            return sharedPreferences.getString("name", "");
        }
        return "";
    }

    public void setUsername(String value) {
        editor.putString("username", value);
        editor.apply();
    }

    public String getUsername() {
        if (!sharedPreferences.getString("username", "").equals("")) {
            return sharedPreferences.getString("username", "");
        }
        return "";
    }

    public void setBirthday(String value) {
        editor.putString("birthday", value);
        editor.apply();
    }

    public String getBirthday() {
        if (!sharedPreferences.getString("birthday", "").equals("")) {
            return DateManager.gregorianToJalali(sharedPreferences.getString("birthday", ""));
        }
        return "";
    }

    public void setEmail(String value) {
        editor.putString("email", value);
        editor.apply();
    }

    public String getEmail() {
        if (!sharedPreferences.getString("email", "").equals("")) {
            return sharedPreferences.getString("email", "");
        }
        return "";
    }

    public void setMobile(String value) {
        editor.putString("mobile", value);
        editor.apply();
    }

    public String getMobile() {
        if (!sharedPreferences.getString("mobile", "").equals("")) {
            return sharedPreferences.getString("mobile", "");
        }
        return "";
    }

    public void setStatus(String value) {
        editor.putString("status", value);
        editor.apply();
    }

    public String getStatus() {
        if (!sharedPreferences.getString("status", "").equals("")) {
            return sharedPreferences.getString("status", "");
        }
        return "";
    }

    public void setType(String value) {
        editor.putString("type", value);
        editor.apply();
    }

    public String getType() {
        if (!sharedPreferences.getString("type", "").equals("")) {
            return sharedPreferences.getString("type", "");
        }
        return "";
    }

    public void setGender(String value) {
        editor.putString("gender", value);
        editor.apply();
    }

    public String getGender() {
        if (!sharedPreferences.getString("gender", "").equals("")) {
            return sharedPreferences.getString("gender", "");
        }
        return "";
    }

    public void setAvatar(String value) {
        editor.putString("avatar", value);
        editor.apply();
    }

    public String getAvatar() {
        if (!sharedPreferences.getString("avatar", "").equals("")) {
            return sharedPreferences.getString("avatar", "");
        }
        return "";
    }

    public void setPublicKey(String value) {
        editor.putString("public_key", value);
        editor.apply();
    }

    public String getPublicKey() {
        if (!sharedPreferences.getString("public_key", "").equals("")) {
            return sharedPreferences.getString("public_key", "");
        }
        return "";
    }

    public void setPrivateKey(String value) {
        editor.putString("private_key", value);
        editor.apply();
    }

    public String getPrivateKey() {
        if (!sharedPreferences.getString("private_key", "").equals("")) {
            return sharedPreferences.getString("private_key", "");
        }
        return "";
    }

    public void setMoney(String value) {
        editor.putString("money", value);
        editor.apply();
    }

    public String getMoney() {
        if (!sharedPreferences.getString("money", "").equals("")) {
            return sharedPreferences.getString("money", "");
        }
        return "";
    }

    public void logOut() {
        editor.remove("token");
        editor.remove("authorization");
        editor.remove("userId");
        editor.remove("name");
        editor.remove("username");
        editor.remove("birthday");
        editor.remove("email");
        editor.remove("mobile");
        editor.remove("status");
        editor.remove("type");
        editor.remove("gender");
        editor.remove("avatar");
        editor.remove("public_key");
        editor.remove("private_key");
        editor.remove("money");
        editor.apply();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////













    public String getNotification() {
        if (!sharedPreferences.getString("notification", "").equals("")) {
            return sharedPreferences.getString("notification", "");
        }
        return "";
    }

    public String getEducation() {
        if (!sharedPreferences.getString("education", "").equals("")) {
            return sharedPreferences.getString("education", "");
        }
        return "";
    }





    public String getPassword() {
        if (!sharedPreferences.getString("password", "").equals("")) {
            return sharedPreferences.getString("password", "");
        }
        return "";
    }


    public String getOwner() {
        if (!sharedPreferences.getString("owner", "").equals("")) {
            return sharedPreferences.getString("owner", "");
        }
        return "";
    }

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

    public String getManager() {
        if (!sharedPreferences.getString("manager", "").equals("")) {
            return sharedPreferences.getString("manager", "");
        }
        return "";
    }

    public String getPsychology() {
        if (!sharedPreferences.getString("psychology", "").equals("")) {
            return sharedPreferences.getString("psychology", "");
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

    public boolean getEnter() {
        return sharedPreferences.getBoolean("enter", true);
    }

    public boolean getCreateCase() {
        return sharedPreferences.getBoolean("createCase", true);
    }

    public boolean getBulkSession() {
        return sharedPreferences.getBoolean("bulkSession", false);
    }

    public boolean getEndScheduleTime() {
        return sharedPreferences.getBoolean("endScheduleTime", false);
    }

}