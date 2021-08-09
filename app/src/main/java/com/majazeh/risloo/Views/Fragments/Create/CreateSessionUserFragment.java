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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.CheckedAdapter;
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
    public CheckedAdapter clientsAdapter;

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

        detector();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        clientsAdapter = new CheckedAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.axisIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionUserFragmentAxisHeader));
        binding.platformIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionUserFragmentPlatformHeader));
        binding.clientIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionUserFragmentClientHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionUserFragmentDescriptionHeader));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.clientIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateSessionUserFragmentButton), getResources().getColor(R.color.White));
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
        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.axisIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

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

        ClickManager.onDelayedClickListener(() -> {
            if (binding.axisErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.axisIncludeLayout.selectSpinner, binding.axisErrorLayout.getRoot(), binding.axisErrorLayout.errorTextView);
            if (binding.platformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.platformIncludeLayout.selectSpinner, binding.platformErrorLayout.getRoot(), binding.platformErrorLayout.errorTextView);
            if (binding.clientErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), (ConstraintLayout) null, binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView);
            if (binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        String type = CreateSessionUserFragmentArgs.fromBundle(getArguments()).getType();
        TypeModel typeModel = CreateSessionUserFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (type.equals("session")) {
                SessionModel sessionModel = (SessionModel) CreateSessionUserFragmentArgs.fromBundle(getArguments()).getTypeModel();
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

        if (model.getCaseModel() != null && model.getCaseModel().getClients() != null && model.getCaseModel().getClients().size() != 0) {
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
                if (fields.getJSONObject(i).has("amount"))
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

        InitManager.normal12sspSpinner(requireActivity(), binding.axisIncludeLayout.selectSpinner, options);
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

    private void setClients(List clients) {
        if (clients != null && clients.data().size() != 0) {
            ArrayList<TypeModel> items = new ArrayList<>();

            for (int i = 0; i < clients.data().size(); i++) {
                UserModel user = (UserModel) clients.data().get(i);
                if (user != null) {
                    items.add(new TypeModel(clients.data().get(i).object));
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

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        data.put("field", axis);
        data.put("session_platform", platform);
        data.put("client_id", clientsAdapter.getIds());
        data.put("description", description);

        Session.addUser(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        ToastManager.showSuccesToast(requireActivity(), getResources().getString(R.string.ToastNewReferenceAdded));

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
                                            case "field":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.axisIncludeLayout.selectSpinner, binding.axisErrorLayout.getRoot(), binding.axisErrorLayout.errorTextView, validation);
                                                break;
                                            case "session_platform":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.platformIncludeLayout.selectSpinner, binding.platformErrorLayout.getRoot(), binding.platformErrorLayout.errorTextView, validation);
                                                break;
                                            case "client_id":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, binding.clientErrorLayout.getRoot(), binding.clientErrorLayout.errorTextView, validation);
                                                break;
                                            case "description":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, validation);
                                                break;
                                        }

                                        errors.append(validation);
                                        errors.append("\n");
                                    }
                                }

                                ToastManager.showErrorToast(requireActivity(), errors.substring(0, errors.length() - 1));
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