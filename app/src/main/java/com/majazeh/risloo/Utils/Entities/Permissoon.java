package com.majazeh.risloo.utils.entities;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

public class Permissoon {

    // Objects
    private final Activity activity;

    /*
    ---------- Intialize ----------
    */

    public Permissoon(@NonNull Activity activity) {
        this.activity = activity;
    }

    /*
    ---------- Main ----------
    */

    public boolean showUsers(UserModel userModel) {
        if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showBulkSamples(UserModel userModel) {
        if (userModel != null) {
            if (userModel.getType().equals("admin"))
                return true;

            if (!userModel.getCenters().data().isEmpty())
                for (TypeModel typeModel : userModel.getCenters().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null)
                        if (centerModel.getAcceptation().getPosition().equals("manager") || centerModel.getAcceptation().getPosition().equals("operator") || centerModel.getAcceptation().getPosition().equals("psychologist"))
                            return true;
                }

        }

        return false;
    }

    public boolean showScales(UserModel userModel) {
        if (userModel != null) {
            if (userModel.getType().equals("admin") || userModel.getType().equals("operator"))
                return true;

            if (!userModel.getCenters().data().isEmpty())
                for (TypeModel typeModel : userModel.getCenters().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null)
                        if (centerModel.getAcceptation().getPosition().equals("manager") || centerModel.getAcceptation().getPosition().equals("operator") || centerModel.getAcceptation().getPosition().equals("psychologist"))
                            return true;
                }

        }

