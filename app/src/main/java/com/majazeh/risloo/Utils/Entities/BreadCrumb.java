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
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Main.Create.ReserveScheduleFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditCenterFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditCenterUserFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditPlatformFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditSessionFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditTreasuryFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditUserFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.BalancesFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.CenterPlatformsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.CenterSchedulesFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.CenterTagsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.CenterUsersFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.ClientReportsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.CommissionsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomPlatformsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomSchedulesFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomTagsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomUsersFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Index.SamplesFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.BillFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.BulkSampleFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.CaseFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.CenterAccountingFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.CenterFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.FolderFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.MeFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.ReferenceFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.RoomFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.SampleFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.SessionFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.TreasuryFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Main.Show.UserFragmentArgs;
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
    ---------- Methods ----------
    */

    public SpannableStringBuilder getFa(NavDestination destination, Bundle arguments) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        navDestination = destination;

        ArrayList<String> list = intialize(destination, arguments);
        for (int i = 0; i < list.size(); i++) {
            String label = list.get(i);

            if (list.size() == 1 && label.equals("خانه"))
                label = activity.getResources().getString(R.string.DashboardFragmentWelcome);

            builder.append(label);
            if (i != list.size() - 1) {
                builder.append("  >  ");
                int position = i;

                String finalLabel = label;
                builder.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        if (!finalLabel.equals("نامعلوم"))
                            navigateTo(destinationIds.get(position));
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint textPaint) {
                        textPaint.setColor(activity.getResources().getColor(R.color.coolGray500));
                        textPaint.setUnderlineText(false);
                    }

                }, builder.toString().indexOf(label), builder.toString().indexOf(label) + label.length(), 0);
            }
        }

        return builder;
    }

    /*
    ---------- Voids ----------
    */

    @SuppressLint("NonConstantResourceId")
    private ArrayList<String> intialize(NavDestination destination, Bundle arguments) {
        switch (destination.getId()) {

            // -------------------- Drawer

            case R.id.dashboardFragment:
                return dashboard();
            case R.id.centersFragment:
                return centers();
            case R.id.casesFragment:
                return cases();
            case R.id.sessionsFragment:
                return sessions();
            case R.id.usersFragment:
                return users();
            case R.id.samplesFragment:
                chainId = SamplesFragmentArgs.fromBundle(arguments).getChainId();
                sampleIds = SamplesFragmentArgs.fromBundle(arguments).getSampleIds();

                return samples();
            case R.id.bulkSamplesFragment:
                return bulkSamples();
            case R.id.scalesFragment:
                return scales();
            case R.id.documentsFragment:
                return documents();
            case R.id.downloadsFragment:
                return downloads();

            // -------------------- Toolbar

            case R.id.meFragment:
                setModels(MeFragmentArgs.fromBundle(arguments).getTypeModel());
                return me();
            case R.id.accountingFragment:
                return accounting();
            case R.id.paymentsFragment:
                return payments();

            // -------------------- Create

            case R.id.createBillFragment:
                return createBill();
            case R.id.createCaseFragment:
                return createCase();
            case R.id.createCaseUserFragment:
                return createCaseUser();
            case R.id.createCenterFragment:
                return createCenter();
            case R.id.createCenterUserFragment:
                return createCenterUser();
            case R.id.createClientReportFragment:
                return createClientReport();
            case R.id.createDocumentFragment:
                return createDocument();
            case R.id.createPlatformFragment:
                return createPlatform();
            case R.id.createPracticeFragment:
                return createPractice();
            case R.id.createRoomFragment:
                return createRoom();
            case R.id.createRoomUserFragment:
                return createRoomUser();
            case R.id.createSampleFragment:
                return createSample();
            case R.id.createScheduleFragment:
                return createSchedule();
            case R.id.createSessionFragment:
                return createSession();
            case R.id.createSessionUserFragment:
                return createSessionUser();
            case R.id.createTreasuryFragment:
                return createTreasury();
            case R.id.createUserFragment:
                return createUser();
            case R.id.reserveScheduleFragment:
                setModels(ReserveScheduleFragmentArgs.fromBundle(arguments).getTypeModel());
                return reserveSchedule();

            // -------------------- Edit

            case R.id.editCenterFragment:
                setModels(EditCenterFragmentArgs.fromBundle(arguments).getTypeModel());
                return editCenter();
            case R.id.editCenterUserFragment:
                setModels(EditCenterUserFragmentArgs.fromBundle(arguments).getTypeModel());
                return editCenterUser();
            case R.id.editPlatformFragment:
                setModels(EditPlatformFragmentArgs.fromBundle(arguments).getTypeModel());
                return editPlatform();
            case R.id.editSessionFragment: {
                TypeModel typeModel = EditSessionFragmentArgs.fromBundle(arguments).getTypeModel();

                if (StringManager.substring(typeModel.getClass().getName(), '.').equals("ScheduleModel"))
                    sessionType = "schedule";
                else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("SessionModel"))
                    sessionType = "session";

                setModels(typeModel);
                return editSession();
            } case R.id.editTreasuryFragment:
                setModels(EditTreasuryFragmentArgs.fromBundle(arguments).getTypeModel());
                return editTreasury();
            case R.id.editUserFragment:
                setModels(EditUserFragmentArgs.fromBundle(arguments).getTypeModel());
                return editUser();

            // -------------------- Index

            case R.id.balancesFragment:
                setModels(BalancesFragmentArgs.fromBundle(arguments).getTypeModel());
                return balances();
            case R.id.banksFragment:
                return banks();
            case R.id.billingsFragment:
                return billings();
            case R.id.centerPlatformsFragment:
                setModels(CenterPlatformsFragmentArgs.fromBundle(arguments).getTypeModel());
                return centerPlatforms();
            case R.id.centerSchedulesFragment:
                setModels(CenterSchedulesFragmentArgs.fromBundle(arguments).getTypeModel());
                return centerSchedules();
            case R.id.centerTagsFragment:
                setModels(CenterTagsFragmentArgs.fromBundle(arguments).getTypeModel());
                return centerTags();
            case R.id.centerUsersFragment:
                setModels(CenterUsersFragmentArgs.fromBundle(arguments).getTypeModel());
                return centerUsers();
            case R.id.clientReportsFragment: {
                TypeModel typeModel = ClientReportsFragmentArgs.fromBundle(arguments).getTypeModel();

                if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CaseModel"))
                    clientReportsType = "case";
                else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("SessionModel"))
                    clientReportsType = "session";

                setModels(typeModel);
                return clientReports();
            } case R.id.commissionsFragment:
                setModels(CommissionsFragmentArgs.fromBundle(arguments).getTypeModel());
                return commissions();
            case R.id.roomPlatformsFragment:
                setModels(RoomPlatformsFragmentArgs.fromBundle(arguments).getTypeModel());
                return roomPlatforms();
            case R.id.roomSchedulesFragment:
                setModels(RoomSchedulesFragmentArgs.fromBundle(arguments).getTypeModel());
                return roomSchedules();
            case R.id.roomTagsFragment:
                setModels(RoomTagsFragmentArgs.fromBundle(arguments).getTypeModel());
                return roomTags();
            case R.id.roomUsersFragment:
                setModels(RoomUsersFragmentArgs.fromBundle(arguments).getTypeModel());
                return roomUsers();
            case R.id.roomsFragment:
                setModels(RoomsFragmentArgs.fromBundle(arguments).getTypeModel());
                return rooms();
            case R.id.treasuriesFragment:
                return treasuries();

            // -------------------- Show

            case R.id.billFragment:
                setModels(BillFragmentArgs.fromBundle(arguments).getTypeModel());
                return bill();
            case R.id.bulkSampleFragment:
                setModels(BulkSampleFragmentArgs.fromBundle(arguments).getTypeModel());
                return bulkSample();
            case R.id.caseFragment:
                setModels(CaseFragmentArgs.fromBundle(arguments).getTypeModel());
                return casse();
            case R.id.centerAccountingFragment: {
                TypeModel typeModel = CenterAccountingFragmentArgs.fromBundle(arguments).getTypeModel();

                if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CenterModel"))
                    roomType = "counseling_center";
                else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("RoomModel"))
                    roomType = "personal_clinic";

                setModels(typeModel);
                return centerAccounting();
            } case R.id.centerFragment:
                setModels(CenterFragmentArgs.fromBundle(arguments).getTypeModel());
                return center();
            case R.id.folderFragment:
                folderName = FolderFragmentArgs.fromBundle(arguments).getFolderName();
                return folder();
            case R.id.referenceFragment: {
                UserModel userModel = (UserModel) ReferenceFragmentArgs.fromBundle(arguments).getTypeModel();

                if (((MainActivity) activity).singleton.getUserModel().getId().equals(userModel.getUserId())) {
                    TypeModel centerModel = ReferenceFragmentArgs.fromBundle(arguments).getCenterModel();

                    if (StringManager.substring(centerModel.getClass().getName(), '.').equals("CenterModel"))
                        referenceType = "center";
                    else if (StringManager.substring(centerModel.getClass().getName(), '.').equals("RoomModel"))
                        referenceType = "room";

                    setModels(centerModel);
                } else {
                    TypeModel typeModel = ReferenceFragmentArgs.fromBundle(arguments).getTypeModel();
                    referenceType = "user";

                    setModels(typeModel);
                }

                return reference();
            } case R.id.roomFragment: {
                TypeModel typeModel = RoomFragmentArgs.fromBundle(arguments).getTypeModel();

                if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CenterModel"))
                    roomType = "personal_clinic";
                else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("RoomModel"))
                    roomType = "room";

                setModels(typeModel);
                return room();
            } case R.id.sampleFragment:
                setModels(SampleFragmentArgs.fromBundle(arguments).getTypeModel());
                return sample();
            case R.id.sessionFragment: {
                TypeModel typeModel = SessionFragmentArgs.fromBundle(arguments).getTypeModel();

                if (StringManager.substring(typeModel.getClass().getName(), '.').equals("ScheduleModel"))
                    sessionType = "schedule";
                else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("SessionModel"))
                    sessionType = "session";

                setModels(typeModel);
                return session();
            } case R.id.treasuryFragment:
                setModels(TreasuryFragmentArgs.fromBundle(arguments).getTypeModel());
                return treasury();
            case R.id.userFragment:
                setModels(UserFragmentArgs.fromBundle(arguments).getTypeModel());
                return user();
        }

        return new ArrayList<>();
    }

    @SuppressLint("NonConstantResourceId")
    private void navigateTo(int position) {
        switch (position) {

            // -------------------- Drawer

            case R.id.dashboardFragment:
                ((MainActivity) activity).navigatoon.navigateToDashboardFragment();
                break;
            case R.id.centersFragment:
                ((MainActivity) activity).navigatoon.navigateToCentersFragment();
                break;
            case R.id.casesFragment:
                ((MainActivity) activity).navigatoon.navigateToCasesFragment();
                break;
            case R.id.sessionsFragment:
                ((MainActivity) activity).navigatoon.navigateToSessionsFragment();
                break;
            case R.id.usersFragment:
                ((MainActivity) activity).navigatoon.navigateToUsersFragment();
                break;
            case R.id.samplesFragment:
                ((MainActivity) activity).navigatoon.navigateToSamplesFragment(chainId, sampleIds);
                break;
            case R.id.bulkSamplesFragment:
                ((MainActivity) activity).navigatoon.navigateToBulkSamplesFragment();
                break;
            case R.id.scalesFragment:
                ((MainActivity) activity).navigatoon.navigateToScalesFragment();
                break;
            case R.id.documentsFragment:
                ((MainActivity) activity).navigatoon.navigateToDocumentsFragment();
                break;
            case R.id.downloadsFragment:
                ((MainActivity) activity).navigatoon.navigateToDownloadsFragment();
                break;

            // -------------------- Toolbar

            case R.id.meFragment:
                ((MainActivity) activity).navigatoon.navigateToMeFragment(((MainActivity) activity).singleton.getUserModel());
                break;
            case R.id.accountingFragment:
                ((MainActivity) activity).navigatoon.navigateToAccountingFragment();
                break;
            case R.id.paymentsFragment:
                ((MainActivity) activity).navigatoon.navigateToPaymentsFragment(null);
                break;

            // -------------------- Index

            case R.id.balancesFragment:
                if (roomType.equals("counseling_center"))
                    ((MainActivity) activity).navigatoon.navigateToBalancesFragment(centerModel);
                else
                    ((MainActivity) activity).navigatoon.navigateToBalancesFragment(roomModel);

                break;
            case R.id.banksFragment:
                ((MainActivity) activity).navigatoon.navigateToBanksFragment(null);
                break;
            case R.id.billingsFragment:
                ((MainActivity) activity).navigatoon.navigateToBillingsFragment();
                break;
            case R.id.centerPlatformsFragment:
                ((MainActivity) activity).navigatoon.navigateToCenterPlatformsFragment(centerModel);
                break;
            case R.id.centerSchedulesFragment:
                ((MainActivity) activity).navigatoon.navigateToCenterSchedulesFragment(centerModel);
                break;
            case R.id.centerTagsFragment:
                ((MainActivity) activity).navigatoon.navigateToCenterTagsFragment(centerModel);
                break;
            case R.id.centerUsersFragment:
                ((MainActivity) activity).navigatoon.navigateToCenterUsersFragment(centerModel);
                break;
            case R.id.clientReportsFragment:
                if (clientReportsType.equals("case"))
                    ((MainActivity) activity).navigatoon.navigateToClientReportsFragment(caseModel);
                else
                    ((MainActivity) activity).navigatoon.navigateToClientReportsFragment(sessionModel);

            break;
            case R.id.commissionsFragment:
                if (roomType.equals("counseling_center"))
                    ((MainActivity) activity).navigatoon.navigateToCommissionsFragment(centerModel);
                else
                    ((MainActivity) activity).navigatoon.navigateToCommissionsFragment(roomModel);

                break;
            case R.id.roomPlatformsFragment:
                ((MainActivity) activity).navigatoon.navigateToRoomPlatformsFragment(roomModel);
                break;
            case R.id.roomSchedulesFragment:
                ((MainActivity) activity).navigatoon.navigateToRoomSchedulesFragment(roomModel);
                break;
            case R.id.roomTagsFragment:
                ((MainActivity) activity).navigatoon.navigateToRoomTagsFragment(roomModel);
                break;
            case R.id.roomUsersFragment:
                ((MainActivity) activity).navigatoon.navigateToRoomUsersFragment(roomModel);
                break;
            case R.id.roomsFragment:
                ((MainActivity) activity).navigatoon.navigateToRoomsFragment(centerModel);
                break;
            case R.id.treasuriesFragment:
                ((MainActivity) activity).navigatoon.navigateToTreasuriesFragment();
                break;

            // -------------------- Show

            case R.id.billFragment:
                ((MainActivity) activity).navigatoon.navigateToBillFragment(billingModel);
                break;
            case R.id.bulkSampleFragment:
                ((MainActivity) activity).navigatoon.navigateToBulkSampleFragment(bulkSampleModel);
                break;
            case R.id.caseFragment:
                ((MainActivity) activity).navigatoon.navigateToCaseFragment(caseModel);
                break;
            case R.id.centerAccountingFragment:
                if (roomType.equals("counseling_center"))
                    ((MainActivity) activity).navigatoon.navigateToCenterAccountingFragment(centerModel);
                else
                    ((MainActivity) activity).navigatoon.navigateToCenterAccountingFragment(roomModel);

                break;
            case R.id.centerFragment:
                ((MainActivity) activity).navigatoon.navigateToCenterFragment(centerModel);
                break;
            case R.id.folderFragment:
                ((MainActivity) activity).navigatoon.navigateToFolderFragment(folderName);
                break;
            case R.id.referenceFragment:
                if (referenceType.equals("user"))
                    ((MainActivity) activity).navigatoon.navigateToReferenceFragment(centerModel, userModel);
                else if (referenceType.equals("center"))
                    ((MainActivity) activity).navigatoon.navigateToReferenceFragment(centerModel, ((MainActivity) activity).singleton.getUserModel());
                else
                    ((MainActivity) activity).navigatoon.navigateToReferenceFragment(roomModel, ((MainActivity) activity).singleton.getUserModel());

                break;
            case R.id.roomFragment:
                if (roomType.equals("room"))
                    ((MainActivity) activity).navigatoon.navigateToRoomFragment(roomModel);
                else
                    ((MainActivity) activity).navigatoon.navigateToRoomFragment(centerModel);

                break;
            case R.id.sampleFragment:
                ((MainActivity) activity).navigatoon.navigateToSampleFragment(sampleModel);
                break;
            case R.id.sessionFragment:
                if (sessionType.equals("session"))
                    ((MainActivity) activity).navigatoon.navigateToSessionFragment(sessionModel);
                else
                    ((MainActivity) activity).navigatoon.navigateToSessionFragment(scheduleModel);

                break;
            case R.id.treasuryFragment:
                ((MainActivity) activity).navigatoon.navigateToTreasuryFragment(treasuriesModel);
                break;
            case R.id.userFragment:
                ((MainActivity) activity).navigatoon.navigateToUserFragment(userModel);
                break;
        }
    }

    private void setModels(TypeModel typeModel) {
        switch (StringManager.substring(typeModel.getClass().getName(), '.')) {
            case "BillingModel":
                billingModel = (BillingModel) typeModel;
                break;
            case "BulkSampleModel":
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

                break;
            case "CaseModel":
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

                break;
            case "CenterModel":
                centerModel = (CenterModel) typeModel;
                roomType = centerModel.getType();

                break;
            case "RoomModel":
                roomModel = (RoomModel) typeModel;
                roomType = roomModel.getType();

                if (roomModel.getCenter() != null)
                    centerModel = roomModel.getCenter();
                else if (navDestination.getId() != R.id.roomFragment)
                    centerModel = null;

                break;
            case "SampleModel":
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

                break;
            case "ScheduleModel":
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

                break;
            case "SessionModel":
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

                break;
            case "TreasuriesModel":
                treasuriesModel = (TreasuriesModel) typeModel;

                if (treasuriesModel.getCenter() != null) {
                    centerModel = treasuriesModel.getCenter();
                    roomType = centerModel.getType();
                } else {
                    centerModel = null;
                    roomType = null;
                }

                break;
            case "UserModel":
                userModel = (UserModel) typeModel;
                break;
            default:
                // TODO : Place If Needed
                break;
        }
    }

    /*
    ---------- Drawer ----------
    */

    private ArrayList<String> dashboard() {
        ArrayList<String> list = new ArrayList<>();
        list.add(activity.getResources().getString(R.string.DashboardFragmentTitle));

        destinationIds = dashboardIds();
        return list;
    }
    private ArrayList<Integer> dashboardIds() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.id.dashboardFragment);

        return list;
    }

    private ArrayList<String> centers() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.CentersFragmentTitle));

        destinationIds = centersIds();
        return list;
    }
    private ArrayList<Integer> centersIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.centersFragment);

        return list;
    }

    private ArrayList<String> cases() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.CasesFragmentTitle));

        destinationIds = casesIds();
        return list;
    }
    private ArrayList<Integer> casesIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.casesFragment);

        return list;
    }

    private ArrayList<String> sessions() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.SessionsFragmentTitle));

        destinationIds = sessionsIds();
        return list;
    }
    private ArrayList<Integer> sessionsIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.sessionsFragment);

        return list;
    }

    private ArrayList<String> users() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.UsersFragmentTitle));

        destinationIds = usersIds();
        return list;
    }
    private ArrayList<Integer> usersIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.usersFragment);

        return list;
    }

    private ArrayList<String> samples() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.SamplesFragmentTitle));

        destinationIds = samplesIds();
        return list;
    }
    private ArrayList<Integer> samplesIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.samplesFragment);

        return list;
    }

    private ArrayList<String> bulkSamples() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.BulkSamplesFragmentTitle));

        destinationIds = bulkSamplesIds();
        return list;
    }
    private ArrayList<Integer> bulkSamplesIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.bulkSamplesFragment);

        return list;
    }

    private ArrayList<String> scales() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.ScalesFragmentTitle));

        destinationIds = scalesIds();
        return list;
    }
    private ArrayList<Integer> scalesIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.scalesFragment);

        return list;
    }

    private ArrayList<String> documents() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.DocumentsFragmentTitle));

        destinationIds = documentsIds();
        return list;
    }
    private ArrayList<Integer> documentsIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.documentsFragment);

        return list;
    }

    private ArrayList<String> downloads() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.DownloadsFragmentTitle));

        destinationIds = downloadsIds();
        return list;
    }
    private ArrayList<Integer> downloadsIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.downloadsFragment);

        return list;
    }

    /*
    ---------- Toolbar ----------
    */

    private ArrayList<String> me() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.MeFragmentTitle));

        destinationIds = meIds();
        return list;
    }
    private ArrayList<Integer> meIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.meFragment);

        return list;
    }

    private ArrayList<String> accounting() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.AccountingFragmentTitle));

        destinationIds = accountingIds();
        return list;
    }
    private ArrayList<Integer> accountingIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.accountingFragment);

        return list;
    }

    private ArrayList<String> payments() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.PaymentsFragmentTitle));

        destinationIds = paymentsIds();
        return list;
    }
    private ArrayList<Integer> paymentsIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.paymentsFragment);

        return list;
    }

    /*
    ---------- Create ----------
    */

    private ArrayList<String> createBill() {
        ArrayList<String> list = session();
        list.add(activity.getResources().getString(R.string.CreateBillFragmentTitle));

        destinationIds = createBillIds();
        return list;
    }
    private ArrayList<Integer> createBillIds() {
        ArrayList<Integer> list = sessionIds();
        list.add(R.id.createBillFragment);

        return list;
    }

    private ArrayList<String> createCase() {
        ArrayList<String> list = room();
        list.add(activity.getResources().getString(R.string.CreateCaseFragmentTitle));

        destinationIds = createCaseIds();
        return list;
    }
    private ArrayList<Integer> createCaseIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.createCaseFragment);

        return list;
    }

    private ArrayList<String> createCaseUser() {
        ArrayList<String> list = casse();
        list.add(activity.getResources().getString(R.string.CreateCaseUserFragmentTitle));

        destinationIds = createCaseUserIds();
        return list;
    }
    private ArrayList<Integer> createCaseUserIds() {
        ArrayList<Integer> list = casseIds();
        list.add(R.id.createCaseUserFragment);

        return list;
    }

    private ArrayList<String> createCenter() {
        ArrayList<String> list = centers();
        list.add(activity.getResources().getString(R.string.CreateCenterFragmentTitle));

        destinationIds = createCenterIds();
        return list;
    }
    private ArrayList<Integer> createCenterIds() {
        ArrayList<Integer> list = centersIds();
        list.add(R.id.createCenterFragment);

        return list;
    }

    private ArrayList<String> createCenterUser() {
        ArrayList<String> list = centerUsers();
        list.add(activity.getResources().getString(R.string.CreateCenterUserFragmentTitle));

        destinationIds = createCenterUserIds();
        return list;
    }
    private ArrayList<Integer> createCenterUserIds() {
        ArrayList<Integer> list = centerUsersIds();
        list.add(R.id.createCenterUserFragment);

        return list;
    }

    private ArrayList<String> createClientReport() {
        ArrayList<String> list = clientReports();
        list.add(activity.getResources().getString(R.string.CreateClientReportFragmentTitle));

        destinationIds = createClientReportIds();
        return list;
    }
    private ArrayList<Integer> createClientReportIds() {
        ArrayList<Integer> list = clientReportsIds();
        list.add(R.id.createClientReportFragment);

        return list;
    }

    private ArrayList<String> createDocument() {
        ArrayList<String> list = documents();
        list.add(activity.getResources().getString(R.string.CreateDocumentFragmentTitle));

        destinationIds = createDocumentIds();
        return list;
    }
    private ArrayList<Integer> createDocumentIds() {
        ArrayList<Integer> list = documentsIds();
        list.add(R.id.createDocumentFragment);

        return list;
    }

    private ArrayList<String> createPlatform() {
        ArrayList<String> list = centerPlatforms();
        list.add(activity.getResources().getString(R.string.CreatePlatformFragmentTitle));

        destinationIds = createPlatformIds();
        return list;
    }
    private ArrayList<Integer> createPlatformIds() {
        ArrayList<Integer> list = centerPlatformsIds();
        list.add(R.id.createPlatformFragment);

        return list;
    }

    private ArrayList<String> createPractice() {
        ArrayList<String> list = session();
        list.add(activity.getResources().getString(R.string.CreatePracticeFragmentTitle));

        destinationIds = createPracticeIds();
        return list;
    }
    private ArrayList<Integer> createPracticeIds() {
        ArrayList<Integer> list = sessionIds();
        list.add(R.id.createPracticeFragment);

        return list;
    }

    private ArrayList<String> createRoom() {
        ArrayList<String> list = center();
        list.add(activity.getResources().getString(R.string.CreateRoomFragmentTitle));

        destinationIds = createRoomIds();
        return list;
    }
    private ArrayList<Integer> createRoomIds() {
        ArrayList<Integer> list = centerIds();
        list.add(R.id.createRoomFragment);

        return list;
    }

    private ArrayList<String> createRoomUser() {
        ArrayList<String> list = roomUsers();
        list.add(activity.getResources().getString(R.string.CreateRoomUserFragmentTitle));

        destinationIds = createRoomUserIds();
        return list;
    }
    private ArrayList<Integer> createRoomUserIds() {
        ArrayList<Integer> list = roomUsersIds();
        list.add(R.id.createRoomUserFragment);

        return list;
    }

    private ArrayList<String> createSample() {
        ArrayList<String> list = samples();
        list.add(activity.getResources().getString(R.string.CreateSampleFragmentTitle));

        destinationIds = createSampleIds();
        return list;
    }
    private ArrayList<Integer> createSampleIds() {
        ArrayList<Integer> list = samplesIds();
        list.add(R.id.createSampleFragment);

        return list;
    }

    private ArrayList<String> createSchedule() {
        ArrayList<String> list = room();
        list.add(activity.getResources().getString(R.string.CreateScheduleFragmentTitle));

        destinationIds = createScheduleIds();
        return list;
    }
    private ArrayList<Integer> createScheduleIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.createScheduleFragment);

        return list;
    }

    private ArrayList<String> createSession() {
        ArrayList<String> list = casse();
        list.add(activity.getResources().getString(R.string.CreateSessionFragmentTitle));

        destinationIds = createSessionIds();
        return list;
    }
    private ArrayList<Integer> createSessionIds() {
        ArrayList<Integer> list = casseIds();
        list.add(R.id.createSessionFragment);

        return list;
    }

    private ArrayList<String> createSessionUser() {
        ArrayList<String> list = session();
        list.add(activity.getResources().getString(R.string.CreateSessionUserFragmentTitle));

        destinationIds = createSessionUserIds();
        return list;
    }
    private ArrayList<Integer> createSessionUserIds() {
        ArrayList<Integer> list = sessionIds();
        list.add(R.id.createSessionUserFragment);

        return list;
    }

    private ArrayList<String> createTreasury() {
        ArrayList<String> list = treasuries();
        list.add(activity.getResources().getString(R.string.CreateTreasuryFragmentTitle));

        destinationIds = createTreasuryIds();
        return list;
    }
    private ArrayList<Integer> createTreasuryIds() {
        ArrayList<Integer> list = treasuriesIds();
        list.add(R.id.createTreasuryFragment);

        return list;
    }

    private ArrayList<String> createUser() {
        ArrayList<String> list = users();
        list.add(activity.getResources().getString(R.string.CreateUserFragmentTitle));

        destinationIds = createUserIds();
        return list;
    }
    private ArrayList<Integer> createUserIds() {
        ArrayList<Integer> list = usersIds();
        list.add(R.id.createUserFragment);

        return list;
    }

    private ArrayList<String> reserveSchedule() {
        ArrayList<String> list = roomSchedules();
        list.add(activity.getResources().getString(R.string.AppReserve));

        destinationIds = reserveScheduleIds();
        return list;
    }
    private ArrayList<Integer> reserveScheduleIds() {
        ArrayList<Integer> list = roomSchedulesIds();
        list.add(R.id.reserveScheduleFragment);

        return list;
    }

    /*
    ---------- Edit ----------
    */

    private ArrayList<String> editCenter() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = center();
        else
            list = room();

        list.add(activity.getResources().getString(R.string.AppEdit));

        destinationIds = editCenterIds();
        return list;
    }
    private ArrayList<Integer> editCenterIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.editCenterFragment);

        return list;
    }

    private ArrayList<String> editCenterUser() {
        ArrayList<String> list = reference();
        list.add(activity.getResources().getString(R.string.AppEdit));

        destinationIds = editCenterUserIds();
        return list;
    }
    private ArrayList<Integer> editCenterUserIds() {
        ArrayList<Integer> list = referenceIds();
        list.add(R.id.editCenterUserFragment);

        return list;
    }

    private ArrayList<String> editPlatform() {
        ArrayList<String> list = centerPlatforms();
        list.add(activity.getResources().getString(R.string.AppEdit));

        destinationIds = editPlatformIds();
        return list;
    }
    private ArrayList<Integer> editPlatformIds() {
        ArrayList<Integer> list = centerPlatformsIds();
        list.add(R.id.editPlatformFragment);

        return list;
    }

    private ArrayList<String> editSession() {
        ArrayList<String> list = session();
        list.add(activity.getResources().getString(R.string.AppEdit));

        destinationIds = editSessionIds();
        return list;
    }
    private ArrayList<Integer> editSessionIds() {
        ArrayList<Integer> list = sessionIds();
        list.add(R.id.editSessionFragment);

        return list;
    }

    private ArrayList<String> editTreasury() {
        ArrayList<String> list = treasury();
        list.add(activity.getResources().getString(R.string.AppEdit));

        destinationIds = editTreasuryIds();
        return list;
    }
    private ArrayList<Integer> editTreasuryIds() {
        ArrayList<Integer> list = treasuryIds();
        list.add(R.id.editTreasuryFragment);

        return list;
    }

    private ArrayList<String> editUser() {
        ArrayList<String> list = user();
        list.add(activity.getResources().getString(R.string.AppEdit));

        destinationIds = editUserIds();
        return list;
    }
    private ArrayList<Integer> editUserIds() {
        ArrayList<Integer> list = userIds();
        list.add(R.id.editUserFragment);

        return list;
    }

    /*
    ---------- Index ----------
    */

    private ArrayList<String> balances() {
        ArrayList<String> list = centerAccounting();
        list.add(activity.getResources().getString(R.string.BalancesFragmentTitle));

        destinationIds = balancesIds();
        return list;
    }
    private ArrayList<Integer> balancesIds() {
        ArrayList<Integer> list = centerAccountingIds();
        list.add(R.id.balancesFragment);

        return list;
    }

    private ArrayList<String> banks() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.BanksFragmentTitle));

        destinationIds = banksIds();
        return list;
    }
    private ArrayList<Integer> banksIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.banksFragment);

        return list;
    }

    private ArrayList<String> billings() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.BillingsFragmentTitle));

        destinationIds = billingsIds();
        return list;
    }
    private ArrayList<Integer> billingsIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.billingsFragment);

        return list;
    }

    private ArrayList<String> centerPlatforms() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = center();
        else
            list = room();

        list.add(activity.getResources().getString(R.string.AppPlatforms));

        destinationIds = centerPlatformsIds();
        return list;
    }
    private ArrayList<Integer> centerPlatformsIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.centerPlatformsFragment);

        return list;
    }

    private ArrayList<String> centerSchedules() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = center();
        else
            list = room();

        list.add(activity.getResources().getString(R.string.AppSchedules));

        destinationIds = centerSchedulesIds();
        return list;
    }
    private ArrayList<Integer> centerSchedulesIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.centerSchedulesFragment);

        return list;
    }

    private ArrayList<String> centerTags() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = center();
        else
            list = room();

        list.add(activity.getResources().getString(R.string.AppTags));

        destinationIds = centerTagsIds();
        return list;
    }
    private ArrayList<Integer> centerTagsIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.centerTagsFragment);

        return list;
    }

    private ArrayList<String> centerUsers() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = center();
        else
            list = room();

        list.add(activity.getResources().getString(R.string.AppUsers));

        destinationIds = centerUsersIds();
        return list;
    }
    private ArrayList<Integer> centerUsersIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.centerUsersFragment);

        return list;
    }

    private ArrayList<String> clientReports() {
        ArrayList<String> list;

        if (clientReportsType.equals("case"))
            list = casse();
        else
            list = session();

        list.add(activity.getResources().getString(R.string.ClientReportsFragmentTitle));

        destinationIds = clientReportsIds();
        return list;
    }
    private ArrayList<Integer> clientReportsIds() {
        ArrayList<Integer> list;

        if (clientReportsType.equals("case"))
            list = casseIds();
        else
            list = sessionIds();

        list.add(R.id.clientReportsFragment);

        return list;
    }

    private ArrayList<String> commissions() {
        ArrayList<String> list = centerAccounting();
        list.add(activity.getResources().getString(R.string.CommissionsFragmentTitle));

        destinationIds = commissionsIds();
        return list;
    }
    private ArrayList<Integer> commissionsIds() {
        ArrayList<Integer> list = centerAccountingIds();
        list.add(R.id.commissionsFragment);

        return list;
    }

    private ArrayList<String> roomPlatforms() {
        ArrayList<String> list = room();
        list.add(activity.getResources().getString(R.string.AppPlatforms));

        destinationIds = roomPlatformsIds();
        return list;
    }
    private ArrayList<Integer> roomPlatformsIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.roomPlatformsFragment);

        return list;
    }

    private ArrayList<String> roomSchedules() {
        ArrayList<String> list = room();
        list.add(activity.getResources().getString(R.string.AppSchedules));

        destinationIds = roomSchedulesIds();
        return list;
    }
    private ArrayList<Integer> roomSchedulesIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.roomSchedulesFragment);

        return list;
    }

    private ArrayList<String> roomTags() {
        ArrayList<String> list = room();
        list.add(activity.getResources().getString(R.string.AppTags));

        destinationIds = roomTagsIds();
        return list;
    }
    private ArrayList<Integer> roomTagsIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.roomTagsFragment);

        return list;
    }

    private ArrayList<String> roomUsers() {
        ArrayList<String> list = room();
        list.add(activity.getResources().getString(R.string.AppUsers));

        destinationIds = roomUsersIds();
        return list;
    }
    private ArrayList<Integer> roomUsersIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.roomUsersFragment);

        return list;
    }

    private ArrayList<String> rooms() {
        ArrayList<String> list = center();
        list.add(activity.getResources().getString(R.string.AppRooms));

        destinationIds = roomsIds();
        return list;
    }
    private ArrayList<Integer> roomsIds() {
        ArrayList<Integer> list = centerIds();
        list.add(R.id.roomsFragment);

        return list;
    }

    private ArrayList<String> treasuries() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.TreasuriesFragmentTitle));

        destinationIds = treasuriesIds();
        return list;
    }
    private ArrayList<Integer> treasuriesIds() {
        ArrayList<Integer> list = dashboardIds();
        list.add(R.id.treasuriesFragment);

        return list;
    }

    /*
    ---------- Show ----------
    */

    private ArrayList<String> bill() {
        ArrayList<String> list = billings();

        if (billingModel != null && billingModel.getTitle() != null && !billingModel.getTitle().equals(""))
            list.add(billingModel.getTitle());
        else if (billingModel != null && billingModel.getId() != null && !billingModel.getId().equals(""))
            list.add(billingModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = billIds();
        return list;
    }
    private ArrayList<Integer> billIds() {
        ArrayList<Integer> list = billingsIds();
        list.add(R.id.billFragment);

        return list;
    }

    private ArrayList<String> bulkSample() {
        ArrayList<String> list = bulkSamples();

        if (bulkSampleModel != null && bulkSampleModel.getTitle() != null && !bulkSampleModel.getTitle().equals(""))
            list.add(bulkSampleModel.getTitle());
        else if (bulkSampleModel != null && bulkSampleModel.getId() != null && !bulkSampleModel.getId().equals(""))
            list.add(bulkSampleModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = bulkSampleIds();
        return list;
    }
    private ArrayList<Integer> bulkSampleIds() {
        ArrayList<Integer> list = bulkSamplesIds();
        list.add(R.id.bulkSampleFragment);

        return list;
    }

    private ArrayList<String> casse() {
        ArrayList<String> list = room();

        if (caseModel != null && caseModel.getId() != null && !caseModel.getId().equals(""))
            list.add("پرونده" + " " + caseModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = casseIds();
        return list;
    }
    private ArrayList<Integer> casseIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.caseFragment);

        return list;
    }

    private ArrayList<String> centerAccounting() {
        ArrayList<String> list;

        if (roomType.equals("counseling_center"))
            list = center();
        else
            list = room();

        list.add(activity.getResources().getString(R.string.AppAccounting));

        destinationIds = centerAccountingIds();
        return list;
    }
    private ArrayList<Integer> centerAccountingIds() {
        ArrayList<Integer> list;

        if (roomType.equals("counseling_center"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.centerAccountingFragment);

        return list;
    }

    private ArrayList<String> center() {
        ArrayList<String> list = centers();

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
    private ArrayList<Integer> centerIds() {
        ArrayList<Integer> list = centersIds();
        list.add(R.id.centerFragment);

        return list;
    }

    private ArrayList<String> folder() {
        ArrayList<String> list = downloads();
        list.add(folderName);

        destinationIds = folderIds();
        return list;
    }
    private ArrayList<Integer> folderIds() {
        ArrayList<Integer> list = downloadsIds();
        list.add(R.id.folderFragment);

        return list;
    }

    private ArrayList<String> reference() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = centerUsers();
        else
            list = roomUsers();

        if (referenceType.equals("user")) {
            if (userModel != null && userModel.getId() != null && userModel.getId().equals(((MainActivity) activity).singleton.getUserModel().getId()))
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
    private ArrayList<Integer> referenceIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centerUsersIds();
        else
            list = roomUsersIds();

        list.add(R.id.referenceFragment);

        return list;
    }

    private ArrayList<String> room() {
        ArrayList<String> list;

        if (!roomType.equals("room"))
            list = centers();
        else
            list = center();

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
    private ArrayList<Integer> roomIds() {
        ArrayList<Integer> list;

        if (!roomType.equals("room"))
            list = centersIds();
        else
            list = centerIds();

        list.add(R.id.roomFragment);

        return list;
    }

    private ArrayList<String> sample() {
        ArrayList<String> list;

        if (caseModel != null)
            list = casse();
        else
            list = reference();

        if (sampleModel != null && sampleModel.getTitle() != null && !sampleModel.getTitle().equals(""))
            list.add(sampleModel.getTitle());
        else if (sampleModel != null && sampleModel.getId() != null && !sampleModel.getId().equals(""))
            list.add("نمونه" + " " + sampleModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = sampleIds();
        return list;
    }
    private ArrayList<Integer> sampleIds() {
        ArrayList<Integer> list;

        if (caseModel != null)
            list = casseIds();
        else
            list = referenceIds();

        list.add(R.id.sampleFragment);

        return list;
    }

    private ArrayList<String> session() {
        ArrayList<String> list;

        if (sessionType.equals("session") && sessionModel != null && sessionModel.getCasse() != null)
            list = casse();
        else if (sessionType.equals("schedule") && scheduleModel != null && scheduleModel.getCasse() != null)
            list = casse();
        else
            list = room();

        if (sessionType.equals("session") && sessionModel != null && sessionModel.getId() != null && !sessionModel.getId().equals(""))
            list.add("جلسه" + " " + sessionModel.getId());
        else if (sessionType.equals("schedule") && scheduleModel != null && scheduleModel.getId() != null && !scheduleModel.getId().equals(""))
            list.add("جلسه" + " " + scheduleModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = sessionIds();
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

        list.add(R.id.sessionFragment);

        return list;
    }

    private ArrayList<String> treasury() {
        ArrayList<String> list = treasuries();

        if (treasuriesModel != null && treasuriesModel.getTitle() != null && !treasuriesModel.getTitle().equals(""))
            list.add(treasuriesModel.getTitle());
        else if (treasuriesModel != null && treasuriesModel.getId() != null && !treasuriesModel.getId().equals(""))
            list.add(treasuriesModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = treasuryIds();
        return list;
    }
    private ArrayList<Integer> treasuryIds() {
        ArrayList<Integer> list = treasuriesIds();
        list.add(R.id.treasuryFragment);

        return list;
    }

    private ArrayList<String> user() {
        ArrayList<String> list = users();

        if (userModel != null && userModel.getName() != null && !userModel.getName().equals(""))
            list.add(userModel.getName());
        else if (userModel != null && userModel.getId() != null && !userModel.getId().equals(""))
            list.add(userModel.getId());
        else
            list.add(activity.getResources().getString(R.string.AppDefaultUnknown));

        destinationIds = userIds();
        return list;
    }
    private ArrayList<Integer> userIds() {
        ArrayList<Integer> list = usersIds();
        list.add(R.id.userFragment);

        return list;
    }

}