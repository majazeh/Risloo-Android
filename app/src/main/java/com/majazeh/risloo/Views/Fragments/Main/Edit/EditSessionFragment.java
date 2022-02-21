package com.majazeh.risloo.views.fragments.main.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.tab.EditSessionAdapter;
import com.majazeh.risloo.views.fragments.main.tab.EditSessionTabPaymentFragment;
import com.majazeh.risloo.views.fragments.main.tab.EditSessionTabPlatformFragment;
import com.majazeh.risloo.views.fragments.main.tab.EditSessionTabReferenceFragment;
import com.majazeh.risloo.views.fragments.main.tab.EditSessionTabSessionFragment;
import com.majazeh.risloo.views.fragments.main.tab.EditSessionTabTimeFragment;
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
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());
    }

    private void setArgs() {
        sessionModel = (SessionModel) EditSessionFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData();
    }

    private void setData() {
        if (sessionModel.getId() != null && !sessionModel.getId().equals("")) {
            data.put("id", sessionModel.getId());
        }

        if (sessionModel.getCasse() != null && sessionModel.getCasse().getId() != null && !sessionModel.getCasse().getId().equals("")) {
            data.put("case_id", sessionModel.getCasse().getId());
            data.put("clients_type", "case");

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
        time = ((ActivityMain) requireActivity()).fragmont.getEditSessionTabTime();
        reference = ((ActivityMain) requireActivity()).fragmont.getEditSessionTabReference(hasCase);
        session = ((ActivityMain) requireActivity()).fragmont.getEditSessionTabSession(hasCase);
        platform = ((ActivityMain) requireActivity()).fragmont.getEditSessionTabPlatform(hasCase);
        payment = ((ActivityMain) requireActivity()).fragmont.getEditSessionTagPayment(hasCase);

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

        // Time Data
        if (time instanceof EditSessionTabTimeFragment)
            ((EditSessionTabTimeFragment) time).setHashmap(data);

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
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        setHashmap();

        Session.edit(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackChangesSaved));

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

                                        // Time Data
                                        case "time":
                                        case "duration":
                                        case "date":
                                            if (time instanceof EditSessionTabTimeFragment)
                                                ((EditSessionTabTimeFragment) time).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

                                            break;

                                        // Reference Data
                                        case "selection_type":
                                        case "clients_type":
                                        case "case_id":
                                        case "problem":
                                        case "group_session":
                                        case "clients_number":
                                            if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                ((EditSessionTabReferenceFragment) reference).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

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
                                                ((EditSessionTabSessionFragment) session).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

                                            break;

                                        // Platform Data
                                        case "platforms":
                                        case "pin_platform":
                                        case "identifier_platform":
                                            if (platform instanceof EditSessionTabPlatformFragment)
                                                ((EditSessionTabPlatformFragment) platform).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

                                            break;

                                        // Payment Data
                                        case "payment_status":
                                            if (payment instanceof EditSessionTabPaymentFragment)
                                                ((EditSessionTabPaymentFragment) payment).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

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
    }

}