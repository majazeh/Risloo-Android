package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class Singleton {

    // Objects
    private final Activity activity;
    public final SharedPreferences sharedPreferences;
    public final SharedPreferences.Editor editor;

    public Singleton(@NonNull Activity activity) {
        this.activity = activity;

        sharedPreferences = activity.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();
        editor.apply();
    }

    public String getToken() {
        if (!sharedPreferences.getString("token", "").equals("")) {
            return sharedPreferences.getString("token", "");
        }
        return "";
    }

    public String getUserId() {
        if (!sharedPreferences.getString("userId", "").equals("")) {
            return sharedPreferences.getString("userId", "");
        }
        return "";
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
    public boolean getCreateCase() {
        return sharedPreferences.getBoolean("createCase", true);
    }

    public boolean getBulkSession() {
        return sharedPreferences.getBoolean("bulkSession", false);
    }

    public boolean getEndScheduleTime() {
        return sharedPreferences.getBoolean("endScheduleTime", false);
    }

    public void logout() {
        editor.remove("token");
        editor.remove("userId");
        editor.remove("auth");
        editor.remove("notification");
        editor.remove("name");
        editor.remove("username");
        editor.remove("education");
        editor.remove("birthday");
        editor.remove("email");
        editor.remove("mobile");
        editor.remove("money");
        editor.remove("status");
        editor.remove("type");
        editor.remove("gender");
        editor.remove("avatar");
        editor.remove("password");
        editor.remove("public_key");
        editor.remove("private_key");
        editor.remove("owner");
        editor.remove("description");
        editor.remove("start_date");
        editor.remove("start_time");
        editor.remove("duration");
        editor.remove("manager");
        editor.remove("psychology");
        editor.remove("address");
        editor.remove("progress");
        editor.remove("createCase");
        editor.remove("bulkSession");
        editor.remove("endScheduleTime");
        editor.apply();
    }

}