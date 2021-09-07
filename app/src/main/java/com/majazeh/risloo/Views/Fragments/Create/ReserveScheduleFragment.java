package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.PaymentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Create.CreateCheckAdapter;
import com.majazeh.risloo.databinding.FragmentReserveScheduleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Payment;
import com.mre.ligheh.Model.Madule.Schedules;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.PaymentModel;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ReserveScheduleFragment extends Fragment {

    // Binding
    private FragmentReserveScheduleBinding binding;

    // Adapters
    public CreateCheckAdapter clientsAdapter;

    // Models
    private ScheduleModel scheduleModel;

    // Objects
    private HashMap data, header;

    // Vars
    private ArrayList<String> fieldsIds = new ArrayList<>(), platformIds = new ArrayList<>();
    public String roomId = "", field = "", platform = "", type = "center", caseId = "", referenceId = "", problem = "", description = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentReserveScheduleBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        setPermission();

        setCallBack();

        return binding.getRoot();
    }

    private void initializer() {
        clientsAdapter = new CreateCheckAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.fieldIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentFieldHeader));
        binding.platformIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentPlatformHeader));
        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentTypeHeader));
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentReferenceHeader));
        binding.caseIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentCaseHeader));
        binding.clientIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentClientHeader));
        binding.problemIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentProblemHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentDescriptionHeader));

        binding.typeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.ReserveScheduleFragmentTypeReference));
        binding.typeIncludeLayout.firstRadioButton.setChecked(true);
        binding.typeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.ReserveScheduleFragmentTypeCase));

        binding.caseGuideLayout.guideTextView.setText(getResources().getString(R.string.ReserveScheduleFragmentCaseGuide));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.clientIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColor(binding.reserveTextView.getRoot(), getResources().getString(R.string.ReserveScheduleFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.reserveTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.reserveTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.fieldIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

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

        binding.platformIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
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
            DialogManager.showSearchableDialog(requireActivity(), "cases");
        }).widget(binding.caseIncludeLayout.selectContainer);

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showSearchableDialog(requireActivity(), "references");
        }).widget(binding.referenceIncludeLayout.selectTextView);

        binding.problemIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.problemIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.problemIncludeLayout.inputEditText);
            return false;
        });

        binding.problemIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            problem = binding.problemIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.fieldErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.fieldErrorLayout.getRoot(), binding.fieldErrorLayout.errorTextView);
            if (binding.platformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.platformErrorLayout.getRoot(), binding.platformErrorLayout.errorTextView);
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView);
            if (binding.clientErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView);
            if (binding.referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView);
            if (binding.problemErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView);
            if (binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);

            doWork();
        }).widget(binding.reserveTextView.getRoot());
    }

    private void setArgs() {
        scheduleModel = (ScheduleModel) ReserveScheduleFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(scheduleModel);
    }

    private void setData(ScheduleModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            binding.serialTextView.setText(requireActivity().getResources().getString(R.string.ReserveScheduleFragmentSerialHeader) + " " + model.getId());
            data.put("id", model.getId());
        }

        if (model.getStarted_at() != 0) {
            binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getStarted_at()), " "));
        }

        if (model.getDuration() != 0) {
            binding.durationTextView.setText(model.getDuration() + " " + "دقیقه");
        }

        if (model.getRoom() != null && model.getRoom().getRoomId() != null && !model.getRoom().getRoomId().equals("")) {
            roomId = model.getRoom().getRoomId();
        }

        if (model.getFields() != null && model.getFields().length() != 0) {
            setAxis(model.getFields());
        }

        if (model.getSession_platforms() != null) {
            setPlatform(model.getSession_platforms());
        }

        if (model.getClients_type() != null && !model.getClients_type().equals("")) {
            type = model.getClients_type();
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

        if (model.getCaseModel() != null && model.getCaseModel().getCaseId() != null && !model.getCaseModel().getCaseId().equals("")) {
            caseId = model.getCaseModel().getCaseId();

            binding.caseIncludeLayout.primaryTextView.setText(caseId);
            setClients(model.getCaseModel().getClients());

            binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
        }

        if (model.getDescription() != null && !model.getDescription().equals("")) {
            description = model.getDescription();
            binding.descriptionIncludeLayout.inputEditText.setText(description);
        }
    }

    private void setPermission() {
        UserModel model = ((MainActivity) requireActivity()).singleton.getUserModel();

        if (((MainActivity) requireActivity()).permissoon.showReserveScheduleClientType(model, scheduleModel))
            binding.typeIncludeLayout.getRoot().setVisibility(View.VISIBLE);
        else
            binding.typeIncludeLayout.getRoot().setVisibility(View.GONE);

        if (((MainActivity) requireActivity()).permissoon.showReserveScheduleReference(model, scheduleModel))
            binding.referenceIncludeLayout.getRoot().setVisibility(View.VISIBLE);
        else {
            binding.referenceIncludeLayout.getRoot().setVisibility(View.GONE);
            binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
        }

        if (((MainActivity) requireActivity()).permissoon.showReserveScheduleCaseGuide(model, scheduleModel))
            if (binding.caseIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                binding.caseGuideLayout.getRoot().setVisibility(View.VISIBLE);
        else
            binding.caseGuideLayout.getRoot().setVisibility(View.GONE);
    }

    private void setAxis(JSONArray fields) {
        ArrayList<String> options = new ArrayList<>();

        for (int i = 0; i < fields.length(); i++) {
            try {
                if (fields.getJSONObject(i).has("amount"))
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

        InitManager.normal12sspSpinner(requireActivity(), binding.fieldIncludeLayout.selectSpinner, options);
    }

    private void setPlatform(List platforms) {
        ArrayList<String> options = new ArrayList<>();

        for (int i = 0; i < platforms.data().size(); i++) {
            SessionPlatformModel model = (SessionPlatformModel) platforms.data().get(i);
            options.add(model.getTitle() + " " + StringManager.bracing(SelectionManager.getPlatformSession(requireActivity(), "fa", model.getType())));

            platformIds.add(model.getId());
        }

        options.add("");
        platformIds.add("");

        InitManager.normal12sspSpinner(requireActivity(), binding.platformIncludeLayout.selectSpinner, options);
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

                DialogManager.dismissSearchableDialog();
            } break;
            case "cases": {
                CaseModel model = (CaseModel) item;

                if (!caseId.equals(model.getCaseId())) {
                    caseId = model.getCaseId();

                    binding.caseIncludeLayout.primaryTextView.setText(caseId);
                    setClients(model.getClients());

                    binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
                } else if (caseId.equals(model.getCaseId())) {
                    caseId = "";

                    binding.caseIncludeLayout.primaryTextView.setText("");
                    binding.caseIncludeLayout.secondaryTextView.setText("");

                    setClients(null);

                    binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                }

                DialogManager.dismissSearchableDialog();
            } break;
        }
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "loading");

        data.put("field", field);
        data.put("session_platform", platform);
        data.put("client_typ", type);

        switch (type) {
            case "case":
                data.put("case_id", caseId);

                if (clientsAdapter.getIds() != null && clientsAdapter.getIds().size() != 0)
                    data.put("client_id", clientsAdapter.getIds());

                if (caseId.equals(""))
                    data.put("problem", problem);
                break;
            case "center":
                data.put("client_id", referenceId);
                data.put("problem", problem);
                break;
        }

        data.put("description", description);

        Schedules.booking(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastNewScheduleReserved));

                        NavDirections action = NavigationMainDirections.actionGlobalSessionFragment("schedule", scheduleModel);
                        ((MainActivity) requireActivity()).navController.navigate(action);
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
                                DialogManager.dismissLoadingDialog();

                                JSONObject payment = responseObject.getJSONObject("payment");
                                String key = payment.getString("authorized_key");

                                callAuth("payment", key);
                            } else {
                                if (!responseObject.isNull("errors")) {
                                    JSONObject errorsObject = responseObject.getJSONObject("errors");

                                    Iterator<String> keys = (errorsObject.keys());
                                    StringBuilder errors = new StringBuilder();

                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                            String validation = errorsObject.getJSONArray(key).get(i).toString();

                                            switch (key) {
                                                case "field":
                                                    ((MainActivity) requireActivity()).validatoon.showValid(binding.fieldErrorLayout.getRoot(), binding.fieldErrorLayout.errorTextView, validation);
                                                    break;
                                                case "session_platform":
                                                    ((MainActivity) requireActivity()).validatoon.showValid(binding.platformErrorLayout.getRoot(), binding.platformErrorLayout.errorTextView, validation);
                                                    break;
                                                case "client_typ":
                                                    ((MainActivity) requireActivity()).validatoon.showValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, validation);
                                                    break;
                                                case "case_id":
                                                    ((MainActivity) requireActivity()).validatoon.showValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView, validation);
                                                    break;
                                                case "client_id":
                                                    if (type.equals("case") && clientsAdapter.getIds() != null && clientsAdapter.getIds().size() != 0 && binding.clientIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                                                        ((MainActivity) requireActivity()).validatoon.showValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView, validation);
                                                    else if (type.equals("center"))
                                                        ((MainActivity) requireActivity()).validatoon.showValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView, validation);
                                                    break;
                                                case "problem":
                                                    ((MainActivity) requireActivity()).validatoon.showValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView, validation);
                                                    break;
                                                case "description":
                                                    ((MainActivity) requireActivity()).validatoon.showValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, validation);
                                                    break;
                                            }

                                            errors.append(validation);
                                            errors.append("\n");
                                        }
                                    }

                                    SnackManager.showErrorSnack(requireActivity(), errors.substring(0, errors.length() - 1));
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

    private void setCallBack() {
        if (!((MainActivity) requireActivity()).singleton.getPaymentAuthKey().equals(""))
            callAuth("callback", ((MainActivity) requireActivity()).singleton.getPaymentAuthKey());
    }

    private void callAuth(String method, String key) {
        DialogManager.showLoadingDialog(requireActivity(), method);

        HashMap data = new HashMap<>();
        HashMap header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        data.put("authorized_key", key);

        Payment.auth(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                PaymentModel model = (PaymentModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();

                        if (method.equals("payment")) {
                            // TODO : Update Schedule Model

                            ((MainActivity) requireActivity()).singleton.fillPayment(key, scheduleModel);

                            PaymentManager.request(requireActivity(), model.getRedirect());
                        } else {
                            NavDirections action = NavigationMainDirections.actionGlobalSessionFragment("schedule", scheduleModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);

                            ((MainActivity) requireActivity()).singleton.clearPayment();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        // Place Code if Needed
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