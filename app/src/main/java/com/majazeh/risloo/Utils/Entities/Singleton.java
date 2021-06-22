package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

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

    public void login(AuthModel object) {
        UserModel user = object.getUser();

        if (object.getToken() != null)
            setToken(object.getToken());

        if (object.getToken() != null)
            setAuthorization("Bearer " + object.getToken());

        if (user.getId() != null)
            setId(user.getId());

        if (user.getName() != null)
            setName(user.getName());

        if (user.getUsername() != null)
            setUsername(user.getUsername());

        if (user.getBirthday() != null)
            setBirthday(user.getBirthday());

        if (user.getEmail() != null)
            setEmail(user.getEmail());

        if (user.getMobile() != null)
            setMobile(user.getMobile());

        if (user.getUserStatus() != null)
            setStatus(user.getUserStatus());

        if (user.getUserType() != null)
            setType(user.getUserType());

        if (user.getGender() != null)
            setGender(user.getGender());

        if (user.getAvatar() != null && user.getAvatar().getMedium() != null && user.getAvatar().getMedium().getUrl() != null)
            setAvatar(user.getAvatar().getMedium().getUrl());

        if (user.getPublic_key() != null)
            setPublicKey(user.getPublic_key());

        if (user.getTreasuries() != null) {
            try {
                int money = 0;
                for (int i = 0; i < user.getTreasuries().length(); i++) {
                    if (user.getTreasuries().getJSONObject(i).getString("symbol").equals("gift") || user.getTreasuries().getJSONObject(i).getString("symbol").equals("wallet") )
                        money += user.getTreasuries().getJSONObject(i).getInt("balance");
                }
                setMoney(String.valueOf(money));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(UserModel object) {
        if (object.getId() != null)
            setId(object.getId());

        if (object.getName() != null)
            setName(object.getName());

        if (object.getUsername() != null)
            setUsername(object.getUsername());

        if (object.getBirthday() != null)
            setBirthday(object.getBirthday());

        if (object.getEmail() != null)
            setEmail(object.getEmail());

        if (object.getMobile() != null)
            setMobile(object.getMobile());

        if (object.getUserStatus() != null)
            setStatus(object.getUserStatus());

        if (object.getUserType() != null)
            setType(object.getUserType());

        if (object.getGender() != null)
            setGender(object.getGender());

        if (object.getAvatar() != null && object.getAvatar().getMedium() != null && object.getAvatar().getMedium().getUrl() != null)
            setAvatar(object.getAvatar().getMedium().getUrl());

        if (object.getPublic_key() != null)
            setPublicKey(object.getPublic_key());

        if (object.getTreasuries() != null) {
            try {
                int money = 0;
                for (int i = 0; i < object.getTreasuries().length(); i++) {
                    if (object.getTreasuries().getJSONObject(i).getString("symbol").equals("gift") || object.getTreasuries().getJSONObject(i).getString("symbol").equals("wallet") )
                        money += object.getTreasuries().getJSONObject(i).getInt("balance");
                }
                setMoney(String.valueOf(money));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void logout() {
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

    public void setToken(String value) {
        editor.putString("token", value);
        editor.apply();
    }

    public void setAuthorization(String value) {
        editor.putString("authorization", value);
        editor.apply();
    }

    public void setId(String value) {
        editor.putString("id", value);
        editor.apply();
    }

    public void setName(String value) {
        editor.putString("name", value);
        editor.apply();
    }

    public void setUsername(String value) {
        editor.putString("username", value);
        editor.apply();
    }

    public void setEducation(String value) {
        editor.putString("education", value);
        editor.apply();
    }

    public void setBirthday(String value) {
        editor.putString("birthday", value);
        editor.apply();
    }

    public void setEmail(String value) {
        editor.putString("email", value);
        editor.apply();
    }

    public void setMobile(String value) {
        editor.putString("mobile", value);
        editor.apply();
    }

    public void setStatus(String value) {
        editor.putString("status", value);
        editor.apply();
    }

    public void setType(String value) {
        editor.putString("type", value);
        editor.apply();
    }

    public void setGender(String value) {
        editor.putString("gender", value);
        editor.apply();
    }

    public void setAvatar(String value) {
        editor.putString("avatar", value);
        editor.apply();
    }

    public void setPublicKey(String value) {
        editor.putString("public_key", value);
        editor.apply();
    }

    public void setPrivateKey(String value) {
        editor.putString("private_key", value);
        editor.apply();
    }

    public void setMoney(String value) {
        editor.putString("money", value);
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

    public String getId() {
        if (!sharedPreferences.getString("id", "").equals("")) {
            return sharedPreferences.getString("id", "");
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

    public String getMoney() {
        if (!sharedPreferences.getString("money", "").equals("")) {
            return sharedPreferences.getString("money", "");
        }
        return "";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getNotification() {
        if (!sharedPreferences.getString("notification", "").equals("")) {
            return sharedPreferences.getString("notification", "");
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