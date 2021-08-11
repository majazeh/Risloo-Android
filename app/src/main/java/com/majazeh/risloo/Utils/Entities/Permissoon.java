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

    public static boolean showCenterDropdownUsers(AcceptationModel model) {
        if (model != null)
            return model.getPosition().equals("admin") || model.getPosition().equals("manager") || model.getPosition().equals("operator");
        else
            return false;
    }

    public static boolean showCenterDropdownEdit(AcceptationModel model) {
        if (model != null)
            return model.getPosition().equals("admin") || model.getPosition().equals("manager") || model.getPosition().equals("operator");
        else
            return false;
    }

    public static boolean showCenterDropdownPlatforms(AcceptationModel model) {
        if (model != null)
            return model.getPosition().equals("admin") || model.getPosition().equals("manager") || model.getPosition().equals("operator");
        else
            return false;
    }

    public static boolean showCenterDropdownTags(AcceptationModel model) {
        if (model != null)
            return model.getPosition().equals("admin") || model.getPosition().equals("manager") || model.getPosition().equals("operator");
        else
            return false;
    }

    public static boolean showCenterCreateRoom(AcceptationModel model) {
        if (model != null)
            return model.getPosition().equals("admin") || model.getPosition().equals("manager");
        else
            return false;
    }

    /*
    ---------- Room ----------
    */

    public static boolean showRoomDropdownCreateSchedule(AcceptationModel model) {
        if (model != null)
            return model.getPosition().equals("admin") || model.getPosition().equals("manager") || model.getPosition().equals("operator");
        else
            return false;
    }

    public static boolean showRoomDropdownUsers(AcceptationModel model) {
        if (model != null)
            return model.getPosition().equals("admin") || model.getPosition().equals("manager") || model.getPosition().equals("operator");
        else
            return false;
    }

    public static boolean showRoomDropdownPlatforms(AcceptationModel model) {
        if (model != null)
            return model.getPosition().equals("admin") || model.getPosition().equals("manager") || model.getPosition().equals("operator");
        else
            return false;
    }

    public static boolean showRoomDropdownTags(AcceptationModel model) {
        if (model != null)
            return model.getPosition().equals("admin") || model.getPosition().equals("manager") || model.getPosition().equals("operator");
        else
            return false;
    }

}