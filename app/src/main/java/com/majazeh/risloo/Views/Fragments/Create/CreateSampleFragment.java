package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateSampleBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CreateSampleFragment extends Fragment {

    // Binding
    private FragmentCreateSampleBinding binding;

    // Adapters
    public SelectedAdapter scalesAdapter, referencesAdapter;

    // Dialogs
    private SearchableDialog scalesDialog, roomsDialog, referencesDialog, casesDialog, sessionsDialog;

    // Objects
    private ClickableSpan assessmentLinkSpan;

    // Vars
    private HashMap data, header;
    public String roomId = "", roomName = "", centerName = "", type = "case_user", title = "", membersCount = "", caseStatus = "", problem = "", caseId = "",  sessionId = "", psychologyDescription = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setClickableSpan();

        setExtra();

        return binding.getRoot();
    }

    private void initializer() {
        scalesAdapter = new SelectedAdapter(requireActivity());
        referencesAdapter = new SelectedAdapter(requireActivity());

        scalesDialog = new SearchableDialog();
        roomsDialog = new SearchableDialog();
        referencesDialog = new SearchableDialog();
        casesDialog = new SearchableDialog();
        sessionsDialog = new SearchableDialog();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.scaleIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentScaleHeader));
        binding.roomIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentRoomHeader));
        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentTitleHeader));
        binding.membersCountIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentMembersCountHeader));
        binding.caseStatusIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentCaseStatusHeader));
        binding.problemIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentProblemHeader));
        binding.caseIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentCaseHeader));
        binding.sessionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentSessionHeader));
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentReferenceHeader));
        binding.psychologyDescriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentPsychologyDescriptionHeader));

        binding.titleIncludeLayout.inputEditText.setHint(getResources().getString(R.string.CreateSampleFragmentTitleHint));

        binding.scaleGuideLayout.guideTextView.setMovementMethod(LinkMovementMethod.getInstance());

        binding.titleGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentTitleGuide));
        binding.membersCountGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentMembersCountGuide));
        binding.psychologyDescriptionGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentPsychologyDescriptionGuide));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);
        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.fixedSpinner(requireActivity(), binding.caseStatusIncludeLayout.selectSpinner, R.array.CaseTypes, "main");

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.scaleIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                scalesDialog.show(requireActivity().getSupportFragmentManager(), "scalesDialog");
                scalesDialog.setData("scales");
            }
            return false;
        });

        assessmentLinkSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                ((MainActivity) requireActivity()).navigator(R.id.scalesFragment);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint textPaint) {
                textPaint.setColor(getResources().getColor(R.color.Blue600));
                textPaint.setUnderlineText(false);
            }
        };

        ClickManager.onDelayedClickListener(() -> {
            roomsDialog.show(requireActivity().getSupportFragmentManager(), "roomsDialog");
            roomsDialog.setData("rooms");
        }).widget(binding.roomIncludeLayout.selectContainer);

        binding.typeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    type = "case_user";

                    binding.caseUserGroup.setVisibility(View.VISIBLE);
                    binding.roomUserGroup.setVisibility(View.GONE);
                    binding.bulkGroup.setVisibility(View.GONE);

                    binding.caseIncludeLayout.selectContainer.setEnabled(true);
                    binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
                } else if (tab.getPosition() == 1) {
                    type = "room_user";

                    binding.caseUserGroup.setVisibility(View.GONE);
                    binding.roomUserGroup.setVisibility(View.VISIBLE);
                    binding.bulkGroup.setVisibility(View.GONE);
                } else {
                    type = "bulk";

                    binding.caseUserGroup.setVisibility(View.GONE);
                    binding.roomUserGroup.setVisibility(View.GONE);
                    binding.bulkGroup.setVisibility(View.VISIBLE);

                    binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.caseIncludeLayout.selectContainer.setEnabled(false);
                    binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.titleIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.titleIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.membersCountIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.membersCountIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.membersCountIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.caseStatusIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                caseStatus = parent.getItemAtPosition(position).toString();

                if (position == 0) {
                    binding.caseIncludeLayout.selectContainer.setEnabled(false);
                    binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);

                    binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
                } else if (position == 1 || position == 2) {
                    binding.caseIncludeLayout.selectContainer.setEnabled(false);
                    binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);

                    binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                } else {
                    binding.caseIncludeLayout.selectContainer.setEnabled(true);
                    binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);

                    binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.problemIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.problemIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.problemIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            casesDialog.show(requireActivity().getSupportFragmentManager(), "casesDialog");
            casesDialog.setData("cases");
        }).widget(binding.caseIncludeLayout.selectContainer);

        ClickManager.onDelayedClickListener(() -> {
            sessionsDialog.show(requireActivity().getSupportFragmentManager(), "sessionsDialog");
            sessionsDialog.setData("sessions");
        }).widget(binding.sessionIncludeLayout.selectContainer);

        binding.referenceIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                referencesDialog.show(requireActivity().getSupportFragmentManager(), "referencesDialog");
                referencesDialog.setData("references");
            }
            return false;
        });

        binding.psychologyDescriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.psychologyDescriptionIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.psychologyDescriptionIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.scaleIncludeLayout.selectRecyclerView.getChildCount() == 0)
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, binding.scaleErrorLayout.getRoot(), binding.scaleErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, binding.scaleErrorLayout.getRoot(), binding.scaleErrorLayout.errorTextView);

            if (roomId.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView);

            if (binding.scaleIncludeLayout.selectRecyclerView.getChildCount() != 0 && !roomId.equals(""))
                doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setClickableSpan() {
        binding.scaleGuideLayout.guideTextView.setText(StringManager.clickable(requireActivity().getResources().getString(R.string.CreateSampleFragmentScaleGuide), 220, 228, assessmentLinkSpan));
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("scales") != null && !getArguments().getString("scales").equals("")) {
                try {
                    JSONArray list = new JSONArray(getArguments().getString("scales"));

                    ArrayList<TypeModel> scales = new ArrayList<>();
                    ArrayList<String> ids = new ArrayList<>();

                    for (int i = 0; i < list.length(); i++) {
                        TypeModel model = new TypeModel((JSONObject) list.get(i));

                        scales.add(model);
                        ids.add(model.object.getString("id"));
                    }

                    setRecyclerView(scales, ids, "scales");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                setRecyclerView(new ArrayList<>(), new ArrayList<>(), "scales");
            }

            if (getArguments().getString("room_id") != null && !getArguments().getString("room_id").equals("") && getArguments().getString("room_name") != null && !getArguments().getString("room_name").equals("")) {
                roomId = getArguments().getString("room_id");
                roomName = getArguments().getString("room_name");
                binding.roomIncludeLayout.primaryTextView.setText(roomName);
            }

            if (getArguments().getString("center_name") != null && !getArguments().getString("center_name").equals("")) {
                centerName = getArguments().getString("center_name");
                binding.roomIncludeLayout.secondaryTextView.setText(centerName);
            }

            if (getArguments().getString("type") != null && !getArguments().getString("type").equals("")) {
                type = getArguments().getString("type");
                switch (type) {
                    case "case_user":
                        binding.typeTabLayout.getTabAt(0);

                        binding.caseUserGroup.setVisibility(View.VISIBLE);
                        binding.roomUserGroup.setVisibility(View.GONE);
                        binding.bulkGroup.setVisibility(View.GONE);

                        binding.caseIncludeLayout.selectContainer.setEnabled(true);
                        binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
                        break;
                    case "room_user":
                        binding.typeTabLayout.getTabAt(1);

                        binding.caseUserGroup.setVisibility(View.GONE);
                        binding.roomUserGroup.setVisibility(View.VISIBLE);
                        binding.bulkGroup.setVisibility(View.GONE);
                        break;
                    case "bulk":
                        binding.typeTabLayout.getTabAt(2);

                        binding.caseUserGroup.setVisibility(View.GONE);
                        binding.roomUserGroup.setVisibility(View.GONE);
                        binding.bulkGroup.setVisibility(View.VISIBLE);

                        binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.caseIncludeLayout.selectContainer.setEnabled(false);
                        binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);
                        break;
                }
            }

            if (getArguments().getString("title") != null && !getArguments().getString("title").equals("")) {
                title = getArguments().getString("title");
                binding.titleIncludeLayout.inputEditText.setText(title);
            }

            if (getArguments().getString("members_count") != null && !getArguments().getString("members_count").equals("")) {
                membersCount = getArguments().getString("members_count");
                binding.membersCountIncludeLayout.inputEditText.setText(membersCount);
            }

            if (getArguments().getString("case_status") != null && !getArguments().getString("case_status").equals("")) {
                caseStatus = SelectionManager.getCaseStatus2(requireActivity(), "fa", getArguments().getString("case_status"));
                for (int i=0; i<binding.caseStatusIncludeLayout.selectSpinner.getCount(); i++) {
                    if (binding.caseStatusIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(caseStatus)) {
                        binding.caseStatusIncludeLayout.selectSpinner.setSelection(i);

                        if (i == 0) {
                            binding.caseIncludeLayout.selectContainer.setEnabled(false);
                            binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);

                            binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
                        } else if (i == 1 || i == 2) {
                            binding.caseIncludeLayout.selectContainer.setEnabled(false);
                            binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);

                            binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                        } else {
                            binding.caseIncludeLayout.selectContainer.setEnabled(true);
                            binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);

                            binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
                        }
                    }
                }
            }

            if (getArguments().getString("problem") != null && !getArguments().getString("problem").equals("")) {
                problem = getArguments().getString("problem");
                binding.problemIncludeLayout.inputEditText.setText(problem);
            }

            if (getArguments().getString("case_id") != null && !getArguments().getString("case_id").equals("")) {
                caseId = getArguments().getString("case_id");
                binding.caseIncludeLayout.primaryTextView.setText(caseId);
            }

            if (getArguments().getString("session_id") != null && !getArguments().getString("session_id").equals("")) {
                sessionId = getArguments().getString("session_id");
                binding.sessionIncludeLayout.primaryTextView.setText(sessionId);
            }

            if (getArguments().getString("clients") != null && !getArguments().getString("clients").equals("")) {
                try {
                    JSONArray clients = new JSONArray(getArguments().getString("clients"));

                    ArrayList<TypeModel> references = new ArrayList<>();
                    ArrayList<String> ids = new ArrayList<>();

                    for (int i = 0; i < clients.length(); i++) {
                        TypeModel model = new TypeModel((JSONObject) clients.get(i));

                        references.add(model);
                        ids.add(model.object.getString("id"));
                    }

                    setRecyclerView(references, ids, "references");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
            }

            if (getArguments().getString("psychologist_description") != null && !getArguments().getString("psychologist_description").equals("")) {
                psychologyDescription = getArguments().getString("psychologist_description");
                binding.psychologyDescriptionIncludeLayout.inputEditText.setText(psychologyDescription);
            }

        } else {
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "scales");
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
        }
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
        if (method.equals("scales")) {
            scalesAdapter.setItems(items, ids, method, binding.scaleIncludeLayout.countTextView);
            binding.scaleIncludeLayout.selectRecyclerView.setAdapter(scalesAdapter);
        } else if (method.equals("references")) {
            referencesAdapter.setItems(items, ids, method, binding.referenceIncludeLayout.countTextView);
            binding.referenceIncludeLayout.selectRecyclerView.setAdapter(referencesAdapter);
        }
    }

    public void responseDialog(String method, TypeModel item) {
        try {
            switch (method) {
                case "scales": {
                    SampleModel model = (SampleModel) item;

                    int position = scalesAdapter.getIds().indexOf(model.getSampleId());

                    if (position == -1)
                        scalesAdapter.addItem(item);
                    else
                        scalesAdapter.removeItem(position);

                    if (scalesAdapter.getIds().size() != 0) {
                        binding.scaleIncludeLayout.countTextView.setVisibility(View.VISIBLE);
                        binding.scaleIncludeLayout.countTextView.setText("(" + scalesAdapter.getIds().size() + ")");
                    } else {
                        binding.scaleIncludeLayout.countTextView.setVisibility(View.GONE);
                        binding.scaleIncludeLayout.countTextView.setText("");
                    }
                }
                break;
                case "rooms": {
                    RoomModel model = (RoomModel) item;

                    if (!roomId.equals(model.getRoomId())) {
                        roomId = model.getRoomId();
                        roomName = model.getRoomManager().getName();
                        centerName = model.getRoomCenter().getDetail().getString("title");

                        binding.roomIncludeLayout.primaryTextView.setText(roomName);
                        binding.roomIncludeLayout.secondaryTextView.setText(centerName);
                    } else if (roomId.equals(model.getRoomId())) {
                        roomId = "";
                        roomName = "";
                        centerName = "";

                        binding.roomIncludeLayout.primaryTextView.setText("");
                        binding.roomIncludeLayout.secondaryTextView.setText("");
                    }

                    roomsDialog.dismiss();
                }
                break;
                case "references": {
                    UserModel model = (UserModel) item;

                    int position = referencesAdapter.getIds().indexOf(model.getId());

                    if (position == -1)
                        referencesAdapter.addItem(item);
                    else
                        referencesAdapter.removeItem(position);

                    if (referencesAdapter.getIds().size() != 0) {
                        binding.referenceIncludeLayout.countTextView.setVisibility(View.VISIBLE);
                        binding.referenceIncludeLayout.countTextView.setText("(" + referencesAdapter.getIds().size() + ")");
                    } else {
                        binding.referenceIncludeLayout.countTextView.setVisibility(View.GONE);
                        binding.referenceIncludeLayout.countTextView.setText("");
                    }
                }
                break;
                case "cases": {
                    CaseModel model = (CaseModel) item;

                    if (!caseId.equals(model.getCaseId())) {
                        caseId = model.getCaseId();

                        binding.caseIncludeLayout.primaryTextView.setText(caseId);
                        if (model.getClients() != null && !model.getClients().data().isEmpty()) {
                            binding.caseIncludeLayout.secondaryTextView.setText("");
                            for (int i = 0; i < model.getClients().data().size(); i++) {
                                UserModel user = (UserModel) model.getClients().data().get(i);
                                binding.caseIncludeLayout.secondaryTextView.append(user.getName());
                                if (i != model.getClients().size() -1) {
                                    binding.caseIncludeLayout.secondaryTextView.append(" - ");
                                }
                            }
                        } else {
                            binding.caseIncludeLayout.secondaryTextView.setVisibility(View.GONE);
                        }
                    } else if (caseId.equals(model.getCaseId())) {
                        caseId = "";

                        binding.caseIncludeLayout.primaryTextView.setText("");
                        binding.caseIncludeLayout.secondaryTextView.setText("");
                    }

                    casesDialog.dismiss();
                }
                break;
                case "sessions": {
                    SessionModel model = (SessionModel) item;

                    if (!sessionId.equals(model.getId())) {
                        sessionId = model.getId();

                        String name = sessionId + " " + "(" + SelectionManager.getSessionStatus(requireActivity(), "fa", model.getStatus()) + ")";

                        binding.sessionIncludeLayout.primaryTextView.setText(StringManager.foregroundSize(name, 10, name.length(), getResources().getColor(R.color.Gray600), (int) getResources().getDimension(R.dimen._8ssp)));
                        binding.sessionIncludeLayout.secondaryTextView.setText(DateManager.gregorianToJalali6(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(model.getStarted_at()))) + " / " + model.getDuration() + " " + "دقیقه");
                    } else if (sessionId.equals(model.getId())) {
                        sessionId = "";

                        binding.sessionIncludeLayout.primaryTextView.setText("");
                        binding.sessionIncludeLayout.secondaryTextView.setText("");
                    }

                    sessionsDialog.dismiss();
                }
                break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        membersCount = binding.membersCountIncludeLayout.inputEditText.getText().toString().trim();
        problem = binding.problemIncludeLayout.inputEditText.getText().toString().trim();
        psychologyDescription = binding.psychologyDescriptionIncludeLayout.inputEditText.getText().toString().trim();

        data.put("scale_id", scalesAdapter.getIds());
        data.put("room_id", roomId);

        data.put("type", type);
        data.put("psychologist_description", psychologyDescription);

        switch (type) {
            case "case_user":
                data.put("case_id", caseId);
                data.put("client_id", "");
                data.put("session_id", sessionId);
                break;
            case "room_user":
                data.put("client_id", referencesAdapter.getIds());
                break;
            case "bulk":
                data.put("title", title);
                data.put("members_count", membersCount);
                data.put("case_status", SelectionManager.getCaseStatus2(requireActivity(), "en", caseStatus));

                if (data.get("case_status").equals("group") || data.get("case_status").equals("personal"))
                    data.put("problem", problem);
                break;
        }

        Sample.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
                        ((MainActivity) requireActivity()).navigator(R.id.samplesFragment);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.isNull("errors")) {
                                Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                                        switch (key) {
                                            case "scale_id":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, binding.scaleErrorLayout.getRoot(), binding.scaleErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "room_id":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "title":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.titleIncludeLayout.inputEditText, binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "members_count":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.membersCountIncludeLayout.inputEditText, binding.membersCountErrorLayout.getRoot(), binding.membersCountErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "case_status":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.caseStatusIncludeLayout.selectSpinner, binding.caseStatusErrorLayout.getRoot(), binding.caseStatusErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "problem":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.problemIncludeLayout.inputEditText, binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "case_id":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.caseIncludeLayout.selectContainer, binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "session_id":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.sessionIncludeLayout.selectContainer, binding.sessionErrorLayout.getRoot(), binding.sessionErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "client_id":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "psychologist_description":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.psychologyDescriptionIncludeLayout.inputEditText, binding.psychologyDescriptionErrorLayout.getRoot(), binding.psychologyDescriptionErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                        }
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}