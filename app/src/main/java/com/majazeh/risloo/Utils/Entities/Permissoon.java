package com.majazeh.risloo.Utils.Entities;

import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

public class Permissoon {

    public Permissoon() {
        // TODO : Place Code If Needed
    }

    /*
    ---------- EditUserTabPersonal ----------
    */

    public boolean showEditUserTabPersonalMobile(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showEditUserTabPersonalEmail(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showEditUserTabPersonalBirthday(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showEditUserTabPersonalStatus(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showEditUserTabPersonalType(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    /*
    ---------- EditUserTabPassword ----------
    */

    public boolean showEditUserTabPasswordCurrent(UserModel model) {
        if (model != null)
            return !model.getUserType().equals("admin") && !model.isNo_password();
        else
            return false;
    }

    /*
    ---------- Centers ----------
    */

    public boolean showCentersCreateCenter(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    /*
    ---------- Center ----------
    */

    public boolean showCenterDropdownUsers(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public boolean showCenterDropdownEdit(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public boolean showCenterDropdownPlatforms(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public boolean showCenterCreateRoom(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager");
        else
            return false;
    }

    /*
    ---------- Room ----------
    */

    public boolean showRoomDropdownCreateSchedule(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public boolean showRoomDropdownUsers(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public boolean showRoomDropdownPlatforms(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public boolean showRoomDropdownTags(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    public boolean showRoomCreateCase(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return false;
    }

    /*
    ---------- Case ----------
    */

    public boolean showCaseDropdownReports(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("operator");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showCaseDropdownEdit(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("operator");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showCaseCreateSession(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("operator");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showCaseCreateSample(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("operator");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    /*
    ---------- Session ----------
    */

    public boolean showSessionDropdownReports(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("operator");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionDropdownEdit(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("operator");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreateUser(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("operator");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreatePractice(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("operator");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreateSample(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("operator");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    /*
    ---------- Scales ----------
    */

    public boolean showScalesCreateSample(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    /*
    ---------- Samples ----------
    */

    public boolean showSamplesCreateSample(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    /*
    ---------- Main ----------
    */

    public boolean showBulkSamples(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showUsers(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

}