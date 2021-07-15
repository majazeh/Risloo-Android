package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavDestination;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterUserFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.CenterSchedulesFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.CenterUsersFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.ClientReportsFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.RoomSchedulesFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Index.RoomUsersFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.BulkSampleFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.CaseFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.CenterFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.MeFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.ReferenceFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.RoomFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.SampleFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.SessionFragmentArgs;
import com.majazeh.risloo.Views.Fragments.Show.UserFragmentArgs;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class BreadCrumb {

    // Objects
    private final Activity activity;

    // Models
    private UserModel userModel;
    private CenterModel centerModel;
    private RoomModel roomModel;
    private CaseModel caseModel;
    private SessionModel sessionModel;
    private SampleModel sampleModel;
    private BulkSampleModel bulkSampleModel;

    // Vars
    private String centerType = "", clientReportsType = "";
    private String referenceCenterId = "", referenceUserId = "";

    public BreadCrumb(@NonNull Activity activity) {
        this.activity = activity;
    }
    
    public SpannableStringBuilder getFa(NavDestination destination, Bundle arguments) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        ArrayList<String> list = construct(destination, arguments);

        for (int i = 0; i < list.size(); i++) {
            String label = list.get(i);

            builder.append(label);
            if (i != list.size() - 1) {
                builder.append("  >  ");

                builder.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        Log.e("Label", label);
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

    private ArrayList<String> construct(NavDestination destination, Bundle arguments) {
        switch (destination.getId()) {

            // -------------------- Toolbar

            case R.id.meFragment:
                setModals("user", MeFragmentArgs.fromBundle(arguments).getTypeModel());
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
            case R.id.createPracticeFragment:
                return createPractice();
            case R.id.createReportFragment:
                return createReport();
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
            case R.id.createTreasuryFragment:
                return createTreasury();
            case R.id.createUserFragment:
                return createUser();

            // -------------------- Edit

            case R.id.editCenterFragment:
                setModals("center", EditCenterFragmentArgs.fromBundle(arguments).getTypeModel());
                return editCenter();
            case R.id.editCenterUserFragment:
                setModals("user", EditCenterUserFragmentArgs.fromBundle(arguments).getTypeModel());
                return editCenterUser();
            case R.id.editSessionFragment:
                setModals("session", EditSessionFragmentArgs.fromBundle(arguments).getTypeModel());
                return editSession();
            case R.id.editTreasuryFragment:
                return editTreasury();
            case R.id.editUserFragment:
                setModals("user", EditUserFragmentArgs.fromBundle(arguments).getTypeModel());
                return editUser();

            // -------------------- Index

            case R.id.centerUsersFragment:
                setModals("center", CenterUsersFragmentArgs.fromBundle(arguments).getTypeModel());
                return centerUsers();
            case R.id.centerSchedulesFragment:
                setModals("center", CenterSchedulesFragmentArgs.fromBundle(arguments).getTypeModel());
                return centerSchedules();
            case R.id.roomUsersFragment:
                setModals("room", RoomUsersFragmentArgs.fromBundle(arguments).getTypeModel());
                return roomUsers();
            case R.id.roomSchedulesFragment:
                setModals("room", RoomSchedulesFragmentArgs.fromBundle(arguments).getTypeModel());
                return roomSchedules();
            case R.id.clientReportsFragment:
                clientReportsType = ClientReportsFragmentArgs.fromBundle(arguments).getType();

                if (clientReportsType.equals("case"))
                    setModals("case", ClientReportsFragmentArgs.fromBundle(arguments).getTypeModel());
                else
                    setModals("session", ClientReportsFragmentArgs.fromBundle(arguments).getTypeModel());

                return clientReports();

            // -------------------- Show

            case R.id.treasuryFragment:
                return treasury();
            case R.id.billFragment:
                return bill();
            case R.id.centerFragment:
                setModals("center", CenterFragmentArgs.fromBundle(arguments).getTypeModel());
                return center();
            case R.id.roomFragment:
                centerType = RoomFragmentArgs.fromBundle(arguments).getType();

                if (!centerType.equals("room"))
                    setModals("center", RoomFragmentArgs.fromBundle(arguments).getTypeModel());
                else
                    setModals("room", RoomFragmentArgs.fromBundle(arguments).getTypeModel());

                return room();
            case R.id.caseFragment:
                setModals("case", CaseFragmentArgs.fromBundle(arguments).getTypeModel());
                return casse();
            case R.id.sessionFragment:
                setModals("session", SessionFragmentArgs.fromBundle(arguments).getTypeModel());
                return session();
            case R.id.userFragment:
                setModals("user", UserFragmentArgs.fromBundle(arguments).getTypeModel());
                return user();
            case R.id.sampleFragment:
                setModals("sample", SampleFragmentArgs.fromBundle(arguments).getTypeModel());
                return sample();
            case R.id.bulkSampleFragment:
                setModals("bulk", BulkSampleFragmentArgs.fromBundle(arguments).getTypeModel());
                return bulkSample();
            case R.id.referenceFragment:
                referenceCenterId = ReferenceFragmentArgs.fromBundle(arguments).getCenterId();
                referenceUserId = ReferenceFragmentArgs.fromBundle(arguments).getUserId();

                if (referenceCenterId != null)
                    setModals("user", ReferenceFragmentArgs.fromBundle(arguments).getTypeModel());

                if (referenceUserId != null)
                    setModals("center", ReferenceFragmentArgs.fromBundle(arguments).getTypeModel());

                return reference();
        }
        return new ArrayList<>();
    }

    private void setModals(String type, TypeModel model) {
        switch (type) {
            case "user":
                userModel = (UserModel) model;
                break;
            case "center":
                centerModel = (CenterModel) model;
                break;
            case "room":
                roomModel = (RoomModel) model;

                if (roomModel.getRoomCenter() != null)
                    centerModel = roomModel.getRoomCenter();
                break;
            case "case":
                caseModel = (CaseModel) model;

                if (caseModel.getCaseRoom() != null) {
                    roomModel = caseModel.getCaseRoom();

                    if (roomModel.getRoomCenter() != null)
                        centerModel = roomModel.getRoomCenter();
                }
                break;
            case "session":
                sessionModel = (SessionModel) model;

                if (sessionModel.getCaseModel() != null) {
                    caseModel = sessionModel.getCaseModel();

                    if (caseModel.getCaseRoom() != null) {
                        roomModel = caseModel.getCaseRoom();

                        if (roomModel.getRoomCenter() != null)
                            centerModel = roomModel.getRoomCenter();
                    }
                } else {
                    if (sessionModel.getRoom() != null) {
                        roomModel = sessionModel.getRoom();

                        if (roomModel.getRoomCenter() != null)
                            centerModel = roomModel.getRoomCenter();
                    }
                }
                break;
            case "sample":
                sampleModel = (SampleModel) model;

                if (sampleModel.getSampleCase() != null) {
                    caseModel = sampleModel.getSampleCase();

                    if (caseModel.getCaseRoom() != null) {
                        roomModel = caseModel.getCaseRoom();

                        if (roomModel.getRoomCenter() != null)
                            centerModel = roomModel.getRoomCenter();
                    }
                } else {
                    if (sampleModel.getSampleRoom() != null) {
                        roomModel = sampleModel.getSampleRoom();

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
        }
    }

    /*
    ---------- Toolbar ----------
    */

    private ArrayList<String> me() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.MeFragmentTitle));

        return list;
    }

    private ArrayList<String> treasuries() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.TreasuriesFragmentTitle));

        return list;
    }

    private ArrayList<String> billings() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.BillingsFragmentTitle));

        return list;
    }

    private ArrayList<String> payments() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.PaymentsFragmentTitle));

        return list;
    }

    /*
    ---------- Drawer ----------
    */

    private ArrayList<String> dashboard() {
        ArrayList<String> list = new ArrayList<>();
        list.add(activity.getResources().getString(R.string.DashboardFragmentTitle));

        return list;
    }

    private ArrayList<String> centers() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.CentersFragmentTitle));

        return list;
    }

    private ArrayList<String> cases() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.CasesFragmentTitle));

        return list;
    }

    private ArrayList<String> sessions() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.SessionsFragmentTitle));

        return list;
    }

    private ArrayList<String> users() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.UsersFragmentTitle));

        return list;
    }

    private ArrayList<String> scales() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.ScalesFragmentTitle));

        return list;
    }

    private ArrayList<String> samples() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.SamplesFragmentTitle));

        return list;
    }

    private ArrayList<String> bulkSamples() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.BulkSamplesFragmentTitle));

        return list;
    }

    private ArrayList<String> documents() {
        ArrayList<String> list = dashboard();
        list.add(activity.getResources().getString(R.string.DocumentsFragmentTitle));

        return list;
    }

    /*
    ---------- Create ----------
    */

    private ArrayList<String> createCase() {
        ArrayList<String> list = room();
        list.add(activity.getResources().getString(R.string.CreateCaseFragmentTitle));

        return list;
    }

    private ArrayList<String> createCaseUser() {
        ArrayList<String> list = casse();
        list.add(activity.getResources().getString(R.string.CreateCaseUserFragmentTitle));

        return list;
    }

    private ArrayList<String> createCenter() {
        ArrayList<String> list = centers();
        list.add(activity.getResources().getString(R.string.CreateCenterFragmentTitle));

        return list;
    }

    private ArrayList<String> createCenterUser() {
        ArrayList<String> list = centerUsers();
        list.add(activity.getResources().getString(R.string.CreateCenterUserFragmentTitle));

        return list;
    }

    private ArrayList<String> createDocument() {
        ArrayList<String> list = documents();
        list.add(activity.getResources().getString(R.string.CreateDocumentFragmentTitle));

        return list;
    }

    private ArrayList<String> createPractice() {
        ArrayList<String> list = session();
        list.add(activity.getResources().getString(R.string.CreatePracticeFragmentTitle));

        return list;
    }

    private ArrayList<String> createReport() {
        ArrayList<String> list = clientReports();
        list.add(activity.getResources().getString(R.string.CreateReportFragmentTitle));

        return list;
    }

    private ArrayList<String> createRoom() {
        ArrayList<String> list = center();
        list.add(activity.getResources().getString(R.string.CreateRoomFragmentTitle));

        return list;
    }

    private ArrayList<String> createRoomUser() {
        ArrayList<String> list = roomUsers();
        list.add(activity.getResources().getString(R.string.CreateRoomUserFragmentTitle));

        return list;
    }

    private ArrayList<String> createSample() {
        ArrayList<String> list = samples();
        list.add(activity.getResources().getString(R.string.CreateSampleFragmentTitle));

        return list;
    }

    private ArrayList<String> createSchedule() {
        ArrayList<String> list = room();
        list.add(activity.getResources().getString(R.string.CreateScheduleFragmentTitle));

        return list;
    }

    private ArrayList<String> createSession() {
        ArrayList<String> list = casse();
        list.add(activity.getResources().getString(R.string.CreateSessionFragmentTitle));

        return list;
    }

    private ArrayList<String> createTreasury() {
        ArrayList<String> list = treasuries();
        list.add(activity.getResources().getString(R.string.CreateTreasuryFragmentTitle));

        return list;
    }

    private ArrayList<String> createUser() {
        ArrayList<String> list = users();
        list.add(activity.getResources().getString(R.string.CreateUserFragmentTitle));

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

        return list;
    }

    private ArrayList<String> editCenterUser() {
        ArrayList<String> list = reference();
        list.add("ویرایش");

        return new ArrayList<>();
    }

    private ArrayList<String> editSession() {
        ArrayList<String> list = session();
        list.add("ویرایش");

        return new ArrayList<>();
    }

    private ArrayList<String> editTreasury() {
        ArrayList<String> list = treasury();
        list.add("ویرایش");

        return new ArrayList<>();
    }

    private ArrayList<String> editUser() {
        ArrayList<String> list = user();
        list.add("ویرایش");

        return list;
    }

    /*
    ---------- Index ----------
    */

    private ArrayList<String> centerUsers() {
        ArrayList<String> list;

        if (!centerType.equals("room"))
            list = center();
        else
            list = room();

        list.add("اعضاء");

        return list;
    }

    private ArrayList<String> centerSchedules() {
        ArrayList<String> list;

        if (!centerType.equals("room"))
            list = center();
        else
            list = room();

        list.add("برنامه\u200Cها");

        return list;
    }

    private ArrayList<String> roomUsers() {
        ArrayList<String> list = room();
        list.add("اعضاء");

        return new ArrayList<>();
    }

    private ArrayList<String> roomSchedules() {
        ArrayList<String> list = room();
        list.add("برنامه\u200Cها");

        return new ArrayList<>();
    }

    private ArrayList<String> clientReports() {
        ArrayList<String> list;

        if (clientReportsType.equals("case"))
            list = casse();
        else
            list = session();

        list.add(activity.getResources().getString(R.string.ClientReportsFragmentTitle));

        return list;
    }

    /*
    ---------- Show ----------
    */

    private ArrayList<String> treasury() {
        ArrayList<String> list = treasuries();
        list.add(" - ");

        return list;
    }

    private ArrayList<String> bill() {
        ArrayList<String> list = billings();
        list.add(" - ");

        return list;
    }

    private ArrayList<String> center() {
        ArrayList<String> list = centers();

        try {
            list.add(centerModel.getDetail().getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    private ArrayList<String> room() {
        ArrayList<String> list = centers();

        if (!centerType.equals("room")) {
            try {
                list.add("کلینیک شخصی" + " " + centerModel.getDetail().getString("title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            list.add("اتاق درمان" + " " + roomModel.getRoomManager().getName());
        }

        return list;
    }

    private ArrayList<String> casse() {
        ArrayList<String> list = room();
        list.add(activity.getResources().getString(R.string.CaseFragmentTitle) + " " + caseModel.getCaseId());

        return list;
    }

    private ArrayList<String> session() {
        ArrayList<String> list;

        if (sessionModel.getCaseModel() != null)
            list = casse();
        else
            list = room();

        list.add(activity.getResources().getString(R.string.SessionFragmentTitle) + " " + sessionModel.getId());

        return list;
    }

    private ArrayList<String> user() {
        ArrayList<String> list = users();
        list.add(userModel.getName());

        return list;
    }

    private ArrayList<String> sample() {
        ArrayList<String> list = reference();
        list.add(activity.getResources().getString(R.string.SampleFragmentTitle) + " " + sampleModel.getSampleId());

        return list;
    }

    private ArrayList<String> bulkSample() {
        ArrayList<String> list = bulkSamples();

        if (bulkSampleModel.getTitle() != null && !bulkSampleModel.getTitle().equals(""))
            list.add(bulkSampleModel.getTitle());
        else
            list.add("نامعلوم");

        return list;
    }

    private ArrayList<String> reference() {
        ArrayList<String> list;

        if (!centerType.equals("room"))
            list = centerUsers();
        else
            list = roomUsers();

        if (activity instanceof MainActivity) {
            if (referenceCenterId != null) {
                if (userModel.getId().equals(((MainActivity) activity).singleton.getId()))
                    list.add(activity.getResources().getString(R.string.MeFragmentTitle));
                else
                    list.add(userModel.getId());
            }

            if (referenceUserId != null) {
                if (referenceUserId.equals(((MainActivity) activity).singleton.getId()))
                    list.add(activity.getResources().getString(R.string.MeFragmentTitle));
                else
                    list.add(referenceUserId);
            }
        }

        return list;
    }

}