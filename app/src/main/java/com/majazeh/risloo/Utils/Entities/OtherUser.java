package com.majazeh.risloo.Utils.Entities;

import com.mre.ligheh.Model.TypeModel.UserModel;

public class OtherUser {

    // Instance
    private static OtherUser instance;

    // Models
    private UserModel userModel = null;

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

    public void login(UserModel userModel) {
        if (userModel != null)
            setUserModel(userModel);
    }

    public void logout() {
        this.userModel = null;
    }

    /*
    ---------- Setters ----------
    */

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    /*
    ---------- Getters ----------
    */

    public UserModel getUserModel() {
        return userModel;
    }

}