package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

public class Singleton {

    // Objects
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public Singleton(@NonNull Activity activity) {
        sharedPreferences = activity.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();
        editor.apply();
    }

    /*
    ---------- Voids ----------
    */

    public void login(AuthModel authModel) {
        if (authModel.getToken() != null)
            setToken(authModel.getToken());

        if (authModel.getToken() != null)
            setAuthorization("Bearer " + authModel.getToken());

        if (authModel.getUser() != null)
            setUserModel(authModel.getUser());
    }

    public void update(UserModel userModel) {
        if (userModel != null)
            setUserModel(userModel);
    }

    public void password(String password) {
        if (password != null)
            setPassword(password);
    }

    public void logout() {
        editor.remove("token");
        editor.remove("authorization");
        editor.remove("usermodel");
        editor.apply();
    }

    /*
    ---------- Setters ----------
    */

    private void setToken(String value) {
        editor.putString("token", value);
        editor.apply();
    }

    private void setAuthorization(String value) {
        editor.putString("authorization", value);
        editor.apply();
    }

    private void setUserModel(UserModel userModel) {
        editor.putString("usermodel", new Gson().toJson(userModel));
        editor.apply();
    }

    private void setPassword(String value) {
        editor.putString("password", value);
        editor.apply();
    }

    /*
    ---------- Getters ----------
    */

    public String getToken() {
        if (!sharedPreferences.getString("token", "").equals(""))
            return sharedPreferences.getString("token", "");

        return "";
    }

    public String getAuthorization() {
        if (!sharedPreferences.getString("authorization", "").equals(""))
            return sharedPreferences.getString("authorization", "");

        return "";
    }

    public UserModel getUserModel() {
        if (!sharedPreferences.getString("usermodel", "").equals(""))
            return new Gson().fromJson(sharedPreferences.getString("usermodel", ""), UserModel.class);

        return null;
    }

    public String getPassword() {
        if (!sharedPreferences.getString("password", "").equals(""))
            return sharedPreferences.getString("password", "");

        return "";
    }

    /*
    ---------- UserModel Properties ----------
    */

    public String getId() {
        if (getUserModel().getId() != null)
            return getUserModel().getId();

        return "";
    }

    public String getName() {
        if (getUserModel().getName() != null)
            return getUserModel().getName();

        return "";
    }

    public String getAvatar() {
        if (getUserModel().getAvatar() != null && getUserModel().getAvatar().getMedium() != null && getUserModel().getAvatar().getMedium().getUrl() != null && !getUserModel().getAvatar().getMedium().getUrl().equals(""))
            return getUserModel().getAvatar().getMedium().getUrl();

        return "";
    }

    public String getMoney() {
        return "";
    }

}