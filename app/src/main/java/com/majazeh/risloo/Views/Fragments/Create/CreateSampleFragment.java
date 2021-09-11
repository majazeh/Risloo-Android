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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.google.android.material.tabs.TabLayout;
import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Views.Adapters.Recycler.Create.CreateCheckAdapter;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Dialog.DialogSelectedAdapter;
import com.majazeh.risloo.databinding.FragmentCreateSampleBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CreateSampleFragment extends Fragment {

    // Binding
    private FragmentCreateSampleBinding binding;

    // Adapters
    public DialogSelectedAdapter scalesAdapter, referencesAdapter;
    public CreateCheckAdapter clientsAdapter;

    // Objects
    private ClickableSpan assessmentLinkSpan;
    private HashMap data, header;

    // Vars
    public String roomId = "", type = "case_user", title = "", membersCount = "", caseStatus = "", problem = "", caseId = "", sessionId = "", psychologyDescription = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setClickSpan();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        scalesAdapter = new DialogSelectedAdapter(requireActivity());
        referencesAdapter = new DialogSelectedAdapter(requireActivity());
        clientsAdapter = new CreateCheckAdapter(requireActivity());

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
        binding.clientIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentClientHeader));
        binding.sessionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentSessionHeader));
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentReferenceHeader));
        binding.psychologyDescriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentPsychologyDescriptionHeader));

        binding.bulkHelperView.getRoot().setText(getResources().getString(R.string.CreateSampleFragmentBulkHelper));

        binding.scaleGuideLayout.guideTextView.setMovementMethod(LinkMovementMethod.getInstance());
        binding.titleGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentTitleGuide));
        binding.membersCountGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentMembersCountGuide));
        binding.psychologyDescriptionGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentPsychologyDescriptionGuide));

        InitManager.normal12sspSpinner(requireActivity(), binding.caseStatusIncludeLayout.selectSpinner, R.array.CaseStatus);

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);
        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);
        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.clientIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

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
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showSearchableDialog(requireActivity(), "scales");
            return false;
        });

        assessmentLinkSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                NavDirections action = NavigationMainDirections.actionGlobalScalesFragment();
                ((MainActivity) requireActivity()).navController.navigate(action);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint textPaint) {
                textPaint.setColor(getResources().getColor(R.color.Blue600));
                textPaint.setUnderlineText(false);
            }
        };

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showSearchableDialog(requireActivity(), "rooms");
        }).widget(binding.roomIncludeLayout.selectContainer);

        binding.typeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    type = "case_user";
                    setType(type);
                } else if (tab.getPosition() == 1) {
                    type = "room_user";
                    setType(type);
                } else {
                    type = "bulk";
                    setType(type);
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
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.membersCountIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.membersCountIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.membersCountIncludeLayout.inputEditText);
            return false;
        });

        binding.membersCountIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            membersCount = binding.membersCountIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.caseStatusIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.caseStatusIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    caseStatus = parent.getItemAtPosition(position).toString();

                    switch (caseStatus) {
                        case "بدون پرونده":
                            binding.caseIncludeLayout.selectContainer.setEnabled(false);
                            binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);

                            binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
                            break;
                        case "ساخت پرونده مجزا برای هر فرد":
                        case "ساخت یک پرونده گروهی برای همه افراد":
                            binding.caseIncludeLayout.selectContainer.setEnabled(false);
                            binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);

                            binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                            break;
                        case "افزودن در پرونده انتخابی":
                            binding.caseIncludeLayout.selectContainer.setEnabled(true);
                            binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);

                            binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
                            break;
                    }

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.problemIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.problemIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.problemIncludeLayout.inputEditText);
            return false;
        });

        binding.problemIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            problem = binding.problemIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showSearchableDialog(requireActivity(), "cases");
        }).widget(binding.caseIncludeLayout.selectContainer);

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showSearchableDialog(requireActivity(), "sessions");
        }).widget(binding.sessionIncludeLayout.selectContainer);

        binding.referenceIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showSearchableDialog(requireActivity(), "references");
            return false;
        });

        binding.psychologyDescriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.psychologyDescriptionIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.psychologyDescriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.psychologyDescriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            psychologyDescription = binding.psychologyDescriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.scaleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.scaleErrorLayout.getRoot(), binding.scaleErrorLayout.errorTextView);
            if (binding.roomErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView);
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);
            if (binding.membersCountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.membersCountErrorLayout.getRoot(), binding.membersCountErrorLayout.errorTextView);
            if (binding.caseStatusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.caseStatusErrorLayout.getRoot(), binding.caseStatusErrorLayout.errorTextView);
            if (binding.problemErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView);
            if (binding.caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView);
            if (binding.sessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.sessionErrorLayout.getRoot(), binding.sessionErrorLayout.errorTextView);
            if (binding.clientErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView);
            if (binding.referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView);
            if (binding.psychologyDescriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.psychologyDescriptionErrorLayout.getRoot(), binding.psychologyDescriptionErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setClickSpan() {
        binding.scaleGuideLayout.guideTextView.setText(StringManager.clickable(requireActivity().getResources().getString(R.string.CreateSampleFragmentScaleGuide), 220, 228, assessmentLinkSpan));
    }

    private void setType(String type) {
        switch (type) {
            case "case_user":
                binding.caseUserGroup.setVisibility(View.VISIBLE);
                binding.roomUserGroup.setVisibility(View.GONE);
                binding.bulkGroup.setVisibility(View.GONE);

                binding.caseIncludeLayout.selectContainer.setEnabled(true);
                binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);

                if (clientsAdapter.getItemCount() != 0)
                    binding.clientIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                else
                    binding.clientIncludeLayout.getRoot().setVisibility(View.GONE);
                break;
            case "room_user":
                binding.caseUserGroup.setVisibility(View.GONE);
                binding.roomUserGroup.setVisibility(View.VISIBLE);
                binding.bulkGroup.setVisibility(View.GONE);

                if (binding.clientIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                    binding.clientIncludeLayout.getRoot().setVisibility(View.GONE);
                break;
            case "bulk":
                binding.caseUserGroup.setVisibility(View.GONE);
                binding.roomUserGroup.setVisibility(View.GONE);
                binding.bulkGroup.setVisibility(View.VISIBLE);

                binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                binding.caseIncludeLayout.selectContainer.setEnabled(false);
                binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);

                if (binding.clientIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                    binding.clientIncludeLayout.getRoot().setVisibility(View.GONE);
                break;
        }
    }

    private void setArgs() {
        TypeModel typeModel = CreateSampleFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            switch (StringManager.substring(typeModel.getClass().getName(), '.')) {
                case "ScaleModel":
                    setData((ScaleModel) typeModel);
                    break;
                case "CaseModel":
                    setData((CaseModel) typeModel);
                    break;
                case "SessionModel":
                    setData((SessionModel) typeModel);
                    break;
            }
        } else {
            switch (type) {
                case "sample":
                    binding.typeTabLayout.getTabAt(0).select();

                    this.type = "case_user";
                    setType(this.type);
                    break;
                case "room":
                    binding.typeTabLayout.getTabAt(1).select();

                    this.type = "room_user";
                    setType(this.type);
                    break;
                case "bulk":
                    binding.typeTabLayout.getTabAt(2).select();

                    this.type = "bulk";
                    setType(this.type);
                    break;
            }

            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "scales");
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
        }
    }

    private void setData(ScaleModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            ArrayList<TypeModel> scales = new ArrayList<>();
            ArrayList<String> ids = new ArrayList<>();

            scales.add(model);
            ids.add(model.getId());

            setRecyclerView(scales, ids, "scales");
        } else {
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "scales");
        }

        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
    }

    private void setData(CaseModel model) {
        if (model.getCaseRoom() != null && model.getCaseRoom().getRoomId() != null && !model.getCaseRoom().getRoomId().equals("")) {
            roomId = model.getCaseRoom().getRoomId();
        }

        if (model.getCaseRoom() != null && model.getCaseRoom().getRoomManager() != null && model.getCaseRoom().getRoomManager().getName() != null && !model.getCaseRoom().getRoomManager().getName().equals("")) {
            binding.roomIncludeLayout.primaryTextView.setText(model.getCaseRoom().getRoomManager().getName());
        }

        try {
            if (model.getCaseRoom() != null && model.getCaseRoom().getRoomCenter() != null && model.getCaseRoom().getRoomCenter().getDetail() != null && model.getCaseRoom().getRoomCenter().getDetail().has("title") && !model.getCaseRoom().getRoomCenter().getDetail().getString("title").equals(""))
                binding.roomIncludeLayout.secondaryTextView.setText(model.getCaseRoom().getRoomCenter().getDetail().getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (model.getCaseId() != null && !model.getCaseId().equals("")) {
            caseId = model.getCaseId();

            binding.caseIncludeLayout.primaryTextView.setText(caseId);
            setClients(model.getClients());
        }

        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "scales");
        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
    }

    private void setData(SessionModel model) {
        if (model.getCaseModel() != null && model.getCaseModel().getCaseId() != null && !model.getCaseModel().getCaseId().equals("")) {
            caseId = model.getCaseModel().getCaseId();

            binding.caseIncludeLayout.primaryTextView.setText(caseId);
            setClients(model.getCaseModel().getClients());
        }

        if (model.getId() != null && !model.getId().equals("")) {
            sessionId = model.getId();

            String primaryText = sessionId + " " + "(" + SelectionManager.getSessionStatus(requireActivity(), "fa", model.getStatus()) + ")";
            String secondaryText = DateManager.jalDayName(String.valueOf(model.getStarted_at())) + " " + DateManager.jalYYYYsMMsDD(String.valueOf(model.getStarted_at()), ".") + " / " + "ساعت" + " " + DateManager.jalHHsMM(String.valueOf(model.getStarted_at())) + " / " + model.getDuration() + " " + "دقیقه";

            binding.sessionIncludeLayout.primaryTextView.setText(StringManager.foregroundSize(primaryText, 10, primaryText.length(), getResources().getColor(R.color.Gray600), (int) getResources().getDimension(R.dimen._8ssp)));
            binding.sessionIncludeLayout.secondaryTextView.setText(secondaryText);
        }

        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "scales");
        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
        switch (method) {
            case "scales":
                scalesAdapter.setItems(items, ids, method, binding.scaleIncludeLayout.countTextView);
                binding.scaleIncludeLayout.selectRecyclerView.setAdapter(scalesAdapter);
                break;
            case "references":
                referencesAdapter.setItems(items, ids, method, binding.referenceIncludeLayout.countTextView);
                binding.referenceIncludeLayout.selectRecyclerView.setAdapter(referencesAdapter);
                break;
            case "clients":
                clientsAdapter.setItems(items, ids, binding.clientIncludeLayout.countTextView);
                binding.clientIncludeLayout.selectRecyclerView.setAdapter(clientsAdapter);
                break;
        }
    }

    private void setClients(List clients) {
        if (clients != null && clients.data().size() != 0) {
            binding.caseIncludeLayout.secondaryTextView.setVisibility(View.VISIBLE);

            ArrayList<TypeModel> items = new ArrayList<>();

            binding.caseIncludeLayout.secondaryTextView.setText("");
            for (int i = 0; i < clients.data().size(); i++) {
                UserModel user = (UserModel) clients.data().get(i);
                if (user != null) {
                    binding.caseIncludeLayout.secondaryTextView.append(user.getName());
                    if (i != clients.data().size() - 1) {
                        binding.caseIncludeLayout.secondaryTextView.append("  -  ");
                    }
                    items.add(new TypeModel(clients.data().get(i).object));
                }
            }

            setRecyclerView(items, new ArrayList<>(), "clients");
            binding.clientIncludeLayout.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.caseIncludeLayout.secondaryTextView.setVisibility(View.GONE);

            clientsAdapter.clearItems();
            binding.clientIncludeLayout.getRoot().setVisibility(View.GONE);
        }
    }

    public void responseDialog(String method, TypeModel item) {
        try {
            switch (method) {
                case "scales": {
                    ScaleModel model = (ScaleModel) item;

                    int position = scalesAdapter.getIds().indexOf(model.getId());

                    if (position == -1)
                        scalesAdapter.addItem(item);
                    else
                        scalesAdapter.removeItem(position);

                    if (scalesAdapter.getIds().size() != 0) {
                        binding.scaleIncludeLayout.countTextView.setVisibility(View.VISIBLE);
                        binding.scaleIncludeLayout.countTextView.setText(StringManager.bracing(scalesAdapter.getIds().size()));
                    } else {
                        binding.scaleIncludeLayout.countTextView.setVisibility(View.GONE);
                        binding.scaleIncludeLayout.countTextView.setText("");
                    }
                } break;
                case "rooms": {
                    RoomModel model = (RoomModel) item;

                    if (!roomId.equals(model.getRoomId())) {
                        roomId = model.getRoomId();

                        binding.roomIncludeLayout.primaryTextView.setText(model.getRoomManager().getName());
                        binding.roomIncludeLayout.secondaryTextView.setText(model.getRoomCenter().getDetail().getString("title"));
                    } else if (roomId.equals(model.getRoomId())) {
                        roomId = "";

                        binding.roomIncludeLayout.primaryTextView.setText("");
                        binding.roomIncludeLayout.secondaryTextView.setText("");
                    }

                    DialogManager.dismissSearchableDialog();
                } break;
                case "references": {
                    UserModel model = (UserModel) item;

                    int position = referencesAdapter.getIds().indexOf(model.getId());

                    if (position == -1)
                        referencesAdapter.addItem(item);
                    else
                        referencesAdapter.removeItem(position);

                    if (referencesAdapter.getIds().size() != 0) {
                        binding.referenceIncludeLayout.countTextView.setVisibility(View.VISIBLE);
                        binding.referenceIncludeLayout.countTextView.setText(StringManager.bracing(referencesAdapter.getIds().size()));
                    } else {
                        binding.referenceIncludeLayout.countTextView.setVisibility(View.GONE);
                        binding.referenceIncludeLayout.countTextView.setText("");
                    }
                } break;
                case "cases": {
                    CaseModel model = (CaseModel) item;

                    if (!caseId.equals(model.getCaseId())) {
                        caseId = model.getCaseId();

                        binding.caseIncludeLayout.primaryTextView.setText(caseId);
                        setClients(model.getClients());
                    } else if (caseId.equals(model.getCaseId())) {
                        caseId = "";

                        binding.caseIncludeLayout.primaryTextView.setText("");
                        binding.caseIncludeLayout.secondaryTextView.setText("");

                        setClients(null);
                    }

                    DialogManager.dismissSearchableDialog();
                } break;
                case "sessions": {
                    SessionModel model = (SessionModel) item;

                    if (!sessionId.equals(model.getId())) {
                        sessionId = model.getId();

                        String primaryText = sessionId + " " + "(" + SelectionManager.getSessionStatus(requireActivity(), "fa", model.getStatus()) + ")";
                        String secondaryText = DateManager.jalDayName(String.valueOf(model.getStarted_at())) + " " + DateManager.jalYYYYsMMsDD(String.valueOf(model.getStarted_at()), ".") + " / " + "ساعت" + " " + DateManager.jalHHsMM(String.valueOf(model.getStarted_at())) + " / " + model.getDuration() + " " + "دقیقه";

                        binding.sessionIncludeLayout.primaryTextView.setText(StringManager.foregroundSize(primaryText, 10, primaryText.length(), getResources().getColor(R.color.Gray600), (int) getResources().getDimension(R.dimen._8ssp)));
                        binding.sessionIncludeLayout.secondaryTextView.setText(secondaryText);
                    } else if (sessionId.equals(model.getId())) {
                        sessionId = "";

                        binding.sessionIncludeLayout.primaryTextView.setText("");
                        binding.sessionIncludeLayout.secondaryTextView.setText("");
                    }

                    DialogManager.dismissSearchableDialog();
                } break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "loading");

        data.put("scale_id", scalesAdapter.getIds());
        data.put("room_id", roomId);
        data.put("type", type);

        switch (type) {
            case "case_user":
                data.put("case_id", caseId);
                data.put("client_id", clientsAdapter.getIds());
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
                else if (data.get("case_status").equals("exist"))
                    data.put("case_id", caseId);

                break;
        }

        data.put("psychologist_description", psychologyDescription);

        Sample.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        if (type.equals("bulk"))
                            SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastNewBulkSampleAdded));
                        else
                            SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastNewSampleAdded));

                        ((MainActivity) requireActivity()).navController.navigateUp();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if (!responseObject.isNull("errors")) {
                                JSONObject errorsObject = responseObject.getJSONObject("errors");

                                Iterator<String> keys = (errorsObject.keys());
                                StringBuilder errors = new StringBuilder();

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                        String validation = errorsObject.getJSONArray(key).get(i).toString();

                                        switch (key) {
                                            case "scale_id":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.scaleErrorLayout.getRoot(), binding.scaleErrorLayout.errorTextView, validation);
                                                break;
                                            case "room_id":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView, validation);
                                                break;
                                            case "type":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, validation);
                                                break;
                                            case "title":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, validation);
                                                break;
                                            case "members_count":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.membersCountErrorLayout.getRoot(), binding.membersCountErrorLayout.errorTextView, validation);
                                                break;
                                            case "case_status":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.caseStatusErrorLayout.getRoot(), binding.caseStatusErrorLayout.errorTextView, validation);
                                                break;
                                            case "problem":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView, validation);
                                                break;
                                            case "case_id":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView, validation);
                                                break;
                                            case "session_id":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.sessionErrorLayout.getRoot(), binding.sessionErrorLayout.errorTextView, validation);
                                                break;
                                            case "client_id":
                                                if (type.equals("case_user"))
                                                    ((MainActivity) requireActivity()).validatoon.showValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView, validation);
                                                else if (type.equals("room_user"))
                                                    ((MainActivity) requireActivity()).validatoon.showValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView, validation);
                                                break;
                                            case "psychologist_description":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.psychologyDescriptionErrorLayout.getRoot(), binding.psychologyDescriptionErrorLayout.errorTextView, validation);
                                                break;
                                        }

                                        errors.append(validation);
                                        errors.append("\n");
                                    }
                                }

                                SnackManager.showErrorSnack(requireActivity(), errors.substring(0, errors.length() - 1));
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