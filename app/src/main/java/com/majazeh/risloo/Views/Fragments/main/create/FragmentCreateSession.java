package com.majazeh.risloo.views.fragments.main.create;

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
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.tab.CreateSessionAdapter;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabPayment;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabPlatform;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabTime;
import com.majazeh.risloo.databinding.FragmentCreateSessionBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class FragmentCreateSession extends Fragment {

    // Binding
    public FragmentCreateSessionBinding binding;

    // Adapters
    public CreateSessionAdapter adapter;

    // Models
    public CaseModel caseModel;

    // Fragments
    private Fragment time, session, platform, payment;

    // Objects
    private TabLayoutMediator tabLayoutMediator;
    private HashMap data, header;

    // Vars
    private String[] tabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new CreateSessionAdapter(requireActivity());

        tabs = getResources().getStringArray(R.array.CreateSessionTabs);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());
    }

    private void setArgs() {
        TypeModel typeModel = FragmentCreateSessionArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CaseModel")) {
                caseModel = (CaseModel) typeModel;
                setData(caseModel);
            }
        }
    }

    private void setData(CaseModel model) {
        if (model.getRoom() != null && model.getRoom().getId() != null && !model.getRoom().getId().equals("")) {
            data.put("id", model.getRoom().getId());
        }

        if (model.getId() != null && !model.getId().equals("")) {
            data.put("case_id", model.getId());
            data.put("clients_type", "case");
        }

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());

        tabLayoutMediator.attach();
    }

    public void checkRequire() {
        time = ((ActivityMain) requireActivity()).fragmont.getTime();
        session = ((ActivityMain) requireActivity()).fragmont.getSession();
        platform = ((ActivityMain) requireActivity()).fragmont.getPlatform();
        payment = ((ActivityMain) requireActivity()).fragmont.getPayment();

        // Time Data
        if (time instanceof FragmentCreateSessionTabTime)
            ((FragmentCreateSessionTabTime) time).hideValid();

        // Session Data
        if (session instanceof FragmentCreateSessionTabSession)
            ((FragmentCreateSessionTabSession) session).hideValid();

        // Platform Data
        if (platform instanceof FragmentCreateSessionTabPlatform)
            ((FragmentCreateSessionTabPlatform) platform).hideValid();

        // Payment Data
        if (payment instanceof FragmentCreateSessionTabPayment)
            ((FragmentCreateSessionTabPayment) payment).hideValid();

        doWork();
    }

    private void setHashmap() {

        // Time Data
        if (time instanceof FragmentCreateSessionTabTime)
            ((FragmentCreateSessionTabTime) time).setHashmap(data);

        // Session Data
        if (session instanceof FragmentCreateSessionTabSession)
            ((FragmentCreateSessionTabSession) session).setHashmap(data);

        // Platform Data
        if (platform instanceof FragmentCreateSessionTabPlatform)
            ((FragmentCreateSessionTabPlatform) platform).setHashmap(data);

        // Payment Data
        if (payment instanceof FragmentCreateSessionTabPayment)
            ((FragmentCreateSessionTabPayment) payment).setHashmap(data);
    }

    public void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        setHashmap();

        Session.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                SessionModel sessionModel = (SessionModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackCreatedNewSession));

                        ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentSession(sessionModel);
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
                                        case "date_type":
                                        case "date":
                                        case "week_days":
                                        case "repeat_status":
                                        case "repeat":
                                        case "repeat_from":
                                        case "repeat_to":
                                            if (time instanceof FragmentCreateSessionTabTime)
                                                ((FragmentCreateSessionTabTime) time).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

                                            break;

                                        // Session Data
                                        case "status":
                                        case "opens_at_type":
                                        case "opens_at":
                                        case "closed_at_type":
                                        case "closed_at":
                                        case "fields":
                                        case "description":
                                        case "client_reminder":
                                            if (session instanceof FragmentCreateSessionTabSession)
                                                ((FragmentCreateSessionTabSession) session).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

                                            break;

                                        // Platform Data
                                        case "platforms":
                                        case "pin_platform":
                                        case "identifier_platform":
                                            if (platform instanceof FragmentCreateSessionTabPlatform)
                                                ((FragmentCreateSessionTabPlatform) platform).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

                                            break;

                                        // Payment Data
                                        case "payment_status":
                                            if (payment instanceof FragmentCreateSessionTabPayment)
                                                ((FragmentCreateSessionTabPayment) payment).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

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