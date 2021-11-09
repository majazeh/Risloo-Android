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
import com.majazeh.risloo.Utils.Managers.SelectionManager;
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
        if (!hasCase && reference instanceof EditSessionTabReferenceFragment) {
            if (((EditSessionTabReferenceFragment) reference).binding.selectionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((EditSessionTabReferenceFragment) reference).binding.selectionErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.selectionErrorLayout.errorTextView);
            if (((EditSessionTabReferenceFragment) reference).binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((EditSessionTabReferenceFragment) reference).binding.typeErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.typeErrorLayout.errorTextView);
            if (((EditSessionTabReferenceFragment) reference).binding.caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((EditSessionTabReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.caseErrorLayout.errorTextView);
            if (((EditSessionTabReferenceFragment) reference).binding.problemErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((EditSessionTabReferenceFragment) reference).binding.problemErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.problemErrorLayout.errorTextView);
            if (((EditSessionTabReferenceFragment) reference).binding.bulkSessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((EditSessionTabReferenceFragment) reference).binding.bulkSessionErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.bulkSessionErrorLayout.errorTextView);
            if (((EditSessionTabReferenceFragment) reference).binding.countErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((EditSessionTabReferenceFragment) reference).binding.countErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.countErrorLayout.errorTextView);
        }

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

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        // Time Data
        if (time instanceof EditSessionTabTimeFragment)
            ((EditSessionTabTimeFragment) time).setHashmap(data);

        // Reference Data
        if (hasCase) {
            data.put("case_id", sessionModel.getCaseModel().getCaseId());
            data.put("clients_type", "case");
        } else {
            if (reference instanceof EditSessionTabReferenceFragment) {
                data.put("selection_type", SelectionManager.getSelectionType(requireActivity(), "en", ((EditSessionTabReferenceFragment) reference).selection));

                if (((EditSessionTabReferenceFragment) reference).type.equals("اعضاء ریسلو")) {
                    data.put("clients_type", "risloo");
                } else if (((EditSessionTabReferenceFragment) reference).type.contains("مرکز")) {
                    data.put("clients_type", "center");
                } else if (((EditSessionTabReferenceFragment) reference).type.contains("اتاق درمان")) {
                    data.put("clients_type", "room");
                } else if (((EditSessionTabReferenceFragment) reference).type.equals("اعضاء پرونده درمانی …")) {
                    data.put("clients_type", "case");
                    data.put("case_id", ((EditSessionTabReferenceFragment) reference).caseId);
                } else if (((EditSessionTabReferenceFragment) reference).type.equals("ساخت پرونده جدید")) {
                    data.put("clients_type", "new_case");
                    data.put("problem", ((EditSessionTabReferenceFragment) reference).problem);
                }

                data.put("group_session", ((EditSessionTabReferenceFragment) reference).groupSession);
                if (((EditSessionTabReferenceFragment) reference).groupSession.equals("on")) {
                    data.put("clients_number", ((EditSessionTabReferenceFragment) reference).count);
                }
            }
        }

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
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((EditSessionTabReferenceFragment) reference).binding.selectionErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.selectionErrorLayout.errorTextView, validation);
                                                break;
                                            case "clients_type":
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((EditSessionTabReferenceFragment) reference).binding.typeErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.typeErrorLayout.errorTextView, validation);
                                                break;
                                            case "case_id":
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((EditSessionTabReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.caseErrorLayout.errorTextView, validation);
                                                break;
                                            case "problem":
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((EditSessionTabReferenceFragment) reference).binding.problemErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.problemErrorLayout.errorTextView, validation);
                                                break;
                                            case "group_session":
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((EditSessionTabReferenceFragment) reference).binding.bulkSessionErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.bulkSessionErrorLayout.errorTextView, validation);
                                                break;
                                            case "clients_number":
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((EditSessionTabReferenceFragment) reference).binding.countErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.countErrorLayout.errorTextView, validation);
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