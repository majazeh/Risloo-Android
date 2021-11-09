package com.majazeh.risloo.Views.Fragments.Main.Edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Tab.EditSessionAdapter;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditSessionTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditSessionTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditSessionTabReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditSessionTabTimeFragment;
import com.majazeh.risloo.databinding.FragmentEditSessionBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.SessionModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class EditSessionFragment extends Fragment {

    // Binding
    public FragmentEditSessionBinding binding;

    // Adapters
    public EditSessionAdapter adapter;

    // Models
    public SessionModel sessionModel;

    // Fragments
    private Fragment time, reference, session, platform, payment;

    // Objects
    private TabLayoutMediator tabLayoutMediator;
    private HashMap data, header;

    // Vars
    private String[] tabs;
    private boolean hasCase = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
    }

    private void setArgs() {
        sessionModel = (SessionModel) EditSessionFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData();
    }

    private void setData() {
        if (sessionModel.getId() != null && !sessionModel.getId().equals("")) {
            data.put("id", sessionModel.getId());
        }

        if (sessionModel.getCaseModel() != null && sessionModel.getCaseModel().getCaseId() != null) {
            hasCase = true;

            tabs = getResources().getStringArray(R.array.EditSessionHasCaseTabs);
            tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));
        } else {
            tabs = getResources().getStringArray(R.array.EditSessionNoCaseTabs);
            tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));
        }

        adapter = new EditSessionAdapter(requireActivity(), hasCase);

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());

        tabLayoutMediator.attach();
    }

    public void checkRequire() {
        time = ((MainActivity) requireActivity()).fragmont.getEditSessionTabTime();
        reference = ((MainActivity) requireActivity()).fragmont.getEditSessionTabReference(hasCase);
        session = ((MainActivity) requireActivity()).fragmont.getEditSessionTabSession(hasCase);
        platform = ((MainActivity) requireActivity()).fragmont.getEditSessionTabPlatform(hasCase);
        payment = ((MainActivity) requireActivity()).fragmont.getEditSessionTagPayment(hasCase);

        // Time Data
        if (time instanceof EditSessionTabTimeFragment)
            ((EditSessionTabTimeFragment) time).hideValid();

        // Reference Data
        if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
            ((EditSessionTabReferenceFragment) reference).hideValid();

        // Session Data
        if (session instanceof EditSessionTabSessionFragment)
            ((EditSessionTabSessionFragment) session).hideValid();

        // Platform Data
        if (platform instanceof EditSessionTabPlatformFragment)
            ((EditSessionTabPlatformFragment) platform).hideValid();

        // Payment Data
        if (payment instanceof EditSessionTabPaymentFragment)
            ((EditSessionTabPaymentFragment) payment).hideValid();

        doWork();
    }

    private void setHashmap() {
        String caseId = sessionModel.getCaseModel().getCaseId();

        if (!caseId.equals(""))
            data.put("case_id", caseId);
        else
            data.remove("case_id");

        if (!caseId.equals(""))
            data.put("clients_type", "case");
        else
            data.remove("clients_type");
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        // Time Data
        if (time instanceof EditSessionTabTimeFragment)
            ((EditSessionTabTimeFragment) time).setHashmap(data);

        // Case Data
        if (hasCase)
            setHashmap();

        // Reference Data
        if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
            ((EditSessionTabReferenceFragment) reference).setHashmap(data);

        // Session Data
        if (session instanceof EditSessionTabSessionFragment)
            ((EditSessionTabSessionFragment) session).setHashmap(data);

        // Platform Data
        if (platform instanceof EditSessionTabPlatformFragment)
            ((EditSessionTabPlatformFragment) platform).setHashmap(data);

        // Payment Data
        if (payment instanceof EditSessionTabPaymentFragment)
            ((EditSessionTabPaymentFragment) payment).setHashmap(data);

        Session.edit(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackChangesSaved));
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

                                            // Time Data
                                            case "time":
                                            case "duration":
                                            case "date":
                                                if (time instanceof EditSessionTabTimeFragment)
                                                    ((EditSessionTabTimeFragment) time).showValid(key, validation);

                                                break;

                                            // Reference Data
                                            case "selection_type":
                                            case "clients_type":
                                            case "case_id":
                                            case "problem":
                                            case "group_session":
                                            case "clients_number":
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((EditSessionTabReferenceFragment) reference).showValid(key, validation);

                                                break;

                                            // Session Data
                                            case "status":
                                            case "opens_at_type":
                                            case "opens_at":
                                            case "closed_at_type":
                                            case "closed_at":
                                            case "description":
                                            case "client_reminder":
                                                if (session instanceof EditSessionTabSessionFragment)
                                                    ((EditSessionTabSessionFragment) session).showValid(key, validation);

                                                break;

                                            // Platform Data
                                            case "platforms":
                                            case "pin_platform":
                                            case "identifier_platform":
                                                if (platform instanceof EditSessionTabPlatformFragment)
                                                    ((EditSessionTabPlatformFragment) platform).showValid(key, validation);

                                                break;

                                            // Payment Data
                                            case "payment_status":
                                                if (payment instanceof EditSessionTabPaymentFragment)
                                                    ((EditSessionTabPaymentFragment) payment).showValid(key, validation);

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