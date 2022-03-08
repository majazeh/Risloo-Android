package com.majazeh.risloo.views.fragments.main.create;

import android.annotation.SuppressLint;
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

import com.google.android.material.tabs.TabLayout;
import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SelectionManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.SpannableManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.dialog.DialogSelectedAdapter;
import com.majazeh.risloo.views.adapters.recycler.main.Create.CreateCheckAdapter;
import com.majazeh.risloo.databinding.FragmentCreateSampleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FragmentCreateSample extends Fragment {

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

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        scalesAdapter = new DialogSelectedAdapter(requireActivity());
        referencesAdapter = new DialogSelectedAdapter(requireActivity());
        clientsAdapter = new CreateCheckAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

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

        binding.scaleGuideLayout.guideTextView.setMovementMethod(LinkMovementMethod.getInstance());
        binding.titleGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentTitleGuide));
        binding.membersCountGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentMembersCountGuide));
        binding.psychologyDescriptionGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentPsychologyDescriptionGuide));

        InitManager.input12sspSpinner(requireActivity(), binding.caseStatusIncludeLayout.selectSpinner, R.array.CaseStatus);

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);
        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);
        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.clientIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.bulkHelperView.getRoot(), getResources().getString(R.string.CreateSampleFragmentBulkHelper), getResources().getColor(R.color.coolGray600), R.drawable.draw_2sdp_solid_coolgray50_border_right_2dp_coolgray400);
        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        assessmentLinkSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentScales();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint textPaint) {
                textPaint.setColor(getResources().getColor(R.color.risloo500));
                textPaint.setUnderlineText(false);
            }
        };

        binding.scaleIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showDialogSearchable(requireActivity(), "scales");
            return false;
        });

        binding.referenceIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showDialogSearchable(requireActivity(), "references");
            return false;
        });

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showDialogSearchable(requireActivity(), "rooms");
        }).widget(binding.roomIncludeLayout.selectContainer);

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showDialogSearchable(requireActivity(), "cases");
        }).widget(binding.caseIncludeLayout.selectContainer);

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showDialogSearchable(requireActivity(), "sessions");
        }).widget(binding.sessionIncludeLayout.selectContainer);

        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.membersCountIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.membersCountIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.membersCountIncludeLayout.inputEditText);
            return false;
        });

        binding.problemIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.problemIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.problemIncludeLayout.inputEditText);
            return false;
        });

        binding.psychologyDescriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.psychologyDescriptionIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.psychologyDescriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.membersCountIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            membersCount = binding.membersCountIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.problemIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            problem = binding.problemIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.psychologyDescriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            psychologyDescription = binding.psychologyDescriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

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

        binding.caseStatusIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.caseStatusIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.caseStatusIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    caseStatus = parent.getItemAtPosition(position).toString();

                    switch (caseStatus) {
                        case "بدون پرونده":
                            binding.caseIncludeLayout.selectContainer.setEnabled(false);
                            binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_coolgray100_border_1sdp_coolgray500);

                            binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
                            break;
                        case "ساخت پرونده مجزا برای هر فرد":
                        case "ساخت یک پرونده گروهی برای همه افراد":
                            binding.caseIncludeLayout.selectContainer.setEnabled(false);
                            binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_coolgray100_border_1sdp_coolgray500);

                            binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                            break;
                        case "افزودن در پرونده انتخابی":
                            binding.caseIncludeLayout.selectContainer.setEnabled(true);
                            binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray500);

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

        CustomClickView.onDelayedListener(() -> {
            if (binding.scaleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.scaleErrorLayout.getRoot(), binding.scaleErrorLayout.errorTextView);
            if (binding.roomErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView);
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);
            if (binding.membersCountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.membersCountErrorLayout.getRoot(), binding.membersCountErrorLayout.errorTextView);
            if (binding.caseStatusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.caseStatusErrorLayout.getRoot(), binding.caseStatusErrorLayout.errorTextView);
            if (binding.problemErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView);
            if (binding.caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView);
            if (binding.sessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.sessionErrorLayout.getRoot(), binding.sessionErrorLayout.errorTextView);
            if (binding.clientErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView);
            if (binding.referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView);
            if (binding.psychologyDescriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.psychologyDescriptionErrorLayout.getRoot(), binding.psychologyDescriptionErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setType(String type) {
        switch (type) {
            case "case_user":
                binding.caseUserGroup.setVisibility(View.VISIBLE);
                binding.roomUserGroup.setVisibility(View.GONE);
                binding.bulkGroup.setVisibility(View.GONE);

                binding.caseIncludeLayout.selectContainer.setEnabled(true);
                binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray500);

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
                binding.caseIncludeLayout.selectContainer.setBackgroundResource(R.drawable.draw_2sdp_solid_coolgray100_border_1sdp_coolgray500);

                if (binding.clientIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                    binding.clientIncludeLayout.getRoot().setVisibility(View.GONE);

                break;
        }
    }

    private void setArgs() {
        TypeModel typeModel = FragmentCreateSampleArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            switch (StringManager.suffix(typeModel.getClass().getName(), '.')) {
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
            if (((ActivityMain) requireActivity()).navigatoon.getBackstackDestinationId() == R.id.fragmentBulkSamples) {
                binding.typeTabLayout.getTabAt(2).select();

                type = "bulk";
                setType(type);
            } else {
                binding.typeTabLayout.getTabAt(0).select();

                type = "case_user";
                setType(type);
            }

            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "scales");
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
        }

        binding.scaleGuideLayout.guideTextView.setText(SpannableManager.clickable(requireActivity().getResources().getString(R.string.CreateSampleFragmentScaleGuide), 220, 228, assessmentLinkSpan));
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
        if (model.getRoom() != null && model.getRoom().getId() != null && !model.getRoom().getId().equals("")) {
            roomId = model.getRoom().getId();
        }

        if (model.getRoom() != null && model.getRoom().getManager() != null && model.getRoom().getManager().getName() != null && !model.getRoom().getManager().getName().equals("")) {
            binding.roomIncludeLayout.primaryTextView.setText(model.getRoom().getManager().getName());
        }

        try {
            if (model.getRoom() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getDetail() != null && model.getRoom().getCenter().getDetail().has("title") && !model.getRoom().getCenter().getDetail().getString("title").equals(""))
                binding.roomIncludeLayout.secondaryTextView.setText(model.getRoom().getCenter().getDetail().getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (model.getId() != null && !model.getId().equals("")) {
            caseId = model.getId();

            binding.caseIncludeLayout.primaryTextView.setText(caseId);
            setClients(model.getClients());
        }

        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "scales");
        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
    }

    private void setData(SessionModel model) {
        if (model.getCasse() != null && model.getCasse().getId() != null && !model.getCasse().getId().equals("")) {
            caseId = model.getCasse().getId();

            binding.caseIncludeLayout.primaryTextView.setText(caseId);
            setClients(model.getCasse().getClients());
        }

        if (model.getId() != null && !model.getId().equals("")) {
            sessionId = model.getId();

            String primaryText = sessionId + " " + "(" + SelectionManager.getSessionStatus(requireActivity(), "fa", model.getStatus()) + ")";
            String secondaryText = DateManager.jalND(String.valueOf(model.getStartedAt())) + " " + DateManager.jalYYYYsMMsDD(String.valueOf(model.getStartedAt()), ".") + " / " + "ساعت" + " " + DateManager.jalHHcMM(String.valueOf(model.getStartedAt())) + " / " + model.getDuration() + " " + "دقیقه";

            binding.sessionIncludeLayout.primaryTextView.setText(SpannableManager.foregroundColorSize(primaryText, 10, primaryText.length(), getResources().getColor(R.color.coolGray600), (int) getResources().getDimension(R.dimen._8ssp)));
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
            ArrayList<TypeModel> items = new ArrayList<>();

            binding.caseIncludeLayout.secondaryTextView.setVisibility(View.VISIBLE);
            binding.caseIncludeLayout.secondaryTextView.setText("");

            for (int i = 0; i < clients.data().size(); i++) {
                UserModel model = (UserModel) clients.data().get(i);

                if (model != null) {
                    binding.caseIncludeLayout.secondaryTextView.append(model.getName());
                    if (i != clients.data().size() - 1)
                        binding.caseIncludeLayout.secondaryTextView.append("  -  ");

                    items.add(new TypeModel(clients.data().get(i).object));
                }
            }

            setRecyclerView(items, new ArrayList<>(), "clients");
            binding.clientIncludeLayout.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.caseIncludeLayout.secondaryTextView.setVisibility(View.GONE);
            binding.caseIncludeLayout.secondaryTextView.setText("");

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

                    if (!roomId.equals(model.getId())) {
                        roomId = model.getId();

                        binding.roomIncludeLayout.primaryTextView.setText(model.getManager().getName());
                        binding.roomIncludeLayout.secondaryTextView.setText(model.getCenter().getDetail().getString("title"));
                    } else if (roomId.equals(model.getId())) {
                        roomId = "";

                        binding.roomIncludeLayout.primaryTextView.setText("");
                        binding.roomIncludeLayout.secondaryTextView.setText("");
                    }

                    DialogManager.dismissDialogSearchable();
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

                    if (!caseId.equals(model.getId())) {
                        caseId = model.getId();

                        binding.caseIncludeLayout.primaryTextView.setText(caseId);
                        setClients(model.getClients());
                    } else if (caseId.equals(model.getId())) {
                        caseId = "";

                        binding.caseIncludeLayout.primaryTextView.setText("");
                        binding.caseIncludeLayout.secondaryTextView.setText("");

                        setClients(null);
                    }

                    DialogManager.dismissDialogSearchable();
                } break;
                case "sessions": {
                    SessionModel model = (SessionModel) item;

                    if (!sessionId.equals(model.getId())) {
                        sessionId = model.getId();

                        String primaryText = sessionId + " " + "(" + SelectionManager.getSessionStatus(requireActivity(), "fa", model.getStatus()) + ")";
                        String secondaryText = DateManager.jalND(String.valueOf(model.getStartedAt())) + " " + DateManager.jalYYYYsMMsDD(String.valueOf(model.getStartedAt()), ".") + " / " + "ساعت" + " " + DateManager.jalHHcMM(String.valueOf(model.getStartedAt())) + " / " + model.getDuration() + " " + "دقیقه";

                        binding.sessionIncludeLayout.primaryTextView.setText(SpannableManager.foregroundColorSize(primaryText, 10, primaryText.length(), getResources().getColor(R.color.coolGray600), (int) getResources().getDimension(R.dimen._8ssp)));
                        binding.sessionIncludeLayout.secondaryTextView.setText(secondaryText);
                    } else if (sessionId.equals(model.getId())) {
                        sessionId = "";

                        binding.sessionIncludeLayout.primaryTextView.setText("");
                        binding.sessionIncludeLayout.secondaryTextView.setText("");
                    }

                    DialogManager.dismissDialogSearchable();
                } break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setHashmap() {
        if (!scalesAdapter.getIds().isEmpty())
            data.put("scale_id", scalesAdapter.getIds());
        else
            data.remove("scale_id");

        if (!roomId.equals(""))
            data.put("room_id", roomId);
        else
            data.remove("room_id");

        data.put("type", type);

        switch (type) {
            case "case_user":
                if (!caseId.equals(""))
                    data.put("case_id", caseId);
                else
                    data.remove("case_id");

                if (!clientsAdapter.getIds().isEmpty())
                    data.put("client_id", clientsAdapter.getIds());
                else
                    data.remove("client_id");

                if (!sessionId.equals(""))
                    data.put("session_id", sessionId);
                else
                    data.remove("session_id");

                break;
            case "room_user":
                if (!referencesAdapter.getIds().isEmpty())
                    data.put("client_id", referencesAdapter.getIds());
                else
                    data.remove("client_id");

                break;
            case "bulk":
                if (!title.equals(""))
                    data.put("title", title);
                else
                    data.remove("title");

                if (!membersCount.equals(""))
                    data.put("members_count", membersCount);
                else
                    data.remove("members_count");

                if (!caseStatus.equals("")) {
                    String status = SelectionManager.getCaseStatus2(requireActivity(), "en", caseStatus);
                    data.put("case_status", status);

                    if (status.equals("group") || status.equals("personal"))
                        data.put("problem", problem);
                    else if (status.equals("exist"))
                        data.put("case_id", caseId);

                } else {
                    data.remove("case_status");
                    data.remove("problem");
                    data.remove("case_id");
                }

                break;
        }

        if (!psychologyDescription.equals(""))
            data.put("psychologist_description", psychologyDescription);
        else
            data.remove("psychologist_description");
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        if (type.equals("bulk")) {
            Sample.createBulk(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    BulkSampleModel bulkSampleModel = (BulkSampleModel) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            DialogManager.dismissDialogLoading();
                            SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackCreatedNewBulkSample));

                            ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentBulkSample(bulkSampleModel);
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
                                    StringBuilder allErrors = new StringBuilder();

                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        StringBuilder keyErrors = new StringBuilder();

                                        for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                            String error = errorsObject.getJSONArray(key).getString(i);

                                            keyErrors.append(error);
                                            keyErrors.append("\n");

                                            allErrors.append(error);
                                            allErrors.append("\n");
                                        }

                                        switch (key) {
                                            case "scale_id":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.scaleErrorLayout.getRoot(), binding.scaleErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "room_id":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "type":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "title":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "members_count":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.membersCountErrorLayout.getRoot(), binding.membersCountErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "case_status":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.caseStatusErrorLayout.getRoot(), binding.caseStatusErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "problem":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "case_id":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "session_id":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.sessionErrorLayout.getRoot(), binding.sessionErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "client_id":
                                                if (type.equals("case_user"))
                                                    ((ActivityMain) requireActivity()).validatoon.showValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                else if (type.equals("room_user"))
                                                    ((ActivityMain) requireActivity()).validatoon.showValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));

                                                break;
                                            case "psychologist_description":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.psychologyDescriptionErrorLayout.getRoot(), binding.psychologyDescriptionErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                        }
                                    }

                                    SnackManager.showSnackError(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            });
        } else {
            Sample.createSample(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            try {
                                ArrayList<String> ids = new ArrayList<>();
                                for (int i = 0; i < ((JSONObject) object).getJSONArray("data").length(); i++) {
                                    SampleModel model = new SampleModel(((JSONObject) object).getJSONArray("data").getJSONObject(i));
                                    ids.add(model.getId());
                                }

                                String[] sampleIds = new String[ids.size()];
                                sampleIds = ids.toArray(sampleIds);

                                DialogManager.dismissDialogLoading();
                                SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackCreatedNewSample));

                                ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentSamples(null, sampleIds);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                                    StringBuilder allErrors = new StringBuilder();

                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        StringBuilder keyErrors = new StringBuilder();

                                        for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                            String error = errorsObject.getJSONArray(key).getString(i);

                                            keyErrors.append(error);
                                            keyErrors.append("\n");

                                            allErrors.append(error);
                                            allErrors.append("\n");
                                        }

                                        switch (key) {
                                            case "scale_id":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.scaleErrorLayout.getRoot(), binding.scaleErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "room_id":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "type":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "title":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "members_count":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.membersCountErrorLayout.getRoot(), binding.membersCountErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "case_status":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.caseStatusErrorLayout.getRoot(), binding.caseStatusErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "problem":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "case_id":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "session_id":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.sessionErrorLayout.getRoot(), binding.sessionErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "client_id":
                                                if (type.equals("case_user"))
                                                    ((ActivityMain) requireActivity()).validatoon.showValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                else if (type.equals("room_user"))
                                                    ((ActivityMain) requireActivity()).validatoon.showValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));

                                                break;
                                            case "psychologist_description":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.psychologyDescriptionErrorLayout.getRoot(), binding.psychologyDescriptionErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                        }
                                    }

                                    SnackManager.showSnackError(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}