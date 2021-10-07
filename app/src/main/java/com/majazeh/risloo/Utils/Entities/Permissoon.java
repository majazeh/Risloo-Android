package com.majazeh.risloo.Utils.Entities;

import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

public class Permissoon {

    public Permissoon() {
        // TODO : Place Code If Needed
    }

    /*
    ---------- Me ----------
    */

    public boolean showMeEdit(UserModel model) {
        return true;
    }

    /*
    ---------- User ----------
    */

    public boolean showUserLogin(UserModel model) {
        return false;
    }

    public boolean showUserEdit(UserModel model) {
        return true;
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

    public boolean showCenterDropdownSchedules(String position) {
        if (!position.equals(""))
            return position.equals("request");
        else
            return false;
    }

    public boolean showCenterDropdownProfile(String position) {
        if (!position.equals(""))
            return !position.equals("request");
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

    public boolean showRoomDropdownSchedules(String position) {
        if (!position.equals(""))
            return position.equals("request");
        else
            return false;
    }

    public boolean showRoomDropdownProfile(String position) {
        if (!position.equals(""))
            return !position.equals("request");
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
    ---------- Schedules ----------
    */

    public boolean showReserveScheduleFragment(UserModel userModel, ScheduleModel scheduleModel) {
        if (userModel != null && scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getRoomAcceptation() != null && scheduleModel.getRoom().getRoomCenter() != null && scheduleModel.getRoom().getRoomCenter().getAcceptation() != null)
            return !userModel.getUserType().equals("admin") && !userModel.getUserType().equals("system") || (scheduleModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getRoomAcceptation().getPosition().equals("operator")) || (scheduleModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("operator"));
        else if (userModel != null && scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getRoomCenter() != null && scheduleModel.getRoom().getRoomCenter().getAcceptation() != null)
            return !userModel.getUserType().equals("admin") && !userModel.getUserType().equals("system") || (scheduleModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("operator"));
        else if (userModel != null && scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getRoomAcceptation() != null)
            return !userModel.getUserType().equals("admin") && !userModel.getUserType().equals("system") || (scheduleModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getRoomAcceptation().getPosition().equals("operator"));
        else if (userModel != null)
            return !userModel.getUserType().equals("admin") && !userModel.getUserType().equals("system");
        else
            return false;
    }

    /*
    ---------- Reserve Schedule ----------
    */

    public boolean showReserveScheduleClientType(ScheduleModel model) {
        if (model != null && model.getRoom() != null && model.getRoom().getRoomAcceptation() != null && model.getRoom().getRoomCenter() != null && model.getRoom().getRoomCenter().getAcceptation() != null)
            return model.getRoom().getRoomAcceptation().getPosition().equals("manager") || model.getRoom().getRoomAcceptation().getPosition().equals("operator") || model.getRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || model.getRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (model != null && model.getRoom() != null && model.getRoom().getRoomCenter() != null && model.getRoom().getRoomCenter().getAcceptation() != null)
            return model.getRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || model.getRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (model != null && model.getRoom() != null && model.getRoom().getRoomAcceptation() != null)
            return model.getRoom().getRoomAcceptation().getPosition().equals("manager") || model.getRoom().getRoomAcceptation().getPosition().equals("operator");
        else
            return false;
    }

    public boolean showReserveScheduleCase(ScheduleModel model) {
        if (model != null && model.getRoom() != null && model.getRoom().getRoomCenter() != null && model.getRoom().getRoomCenter().getAcceptation() != null)
            return model.getRoom().getRoomCenter().getAcceptation().getPosition().equals("client") && model.getRoom().getRoomCenter().getAcceptation().getKicked_at().equals("");
        else
            return false;
    }

    public boolean showReserveScheduleName(ScheduleModel model) {
        if (model != null && model.getRoom() != null && model.getRoom().getRoomCenter() != null && model.getRoom().getRoomCenter().getAcceptation() != null)
            return model.getRoom().getRoomCenter().getAcceptation().getPosition().equals("client") && !model.getRoom().getRoomCenter().getAcceptation().getKicked_at().equals("");
        else
            return false;
    }

    public boolean showReserveScheduleTreasury(ScheduleModel model) {
        if (model != null && model.getRoom() != null && model.getRoom().getRoomAcceptation() != null && model.getRoom().getRoomCenter() != null && model.getRoom().getRoomCenter().getAcceptation() != null)
            return model.getRoom().getRoomAcceptation().getPosition().equals("manager") || model.getRoom().getRoomAcceptation().getPosition().equals("operator") || model.getRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || model.getRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (model != null && model.getRoom() != null && model.getRoom().getRoomCenter() != null && model.getRoom().getRoomCenter().getAcceptation() != null)
            return model.getRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || model.getRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (model != null && model.getRoom() != null && model.getRoom().getRoomAcceptation() != null)
            return model.getRoom().getRoomAcceptation().getPosition().equals("manager") || model.getRoom().getRoomAcceptation().getPosition().equals("operator");
        else
            return false;
    }

    /*
    ---------- Case ----------
    */

    public boolean showCaseDropdownReports(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null && caseModel.getCaseRoom().getRoomCenter() != null && caseModel.getCaseRoom().getRoomCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showCaseDropdownEdit(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null && caseModel.getCaseRoom().getRoomCenter() != null && caseModel.getCaseRoom().getRoomCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showCaseCreateReference(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null && caseModel.getCaseRoom().getRoomCenter() != null && caseModel.getCaseRoom().getRoomCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showCaseCreateSession(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null && caseModel.getCaseRoom().getRoomCenter() != null && caseModel.getCaseRoom().getRoomCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showCaseCreateSample(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null && caseModel.getCaseRoom().getRoomCenter() != null && caseModel.getCaseRoom().getRoomCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getRoomAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    /*
    ---------- Session ----------
    */

    public boolean showSessionDropdownReports(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null && sessionModel.getRoom().getRoomCenter() != null && sessionModel.getRoom().getRoomCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionDropdownEdit(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null && sessionModel.getRoom().getRoomCenter() != null && sessionModel.getRoom().getRoomCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreateUser(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null && sessionModel.getRoom().getRoomCenter() != null && sessionModel.getRoom().getRoomCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreatePractice(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null && sessionModel.getRoom().getRoomCenter() != null && sessionModel.getRoom().getRoomCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreateSample(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null && sessionModel.getRoom().getRoomCenter() != null && sessionModel.getRoom().getRoomCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreateBill(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null && sessionModel.getRoom().getRoomCenter() != null && sessionModel.getRoom().getRoomCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getRoomCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getRoomAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getRoomAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    /*
    ---------- Scales ----------
    */

    public boolean showScalesCreateSample(UserModel model) {
        if (model != null) {
            if (model.getUserType().equals("admin"))
                return true;

            if (!model.getCenterList().data().isEmpty())
                for (TypeModel typeModel : model.getCenterList().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null)
                        if (centerModel.getAcceptation().getPosition().equals("manager") || centerModel.getAcceptation().getPosition().equals("operator") || centerModel.getAcceptation().getPosition().equals("psychologist"))
                            return true;
                }

        } return false;
    }

    /*
    ---------- Samples ----------
    */

    public boolean showSamplesCreateSample(UserModel model) {
        if (model != null) {
            if (model.getUserType().equals("admin"))
                return true;

            if (!model.getCenterList().data().isEmpty())
                for (TypeModel typeModel : model.getCenterList().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null)
                        if (centerModel.getAcceptation().getPosition().equals("manager") || centerModel.getAcceptation().getPosition().equals("operator") || centerModel.getAcceptation().getPosition().equals("psychologist"))
                            return true;
                }

        } return false;
    }

    /*
    ---------- Main ----------
    */

    public boolean showBulkSamples(UserModel model) {
        if (model != null) {
            if (model.getUserType().equals("admin"))
                return true;

            if (!model.getCenterList().data().isEmpty())
                for (TypeModel typeModel : model.getCenterList().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null)
                        if (centerModel.getAcceptation().getPosition().equals("manager") || centerModel.getAcceptation().getPosition().equals("operator") || centerModel.getAcceptation().getPosition().equals("psychologist"))
                            return true;
                }

        } return false;
    }

    public boolean showUsers(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

}