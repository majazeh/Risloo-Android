package com.majazeh.risloo.Utils.Entities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.ReserveScheduleFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterUserFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Edit.EditPlatformFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Edit.EditTreasuryFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.CenterPlatformsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.CenterSchedulesFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.CenterTagsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.CenterUsersFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.ClientReportsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.RoomPlatformsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.RoomSchedulesFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.RoomTagsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.RoomUsersFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.BillFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.BulkSampleFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.CaseFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.CenterFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.MeFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.ReferenceFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.RoomFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.SampleFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.SessionFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.TreasuryFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.UserFragmentArgs;
import com.mre.ligheh.Model.TypeModel.BillingModel;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class BreadCrumb {

    // Objects
    private final Activity activity;

    // Models
    private BillingModel billingModel;
    private BulkSampleModel bulkSampleModel;
    private CaseModel caseModel;
    private CenterModel centerModel;
    private RoomModel roomModel;
    private ScheduleModel scheduleModel;
    private SampleModel sampleModel;
    private SessionModel sessionModel;
    private SessionPlatformModel sessionPlatformModel;
    private TreasuriesModel treasuriesModel;
    private UserModel userModel;

    // Vars
    private String centerType = "", sessionType = "", clientReportsType = "", referenceType = "";
    private ArrayList<Integer> destinationIds;

    public BreadCrumb(@NonNull Activity activity) {
        this.activity = activity;
    }

    /*
    ---------- Methods ----------
    */

    public SpannableStringBuilder getFa(NavDestination destination, Bundle arguments) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        ArrayList<String> list = construct(destination, arguments);
        for (int i = 0; i < list.size(); i++) {
            String label = list.get(i);

            builder.append(label);
            if (i != list.size() - 1) {
                builder.append("  >  ");
                int position = i;

                builder.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        if (!label.equals("نامعلوم"))
                            navigateTo(destinationIds.get(position));
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint textPaint) {
                        textPaint.setColor(activity.getResources().getColor(R.color.Gray500));
                        textPaint.setUnderlineText(false);
                    }

                }, builder.toString().indexOf(label), builder.toString().indexOf(label) + label.length(), 0);
            }
        }

        return builder;
    }

    /*
    ---------- Construct ----------
    */

    @SuppressLint("NonConstantResourceId")
    private ArrayList<String> construct(NavDestination destination, Bundle arguments) {
        switch (destination.getId()) {

            // -------------------- Toolbar

            case R.id.meFragment:
                setModels("user", MeFragmentArgs.fromBundle(arguments).getTypeModel());
                return me();
            case R.id.treasuriesFragment:
                return treasuries();
            case R.id.billingsFragment:
                return billings();
            case R.id.paymentsFragment:
                return payments();

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
            case R.id.scalesFragment:
                return scales();
            case R.id.samplesFragment:
                return samples();
            case R.id.bulkSamplesFragment:
                return bulkSamples();
            case R.id.documentsFragment:
                return documents();

            // -------------------- Create

            case R.id.createCaseFragment:
                return createCase();
            case R.id.createCaseUserFragment:
                return createCaseUser();
            case R.id.createCenterFragment:
                return createCenter();
            case R.id.createCenterUserFragment:
                return createCenterUser();
            case R.id.createDocumentFragment:
                return createDocument();
            case R.id.createReportFragment:
                return createReport();
            case R.id.createPracticeFragment:
                return createPractice();
            case R.id.createRoomFragment:
                return createRoom();
            case R.id.createRoomUserFragment:
                return createRoomUser();
            case R.id.createPlatformFragment:
                return createPlatform();
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
                setModels("schedule", ReserveScheduleFragmentArgs.fromBundle(arguments).getTypeModel());
                return reserveSchedule();

            // -------------------- Edit

            case R.id.editCenterFragment:
                setModels("center", EditCenterFragmentArgs.fromBundle(arguments).getTypeModel());
                return editCenter();
            case R.id.editCenterUserFragment:
                setModels("user", EditCenterUserFragmentArgs.fromBundle(arguments).getTypeModel());
                return editCenterUser();
            case R.id.editPlatformFragment:
                setModels("platform", EditPlatformFragmentArgs.fromBundle(arguments).getTypeModel());
                return editPlatform();
            case R.id.editSessionFragment:
                setModels("session", EditSessionFragmentArgs.fromBundle(arguments).getTypeModel());
                return editSession();
            case R.id.editTreasuryFragment:
                setModels("treasury", EditTreasuryFragmentArgs.fromBundle(arguments).getTypeModel());
                return editTreasury();
            case R.id.editUserFragment:
                setModels("user", EditUserFragmentArgs.fromBundle(arguments).getTypeModel());
                return editUser();

            // -------------------- Index

            case R.id.centerPlatformsFragment:
                setModels("center", CenterPlatformsFragmentArgs.fromBundle(arguments).getTypeModel());
                return centerPlatforms();
            case R.id.centerSchedulesFragment:
                setModels("center", CenterSchedulesFragmentArgs.fromBundle(arguments).getTypeModel());
                return centerSchedules();
            case R.id.centerTagsFragment:
                setModels("center", CenterTagsFragmentArgs.fromBundle(arguments).getTypeModel());
                return centerTags();
            case R.id.centerUsersFragment:
                setModels("center", CenterUsersFragmentArgs.fromBundle(arguments).getTypeModel());
                return centerUsers();
            case R.id.clientReportsFragment:{
                TypeModel typeModel = ClientReportsFragmentArgs.fromBundle(arguments).getTypeModel();

                if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CaseModel")) {
                    clientReportsType = "case";
                    setModels("case", typeModel);
                } else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("SessionModel")) {
                    clientReportsType = "session";
                    setModels("session", typeModel);
                }

                return clientReports();
            } case R.id.roomPlatformsFragment:
                setModels("room", RoomPlatformsFragmentArgs.fromBundle(arguments).getTypeModel());
                return roomPlatforms();
            case R.id.roomSchedulesFragment:
                setModels("room", RoomSchedulesFragmentArgs.fromBundle(arguments).getTypeModel());
                return roomSchedules();
            case R.id.roomTagsFragment:
                setModels("room", RoomTagsFragmentArgs.fromBundle(arguments).getTypeModel());
                return roomTags();
            case R.id.roomUsersFragment:
                setModels("room", RoomUsersFragmentArgs.fromBundle(arguments).getTypeModel());
                return roomUsers();

            // -------------------- Show

            case R.id.billFragment:
                setModels("bill", BillFragmentArgs.fromBundle(arguments).getTypeModel());
                return bill();
            case R.id.bulkSampleFragment:
                setModels("bulk", BulkSampleFragmentArgs.fromBundle(arguments).getTypeModel());
                return bulkSample();
            case R.id.caseFragment:
                setModels("case", CaseFragmentArgs.fromBundle(arguments).getTypeModel());
                return casse();
            case R.id.centerFragment:
                setModels("center", CenterFragmentArgs.fromBundle(arguments).getTypeModel());
                return center();
            case R.id.referenceFragment:
                String centerId = ReferenceFragmentArgs.fromBundle(arguments).getCenterId();

                if (centerId != null)
                    referenceType = "user";
                else
                    referenceType = "center";

                setModels(referenceType, ReferenceFragmentArgs.fromBundle(arguments).getTypeModel());
                return reference();
            case R.id.roomFragment:
                centerType = RoomFragmentArgs.fromBundle(arguments).getType();

                if (!centerType.equals("room"))
                    setModels("center", RoomFragmentArgs.fromBundle(arguments).getTypeModel());
                else
                    setModels("room", RoomFragmentArgs.fromBundle(arguments).getTypeModel());

                return room();
            case R.id.sampleFragment:
                setModels("sample", SampleFragmentArgs.fromBundle(arguments).getTypeModel());
                return sample();
            case R.id.sessionFragment: {
                TypeModel typeModel = SessionFragmentArgs.fromBundle(arguments).getTypeModel();

                if (StringManager.substring(typeModel.getClass().getName(), '.').equals("ScheduleModel")) {
                    sessionType = "schedule";
                    setModels("schedule", typeModel);
                } else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("SessionModel")) {
                    sessionType = "session";
                    setModels("session", typeModel);
                }

                return session();
            } case R.id.treasuryFragment:
                setModels("treasury", TreasuryFragmentArgs.fromBundle(arguments).getTypeModel());
                return treasury();
            case R.id.userFragment:
                setModels("user", UserFragmentArgs.fromBundle(arguments).getTypeModel());
                return user();
        }

        return new ArrayList<>();
    }

    @SuppressLint("NonConstantResourceId")
    private void navigateTo(int position) {
        switch (position) {

            // -------------------- Toolbar

            case R.id.meFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalMeFragment(((MainActivity) activity).singleton.getUserModel());
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.treasuriesFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalTreasuriesFragment();
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.billingsFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalBillingsFragment();
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.paymentsFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalPaymentsFragment();
                ((MainActivity) activity).navController.navigate(action);
            } break;

            // -------------------- Drawer

            case R.id.dashboardFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalDashboardFragment();
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.centersFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalCentersFragment();
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.casesFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalCasesFragment();
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.sessionsFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalSessionsFragment();
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.usersFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalUsersFragment();
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.scalesFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalScalesFragment();
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.samplesFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalSamplesFragment();
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.bulkSamplesFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalBulkSamplesFragment();
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.documentsFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalDocumentsFragment();
                ((MainActivity) activity).navController.navigate(action);
            } break;

            // -------------------- Index

            case R.id.centerPlatformsFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalCenterPlatformsFragment(centerModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.centerSchedulesFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalCenterSchedulesFragment(centerModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.centerTagsFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalCenterTagsFragment(centerModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.centerUsersFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalCenterUsersFragment(centerModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.clientReportsFragment: {
                NavDirections action;

                if (clientReportsType.equals("case"))
                    action = NavigationMainDirections.actionGlobalClientReportsFragment(caseModel);
                else
                    action = NavigationMainDirections.actionGlobalClientReportsFragment(sessionModel);

                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.roomPlatformsFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalRoomPlatformsFragment(roomModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.roomSchedulesFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalRoomSchedulesFragment(roomModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.roomTagsFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalRoomTagsFragment(roomModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.roomUsersFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalRoomUsersFragment(roomModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;

            // -------------------- Show

            case R.id.billFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalBillFragment(billingModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.bulkSampleFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalBulkSampleFragment(bulkSampleModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.caseFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalCaseFragment(caseModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.centerFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalCenterFragment(centerModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.referenceFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(centerType, centerModel.getCenterId(), userModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.roomFragment: {
                NavDirections action;

                if (!centerType.equals("room"))
                    action = NavigationMainDirections.actionGlobalRoomFragment("personal_clinic", centerModel);
                else
                    action = NavigationMainDirections.actionGlobalRoomFragment("room", roomModel);

                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.sampleFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalSampleFragment(sampleModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.sessionFragment: {
                NavDirections action;

                if (!sessionType.equals("session"))
                    action = NavigationMainDirections.actionGlobalSessionFragment(scheduleModel);
                else
                    action = NavigationMainDirections.actionGlobalSessionFragment(sessionModel);

                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.treasuryFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalTreasuryFragment(treasuriesModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
            case R.id.userFragment: {
                NavDirections action = NavigationMainDirections.actionGlobalUserFragment(userModel);
                ((MainActivity) activity).navController.navigate(action);
            } break;
        }
    }

    private void setModels(String type, TypeModel model) {
        switch (type) {
            case "user":
                userModel = (UserModel) model;
                break;
            case "center":
                centerModel = (CenterModel) model;
                centerType = centerModel.getCenterType();
                break;
            case "room":
                roomModel = (RoomModel) model;
                centerType = roomModel.getRoomType();

                if (roomModel.getRoomCenter() != null)
                    centerModel = roomModel.getRoomCenter();
                break;
            case "case":
                caseModel = (CaseModel) model;

                if (caseModel.getCaseRoom() != null) {
                    roomModel = caseModel.getCaseRoom();
                    centerType = roomModel.getRoomType();

                    if (roomModel.getRoomCenter() != null)
                        centerModel = roomModel.getRoomCenter();
                }
                break;
            case "schedule":
                scheduleModel = (ScheduleModel) model;

                if (scheduleModel.getCaseModel() != null) {
                    caseModel = scheduleModel.getCaseModel();

                    if (caseModel.getCaseRoom() != null) {
                        roomModel = caseModel.getCaseRoom();
                        centerType = roomModel.getRoomType();

                        if (roomModel.getRoomCenter() != null)
                            centerModel = roomModel.getRoomCenter();
                    }
                } else {
                    if (scheduleModel.getRoom() != null) {
                        roomModel = scheduleModel.getRoom();
                        centerType = roomModel.getRoomType();

                        if (roomModel.getRoomCenter() != null)
                            centerModel = roomModel.getRoomCenter();
                    }
                }
                break;
            case "session":
                sessionModel = (SessionModel) model;

                if (sessionModel.getCaseModel() != null) {
                    caseModel = sessionModel.getCaseModel();

                    if (caseModel.getCaseRoom() != null) {
                        roomModel = caseModel.getCaseRoom();
                        centerType = roomModel.getRoomType();

                        if (roomModel.getRoomCenter() != null)
                            centerModel = roomModel.getRoomCenter();
                    }
                } else {
                    if (sessionModel.getRoom() != null) {
                        roomModel = sessionModel.getRoom();
                        centerType = roomModel.getRoomType();

                        if (roomModel.getRoomCenter() != null)
                            centerModel = roomModel.getRoomCenter();
                    }
                }
                break;
            case "platform":
                sessionPlatformModel = (SessionPlatformModel) model;
                break;
            case "sample":
                sampleModel = (SampleModel) model;

                if (sampleModel.getSampleCase() != null) {
                    caseModel = sampleModel.getSampleCase();

                    if (caseModel.getCaseRoom() != null) {
                        roomModel = caseModel.getCaseRoom();
                        centerType = roomModel.getRoomType();

                        if (roomModel.getRoomCenter() != null)
                            centerModel = roomModel.getRoomCenter();
                    }
                } else {
                    if (sampleModel.getSampleRoom() != null) {
                        roomModel = sampleModel.getSampleRoom();
                        centerType = roomModel.getRoomType();

                        if (roomModel.getRoomCenter() != null)
                            centerModel = roomModel.getRoomCenter();
                    }
                }

                if (sampleModel.getClient() != null)
                    userModel = sampleModel.getClient();
                break;
            case "bulk":
                bulkSampleModel = (BulkSampleModel) model;
                break;
            case "bill":
                billingModel = (BillingModel) model;
                break;
            case "treasury":
                treasuriesModel = (TreasuriesModel) model;
                break;
        }
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

    /*
    ---------- Create ----------
    */

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

    private ArrayList<String> createReport() {
        ArrayList<String> list = clientReports();
        list.add(activity.getResources().getString(R.string.CreateReportFragmentTitle));

        destinationIds = createReportIds();
        return list;
    }
    private ArrayList<Integer> createReportIds() {
        ArrayList<Integer> list = clientReportsIds();
        list.add(R.id.createReportFragment);

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
        list.add("رزرو");

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

        if (!centerType.equals("room"))
            list = center();
        else
            list = room();

        list.add("ویرایش");

        destinationIds = editCenterIds();
        return list;
    }
    private ArrayList<Integer> editCenterIds() {
        ArrayList<Integer> list;

        if (!centerType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.editCenterFragment);

        return list;
    }

    private ArrayList<String> editCenterUser() {
        ArrayList<String> list = reference();
        list.add("ویرایش");

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
        list.add("ویرایش");

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
        list.add("ویرایش");

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
        list.add("ویرایش");

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
        list.add("ویرایش");

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

    private ArrayList<String> centerPlatforms() {
        ArrayList<String> list;

        if (!centerType.equals("room"))
            list = center();
        else
            list = room();

        list.add("محل\u200Cهای برگزاری");

        destinationIds = centerPlatformsIds();
        return list;
    }
    private ArrayList<Integer> centerPlatformsIds() {
        ArrayList<Integer> list;

        if (!centerType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.centerPlatformsFragment);

        return list;
    }

    private ArrayList<String> centerSchedules() {
        ArrayList<String> list;

        if (!centerType.equals("room"))
            list = center();
        else
            list = room();

        list.add("برنامه\u200Cهای درمانی");

        destinationIds = centerSchedulesIds();
        return list;
    }
    private ArrayList<Integer> centerSchedulesIds() {
        ArrayList<Integer> list;

        if (!centerType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.centerSchedulesFragment);

        return list;
    }

    private ArrayList<String> centerTags() {
        ArrayList<String> list;

        if (!centerType.equals("room"))
            list = center();
        else
            list = room();

        list.add("برچسب\u200Cهای مهم");

        destinationIds = centerTagsIds();
        return list;
    }
    private ArrayList<Integer> centerTagsIds() {
        ArrayList<Integer> list;

        if (!centerType.equals("room"))
            list = centerIds();
        else
            list = roomIds();

        list.add(R.id.centerTagsFragment);

        return list;
    }

    private ArrayList<String> centerUsers() {
        ArrayList<String> list;

        if (!centerType.equals("room"))
            list = center();
        else
            list = room();

        list.add("اعضاء");

        destinationIds = centerUsersIds();
        return list;
    }
    private ArrayList<Integer> centerUsersIds() {
        ArrayList<Integer> list;

        if (!centerType.equals("room"))
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

    private ArrayList<String> roomPlatforms() {
        ArrayList<String> list = room();
        list.add("محل\u200Cهای برگزاری");

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
        list.add("برنامه\u200Cهای درمانی");

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
        list.add("برچسب\u200Cهای مهم");

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
        list.add("اعضاء");

        destinationIds = roomUsersIds();
        return list;
    }
    private ArrayList<Integer> roomUsersIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.roomUsersFragment);

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
            list.add("صورت حساب" + " " + billingModel.getId());
        else
            list.add("نامعلوم");

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
            list.add("نمونه\u200Cای" + " " + bulkSampleModel.getId());
        else
            list.add("نامعلوم");

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

        if (caseModel != null && caseModel.getCaseId() != null && !caseModel.getCaseId().equals(""))
            list.add("پرونده\u200Cی" + " " + caseModel.getCaseId());
        else
            list.add("نامعلوم");

        destinationIds = casseIds();
        return list;
    }
    private ArrayList<Integer> casseIds() {
        ArrayList<Integer> list = roomIds();
        list.add(R.id.caseFragment);

        return list;
    }

    private ArrayList<String> center() {
        ArrayList<String> list = centers();

        try {
            if (centerModel != null && centerModel.getDetail() != null && !centerModel.getDetail().getString("title").equals(""))
                list.add(centerModel.getDetail().getString("title"));
            else if (centerModel != null && centerModel.getCenterId() != null && !centerModel.getCenterId().equals(""))
                list.add("مرکز" + " " + centerModel.getCenterId());
            else
                list.add("نامعلوم");
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

    private ArrayList<String> reference() {
        ArrayList<String> list;

        if (!centerType.equals("room"))
            list = centerUsers();
        else
            list = roomUsers();

        if (referenceType.equals("user")) {
            if (userModel != null && userModel.getId() != null && userModel.getId().equals(((MainActivity) activity).singleton.getId()))
                list.add(activity.getResources().getString(R.string.MeFragmentTitle));
            else if (userModel != null && userModel.getName() != null && !userModel.getName().equals(""))
                list.add(userModel.getName());
            else if (userModel != null && userModel.getId() != null && !userModel.getId().equals(""))
                list.add("مراجع" + " " + userModel.getId());
            else
                list.add("نامعلوم");
        } else {
            list.add(activity.getResources().getString(R.string.MeFragmentTitle));
        }

        destinationIds = referenceIds();
        return list;
    }
    private ArrayList<Integer> referenceIds() {
        ArrayList<Integer> list;

        if (!centerType.equals("room"))
            list = centerUsersIds();
        else
            list = roomUsersIds();

        list.add(R.id.referenceFragment);

        return list;
    }

    private ArrayList<String> room() {
        ArrayList<String> list;

        if (!centerType.equals("room"))
            list = centers();
        else
            list = center();

        if (!centerType.equals("room")) {
            try {
                if (centerModel != null && centerModel.getDetail() != null && !centerModel.getDetail().getString("title").equals(""))
                    list.add("کلینیک شخصی" + " " + centerModel.getDetail().getString("title"));
                else if (centerModel != null && centerModel.getCenterId() != null && !centerModel.getCenterId().equals(""))
                    list.add("کلینیک شخصی" + " " + centerModel.getCenterId());
                else
                    list.add("نامعلوم");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if (roomModel != null && roomModel.getRoomManager() != null && roomModel.getRoomManager().getName() != null && !roomModel.getRoomManager().getName().equals(""))
                list.add("اتاق درمان" + " " + roomModel.getRoomManager().getName());
            else if (roomModel != null && roomModel.getRoomId() != null && !roomModel.getRoomId().equals(""))
                list.add("اتاق درمان" + " " + roomModel.getRoomId());
            else
                list.add("نامعلوم");
        }

        destinationIds = roomIds();
        return list;
    }
    private ArrayList<Integer> roomIds() {
        ArrayList<Integer> list;

        if (!centerType.equals("room"))
            list = centersIds();
        else
            list = centerIds();

        list.add(R.id.roomFragment);

        return list;
    }

    private ArrayList<String> sample() {
        ArrayList<String> list = reference();

        if (sampleModel != null && sampleModel.getSampleTitle() != null && !sampleModel.getSampleTitle().equals(""))
            list.add(sampleModel.getSampleTitle());
        else if (sampleModel != null && sampleModel.getSampleId() != null && !sampleModel.getSampleId().equals(""))
            list.add("نمونه\u200Cای" + " " + sampleModel.getSampleId());
        else
            list.add("نامعلوم");

        destinationIds = sampleIds();
        return list;
    }
    private ArrayList<Integer> sampleIds() {
        ArrayList<Integer> list = referenceIds();
        list.add(R.id.sampleFragment);

        return list;
    }

    private ArrayList<String> session() {
        ArrayList<String> list;

        if (sessionType.equals("session") && sessionModel.getCaseModel() != null)
            list = casse();
        else if (sessionType.equals("schedule") && scheduleModel.getCaseModel() != null)
            list = casse();
        else
            list = room();

        if (sessionType.equals("session") && sessionModel != null && sessionModel.getId() != null && !sessionModel.getId().equals(""))
            list.add("جلسه\u200Cی" + " " + sessionModel.getId());
        else if (sessionType.equals("schedule") && scheduleModel != null && scheduleModel.getId() != null && !scheduleModel.getId().equals(""))
            list.add("جلسه\u200Cی" + " " + scheduleModel.getId());
        else
            list.add("نامعلوم");

        destinationIds = sessionIds();
        return list;
    }
    private ArrayList<Integer> sessionIds() {
        ArrayList<Integer> list;

        if (sessionType.equals("session") && sessionModel.getCaseModel() != null)
            list = casseIds();
        else if (sessionType.equals("schedule") && scheduleModel.getCaseModel() != null)
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
            list.add("کیف پول" + " " + treasuriesModel.getId());
        else
            list.add("نامعلوم");

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
            list.add("عضو" + " " + userModel.getId());
        else
            list.add("نامعلوم");

        destinationIds = userIds();
        return list;
    }
    private ArrayList<Integer> userIds() {
        ArrayList<Integer> list = usersIds();
        list.add(R.id.userFragment);

        return list;
    }

}