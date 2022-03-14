package com.majazeh.risloo.utils.entities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavDestination;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.create.FragmentReserveScheduleArgs;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditCenterArgs;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditCenterUserArgs;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditPlatformArgs;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditSessionArgs;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditTreasuryArgs;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditUserArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentBalancesArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterPlatformsArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterSchedulesArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterTagsArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterUsersArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentClientReportsArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentCommissionsArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomPlatformsArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomSchedulesArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomTagsArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomUsersArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomsArgs;
import com.majazeh.risloo.views.fragments.main.index.FragmentSamplesArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentBillArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentBulkSampleArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentCaseArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentCenterAccountingArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentCenterArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentFolderArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentMeArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentReferenceArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentRoomArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentSampleArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentSessionArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentTreasuryArgs;
import com.majazeh.risloo.views.fragments.main.show.FragmentUserArgs;
import com.mre.ligheh.Model.TypeModel.BillingModel;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class BreadCrumb {

    // Objects
    private final Activity activity;
    private NavDestination navDestination;

    // Models
    private BillingModel billingModel;
    private BulkSampleModel bulkSampleModel;
    private CaseModel caseModel;
    private CenterModel centerModel;
    private RoomModel roomModel;
    private SampleModel sampleModel;
    private ScheduleModel scheduleModel;
    private SessionModel sessionModel;
    private TreasuriesModel treasuriesModel;
    private UserModel userModel;

    // Vars
    private String roomType = "", sessionType = "", clientReportsType = "", referenceType = "", folderName = "";
    private String chainId = null;
    private String[] sampleIds = null;
    private ArrayList<Integer> destinationIds;

    /*
    ---------- Intialize ----------
    */

    public BreadCrumb(@NonNull Activity activity) {
        this.activity = activity;
    }

    /*
    ---------- Func's ----------
    */

    public SpannableStringBuilder getSpan(NavDestination destination, Bundle arguments) {
        ArrayList<String> list = intialize(destination, arguments);
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder();

        for (int i = 0; i < list.size(); i++) {
            String label = list.get(i);

            if (list.size() == 1 && label.equals("خانه")) {
                label = activity.getResources().getString(R.string.DashboardFragmentWelcome);
            }

            spannableBuilder.append(label);
            if (i != list.size() - 1) {
                spannableBuilder.append("  >  ");

                String finalLabel = label;
                int finalPosition = i;

                int start = spannableBuilder.toString().indexOf(label);
                int end = start + label.length();

                spannableBuilder.setSpan(new ClickableSpan() {

                    @Override
                    public void onClick(@NonNull View widget) {
                        if (!finalLabel.equals("نامعلوم"))
                            navigateTo(destinationIds.get(finalPosition));
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint textPaint) {
                        textPaint.setColor(activity.getResources().getColor(R.color.coolGray500));
                        textPaint.setUnderlineText(false);
                    }

                }, start, end, 0);
            }
        }

        return spannableBuilder;
    }

    /*
    ---------- Private's ----------
    */

    @SuppressLint("NonConstantResourceId")
    private ArrayList<String> intialize(NavDestination destination, Bundle arguments) {
        navDestination = destination;

        switch (destination.getId()) {

            // -------------------- Drawer

            case R.id.fragmentDashboard:
                return dashboardList();
            case R.id.fragmentCenters:
                return centersList();
            case R.id.fragmentCases:
                return casesList();
            case R.id.fragmentSessions:
                return sessionsList();
            case R.id.fragmentUsers:
                return usersList();
            case R.id.fragmentSamples:
                chainId = FragmentSamplesArgs.fromBundle(arguments).getChainId();
                sampleIds = FragmentSamplesArgs.fromBundle(arguments).getSampleIds();

                return samplesList();
            case R.id.fragmentBulkSamples:
                return bulkSamplesList();
            case R.id.fragmentScales:
                return scalesList();
            case R.id.fragmentDocuments:
                return documentsList();
            case R.id.fragmentDownloads:
                return downloadsList();

            // -------------------- Toolbar

            case R.id.fragmentMe:
                setModels(FragmentMeArgs.fromBundle(arguments).getTypeModel());
                return meList();
            case R.id.fragmentAccounting:
                return accountingList();
            case R.id.fragmentPayments:
                return paymentsList();

            // -------------------- Create

            case R.id.fragmentCreateBill:
                return createBillList();
            case R.id.fragmentCreateCase:
                return createCaseList();
            case R.id.fragmentCreateCaseUser:
                return createCaseUserList();
            case R.id.fragmentCreateCenter:
                return createCenterList();
            case R.id.fragmentCreateCenterUser:
                return createCenterUserList();
            case R.id.fragmentCreateClientReport:
                return createClientReportList();
            case R.id.fragmentCreateDocument:
                return createDocumentList();
            case R.id.fragmentCreatePlatform:
                return createPlatformList();
            case R.id.fragmentCreatePractice:
                return createPracticeList();
            case R.id.fragmentCreateRoom:
                return createRoomList();
            case R.id.fragmentCreateRoomUser:
                return createRoomUserList();
            case R.id.fragmentCreateSample:
                return createSampleList();
            case R.id.fragmentCreateSchedule:
                return createScheduleList();
            case R.id.fragmentCreateSession:
                return createSessionList();
            case R.id.fragmentCreateSessionUser:
                return createSessionUserList();
            case R.id.fragmentCreateTreasury:
                return createTreasuryList();
            case R.id.fragmentCreateUser:
                return createUserList();
            case R.id.fragmentReserveSchedule:
                setModels(FragmentReserveScheduleArgs.fromBundle(arguments).getTypeModel());
                return reserveScheduleList();

            // -------------------- Edit

            case R.id.fragmentEditCenter:
                setModels(FragmentEditCenterArgs.fromBundle(arguments).getTypeModel());
                return editCenterList();
            case R.id.fragmentEditCenterUser:
                setModels(FragmentEditCenterUserArgs.fromBundle(arguments).getTypeModel());
                return editCenterUserList();
            case R.id.fragmentEditPlatform:
                setModels(FragmentEditPlatformArgs.fromBundle(arguments).getTypeModel());
                return editPlatformList();
            case R.id.fragmentEditSession: {
                TypeModel typeModel = FragmentEditSessionArgs.fromBundle(arguments).getTypeModel();

                if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("ScheduleModel"))
                    sessionType = "schedule";
                else if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("SessionModel"))
                    sessionType = "session";

                setModels(typeModel);
                return editSessionList();
            }
            case R.id.fragmentEditTreasury:
                setModels(FragmentEditTreasuryArgs.fromBundle(arguments).getTypeModel());
                return editTreasuryList();
            case R.id.fragmentEditUser:
                setModels(FragmentEditUserArgs.fromBundle(arguments).getTypeModel());
                return editUserList();

            // -------------------- Index

            case R.id.fragmentBalances:
                setModels(FragmentBalancesArgs.fromBundle(arguments).getTypeModel());
                return balancesList();
            case R.id.fragmentBanks:
                return banksList();
            case R.id.fragmentBillings:
                return billingsList();
            case R.id.fragmentCenterPlatforms:
                setModels(FragmentCenterPlatformsArgs.fromBundle(arguments).getTypeModel());
                return centerPlatformsList();
            case R.id.fragmentCenterSchedules:
                setModels(FragmentCenterSchedulesArgs.fromBundle(arguments).getTypeModel());
                return centerSchedulesList();
            case R.id.fragmentCenterTags:
                setModels(FragmentCenterTagsArgs.fromBundle(arguments).getTypeModel());
                return centerTagsList();
            case R.id.fragmentCenterUsers:
                setModels(FragmentCenterUsersArgs.fromBundle(arguments).getTypeModel());
                return centerUsersList();
            case R.id.fragmentClientReports: {
                TypeModel typeModel = FragmentClientReportsArgs.fromBundle(arguments).getTypeModel();

                if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("CaseModel"))
                    clientReportsType = "case";
                else if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("SessionModel"))
                    clientReportsType = "session";

                setModels(typeModel);
                return clientReportsList();
            }
            case R.id.fragmentCommissions:
                setModels(FragmentCommissionsArgs.fromBundle(arguments).getTypeModel());
                return commissionsList();
            case R.id.fragmentRoomPlatforms:
                setModels(FragmentRoomPlatformsArgs.fromBundle(arguments).getTypeModel());
                return roomPlatformsList();
            case R.id.fragmentRoomSchedules:
                setModels(FragmentRoomSchedulesArgs.fromBundle(arguments).getTypeModel());
                return roomSchedulesList();
            case R.id.fragmentRoomTags:
                setModels(FragmentRoomTagsArgs.fromBundle(arguments).getTypeModel());
                return roomTagsList();
            case R.id.fragmentRoomUsers:
                setModels(FragmentRoomUsersArgs.fromBundle(arguments).getTypeModel());
                return roomUsersList();
            case R.id.fragmentRooms:
                setModels(FragmentRoomsArgs.fromBundle(arguments).getTypeModel());
                return roomsList();
            case R.id.fragmentTreasuries:
                return treasuriesList();

            // -------------------- Show

            case R.id.fragmentBill:
                setModels(FragmentBillArgs.fromBundle(arguments).getTypeModel());
                return billList();
            case R.id.fragmentBulkSample:
                setModels(FragmentBulkSampleArgs.fromBundle(arguments).getTypeModel());
                return bulkSampleList();
            case R.id.fragmentCase:
                setModels(FragmentCaseArgs.fromBundle(arguments).getTypeModel());
                return casseList();
            case R.id.fragmentCenterAccounting: {
                TypeModel typeModel = FragmentCenterAccountingArgs.fromBundle(arguments).getTypeModel();

                if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("CenterModel"))
                    roomType = "counseling_center";
                else if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("RoomModel"))
                    roomType = "personal_clinic";

                setModels(typeModel);
                return centerAccountingList();
            }
            case R.id.fragmentCenter:
                setModels(FragmentCenterArgs.fromBundle(arguments).getTypeModel());
                return centerList();
            case R.id.fragmentFolder:
                folderName = FragmentFolderArgs.fromBundle(arguments).getFolderName();
                return folderList();
            case R.id.fragmentReference: {
                UserModel userModel = (UserModel) FragmentReferenceArgs.fromBundle(arguments).getTypeModel();

                if (((ActivityMain) activity).singleton.getUserModel().getId().equals(userModel.getUserId())) {
                    TypeModel centerModel = FragmentReferenceArgs.fromBundle(arguments).getCenterModel();

                    if (StringManager.suffix(centerModel.getClass().getName(), '.').equals("CenterModel"))
                        referenceType = "center";
                    else if (StringManager.suffix(centerModel.getClass().getName(), '.').equals("RoomModel"))
                        referenceType = "room";

                    setModels(centerModel);
                } else {
                    TypeModel typeModel = FragmentReferenceArgs.fromBundle(arguments).getTypeModel();
                    referenceType = "user";

                    setModels(typeModel);
                }

                return referenceList();
            }
            case R.id.fragmentRoom: {
                TypeModel typeModel = FragmentRoomArgs.fromBundle(arguments).getTypeModel();

                if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("CenterModel"))
                    roomType = "personal_clinic";
                else if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("RoomModel"))
                    roomType = "room";

                setModels(typeModel);
                return roomList();
            }
            case R.id.fragmentSample:
                setModels(FragmentSampleArgs.fromBundle(arguments).getTypeModel());
                return sampleList();
            case R.id.fragmentSession: {
                TypeModel typeModel = FragmentSessionArgs.fromBundle(arguments).getTypeModel();

                if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("ScheduleModel"))
                    sessionType = "schedule";
                else if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("SessionModel"))
                    sessionType = "session";

                setModels(typeModel);
                return sessionList();
            }
            case R.id.fragmentTreasury:
                setModels(FragmentTreasuryArgs.fromBundle(arguments).getTypeModel());
                return treasuryList();
            case R.id.fragmentUser:
                setModels(FragmentUserArgs.fromBundle(arguments).getTypeModel());
                return userList();
        }

        return new ArrayList<>();
    }

    @SuppressLint("NonConstantResourceId")
    private void navigateTo(int position) {
        switch (position) {

            // -------------------- Drawer

            case R.id.fragmentDashboard:
                ((ActivityMain) activity).navigatoon.navigateToFragmentDashboard();
                break;
            case R.id.fragmentCenters:
                ((ActivityMain) activity).navigatoon.navigateToFragmentCenters();
                break;
            case R.id.fragmentCases:
                ((ActivityMain) activity).navigatoon.navigateToFragmentCases();
                break;
            case R.id.fragmentSessions:
                ((ActivityMain) activity).navigatoon.navigateToFragmentSessions();
                break;
            case R.id.fragmentUsers:
                ((ActivityMain) activity).navigatoon.navigateToFragmentUsers();
                break;
            case R.id.fragmentSamples:
                ((ActivityMain) activity).navigatoon.navigateToFragmentSamples(chainId, sampleIds);
                break;
            case R.id.fragmentBulkSamples:
                ((ActivityMain) activity).navigatoon.navigateToFragmentBulkSamples();
                break;
            case R.id.fragmentScales:
                ((ActivityMain) activity).navigatoon.navigateToFragmentScales();
                break;
            case R.id.fragmentDocuments:
                ((ActivityMain) activity).navigatoon.navigateToFragmentDocuments();
                break;
            case R.id.fragmentDownloads:
                ((ActivityMain) activity).navigatoon.navigateToFragmentDownloads();
                break;

            // -------------------- Toolbar

            case R.id.fragmentMe:
                ((ActivityMain) activity).navigatoon.navigateToFragmentMe(((ActivityMain) activity).singleton.getUserModel());
                break;
            case R.id.fragmentAccounting:
                ((ActivityMain) activity).navigatoon.navigateToFragmentAccounting();
                break;
            case R.id.fragmentPayments:
                ((ActivityMain) activity).navigatoon.navigateToFragmentPayments(null);
                break;

            // -------------------- Index

            case R.id.fragmentBalances:
                if (roomType.equals("counseling_center"))
                    ((ActivityMain) activity).navigatoon.navigateToFragmentBalances(centerModel);
                else
                    ((ActivityMain) activity).navigatoon.navigateToFragmentBalances(roomModel);

                break;
            case R.id.fragmentBanks:
                ((ActivityMain) activity).navigatoon.navigateToFragmentBanks(null);
                break;
            case R.id.fragmentBillings:
                ((ActivityMain) activity).navigatoon.navigateToFragmentBillings();
                break;
            case R.id.fragmentCenterPlatforms:
                ((ActivityMain) activity).navigatoon.navigateToFragmentCenterPlatforms(centerModel);
                break;
            case R.id.fragmentCenterSchedules:
                ((ActivityMain) activity).navigatoon.navigateToFragmentCenterSchedules(centerModel);
                break;
            case R.id.fragmentCenterTags:
                ((ActivityMain) activity).navigatoon.navigateToFragmentCenterTags(centerModel);
                break;
            case R.id.fragmentCenterUsers:
                ((ActivityMain) activity).navigatoon.navigateToFragmentCenterUsers(centerModel);
                break;
            case R.id.fragmentClientReports:
                if (clientReportsType.equals("case"))
                    ((ActivityMain) activity).navigatoon.navigateToFragmentClientReports(caseModel);
                else
                    ((ActivityMain) activity).navigatoon.navigateToFragmentClientReports(sessionModel);

                break;
            case R.id.fragmentCommissions:
                if (roomType.equals("counseling_center"))
                    ((ActivityMain) activity).navigatoon.navigateToFragmentCommissions(centerModel);
                else
                    ((ActivityMain) activity).navigatoon.navigateToFragmentCommissions(roomModel);

                break;
            case R.id.fragmentRoomPlatforms:
                ((ActivityMain) activity).navigatoon.navigateToFragmentRoomPlatforms(roomModel);
                break;
            case R.id.fragmentRoomSchedules:
                ((ActivityMain) activity).navigatoon.navigateToFragmentRoomSchedules(roomModel);
                break;
            case R.id.fragmentRoomTags:
                ((ActivityMain) activity).navigatoon.navigateToFragmentRoomTags(roomModel);
                break;
            case R.id.fragmentRoomUsers:
                ((ActivityMain) activity).navigatoon.navigateToFragmentRoomUsers(roomModel);
                break;
            case R.id.fragmentRooms:
                ((ActivityMain) activity).navigatoon.navigateToFragmentRooms(centerModel);
                break;
            case R.id.fragmentTreasuries:
                ((ActivityMain) activity).navigatoon.navigateToFragmentTreasuries();
                break;

            // -------------------- Show

            case R.id.fragmentBill:
                ((ActivityMain) activity).navigatoon.navigateToFragmentBill(billingModel);
                break;
            case R.id.fragmentBulkSample:
                ((ActivityMain) activity).navigatoon.navigateToFragmentBulkSample(bulkSampleModel);
                break;
            case R.id.fragmentCase:
                ((ActivityMain) activity).navigatoon.navigateToFragmentCase(caseModel);
                break;
            case R.id.fragmentCenterAccounting:
                if (roomType.equals("counseling_center"))
                    ((ActivityMain) activity).navigatoon.navigateToFragmentCenterAccounting(centerModel);
                else
                    ((ActivityMain) activity).navigatoon.navigateToFragmentCenterAccounting(roomModel);

                break;
            case R.id.fragmentCenter:
                ((ActivityMain) activity).navigatoon.navigateToFragmentCenter(centerModel);
                break;
            case R.id.fragmentFolder:
                ((ActivityMain) activity).navigatoon.navigateToFragmentFolder(folderName);
                break;
            case R.id.fragmentReference:
                if (referenceType.equals("user"))
                    ((ActivityMain) activity).navigatoon.navigateToFragmentReference(centerModel, userModel);
                else if (referenceType.equals("center"))
                    ((ActivityMain) activity).navigatoon.navigateToFragmentReference(centerModel, ((ActivityMain) activity).singleton.getUserModel());
                else
                    ((ActivityMain) activity).navigatoon.navigateToFragmentReference(roomModel, ((ActivityMain) activity).singleton.getUserModel());

                break;
            case R.id.fragmentRoom:
                if (roomType.equals("room"))
                    ((ActivityMain) activity).navigatoon.navigateToFragmentRoom(roomModel);
                else
                    ((ActivityMain) activity).navigatoon.navigateToFragmentRoom(centerModel);

                break;
            case R.id.fragmentSample:
                ((ActivityMain) activity).navigatoon.navigateToFragmentSample(sampleModel);
                break;
            case R.id.fragmentSession:
                if (sessionType.equals("session"))
                    ((ActivityMain) activity).navigatoon.navigateToFragmentSession(sessionModel);
                else
                    ((ActivityMain) activity).navigatoon.navigateToFragmentSession(scheduleModel);

                break;
            case R.id.fragmentTreasury:
                ((ActivityMain) activity).navigatoon.navigateToFragmentTreasury(treasuriesModel);
                break;
            case R.id.fragmentUser:
                ((ActivityMain) activity).navigatoon.navigateToFragmentUser(userModel);
                break;
        }
    }

    private void setModels(TypeModel typeModel) {
        switch (StringManager.suffix(typeModel.getClass().getName(), '.')) {
            case "BillingModel":
                billingModel(typeModel);
                break;
            case "BulkSampleModel":
                bulkSampleModel(typeModel);
                break;
            case "CaseModel":
                caseModel(typeModel);
                break;
            case "CenterModel":
                centerModel(typeModel);
                break;
            case "RoomModel":
                roomModel(typeModel);
                break;
            case "SampleModel":
                sampleModel(typeModel);
                break;
            case "ScheduleModel":
                scheduleModel(typeModel);
                break;
            case "SessionModel":
                sessionModel(typeModel);
                break;
            case "TreasuriesModel":
                treasuriesModel(typeModel);
                break;
            case "UserModel":
                userModel(typeModel);
                break;
            default:
                // TODO : Place If Needed
                break;
        }
    }

    /*
    ---------- Model ----------
    */

    private void billingModel(TypeModel typeModel) {
        billingModel = (BillingModel) typeModel;
    }

    private void bulkSampleModel(TypeModel typeModel) {
        bulkSampleModel = (BulkSampleModel) typeModel;

        if (bulkSampleModel.getCasse() != null) {
            caseModel = bulkSampleModel.getCasse();

            if (caseModel.getRoom() != null) {
                roomModel = caseModel.getRoom();
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else
                    centerModel = null;

            } else if (bulkSampleModel.getRoom() != null) {
                roomModel = bulkSampleModel.getRoom();
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else
                    centerModel = null;

            } else {
                roomModel = null;
                roomType = "";

                centerModel = null;
            }

        } else {
            caseModel = null;

            if (bulkSampleModel.getRoom() != null) {
                roomModel = bulkSampleModel.getRoom();
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else
                    centerModel = null;

            } else {
                roomModel = null;
                roomType = "";

                centerModel = null;
            }

        }
    }

    private void caseModel(TypeModel typeModel) {
        caseModel = (CaseModel) typeModel;

        if (caseModel.getRoom() != null) {
            roomModel = caseModel.getRoom();
            roomType = roomModel.getType();

            if (roomModel.getCenter() != null)
                centerModel = roomModel.getCenter();
            else
                centerModel = null;

        } else {
            roomModel = null;
            roomType = "";

            centerModel = null;
        }
    }

    private void centerModel(TypeModel typeModel) {
        centerModel = (CenterModel) typeModel;
        roomType = centerModel.getType();
    }

    private void roomModel(TypeModel typeModel) {
        roomModel = (RoomModel) typeModel;
        roomType = roomModel.getType();

        if (roomModel.getCenter() != null)
            centerModel = roomModel.getCenter();
        else if (navDestination.getId() != R.id.fragmentRoom)
            centerModel = null;
    }

    private void sampleModel(TypeModel typeModel) {
        sampleModel = (SampleModel) typeModel;

        if (sampleModel.getCasse() != null) {
            caseModel = sampleModel.getCasse();

            if (caseModel.getRoom() != null) {
                roomModel = caseModel.getRoom();
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else
                    centerModel = null;

            } else if (sampleModel.getRoom() != null) {
                roomModel = sampleModel.getRoom();
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else
                    centerModel = null;

            } else {
                roomModel = null;
                roomType = "";

                centerModel = null;
            }

        } else {
            caseModel = null;

            if (sampleModel.getRoom() != null) {
                roomModel = sampleModel.getRoom();
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else
                    centerModel = null;

            } else {
                roomModel = null;
                roomType = "";

                centerModel = null;
            }

        }

        if (sampleModel.getClient() != null)
            userModel = sampleModel.getClient();
        else
            userModel = null;
    }

    private void scheduleModel(TypeModel typeModel) {
        scheduleModel = (ScheduleModel) typeModel;

        if (scheduleModel.getCasse() != null) {
            caseModel = scheduleModel.getCasse();

            if (caseModel.getRoom() != null) {
                roomModel = caseModel.getRoom();
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else
                    centerModel = null;

            } else if (scheduleModel.getRoom() != null) {
                roomModel = scheduleModel.getRoom();
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else
                    centerModel = null;

            } else {
                roomModel = null;
                roomType = "";

                centerModel = null;
            }

        } else {
            caseModel = null;

            if (scheduleModel.getRoom() != null) {
                roomModel = scheduleModel.getRoom();
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else
                    centerModel = null;

            } else {
                roomModel = null;
                roomType = "";

                centerModel = null;
            }

        }
    }

    private void sessionModel(TypeModel typeModel) {
        sessionModel = (SessionModel) typeModel;

        if (sessionModel.getCasse() != null) {
            caseModel = sessionModel.getCasse();

            if (caseModel.getRoom() != null) {
                roomModel = caseModel.getRoom();
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else
                    centerModel = null;

            } else if (sessionModel.getRoom() != null) {
                roomModel = sessionModel.getRoom();
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else
                    centerModel = null;

            } else {
                roomModel = null;
                roomType = "";

                centerModel = null;
            }

        } else {
            caseModel = null;

            if (sessionModel.getRoom() != null) {
                roomModel = sessionModel.getRoom();
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else
                    centerModel = null;

            } else {
                roomModel = null;
                roomType = "";

                centerModel = null;
            }

        }
    }

    private void treasuriesModel(TypeModel typeModel) {
        treasuriesModel = (TreasuriesModel) typeModel;

        if (treasuriesModel.getCenter() != null) {
            centerModel = treasuriesModel.getCenter();
            roomType = centerModel.getType();
        } else {
            centerModel = null;
            roomType = null;
        }
    }

    private void userModel(TypeModel typeModel) {
        userModel = (UserModel) typeModel;
    }

    /*
    ---------- Drawer ----------
    */

    // -------------------- String

    private ArrayList<String> dashboardList() {
        ArrayList<String> list = new ArrayList<>();
        list.add(activity.getResources().getString(R.string.DashboardFragmentTitle));

        destinationIds = dashboardIds();
        return list;
    }

    private ArrayList<String> centersList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.CentersFragmentTitle));

        destinationIds = centersIds();
        return list;
    }

    private ArrayList<String> casesList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.CasesFragmentTitle));

        destinationIds = casesIds();
        return list;
    }

    private ArrayList<String> sessionsList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.SessionsFragmentTitle));

        destinationIds = sessionsIds();
        return list;
    }

    private ArrayList<String> usersList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.UsersFragmentTitle));

        destinationIds = usersIds();
        return list;
    }

    private ArrayList<String> samplesList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.SamplesFragmentTitle));

        destinationIds = samplesIds();
        return list;
    }

    private ArrayList<String> bulkSamplesList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.BulkSamplesFragmentTitle));

        destinationIds = bulkSamplesIds();
        return list;
    }

    private ArrayList<String> scalesList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.ScalesFragmentTitle));

        destinationIds = scalesIds();
        return list;
    }

    private ArrayList<String> documentsList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.DocumentsFragmentTitle));

        destinationIds = documentsIds();
        return list;
    }

    private ArrayList<String> downloadsList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.DownloadsFragmentTitle));

        destinationIds = downloadsIds();
        return list;
    }

    // -------------------- Int

    private ArrayList<Integer> dashboardIds() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.id.fragmentDashboard);

        return list;
    }

    private ArrayList<Integer> centersIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentCenters);

        return list;
    }

    private ArrayList<Integer> casesIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentCases);

        return list;
    }

    private ArrayList<Integer> sessionsIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentSessions);

        return list;
    }

    private ArrayList<Integer> usersIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentUsers);

        return list;
    }

    private ArrayList<Integer> samplesIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentSamples);

        return list;
    }

    private ArrayList<Integer> bulkSamplesIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentBulkSamples);

        return list;
    }

    private ArrayList<Integer> scalesIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentScales);

        return list;
    }

    private ArrayList<Integer> documentsIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentDocuments);

        return list;
    }

    private ArrayList<Integer> downloadsIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentDownloads);

        return list;
    }

    /*
    ---------- Toolbar ----------
    */

    // -------------------- String

    private ArrayList<String> meList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.MeFragmentTitle));

        destinationIds = meIds();
        return list;
    }

    private ArrayList<String> accountingList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.AccountingFragmentTitle));

        destinationIds = accountingIds();
        return list;
    }

    private ArrayList<String> paymentsList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.PaymentsFragmentTitle));

        destinationIds = paymentsIds();
        return list;
    }

    // -------------------- Int

    private ArrayList<Integer> meIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentMe);

        return list;
    }

    private ArrayList<Integer> accountingIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentAccounting);

        return list;
    }

    private ArrayList<Integer> paymentsIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentPayments);

        return list;
    }

    /*
    ---------- Create ----------
    */

    // -------------------- String

    private ArrayList<String> createBillList() {
        ArrayList<String> list = sessionList();
        list.add(activity.getResources().getString(R.string.CreateBillFragmentTitle));

        destinationIds = createBillIds();
        return list;
    }

    private ArrayList<String> createCaseList() {
        ArrayList<String> list = roomList();
        list.add(activity.getResources().getString(R.string.CreateCaseFragmentTitle));

        destinationIds = createCaseIds();
        return list;
    }

    private ArrayList<String> createCaseUserList() {
        ArrayList<String> list = casseList();
        list.add(activity.getResources().getString(R.string.CreateCaseUserFragmentTitle));

        destinationIds = createCaseUserIds();
        return list;
    }

    private ArrayList<String> createCenterList() {
        ArrayList<String> list = centersList();
        list.add(activity.getResources().getString(R.string.CreateCenterFragmentTitle));

        destinationIds = createCenterIds();
        return list;
    }

    private ArrayList<String> createCenterUserList() {
        ArrayList<String> list = centerUsersList();
        list.add(activity.getResources().getString(R.string.CreateCenterUserFragmentTitle));

        destinationIds = createCenterUserIds();
        return list;
    }

    private ArrayList<String> createClientReportList() {
        ArrayList<String> list = clientReportsList();
        list.add(activity.getResources().getString(R.string.CreateClientReportFragmentTitle));

        destinationIds = createClientReportIds();
        return list;
    }

    private ArrayList<String> createDocumentList() {
        ArrayList<String> list = documentsList();
        list.add(activity.getResources().getString(R.string.CreateDocumentFragmentTitle));

        destinationIds = createDocumentIds();
        return list;
    }

    private ArrayList<String> createPlatformList() {
        ArrayList<String> list = centerPlatformsList();
        list.add(activity.getResources().getString(R.string.CreatePlatformFragmentTitle));

        destinationIds = createPlatformIds();
        return list;
    }

    private ArrayList<String> createPracticeList() {
        ArrayList<String> list = sessionList();
        list.add(activity.getResources().getString(R.string.CreatePracticeFragmentTitle));

        destinationIds = createPracticeIds();
        return list;
    }

    private ArrayList<String> createRoomList() {
        ArrayList<String> list = centerList();
        list.add(activity.getResources().getString(R.string.CreateRoomFragmentTitle));

        destinationIds = createRoomIds();
        return list;
    }

    private ArrayList<String> createRoomUserList() {
        ArrayList<String> list = roomUsersList();
        list.add(activity.getResources().getString(R.string.CreateRoomUserFragmentTitle));

        destinationIds = createRoomUserIds();
        return list;
    }

    private ArrayList<String> createSampleList() {
        ArrayList<String> list = samplesList();
        list.add(activity.getResources().getString(R.string.CreateSampleFragmentTitle));

        destinationIds = createSampleIds();
        return list;
    }

    private ArrayList<String> createScheduleList() {
        ArrayList<String> list = roomList();
        list.add(activity.getResources().getString(R.string.CreateScheduleFragmentTitle));

        destinationIds = createScheduleIds();
        return list;
    }

    private ArrayList<String> createSessionList() {
        ArrayList<String> list = casseList();
        list.add(activity.getResources().getString(R.string.CreateSessionFragmentTitle));

        destinationIds = createSessionIds();
        return list;
    }

    private ArrayList<String> createSessionUserList() {
        ArrayList<String> list = sessionList();
        list.add(activity.getResources().getString(R.string.CreateSessionUserFragmentTitle));

        destinationIds = createSessionUserIds();
        return list;
    }

    private ArrayList<String> createTreasuryList() {
        ArrayList<String> list = treasuriesList();
        list.add(activity.getResources().getString(R.string.CreateTreasuryFragmentTitle));

        destinationIds = createTreasuryIds();
        return list;
    }

    private ArrayList<String> createUserList() {
        ArrayList<String> list = usersList();
        list.add(activity.getResources().getString(R.string.CreateUserFragmentTitle));

        destinationIds = createUserIds();
        return list;
    }

    private ArrayList<String> reserveScheduleList() {
        ArrayList<String> list = roomSchedulesList();
        list.add(activity.getResources().getString(R.string.AppReserve));

        destinationIds = reserveScheduleIds();
        return list;
    }

    // -------------------- Int

    private ArrayList<Integer> createBillIds() {
        ArrayList<Integer> list = sessionIds();
        list.add(R.id.fragmentCreateBill);

        return list;
    }

    private ArrayList<Integer> createCaseIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.fragmentCreateCase);

        return list;
    }

    private ArrayList<Integer> createCaseUserIds() {
        ArrayList<Integer> list = casseIds();
        list.add(R.id.fragmentCreateCaseUser);

        return list;
    }

    private ArrayList<Integer> createCenterIds() {
        ArrayList<Integer> list = centersIds();
        list.add(R.id.fragmentCreateCenter);

        return list;
    }

    private ArrayList<Integer> createCenterUserIds() {
        ArrayList<Integer> list = centerUsersIds();
        list.add(R.id.fragmentCreateCenterUser);

        return list;
    }

    private ArrayList<Integer> createClientReportIds() {
        ArrayList<Integer> list = clientReportsIds();
        list.add(R.id.fragmentCreateClientReport);

        return list;
    }

    private ArrayList<Integer> createDocumentIds() {
        ArrayList<Integer> list = documentsIds();
        list.add(R.id.fragmentCreateDocument);

        return list;
    }

    private ArrayList<Integer> createPlatformIds() {
        ArrayList<Integer> list = centerPlatformsIds();
        list.add(R.id.fragmentCreatePlatform);

        return list;
    }

    private ArrayList<Integer> createPracticeIds() {
        ArrayList<Integer> list = sessionIds();
        list.add(R.id.fragmentCreatePractice);

        return list;
    }

    private ArrayList<Integer> createRoomIds() {
        ArrayList<Integer> list = centerIds();
        list.add(R.id.fragmentCreateRoom);

        return list;
    }

    private ArrayList<Integer> createRoomUserIds() {
        ArrayList<Integer> list = roomUsersIds();
        list.add(R.id.fragmentCreateRoomUser);

        return list;
    }

    private ArrayList<Integer> createSampleIds() {
        ArrayList<Integer> list = samplesIds();
        list.add(R.id.fragmentCreateSample);

        return list;
    }

    private ArrayList<Integer> createScheduleIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.fragmentCreateSchedule);

        return list;
    }

    private ArrayList<Integer> createSessionIds() {
        ArrayList<Integer> list = casseIds();
        list.add(R.id.fragmentCreateSession);

        return list;
    }

    private ArrayList<Integer> createSessionUserIds() {
        ArrayList<Integer> list = sessionIds();
        list.add(R.id.fragmentCreateSessionUser);

        return list;
    }

    private ArrayList<Integer> createTreasuryIds() {
        ArrayList<Integer> list = treasuriesIds();
        list.add(R.id.fragmentCreateTreasury);

        return list;
    }

    private ArrayList<Integer> createUserIds() {
        ArrayList<Integer> list = usersIds();
        list.add(R.id.fragmentCreateUser);

        return list;
    }

    private ArrayList<Integer> reserveScheduleIds() {
        ArrayList<Integer> list = roomSchedulesIds();
        list.add(R.id.fragmentReserveSchedule);

        return list;
    }

    /*
    ---------- Edit ----------
    */

    // -------------------- String

    private ArrayList<String> editCenterList() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = centerList();
        else
            list = roomList();

        list.add(activity.getResources().getString(R.string.AppEdit));

        destinationIds = editCenterIds();
        return list;
    }

    private ArrayList<String> editCenterUserList() {
        ArrayList<String> list = referenceList();
        list.add(activity.getResources().getString(R.string.AppEdit));

        destinationIds = editCenterUserIds();
        return list;
    }

    private ArrayList<String> editPlatformList() {
        ArrayList<String> list = centerPlatformsList();
        list.add(activity.getResources().getString(R.string.AppEdit));

        destinationIds = editPlatformIds();
        return list;
    }

    private ArrayList<String> editSessionList() {
        ArrayList<String> list = sessionList();
        list.add(activity.getResources().getString(R.string.AppEdit));

        destinationIds = editSessionIds();
        return list;
    }

    private ArrayList<String> editTreasuryList() {
        ArrayList<String> list = treasuryList();
        list.add(activity.getResources().getString(R.string.AppEdit));

        destinationIds = editTreasuryIds();
        return list;
    }

    private ArrayList<String> editUserList() {
        ArrayList<String> list = userList();
        list.add(activity.getResources().getString(R.string.AppEdit));

        destinationIds = editUserIds();
        return list;
    }

    // -------------------- int

    private ArrayList<Integer> editCenterIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.fragmentEditCenter);

        return list;
    }

    private ArrayList<Integer> editCenterUserIds() {
        ArrayList<Integer> list = referenceIds();
        list.add(R.id.fragmentEditCenterUser);

        return list;
    }

    private ArrayList<Integer> editPlatformIds() {
        ArrayList<Integer> list = centerPlatformsIds();
        list.add(R.id.fragmentEditPlatform);

        return list;
    }

    private ArrayList<Integer> editSessionIds() {
        ArrayList<Integer> list = sessionIds();
        list.add(R.id.fragmentEditSession);

        return list;
    }

    private ArrayList<Integer> editTreasuryIds() {
        ArrayList<Integer> list = treasuryIds();
        list.add(R.id.fragmentEditTreasury);

        return list;
    }

    private ArrayList<Integer> editUserIds() {
        ArrayList<Integer> list = userIds();
        list.add(R.id.fragmentEditUser);

        return list;
    }

    /*
    ---------- Index ----------
    */

    // -------------------- String

    private ArrayList<String> balancesList() {
        ArrayList<String> list = centerAccountingList();
        list.add(activity.getResources().getString(R.string.BalancesFragmentTitle));

        destinationIds = balancesIds();
        return list;
    }

    private ArrayList<String> banksList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.BanksFragmentTitle));

        destinationIds = banksIds();
        return list;
    }

    private ArrayList<String> billingsList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.BillingsFragmentTitle));

        destinationIds = billingsIds();
        return list;
    }

    private ArrayList<String> centerPlatformsList() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = centerList();
        else
            list = roomList();

        list.add(activity.getResources().getString(R.string.AppPlatforms));

        destinationIds = centerPlatformsIds();
        return list;
    }

    private ArrayList<String> centerSchedulesList() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = centerList();
        else
            list = roomList();

        list.add(activity.getResources().getString(R.string.AppSchedules));

        destinationIds = centerSchedulesIds();
        return list;
    }

    private ArrayList<String> centerTagsList() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = centerList();
        else
            list = roomList();

        list.add(activity.getResources().getString(R.string.AppTags));

        destinationIds = centerTagsIds();
        return list;
    }

    private ArrayList<String> centerUsersList() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = centerList();
        else
            list = roomList();

        list.add(activity.getResources().getString(R.string.AppUsers));

        destinationIds = centerUsersIds();
        return list;
    }

    private ArrayList<String> clientReportsList() {
        ArrayList<String> list;

        if (clientReportsType.equals("case"))
            list = casseList();
        else
            list = sessionList();

        list.add(activity.getResources().getString(R.string.ClientReportsFragmentTitle));

        destinationIds = clientReportsIds();
        return list;
    }

    private ArrayList<String> commissionsList() {
        ArrayList<String> list = centerAccountingList();
        list.add(activity.getResources().getString(R.string.CommissionsFragmentTitle));

        destinationIds = commissionsIds();
        return list;
    }

    private ArrayList<String> roomPlatformsList() {
        ArrayList<String> list = roomList();
        list.add(activity.getResources().getString(R.string.AppPlatforms));

        destinationIds = roomPlatformsIds();
        return list;
    }

    private ArrayList<String> roomSchedulesList() {
        ArrayList<String> list = roomList();
        list.add(activity.getResources().getString(R.string.AppSchedules));

        destinationIds = roomSchedulesIds();
        return list;
    }

    private ArrayList<String> roomTagsList() {
        ArrayList<String> list = roomList();
        list.add(activity.getResources().getString(R.string.AppTags));

        destinationIds = roomTagsIds();
        return list;
    }

    private ArrayList<String> roomUsersList() {
        ArrayList<String> list = roomList();
        list.add(activity.getResources().getString(R.string.AppUsers));

        destinationIds = roomUsersIds();
        return list;
    }

    private ArrayList<String> roomsList() {
        ArrayList<String> list = centerList();
        list.add(activity.getResources().getString(R.string.AppRooms));

        destinationIds = roomsIds();
        return list;
    }

    private ArrayList<String> treasuriesList() {
        ArrayList<String> list = dashboardList();
        list.add(activity.getResources().getString(R.string.TreasuriesFragmentTitle));

        destinationIds = treasuriesIds();
        return list;
    }

    // -------------------- Int

    private ArrayList<Integer> balancesIds() {
        ArrayList<Integer> list = centerAccountingIds();
        list.add(R.id.fragmentBalances);

        return list;
    }

    private ArrayList<Integer> banksIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentBanks);

        return list;
    }

    private ArrayList<Integer> billingsIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentBillings);

        return list;
    }

    private ArrayList<Integer> centerPlatformsIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.fragmentCenterPlatforms);

        return list;
    }

    private ArrayList<Integer> centerSchedulesIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.fragmentCenterSchedules);

        return list;
    }

    private ArrayList<Integer> centerTagsIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.fragmentCenterTags);

        return list;
    }

    private ArrayList<Integer> centerUsersIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.fragmentCenterUsers);

        return list;
    }

    private ArrayList<Integer> clientReportsIds() {
        ArrayList<Integer> list;

        if (clientReportsType.equals("case"))
            list = casseIds();
        else
            list = sessionIds();

        list.add(R.id.fragmentClientReports);

        return list;
    }

    private ArrayList<Integer> commissionsIds() {
        ArrayList<Integer> list = centerAccountingIds();
        list.add(R.id.fragmentCommissions);

        return list;
    }

    private ArrayList<Integer> roomPlatformsIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.fragmentRoomPlatforms);

        return list;
    }

    private ArrayList<Integer> roomSchedulesIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.fragmentRoomSchedules);

        return list;
    }

    private ArrayList<Integer> roomTagsIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.fragmentRoomTags);

        return list;
    }

    private ArrayList<Integer> roomUsersIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.fragmentRoomUsers);

        return list;
    }

    private ArrayList<Integer> roomsIds() {
        ArrayList<Integer> list = centerIds();
        list.add(R.id.fragmentRooms);

        return list;
    }

    private ArrayList<Integer> treasuriesIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.fragmentTreasuries);

        return list;
    }

    /*
    ---------- Show ----------
    */

    // -------------------- String

    private ArrayList<String> billList() {
        ArrayList<String> list = billingsList();

        if (billingModel != null && billingModel.getTitle() != null && !billingModel.getTitle().equals(""))
            list.add(billingModel.getTitle());
        else if (billingModel != null && billingModel.getId() != null && !billingModel.getId().equals(""))
            list.add(billingModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = billIds();
        return list;
    }

    private ArrayList<String> bulkSampleList() {
        ArrayList<String> list = bulkSamplesList();

        if (bulkSampleModel != null && bulkSampleModel.getTitle() != null && !bulkSampleModel.getTitle().equals(""))
            list.add(bulkSampleModel.getTitle());
        else if (bulkSampleModel != null && bulkSampleModel.getId() != null && !bulkSampleModel.getId().equals(""))
            list.add(bulkSampleModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = bulkSampleIds();
        return list;
    }

    private ArrayList<String> casseList() {
        ArrayList<String> list = roomList();

        if (caseModel != null && caseModel.getId() != null && !caseModel.getId().equals(""))
            list.add("پرونده" + " " + caseModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = casseIds();
        return list;
    }

    private ArrayList<String> centerAccountingList() {
        ArrayList<String> list;

        if (roomType.equals("counseling_center"))
            list = centerList();
        else
            list = roomList();

        list.add(activity.getResources().getString(R.string.AppAccounting));

        destinationIds = centerAccountingIds();
        return list;
    }

    private ArrayList<String> centerList() {
        ArrayList<String> list = centersList();

        try {
            if (centerModel != null && centerModel.getDetail() != null && centerModel.getDetail().has("title") && !centerModel.getDetail().isNull("title") && !centerModel.getDetail().getString("title").equals(""))
                list.add(centerModel.getDetail().getString("title"));
            else if (centerModel != null && centerModel.getId() != null && !centerModel.getId().equals(""))
                list.add("مرکز درمان" + " " + centerModel.getId());
            else
                list.add(activity.getResources().getString(R.string.AppDefaultUnknown));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        destinationIds = centerIds();
        return list;
    }

    private ArrayList<String> folderList() {
        ArrayList<String> list = downloadsList();
        list.add(folderName);

        destinationIds = folderIds();
        return list;
    }

    private ArrayList<String> referenceList() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = centerUsersList();
        else
            list = roomUsersList();

        if (referenceType.equals("user")) {
            if (userModel != null && userModel.getId() != null && userModel.getId().equals(((ActivityMain) activity).singleton.getUserModel().getId()))
                list.add(activity.getResources().getString(R.string.MeFragmentTitle));
            else if (userModel != null && userModel.getName() != null && !userModel.getName().equals(""))
                list.add(userModel.getName());
            else if (userModel != null && userModel.getId() != null && !userModel.getId().equals(""))
                list.add(userModel.getId());
            else
                list.add(activity.getResources().getString(R.string.AppDefaultUnknown));
        } else {
            list.add(activity.getResources().getString(R.string.MeFragmentTitle));
        }

        destinationIds = referenceIds();
        return list;
    }

    private ArrayList<String> roomList() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = centersList();
        else
            list = centerList();

        if (!roomType.equals("room")) {
            if (centerModel != null && centerModel.getManager() != null && centerModel.getManager().getName() != null && !centerModel.getManager().getName().equals(""))
                list.add("کلینیک شخصی" + " " + centerModel.getManager().getName());
            else if (centerModel != null && centerModel.getId() != null && !centerModel.getId().equals(""))
                list.add("کلینیک شخصی" + " " + centerModel.getId());
            else
                list.add(activity.getResources().getString(R.string.AppDefaultUnknown));
        } else {
            if (roomModel != null && roomModel.getManager() != null && roomModel.getManager().getName() != null && !roomModel.getManager().getName().equals(""))
                list.add("اتاق درمان" + " " + roomModel.getManager().getName());
            else if (roomModel != null && roomModel.getId() != null && !roomModel.getId().equals(""))
                list.add("اتاق درمان" + " " + roomModel.getId());
            else
                list.add(activity.getResources().getString(R.string.AppDefaultUnknown));
        }

        destinationIds = roomIds();
        return list;
    }

    private ArrayList<String> sampleList() {
        ArrayList<String> list;

        if (caseModel != null)
            list = casseList();
        else
            list = referenceList();

        if (sampleModel != null && sampleModel.getTitle() != null && !sampleModel.getTitle().equals(""))
            list.add(sampleModel.getTitle());
        else if (sampleModel != null && sampleModel.getId() != null && !sampleModel.getId().equals(""))
            list.add("نمونه" + " " + sampleModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = sampleIds();
        return list;
    }

    private ArrayList<String> sessionList() {
        ArrayList<String> list;

        if (sessionType.equals("session") && sessionModel != null && sessionModel.getCasse() != null)
            list = casseList();
        else if (sessionType.equals("schedule") && scheduleModel != null && scheduleModel.getCasse() != null)
            list = casseList();
        else
            list = roomList();

        if (sessionType.equals("session") && sessionModel != null && sessionModel.getId() != null && !sessionModel.getId().equals(""))
            list.add("جلسه" + " " + sessionModel.getId());
        else if (sessionType.equals("schedule") && scheduleModel != null && scheduleModel.getId() != null && !scheduleModel.getId().equals(""))
            list.add("جلسه" + " " + scheduleModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = sessionIds();
        return list;
    }

    private ArrayList<String> treasuryList() {
        ArrayList<String> list = treasuriesList();

        if (treasuriesModel != null && treasuriesModel.getTitle() != null && !treasuriesModel.getTitle().equals(""))
            list.add(treasuriesModel.getTitle());
        else if (treasuriesModel != null && treasuriesModel.getId() != null && !treasuriesModel.getId().equals(""))
            list.add(treasuriesModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = treasuryIds();
        return list;
    }

    private ArrayList<String> userList() {
        ArrayList<String> list = usersList();

        if (userModel != null && userModel.getName() != null && !userModel.getName().equals(""))
            list.add(userModel.getName());
        else if (userModel != null && userModel.getId() != null && !userModel.getId().equals(""))
            list.add(userModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = userIds();
        return list;
    }

    // -------------------- Int

    private ArrayList<Integer> billIds() {
        ArrayList<Integer> list = billingsIds();
        list.add(R.id.fragmentBill);

        return list;
    }

    private ArrayList<Integer> bulkSampleIds() {
        ArrayList<Integer> list = bulkSamplesIds();
        list.add(R.id.fragmentBulkSample);

        return list;
    }

    private ArrayList<Integer> casseIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.fragmentCase);

        return list;
    }

    private ArrayList<Integer> centerAccountingIds() {
        ArrayList<Integer> list;

        if (roomType.equals("counseling_center"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.fragmentCenterAccounting);

        return list;
    }

    private ArrayList<Integer> centerIds() {
        ArrayList<Integer> list = centersIds();
        list.add(R.id.fragmentCenter);

        return list;
    }

    private ArrayList<Integer> folderIds() {
        ArrayList<Integer> list = downloadsIds();
        list.add(R.id.fragmentFolder);

        return list;
    }

    private ArrayList<Integer> referenceIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centerUsersIds();
        else
            list = roomUsersIds();

        list.add(R.id.fragmentReference);

        return list;
    }

    private ArrayList<Integer> roomIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centersIds();
        else
            list = centerIds();

        list.add(R.id.fragmentRoom);

        return list;
    }

    private ArrayList<Integer> sampleIds() {
        ArrayList<Integer> list;

        if (caseModel != null)
            list = casseIds();
        else
            list = referenceIds();

        list.add(R.id.fragmentSample);

        return list;
    }

    private ArrayList<Integer> sessionIds() {
        ArrayList<Integer> list;

        if (sessionType.equals("session") && sessionModel != null && sessionModel.getCasse() != null)
            list = casseIds();
        else if (sessionType.equals("schedule") && scheduleModel != null && scheduleModel.getCasse() != null)
            list = casseIds();
        else
            list = roomIds();

        list.add(R.id.fragmentSession);

        return list;
    }

    private ArrayList<Integer> treasuryIds() {
        ArrayList<Integer> list = treasuriesIds();
        list.add(R.id.fragmentTreasury);

        return list;
    }

    private ArrayList<Integer> userIds() {
        ArrayList<Integer> list = usersIds();
        list.add(R.id.fragmentUser);

        return list;
    }

}