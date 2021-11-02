package com.majazeh.risloo.Utils.Entities;

import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

public class OtherUser {

    // Instance
    private static OtherUser instance;

    // Models
    private UserModel userModel = null;

    // Vars
    private String token = "", authorization = "";

    /*
    ---------- Intialize ----------
    */

    private OtherUser() {
        // TODO : Place Code If Needed
    }

    public static synchronized OtherUser getInstance() {
        if (instance == null) {
            instance = new OtherUser();
        }

        return instance;
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

    public void logout() {
        this.token = "";
        this.authorization = "";
        this.userModel = null;
    }

    /*
    ---------- Setters ----------
    */

    public void setToken(String token) {
        this.token = token;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    /*
    ---------- Getters ----------
    */

    public String getToken() {
        return token;
    }

    public String getAuthorization() {
        return authorization;
    }

    public UserModel getUserModel() {
        return userModel;
    }

}