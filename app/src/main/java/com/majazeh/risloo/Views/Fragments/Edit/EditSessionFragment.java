package com.majazeh.risloo.Views.Fragments.Edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Tab.EditSessionAdapter;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabTimeFragment;
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
        if (time instanceof EditSessionTabTimeFragment) {
            if (((EditSessionTabTimeFragment) time).binding.startTimeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTabTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((EditSessionTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((EditSessionTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView);
            if (((EditSessionTabTimeFragment) time).binding.durationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTabTimeFragment) time).binding.durationIncludeLayout.inputEditText, ((EditSessionTabTimeFragment) time).binding.durationErrorLayout.getRoot(), ((EditSessionTabTimeFragment) time).binding.durationErrorLayout.errorTextView);
            if (((EditSessionTabTimeFragment) time).binding.startDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTabTimeFragment) time).binding.startDateIncludeLayout.selectTextView, ((EditSessionTabTimeFragment) time).binding.startDateErrorLayout.getRoot(), ((EditSessionTabTimeFragment) time).binding.startDateErrorLayout.errorTextView);
        }

        // Reference Data
        if (!hasCase && reference instanceof EditSessionTabReferenceFragment) {
            if (((EditSessionTabReferenceFragment) reference).binding.selectionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTabReferenceFragment) reference).binding.selectionIncludeLayout.selectSpinner, ((EditSessionTabReferenceFragment) reference).binding.selectionErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.selectionErrorLayout.errorTextView);
            if (((EditSessionTabReferenceFragment) reference).binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTabReferenceFragment) reference).binding.typeIncludeLayout.selectSpinner, ((EditSessionTabReferenceFragment) reference).binding.typeErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.typeErrorLayout.errorTextView);
            if (((EditSessionTabReferenceFragment) reference).binding.caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTabReferenceFragment) reference).binding.caseIncludeLayout.selectContainer, ((EditSessionTabReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.caseErrorLayout.errorTextView);
            if (((EditSessionTabReferenceFragment) reference).binding.problemErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTabReferenceFragment) reference).binding.problemIncludeLayout.inputEditText, ((EditSessionTabReferenceFragment) reference).binding.problemErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.problemErrorLayout.errorTextView);
            if (((EditSessionTabReferenceFragment) reference).binding.bulkSessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), (ConstraintLayout) null, ((EditSessionTabReferenceFragment) reference).binding.bulkSessionErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.bulkSessionErrorLayout.errorTextView);
            if (((EditSessionTabReferenceFragment) reference).binding.countErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTabReferenceFragment) reference).binding.caseIncludeLayout.selectContainer, ((EditSessionTabReferenceFragment) reference).binding.countErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.countErrorLayout.errorTextView);
        }

        // Session Data
        if (session instanceof EditSessionTabSessionFragment) {
            if (((EditSessionTabSessionFragment) session).binding.statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTabSessionFragment) session).binding.statusIncludeLayout.selectSpinner, ((EditSessionTabSessionFragment) session).binding.statusErrorLayout.getRoot(), ((EditSessionTabSessionFragment) session).binding.statusErrorLayout.errorTextView);
            if (((EditSessionTabSessionFragment) session).binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTabSessionFragment) session).binding.descriptionIncludeLayout.inputEditText, ((EditSessionTabSessionFragment) session).binding.descriptionErrorLayout.getRoot(), ((EditSessionTabSessionFragment) session).binding.descriptionErrorLayout.errorTextView);
            if (((EditSessionTabSessionFragment) session).binding.coordinationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTabSessionFragment) session).binding.coordinationIncludeLayout.inputEditText, ((EditSessionTabSessionFragment) session).binding.coordinationErrorLayout.getRoot(), ((EditSessionTabSessionFragment) session).binding.coordinationErrorLayout.errorTextView);
        }

        // Platform Data
        if (platform instanceof EditSessionTabPlatformFragment) {
            if (((EditSessionTabPlatformFragment) platform).binding.platformsErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), (ConstraintLayout) null, ((EditSessionTabPlatformFragment) platform).binding.platformsErrorLayout.getRoot(), ((EditSessionTabPlatformFragment) platform).binding.platformsErrorLayout.errorTextView);
            if (((EditSessionTabPlatformFragment) platform).binding.pinPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), (ConstraintLayout) null, ((EditSessionTabPlatformFragment) platform).binding.pinPlatformErrorLayout.getRoot(), ((EditSessionTabPlatformFragment) platform).binding.pinPlatformErrorLayout.errorTextView);
            if (((EditSessionTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), (ConstraintLayout) null, ((EditSessionTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.getRoot(), ((EditSessionTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.errorTextView);
        }

        // Payment Data
        if (payment instanceof EditSessionTabPaymentFragment) {
            if (((EditSessionTabPaymentFragment) payment).binding.paymentErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((EditSessionTabPaymentFragment) payment).binding.paymentIncludeLayout.selectSpinner, ((EditSessionTabPaymentFragment) payment).binding.paymentErrorLayout.getRoot(), ((EditSessionTabPaymentFragment) payment).binding.paymentErrorLayout.errorTextView);
        }

        doWork();
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        // Time Data
        if (time instanceof EditSessionTabTimeFragment) {
            data.put("time", DateManager.jalHHsMM(((EditSessionTabTimeFragment) time).startTime));
            data.put("duration", ((EditSessionTabTimeFragment) time).duration);
            data.put("date", ((EditSessionTabTimeFragment) time).startDate);
        }

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
        if (session instanceof EditSessionTabSessionFragment) {
            data.put("status", SelectionManager.getSessionStatus(requireActivity(), "en", ((EditSessionTabSessionFragment) session).status));
            data.put("description", ((EditSessionTabSessionFragment) session).description);
            data.put("client_reminder", ((EditSessionTabSessionFragment) session).coordination);
        }

        // Platform Data
        if (platform instanceof EditSessionTabPlatformFragment) {
            data.put("platforms", ((EditSessionTabPlatformFragment) platform).adapter.platforms);
            data.put("pin_platform", ((EditSessionTabPlatformFragment) platform).adapter.pinPlatform);
            data.put("identifier_platform", ((EditSessionTabPlatformFragment) platform).adapter.identifierPlatform);
        }

        // Payment Data
        if (payment instanceof EditSessionTabPaymentFragment) {
            data.put("payment_status", SelectionManager.getPaymentStatus(requireActivity(), "en", ((EditSessionTabPaymentFragment) payment).payment));
        }

        Session.edit(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        ToastManager.showSuccesToast(requireActivity(), getResources().getString(R.string.ToastChangesSaved));
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
                                                if (time instanceof EditSessionTabTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTabTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((EditSessionTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((EditSessionTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView, validation);
                                                break;
                                            case "duration":
                                                if (time instanceof EditSessionTabTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTabTimeFragment) time).binding.durationIncludeLayout.inputEditText, ((EditSessionTabTimeFragment) time).binding.durationErrorLayout.getRoot(), ((EditSessionTabTimeFragment) time).binding.durationErrorLayout.errorTextView, validation);
                                                break;
                                            case "date":
                                                if (time instanceof EditSessionTabTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTabTimeFragment) time).binding.startDateIncludeLayout.selectTextView, ((EditSessionTabTimeFragment) time).binding.startDateErrorLayout.getRoot(), ((EditSessionTabTimeFragment) time).binding.startDateErrorLayout.errorTextView, validation);
                                                break;

                                            // Reference Data
                                            case "selection_type":
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTabReferenceFragment) reference).binding.selectionIncludeLayout.selectSpinner, ((EditSessionTabReferenceFragment) reference).binding.selectionErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.selectionErrorLayout.errorTextView, validation);
                                                break;
                                            case "clients_type":
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTabReferenceFragment) reference).binding.typeIncludeLayout.selectSpinner, ((EditSessionTabReferenceFragment) reference).binding.typeErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.typeErrorLayout.errorTextView, validation);
                                                break;
                                            case "case_id":
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTabReferenceFragment) reference).binding.caseIncludeLayout.selectContainer, ((EditSessionTabReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.caseErrorLayout.errorTextView, validation);
                                                break;
                                            case "problem":
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTabReferenceFragment) reference).binding.problemIncludeLayout.inputEditText, ((EditSessionTabReferenceFragment) reference).binding.problemErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.problemErrorLayout.errorTextView, validation);
                                                break;
                                            case "group_session":
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, ((EditSessionTabReferenceFragment) reference).binding.bulkSessionErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.bulkSessionErrorLayout.errorTextView, validation);
                                                break;
                                            case "clients_number":
                                                if (!hasCase && reference instanceof EditSessionTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTabReferenceFragment) reference).binding.countIncludeLayout.inputEditText, ((EditSessionTabReferenceFragment) reference).binding.countErrorLayout.getRoot(), ((EditSessionTabReferenceFragment) reference).binding.countErrorLayout.errorTextView, validation);
                                                break;

                                            // Session Data
                                            case "status":
                                                if (session instanceof EditSessionTabSessionFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTabSessionFragment) session).binding.statusIncludeLayout.selectSpinner, ((EditSessionTabSessionFragment) session).binding.statusErrorLayout.getRoot(), ((EditSessionTabSessionFragment) session).binding.statusErrorLayout.errorTextView, validation);
                                                break;
                                            case "description":
                                                if (session instanceof EditSessionTabSessionFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTabSessionFragment) session).binding.descriptionIncludeLayout.inputEditText, ((EditSessionTabSessionFragment) session).binding.descriptionErrorLayout.getRoot(), ((EditSessionTabSessionFragment) session).binding.descriptionErrorLayout.errorTextView, validation);
                                                break;
                                            case "client_reminder":
                                                if (session instanceof EditSessionTabSessionFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTabSessionFragment) session).binding.coordinationIncludeLayout.inputEditText, ((EditSessionTabSessionFragment) session).binding.coordinationErrorLayout.getRoot(), ((EditSessionTabSessionFragment) session).binding.coordinationErrorLayout.errorTextView, validation);
                                                break;

                                            // Platform Data
                                            case "platforms":
                                                if (platform instanceof EditSessionTabPlatformFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, ((EditSessionTabPlatformFragment) platform).binding.platformsErrorLayout.getRoot(), ((EditSessionTabPlatformFragment) platform).binding.platformsErrorLayout.errorTextView, validation);
                                                break;
                                            case "pin_platform":
                                                if (platform instanceof EditSessionTabPlatformFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, ((EditSessionTabPlatformFragment) platform).binding.pinPlatformErrorLayout.getRoot(), ((EditSessionTabPlatformFragment) platform).binding.pinPlatformErrorLayout.errorTextView, validation);
                                                break;
                                            case "identifier_platform":
                                                if (platform instanceof EditSessionTabPlatformFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, ((EditSessionTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.getRoot(), ((EditSessionTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.errorTextView, validation);
                                                break;

                                            // Payment Data
                                            case "payment_status":
                                                if (payment instanceof EditSessionTabPaymentFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((EditSessionTabPaymentFragment) payment).binding.paymentIncludeLayout.selectSpinner, ((EditSessionTabPaymentFragment) payment).binding.paymentErrorLayout.getRoot(), ((EditSessionTabPaymentFragment) payment).binding.paymentErrorLayout.errorTextView, validation);
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