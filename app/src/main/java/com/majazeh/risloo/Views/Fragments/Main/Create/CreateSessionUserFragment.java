package com.majazeh.risloo.Views.Fragments.Main.Create;

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
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Create.CreateCheckAdapter;
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
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.axisIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionUserFragmentAxisHeader));
        binding.platformIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionUserFragmentPlatformHeader));
        binding.clientIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionUserFragmentClientHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionUserFragmentDescriptionHeader));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.clientIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateSessionUserFragmentButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
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
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.axisErrorLayout.getRoot(), binding.axisErrorLayout.errorTextView);
            if (binding.platformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.platformErrorLayout.getRoot(), binding.platformErrorLayout.errorTextView);
            if (binding.clientErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView);
            if (binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);

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

        if (model.getSession_platforms() != null) {
            setPlatform(model.getSession_platforms());
        }

        if (model.getCaseModel() != null) {
            setClients(model.getCaseModel().getClients());
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
                UserModel userModel = (UserModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackCreatedNewSessionUser));

                        NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(sessionModel, userModel);
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
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.axisErrorLayout.getRoot(), binding.axisErrorLayout.errorTextView, validation);
                                                break;
                                            case "session_platform":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.platformErrorLayout.getRoot(), binding.platformErrorLayout.errorTextView, validation);
                                                break;
                                            case "client_id":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView, validation);
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