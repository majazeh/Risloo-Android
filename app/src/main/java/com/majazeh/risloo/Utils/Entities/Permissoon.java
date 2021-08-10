package com.majazeh.risloo.Utils.Entities;

import com.mre.ligheh.Model.TypeModel.UserModel;

public class Permissoon {

    /*
    ---------- EditUserTabPersonal ----------
    */

    public static boolean showEditUserTabPersonalMobile(UserModel model) {
        return model.getUserType().equals("admin");
    }

    public static boolean showEditUserTabPersonalEmail(UserModel model) {
        return model.getUserType().equals("admin");
    }

    public static boolean showEditUserTabPersonalBirthday(UserModel model) {
        return model.getUserType().equals("admin");
    }

    public static boolean showEditUserTabPersonalStatus(UserModel model) {
        return model.getUserType().equals("admin");
    }

    public static boolean showEditUserTabPersonalType(UserModel model) {
        return model.getUserType().equals("admin");
    }

    /*
    ---------- EditUserTabPassword ----------
    */

    public static boolean showEditUserTabPasswordCurrent(UserModel model) {
        return !model.getUserType().equals("admin") && !model.isNo_password();
    }

}