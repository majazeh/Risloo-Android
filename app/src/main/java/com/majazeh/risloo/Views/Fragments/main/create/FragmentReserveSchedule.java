package com.majazeh.risloo.views.fragments.main.create;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.instances.Paymont;
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.PaymentManager;
import com.majazeh.risloo.utils.managers.SelectionManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Create.CreateCheckAdapter;
import com.majazeh.risloo.databinding.FragmentReserveScheduleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Schedules;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.PaymentModel;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FragmentReserveSchedule extends Fragment {

    // Binding
    private FragmentReserveScheduleBinding binding;

    // Adapters
    public CreateCheckAdapter clientsAdapter;

    // Models
    private ScheduleModel scheduleModel;

    // Objects
    private HashMap data, header;

    // Vars
    private ArrayList<String> fieldsIds = new ArrayList<>(), platformIds = new ArrayList<>(), treasuryIds = new ArrayList<>();
    public String centerId = "", roomId = "", field = "", platform = "", type = "center", referenceId = "", caseId = "", name = "", problem = "", description = "", treasury = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentReserveScheduleBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        setPermission();

        paymentCallback();

        return binding.getRoot();
    }

    private void initializer() {
        clientsAdapter = new CreateCheckAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.fieldIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentFieldHeader));
        binding.platformIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentPlatformHeader));
        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentTypeHeader));
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentReferenceHeader));
        binding.caseIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentCaseHeader));
        binding.clientIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentClientHeader));
        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentNameHeader));
        binding.problemIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentProblemHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentDescriptionHeader));
        binding.treasuryIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentTreasuryHeader));

        binding.typeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.ReserveScheduleFragmentTypeReference));
        binding.typeIncludeLayout.firstRadioButton.setChecked(true);
        binding.typeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.ReserveScheduleFragmentTypeCase));

        binding.caseGuideLayout.guideTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentCaseGuide));
        binding.nameGuideLayout.guideTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentNameGuide));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.clientIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.reserveTextView.getRoot(), getResources().getString(R.string.ReserveScheduleFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.typeIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    type = "center";

                    binding.referenceIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.clientIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);

                    break;
                case R.id.second_radioButton:
                    type = "case";

                    binding.referenceIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);

                    if (clientsAdapter.getIds() != null && clientsAdapter.getIds().size() != 0)
                        binding.clientIncludeLayout.getRoot().setVisibility(View.VISIBLE);

                    if (!caseId.equals(""))
                        binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);

                    break;
            }
        });

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showDialogSearchable(requireActivity(), "cases");
        }).widget(binding.caseIncludeLayout.selectContainer);

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showDialogSearchable(requireActivity(), "references");
        }).widget(binding.referenceIncludeLayout.selectTextView);

        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nameIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.nameIncludeLayout.inputEditText);
            return false;
        });

        binding.problemIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.problemIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.problemIncludeLayout.inputEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.nameIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.problemIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            problem = binding.problemIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.fieldIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.platformIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.treasuryIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.fieldIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.platformIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.treasuryIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.fieldIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    field = fieldsIds.get(position);

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.platformIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    platform = platformIds.get(position);

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.treasuryIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    treasury = treasuryIds.get(position);

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.fieldErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.fieldErrorLayout.getRoot(), binding.fieldErrorLayout.errorTextView);
            if (binding.platformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.platformErrorLayout.getRoot(), binding.platformErrorLayout.errorTextView);
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView);
            if (binding.clientErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView);
            if (binding.referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView);
            if (binding.problemErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView);
            if (binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);

            doWork();
        }).widget(binding.reserveTextView.getRoot());
    }

    private void setArgs() {
        scheduleModel = (ScheduleModel) FragmentReserveScheduleArgs.fromBundle(getArguments()).getTypeModel();
        setData(scheduleModel);

        UserModel userModel = ((ActivityMain) requireActivity()).singleton.getUserModel();
        setData(userModel);
    }

    private void setData(ScheduleModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            binding.serialTextView.setText(requireActivity().getResources().getString(R.string.ReserveScheduleFragmentSerialHeader) + " " + model.getId());
            data.put("id", model.getId());
        }

        if (model.getStartedAt() != 0) {
            binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getStartedAt()), " "));
        }

        if (model.getDuration() != 0) {
            binding.durationTextView.setText(model.getDuration() + " " + "دقیقه");
        }

        if (model.getRoom() != null && model.getRoom().getId() != null && !model.getRoom().getId().equals("")) {
            roomId = model.getRoom().getId();
        }

        if (model.getRoom() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getId() != null && !model.getRoom().getCenter().getId().equals("")) {
            centerId = model.getRoom().getCenter().getId();
        }

        if (model.getFields() != null && model.getFields().length() != 0) {
            setAxis(model.getFields());
        }

        if (model.getSessionPlatforms() != null) {
            setPlatform(model.getSessionPlatforms());
        }

        if (model.getClientsType() != null && !model.getClientsType().equals("")) {
            type = model.getClientsType();
            switch (type) {
                case "case":
                    binding.typeIncludeLayout.secondRadioButton.setChecked(true);

                    binding.referenceIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.clientIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    break;
                default:
                    type = "center";
                    binding.typeIncludeLayout.firstRadioButton.setChecked(true);

                    binding.referenceIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.clientIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    break;
            }
        }

        if (model.getCasse() != null && model.getCasse().getId() != null && !model.getCasse().getId().equals("")) {
            caseId = model.getCasse().getId();

            binding.caseIncludeLayout.primaryTextView.setText(caseId);
            setClients(model.getCasse().getClients());

            binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
        }

        if (model.getDescription() != null && !model.getDescription().equals("")) {
            description = model.getDescription();
            binding.descriptionIncludeLayout.inputEditText.setText(description);
        }
    }

    private void setData(UserModel model) {
        if (model.getCenters() != null) {
            for (TypeModel typeModel : model.getCenters().data()) {
                CenterModel centerModel = (CenterModel) typeModel;

                if (centerModel != null && centerModel.getId() != null && centerModel.getId().equals(centerId)) {
                    if (centerModel.getTreasuries() != null) {
                        setTreasury(centerModel.getTreasuries());
                    }
                    break;
                }
            }
        }
    }

    private void setPermission() {
        if (((ActivityMain) requireActivity()).permissoon.showReserveScheduleClientType(scheduleModel)) {
            binding.typeIncludeLayout.getRoot().setVisibility(View.VISIBLE);

            if (type.equals("center")) {
                binding.referenceIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);
            } else {
                binding.referenceIncludeLayout.getRoot().setVisibility(View.GONE);
                binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            }
        } else {
            binding.typeIncludeLayout.getRoot().setVisibility(View.GONE);

            binding.referenceIncludeLayout.getRoot().setVisibility(View.GONE);
            binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);
        }

        if (((ActivityMain) requireActivity()).permissoon.showReserveScheduleCase(scheduleModel) && binding.typeIncludeLayout.getRoot().getVisibility() != View.VISIBLE) {
            binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            binding.caseGuideLayout.getRoot().setVisibility(View.VISIBLE);
        }

        if (((ActivityMain) requireActivity()).permissoon.showReserveScheduleName(scheduleModel)) {
            binding.nameIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            binding.nameGuideLayout.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.nameIncludeLayout.getRoot().setVisibility(View.GONE);
            binding.nameGuideLayout.getRoot().setVisibility(View.GONE);
        }

        if (((ActivityMain) requireActivity()).permissoon.showReserveScheduleTreasury(scheduleModel))
            binding.treasuryIncludeLayout.getRoot().setVisibility(View.VISIBLE);
        else
            binding.treasuryIncludeLayout.getRoot().setVisibility(View.GONE);
    }

    private void setAxis(JSONArray fields) {
        ArrayList<String> options = new ArrayList<>();

        for (int i = 0; i < fields.length(); i++) {
            try {
                if (fields.getJSONObject(i).has("amount") && !fields.getJSONObject(i).isNull("amount"))
                    options.add(fields.getJSONObject(i).getString("title") + " | " + fields.getJSONObject(i).getString("amount"));
                else
                    options.add(fields.getJSONObject(i).getString("title"));

                fieldsIds.add(fields.getJSONObject(i).getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        options.add("");
        fieldsIds.add("");

        InitManager.input12sspSpinner(requireActivity(), binding.fieldIncludeLayout.selectSpinner, options);
    }

    private void setPlatform(List platforms) {
        ArrayList<String> options = new ArrayList<>();

        for (TypeModel typeModel : platforms.data()) {
            SessionPlatformModel model = (SessionPlatformModel) typeModel;

            if (model != null) {
                options.add(model.getTitle() + " " + StringManager.bracing(SelectionManager.getPlatformSession(requireActivity(), "fa", model.getType())));
                platformIds.add(model.getId());
            }
        }

        options.add("");
        platformIds.add("");

        InitManager.input12sspSpinner(requireActivity(), binding.platformIncludeLayout.selectSpinner, options);
    }

    private void setTreasury(List treasuries) {
        ArrayList<String> options = new ArrayList<>();

        for (TypeModel typeModel : treasuries.data()) {
            TreasuriesModel model = (TreasuriesModel) typeModel;

            if (model != null) {
                if (model.isCreditable() && model.getSymbol().contains(centerId.toLowerCase()))
                    model.setTitle(requireActivity().getResources().getString(R.string.ReserveScheduleFragmentTreasuryOnline));

                options.add(model.getTitle());
                treasuryIds.add(model.getId());
            }
        }

        options.add("");
        treasuryIds.add("");

        InitManager.input12sspSpinner(requireActivity(), binding.treasuryIncludeLayout.selectSpinner, options);
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
        switch (method) {
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
                UserModel user = (UserModel) clients.data().get(i);

                if (user != null) {
                    binding.caseIncludeLayout.secondaryTextView.append(user.getName());
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
        switch (method) {
            case "references": {
                UserModel model = (UserModel) item;

                if (!referenceId.equals(model.getId())) {
                    referenceId = model.getId();

                    binding.referenceIncludeLayout.selectTextView.setText(model.getName());
                } else if (referenceId.equals(model.getId())) {
                    referenceId = "";

                    binding.referenceIncludeLayout.selectTextView.setText("");
                }

                DialogManager.dismissDialogSearchable();
            } break;
            case "cases": {
                CaseModel model = (CaseModel) item;

                if (!caseId.equals(model.getId())) {
                    caseId = model.getId();

                    binding.caseIncludeLayout.primaryTextView.setText(caseId);
                    setClients(model.getClients());

                    binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
                } else if (caseId.equals(model.getId())) {
                    caseId = "";

                    binding.caseIncludeLayout.primaryTextView.setText("");
                    binding.caseIncludeLayout.secondaryTextView.setText("");

                    setClients(null);

                    binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                }

                DialogManager.dismissDialogSearchable();
            } break;
        }
    }

    private void setHashmap() {
        if (!field.equals(""))
            data.put("field", field);
        else
            data.remove("field");

        if (!platform.equals(""))
            data.put("session_platform", platform);
        else
            data.remove("session_platform");

        if (!type.equals(""))
            data.put("client_typ", type);
        else
            data.remove("client_typ");

        switch (type) {
            case "case":
                if (!caseId.equals(""))
                    data.put("case_id", caseId);
                else
                    data.remove("case_id");

                if (!clientsAdapter.getIds().isEmpty())
                    data.put("client_id", clientsAdapter.getIds());
                else
                    data.remove("client_id");

                if (caseId.equals("")) {
                    if (!problem.equals(""))
                        data.put("problem", problem);
                    else
                        data.remove("problem");
                }

                break;
            case "center":
                if (!referenceId.equals(""))
                    data.put("client_id", referenceId);
                else
                    data.remove("client_id");

                if (!problem.equals(""))
                    data.put("problem", problem);
                else
                    data.remove("problem");

                break;
        }

        if (!name.equals(""))
            data.put("nickname", name);
        else
            data.remove("nickname");

        if (!description.equals(""))
            data.put("description", description);
        else
            data.remove("description");

        if (!treasury.equals(""))
            data.put("treasurie_id", treasury);
        else
            data.remove("treasurie_id");
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Schedules.booking(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();
                        SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackScheduleReserved));

                        ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentSession(scheduleModel);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject responseObject = new JSONObject(response);

                            if (responseObject.getString("message").equals("POVERTY")) {
                                JSONObject paymentObject = responseObject.getJSONObject("payment");
                                PaymentModel paymentModel = new PaymentModel(paymentObject);

                                Paymont.getInstance().insertPayment(scheduleModel, paymentModel, data, R.id.fragmentReserveSchedule);
                                PaymentManager.request(requireActivity(), paymentModel);
                            } else {
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
                                            case "field":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.fieldErrorLayout.getRoot(), binding.fieldErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "session_platform":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.platformErrorLayout.getRoot(), binding.platformErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "client_typ":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "case_id":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "client_id":
                                                if (type.equals("case") && clientsAdapter.getIds() != null && clientsAdapter.getIds().size() != 0 && binding.clientIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                                                    ((ActivityMain) requireActivity()).validatoon.showValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                else if (type.equals("center"))
                                                    ((ActivityMain) requireActivity()).validatoon.showValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));

                                                break;
                                            case "problem":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "nickname":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "description":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "treasurie_id":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.treasuryErrorLayout.getRoot(), binding.treasuryErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                        }
                                    }

                                    SnackManager.showSnackError(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
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

    private void paymentCallback() {
        if (Paymont.getInstance().getHashmap() != null) {

            if (Paymont.getInstance().getHashmap().containsKey("field") && !Paymont.getInstance().getHashmap().get("field").equals("")) {
                field = (String) Paymont.getInstance().getHashmap().get("field");
                for (int i = 0; i < fieldsIds.size(); i++) {
                    if (fieldsIds.get(i).equals(field)) {
                        binding.fieldIncludeLayout.selectSpinner.setSelection(i);
                    }
                }
            }

            if (Paymont.getInstance().getHashmap().containsKey("session_platform") && !Paymont.getInstance().getHashmap().get("session_platform").equals("")) {
                platform = (String) Paymont.getInstance().getHashmap().get("session_platform");
                for (int i = 0; i < platformIds.size(); i++) {
                    if (platformIds.get(i).equals(platform)) {
                        binding.platformIncludeLayout.selectSpinner.setSelection(i);
                    }
                }
            }

            if (binding.typeIncludeLayout.getRoot().getVisibility() == View.VISIBLE && Paymont.getInstance().getHashmap().containsKey("client_typ") && !Paymont.getInstance().getHashmap().get("client_typ").equals("")) {
                type = (String) Paymont.getInstance().getHashmap().get("client_typ");
                switch (type) {
                    case "case":
                        binding.typeIncludeLayout.secondRadioButton.setChecked(true);

                        binding.referenceIncludeLayout.getRoot().setVisibility(View.GONE);
                        binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);

                        if (clientsAdapter.getIds() != null && clientsAdapter.getIds().size() != 0)
                            binding.clientIncludeLayout.getRoot().setVisibility(View.VISIBLE);

                        if (!caseId.equals(""))
                            binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                        break;
                    default:
                        type = "center";
                        binding.typeIncludeLayout.firstRadioButton.setChecked(true);

                        binding.referenceIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);
                        binding.clientIncludeLayout.getRoot().setVisibility(View.GONE);
                        binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                        break;
                }
            }

            if (binding.problemIncludeLayout.getRoot().getVisibility() == View.VISIBLE && Paymont.getInstance().getHashmap().containsKey("problem") && !Paymont.getInstance().getHashmap().get("problem").equals("")) {
                problem = (String) Paymont.getInstance().getHashmap().get("problem");
                binding.problemIncludeLayout.inputEditText.setText(problem);
            }

            if (binding.nameIncludeLayout.getRoot().getVisibility() == View.VISIBLE && Paymont.getInstance().getHashmap().containsKey("nickname") && !Paymont.getInstance().getHashmap().get("nickname").equals("")) {
                name = (String) Paymont.getInstance().getHashmap().get("nickname");
                binding.nameIncludeLayout.inputEditText.setText(name);
            }

            if (Paymont.getInstance().getHashmap().containsKey("description") && !Paymont.getInstance().getHashmap().get("description").equals("")) {
                description = (String) Paymont.getInstance().getHashmap().get("description");
                binding.descriptionIncludeLayout.inputEditText.setText(description);
            }

            if (binding.treasuryIncludeLayout.getRoot().getVisibility() == View.VISIBLE && Paymont.getInstance().getHashmap().containsKey("treasurie_id") && !Paymont.getInstance().getHashmap().get("treasurie_id").equals("")) {
                treasury = (String) Paymont.getInstance().getHashmap().get("treasurie_id");
                for (int i = 0; i < treasuryIds.size(); i++) {
                    if (treasuryIds.get(i).equals(treasury)) {
                        binding.treasuryIncludeLayout.selectSpinner.setSelection(i);
                    }
                }
            }

            Paymont.getInstance().clearPayment();

            doWork();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}