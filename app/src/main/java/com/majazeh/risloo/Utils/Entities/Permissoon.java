package com.majazeh.risloo.Utils.Entities;

import com.mre.ligheh.Model.TypeModel.UserModel;

public class Permissoon {

    /*
    ---------- EditUserTabPersonal ----------
    */

    public static boolean showEditUserTabPersonalMobile(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public static boolean showEditUserTabPersonalEmail(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public static boolean showEditUserTabPersonalBirthday(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public static boolean showEditUserTabPersonalStatus(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public static boolean showEditUserTabPersonalType(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    /*
    ---------- EditUserTabPassword ----------
    */

    public static boolean showEditUserTabPasswordCurrent(UserModel model) {
        if (model != null)
            return !model.getUserType().equals("admin") && !model.isNo_password();
        else
            return false;
    }

    /*
    ---------- Centers ----------
    */

    public static boolean showCentersCreateCenter(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    /*
    ---------- Center ----------
    */

    public static boolean showCenterDropdownUsers(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public static boolean showCenterDropdownEdit(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public static boolean showCenterDropdownPlatforms(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public static boolean showCenterCreateRoom(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager");
        else
            return false;
    }

    /*
    ---------- Room ----------
    */

    public static boolean showRoomDropdownCreateSchedule(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public static boolean showRoomDropdownUsers(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public static boolean showRoomDropdownPlatforms(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public static boolean showRoomDropdownTags(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

}