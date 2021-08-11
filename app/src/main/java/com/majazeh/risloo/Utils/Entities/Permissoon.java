package com.majazeh.risloo.Utils.Entities;

import com.mre.ligheh.Model.TypeModel.AcceptationModel;
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

    public static boolean showCenterDropdownUsers(String position) {
        return position.equals("admin") || position.equals("manager") || position.equals("operator");
    }

    public static boolean showCenterDropdownEdit(String position) {
        return position.equals("admin") || position.equals("manager") || position.equals("operator");
    }

    public static boolean showCenterDropdownPlatforms(String position) {
        return position.equals("admin") || position.equals("manager") || position.equals("operator");
    }

    public static boolean showCenterCreateRoom(String position) {
        return position.equals("admin") || position.equals("manager");
    }

    /*
    ---------- Room ----------
    */

    public static boolean showRoomDropdownCreateSchedule(String position) {
        return position.equals("admin") || position.equals("manager") || position.equals("operator");
    }

    public static boolean showRoomDropdownUsers(String position) {
        return position.equals("admin") || position.equals("manager") || position.equals("operator");
    }

    public static boolean showRoomDropdownPlatforms(String position) {
        return position.equals("admin") || position.equals("manager") || position.equals("operator");
    }

    public static boolean showRoomDropdownTags(String position) {
        return position.equals("admin") || position.equals("manager") || position.equals("operator");
    }

}