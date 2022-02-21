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
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SelectionManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Create.CreateCheckAdapter;
import com.majazeh.risloo.databinding.FragmentCreateSessionUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CreateSessionUserFragment extends Fragment {

    // Binding
    private FragmentCreateSessionUserBinding binding;

    // Adapters
    public CreateCheckAdapter clientsAdapter;

    // Models
    public SessionModel sessionModel;

    // Objects
    private HashMap data, header;

    // Vars
    private ArrayList<String> axisIds = new ArrayList<>(), platformIds = new ArrayList<>();
    private String axis = "", platform = "", description;
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSessionUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        clientsAdapter = new CreateCheckAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.axisIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionUserFragmentAxisHeader));
        binding.platformIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionUserFragmentPlatformHeader));
        binding.clientIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionUserFragmentClientHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionUserFragmentDescriptionHeader));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.clientIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateSessionUserFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.axisIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.platformIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.axisIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.platformIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.axisIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    axis = axisIds.get(position);

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

        CustomClickView.onDelayedListener(() -> {
            if (binding.axisErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.axisErrorLayout.getRoot(), binding.axisErrorLayout.errorTextView);
            if (binding.platformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.platformErrorLayout.getRoot(), binding.platformErrorLayout.errorTextView);
            if (binding.clientErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView);
            if (binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = CreateSessionUserFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.substring(typeModel.getClass().getName(), '.').equals("SessionModel")) {
                sessionModel = (SessionModel) typeModel;
                setData(sessionModel);
            }
        }
    }

    private void setData(SessionModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }

        if (model.getFields() != null && model.getFields().length() != 0) {
            setAxis(model.getFields());
        }

        if (model.getSessionPlatforms() != null) {
            setPlatform(model.getSessionPlatforms());
        }

        if (model.getCasse() != null) {
            setClients(model.getCasse().getClients());
        }

        if (model.getDescription() != null && !model.getDescription().equals("")) {
            description = model.getDescription();
            binding.descriptionIncludeLayout.inputEditText.setText(description);
        }
    }

    private void setAxis(JSONArray fields) {
        ArrayList<String> options = new ArrayList<>();

        for (int i = 0; i < fields.length(); i++) {
            try {
                if (fields.getJSONObject(i).has("amount") && !fields.getJSONObject(i).isNull("amount"))
                    options.add(fields.getJSONObject(i).getString("title") + " | " + fields.getJSONObject(i).getString("amount"));
                else
                    options.add(fields.getJSONObject(i).getString("title"));

                axisIds.add(fields.getJSONObject(i).getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        options.add("");
        axisIds.add("");

        InitManager.input12sspSpinner(requireActivity(), binding.axisIncludeLayout.selectSpinner, options);
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

    private void setClients(List clients) {
        if (clients != null && clients.data().size() != 0) {
            ArrayList<TypeModel> items = new ArrayList<>();

            for (TypeModel typeModel : clients.data()) {
                UserModel model = (UserModel) typeModel;

                if (model != null) {
                    items.add(model);
                }
            }

            setRecyclerView(items, new ArrayList<>(), "clients");
            binding.clientIncludeLayout.getRoot().setVisibility(View.VISIBLE);
        } else {
            clientsAdapter.clearItems();
            binding.clientIncludeLayout.getRoot().setVisibility(View.GONE);
        }
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
        switch (method) {
            case "clients":
                clientsAdapter.setItems(items, ids, binding.clientIncludeLayout.countTextView);
                binding.clientIncludeLayout.selectRecyclerView.setAdapter(clientsAdapter);
                break;
        }
    }

    private void setHashmap() {
        if (!axis.equals(""))
            data.put("field", axis);
        else
            data.remove("field");

        if (!platform.equals(""))
            data.put("session_platform", platform);
        else
            data.remove("session_platform");

        if (!clientsAdapter.getIds().isEmpty())
            data.put("client_id", clientsAdapter.getIds());
        else
            data.remove("client_id");

        if (!description.equals(""))
            data.put("description", description);
        else
            data.remove("description");
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        setHashmap();

        Session.addUser(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackCreatedNewSessionUser));

                        ((ActivityMain) requireActivity()).navigatoon.navigateUp();
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
                                        case "field":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.axisErrorLayout.getRoot(), binding.axisErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "session_platform":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.platformErrorLayout.getRoot(), binding.platformErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "client_id":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "description":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                    }
                                }

                                SnackManager.showErrorSnack(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
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
        userSelect = false;
    }

}