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

    public boolean getAuth() {
        return sharedPreferences.getBoolean("auth", true);
    }

    public String getNotification() {
        if (!sharedPreferences.getString("notification", "").equals("")) {
            return sharedPreferences.getString("notification", "");
        }
        return "";
    }

    public String getName() {
        if (!sharedPreferences.getString("name", "").equals("")) {
            return sharedPreferences.getString("name", "");
        }
        return "";
    }

    public String getUsername() {
        if (!sharedPreferences.getString("username", "").equals("")) {
            return sharedPreferences.getString("username", "");
        }
        return "";
    }

    public String getEducation() {
        if (!sharedPreferences.getString("education", "").equals("")) {
            return sharedPreferences.getString("education", "");
        }
        return "";
    }

    public String getBirthday() {
        if (!sharedPreferences.getString("birthday", "").equals("")) {
            return sharedPreferences.getString("birthday", "");
        }
        return "";
    }

    public String getEmail() {
        if (!sharedPreferences.getString("email", "").equals("")) {
            return sharedPreferences.getString("email", "");
        }
        return "";
    }

    public String getMobile() {
        if (!sharedPreferences.getString("mobile", "").equals("")) {
            return sharedPreferences.getString("mobile", "");
        }
        return "";
    }

    public String getMoney() {
        if (!sharedPreferences.getString("money", "").equals("")) {
            return sharedPreferences.getString("money", "");
        }
        return "";
    }

    public String getStatus() {
        if (!sharedPreferences.getString("status", "").equals("")) {
            return sharedPreferences.getString("status", "");
        }
        return "";
    }

    public String getType() {
        if (!sharedPreferences.getString("type", "").equals("")) {
            return sharedPreferences.getString("type", "");
        }
        return "";
    }

    public String getGender() {
        if (!sharedPreferences.getString("gender", "").equals("")) {
            return sharedPreferences.getString("gender", "");
        }
        return "";
    }

    public String getAvatar() {
        if (!sharedPreferences.getString("avatar", "").equals("")) {
            return sharedPreferences.getString("avatar", "");
        }
        return "";
    }

    public String getPassword() {
        if (!sharedPreferences.getString("password", "").equals("")) {
            return sharedPreferences.getString("password", "");
        }
        return "";
    }

    public String getPublicKey() {
        if (!sharedPreferences.getString("public_key", "").equals("")) {
            return sharedPreferences.getString("public_key", "");
        }
        return "";
    }

    public String getPrivateKey() {
        if (!sharedPreferences.getString("private_key", "").equals("")) {
            return sharedPreferences.getString("private_key", "");
        }
        return "";
    }

}