        return false;
    }

    /*
    ---------- Create ----------
    */

    // -------------------- CreateUser

    public boolean showCreateUserBirthday() {
        return false;
    }

    // -------------------- ReserveSchedule

    public boolean showReserveScheduleClientType(ScheduleModel scheduleModel) {
        if (scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getAcceptation() != null && scheduleModel.getRoom().getCenter() != null && scheduleModel.getRoom().getCenter().getAcceptation() != null)
            return scheduleModel.getRoom().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getAcceptation().getPosition().equals("operator") || scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getCenter() != null && scheduleModel.getRoom().getCenter().getAcceptation() != null)
            return scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getAcceptation() != null)
            return scheduleModel.getRoom().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getAcceptation().getPosition().equals("operator");
        else
            return false;
    }

    public boolean showReserveScheduleCase(ScheduleModel scheduleModel) {
        if (scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getCenter() != null && scheduleModel.getRoom().getCenter().getAcceptation() != null)
            return scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("client") && scheduleModel.getRoom().getCenter().getAcceptation().getKickedAt().equals("");
        else
            return false;
    }

    public boolean showReserveScheduleName(ScheduleModel scheduleModel) {
        if (scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getCenter() != null && scheduleModel.getRoom().getCenter().getAcceptation() != null)
            return scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("client") && !scheduleModel.getRoom().getCenter().getAcceptation().getKickedAt().equals("");
        else
            return true;
    }

    public boolean showReserveScheduleTreasury(ScheduleModel scheduleModel) {
        if (scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getAcceptation() != null && scheduleModel.getRoom().getCenter() != null && scheduleModel.getRoom().getCenter().getAcceptation() != null)
            return scheduleModel.getRoom().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getAcceptation().getPosition().equals("operator") || scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getCenter() != null && scheduleModel.getRoom().getCenter().getAcceptation() != null)
            return scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getAcceptation() != null)
            return scheduleModel.getRoom().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getAcceptation().getPosition().equals("operator");
        else
            return false;
    }

    /*
    ---------- Edit ----------
    */



    /*
    ---------- Index ----------
    */

    // -------------------- Centers

    public boolean showCentersCreateCenter(UserModel userModel) {
        if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    // -------------------- CenterSchedules

    public boolean showCenterSchedulesFragmentReserveSchedule(UserModel userModel, ScheduleModel scheduleModel) {
        if (userModel != null && scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getAcceptation() != null && scheduleModel.getRoom().getCenter() != null && scheduleModel.getRoom().getCenter().getAcceptation() != null)
            return !userModel.getType().equals("admin") && !userModel.getType().equals("system") || (scheduleModel.getRoom().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getAcceptation().getPosition().equals("operator")) || (scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator"));
        else if (userModel != null && scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getCenter() != null && scheduleModel.getRoom().getCenter().getAcceptation() != null)
            return !userModel.getType().equals("admin") && !userModel.getType().equals("system") || (scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator"));
        else if (userModel != null && scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getAcceptation() != null)
            return !userModel.getType().equals("admin") && !userModel.getType().equals("system") || (scheduleModel.getRoom().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getAcceptation().getPosition().equals("operator"));
        else if (userModel != null)
            return !userModel.getType().equals("admin") && !userModel.getType().equals("system");
        else
            return false;
    }

    // -------------------- Samples

    public boolean showSamplesCreateSample(UserModel userModel) {
        if (userModel != null) {
            if (userModel.getType().equals("admin"))
                return true;

            if (!userModel.getCenters().data().isEmpty())
                for (TypeModel typeModel : userModel.getCenters().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null)
                        if (centerModel.getAcceptation().getPosition().equals("manager") || centerModel.getAcceptation().getPosition().equals("operator") || centerModel.getAcceptation().getPosition().equals("psychologist"))
                            return true;
                }

        }

        return false;
    }

    public boolean showSamplesFragmentSample(UserModel userModel, SampleModel sampleModel) {
        if (userModel != null && sampleModel != null && sampleModel.getCasse() != null && sampleModel.getCasse().getRoom() != null && sampleModel.getCasse().getRoom().getAcceptation() != null && sampleModel.getCasse().getRoom().getCenter() != null && sampleModel.getCasse().getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || sampleModel.getCasse().getRoom().getAcceptation().getPosition().equals("manager") || sampleModel.getCasse().getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sampleModel.getCasse().getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sampleModel != null && sampleModel.getRoom() != null && sampleModel.getRoom().getAcceptation() != null && sampleModel.getRoom().getCenter() != null && sampleModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || sampleModel.getRoom().getAcceptation().getPosition().equals("manager") || sampleModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sampleModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sampleModel != null && sampleModel.getCasse() != null && sampleModel.getCasse().getRoom() != null && sampleModel.getCasse().getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || sampleModel.getCasse().getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null && sampleModel != null && sampleModel.getRoom() != null && sampleModel.getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || sampleModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    // -------------------- Scales

    public boolean showScalesCreateSample(UserModel userModel) {
        if (userModel != null) {
            if (userModel.getType().equals("admin"))
                return true;

            if (!userModel.getCenters().data().isEmpty())
                for (TypeModel typeModel : userModel.getCenters().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null)
                        if (centerModel.getAcceptation().getPosition().equals("manager") || centerModel.getAcceptation().getPosition().equals("operator") || centerModel.getAcceptation().getPosition().equals("psychologist"))
                            return true;
                }

        }

        return false;
    }

    /*
    ---------- Show ----------
    */

    // -------------------- Case

    public boolean showCaseDropdownReports(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getRoom() != null && caseModel.getRoom().getAcceptation() != null && caseModel.getRoom().getCenter() != null && caseModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || caseModel.getRoom().getAcceptation().getPosition().equals("manager") || caseModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || caseModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getRoom() != null && caseModel.getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || caseModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showCaseDropdownEdit(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getRoom() != null && caseModel.getRoom().getAcceptation() != null && caseModel.getRoom().getCenter() != null && caseModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || caseModel.getRoom().getAcceptation().getPosition().equals("manager") || caseModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || caseModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getRoom() != null && caseModel.getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || caseModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showCaseCreateReference(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getRoom() != null && caseModel.getRoom().getAcceptation() != null && caseModel.getRoom().getCenter() != null && caseModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || caseModel.getRoom().getAcceptation().getPosition().equals("manager") || caseModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || caseModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getRoom() != null && caseModel.getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || caseModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showCaseCreateSession(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getRoom() != null && caseModel.getRoom().getAcceptation() != null && caseModel.getRoom().getCenter() != null && caseModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || caseModel.getRoom().getAcceptation().getPosition().equals("manager") || caseModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || caseModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getRoom() != null && caseModel.getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || caseModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showCaseCreateSample(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getRoom() != null && caseModel.getRoom().getAcceptation() != null && caseModel.getRoom().getCenter() != null && caseModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || caseModel.getRoom().getAcceptation().getPosition().equals("manager") || caseModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || caseModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getRoom() != null && caseModel.getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || caseModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    // -------------------- Center

    public boolean showCenterDropdownUsers(UserModel userModel, String position) {
        if (userModel != null)
            return userModel.getType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showCenterDropdownEdit(UserModel userModel, String position) {
        if (userModel != null)
            return userModel.getType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showCenterDropdownPlatforms(UserModel userModel, String position) {
        if (userModel != null)
            return userModel.getType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showCenterDropdownRooms(UserModel userModel, String position) {
        if (userModel != null)
            return userModel.getType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showCenterDropdownAccounting(UserModel userModel, String position) {
        if (userModel != null)
            return userModel.getType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showCenterCreateRoom(UserModel userModel, String position) {
        if (userModel != null)
            return userModel.getType().equals("admin") || position.equals("manager");
        else
            return position.equals("manager");
    }

    // -------------------- Dashboard

    public boolean showDashboardData(UserModel userModel) {
        if (userModel != null)
            return !userModel.getType().equals("admin");
        else
            return false;
    }

    // -------------------- Room

    public boolean showRoomDropdownUsers(UserModel userModel, String position) {
        if (userModel != null)
            return userModel.getType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showRoomDropdownCreateSchedule(UserModel userModel, String position, String type) {
        if (userModel != null)
            return (userModel.getType().equals("admin") || position.equals("manager") || position.equals("operator")) && type.equals("room");
        else
            return (position.equals("manager") || position.equals("operator")) && type.equals("room");
    }

    public boolean showRoomDropdownEdit(UserModel userModel, String position, String type) {
        if (userModel != null)
            return (userModel.getType().equals("admin") || position.equals("manager") || position.equals("operator")) && !type.equals("room");
        else
            return (position.equals("manager") || position.equals("operator")) && !type.equals("room");
    }

    public boolean showRoomDropdownPlatforms(UserModel userModel, String position) {
        if (userModel != null)
            return userModel.getType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showRoomDropdownTags(UserModel userModel, String position) {
        if (userModel != null)
            return userModel.getType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showRoomDropdownAccounting(UserModel userModel, String position) {
        if (userModel != null)
            return userModel.getType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showRoomCreateCase(UserModel userModel, String position) {
        if (userModel != null)
            return userModel.getType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showRoomCases(UserModel userModel, String position) {
        if (userModel != null)
            return userModel.getType().equals("admin") || (!position.equals("psychologist") && !position.equals("client"));
        else
            return !position.equals("psychologist") && !position.equals("client");
    }

    // -------------------- Session

    public boolean showSessionDropdownReports(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null && sessionModel.getRoom().getCenter() != null && sessionModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showSessionDropdownEdit(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null && sessionModel.getRoom().getCenter() != null && sessionModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreateUser(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null && sessionModel.getRoom().getCenter() != null && sessionModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreatePractice(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null && sessionModel.getRoom().getCenter() != null && sessionModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreateSample(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null && sessionModel.getRoom().getCenter() != null && sessionModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreateBill(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null && sessionModel.getRoom().getCenter() != null && sessionModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null)
            return userModel.getType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    /*
    ---------- Tab ----------
    */

    // -------------------- EditUserTabPassword

    public boolean showEditUserTabPasswordCurrent(UserModel userModel) {
        if (userModel != null)
            return !userModel.getType().equals("admin") && !userModel.isNoPassword();
        else
            return false;
    }

    // -------------------- EditUserTabPersonal

    public boolean showEditUserTabPersonalMobile(UserModel userModel) {
        if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showEditUserTabPersonalEmail(UserModel userModel) {
        if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showEditUserTabPersonalBirthday() {
        return false;
    }

    public boolean showEditUserTabPersonalStatus(UserModel userModel) {
        if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

    public boolean showEditUserTabPersonalType(UserModel userModel) {
        if (userModel != null)
            return userModel.getType().equals("admin");
        else
            return false;
    }

}