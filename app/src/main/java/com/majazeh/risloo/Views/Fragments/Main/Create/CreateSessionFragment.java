package com.majazeh.risloo.Views.Fragments.Main.Create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Tab.CreateSessionAdapter;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabTimeFragment;
import com.majazeh.risloo.databinding.FragmentCreateSessionBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class CreateSessionFragment extends Fragment {

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
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
    }

    private void setArgs() {
        TypeModel typeModel = CreateSessionFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CaseModel")) {
                caseModel = (CaseModel) typeModel;
                setData(caseModel);
            }
        }
    }

    private void setData(CaseModel model) {
        if (model.getCaseRoom() != null && model.getCaseRoom().getRoomId() != null && !model.getCaseRoom().getRoomId().equals("")) {
            data.put("id", model.getCaseRoom().getRoomId());
        }

        if (model.getCaseId() != null && !model.getCaseId().equals("")) {
            data.put("case_id", model.getCaseId());
            data.put("clients_type", "case");
        }

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());

        tabLayoutMediator.attach();
    }

    public void checkRequire() {
        time = ((MainActivity) requireActivity()).fragmont.getTime();
        session = ((MainActivity) requireActivity()).fragmont.getSession();
        platform = ((MainActivity) requireActivity()).fragmont.getPlatform();
        payment = ((MainActivity) requireActivity()).fragmont.getPayment();

        // Time Data
        if (time instanceof CreateSessionTabTimeFragment) {
            if (((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView);
            if (((CreateSessionTabTimeFragment) time).binding.durationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabTimeFragment) time).binding.durationErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.durationErrorLayout.errorTextView);
            if (((CreateSessionTabTimeFragment) time).binding.dateTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabTimeFragment) time).binding.dateTypeErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.dateTypeErrorLayout.errorTextView);
            if (((CreateSessionTabTimeFragment) time).binding.specifiedDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabTimeFragment) time).binding.specifiedDateErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.specifiedDateErrorLayout.errorTextView);
            if (((CreateSessionTabTimeFragment) time).binding.patternDaysErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabTimeFragment) time).binding.patternDaysErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.patternDaysErrorLayout.errorTextView);
            if (((CreateSessionTabTimeFragment) time).binding.patternTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabTimeFragment) time).binding.patternTypeErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.patternTypeErrorLayout.errorTextView);
            if (((CreateSessionTabTimeFragment) time).binding.repeatWeeksErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabTimeFragment) time).binding.repeatWeeksErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.repeatWeeksErrorLayout.errorTextView);
            if (((CreateSessionTabTimeFragment) time).binding.periodStartDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabTimeFragment) time).binding.periodStartDateErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.periodStartDateErrorLayout.errorTextView);
            if (((CreateSessionTabTimeFragment) time).binding.periodEndDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabTimeFragment) time).binding.periodEndDateErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.periodEndDateErrorLayout.errorTextView);
        }

        // Session Data
        if (session instanceof CreateSessionTabSessionFragment)
            ((CreateSessionTabSessionFragment) session).hideValid();

        // Platform Data
        if (platform instanceof CreateSessionTabPlatformFragment)
            ((CreateSessionTabPlatformFragment) platform).hideValid();

        // Payment Data
        if (payment instanceof CreateSessionTabPaymentFragment)
            ((CreateSessionTabPaymentFragment) payment).hideValid();

        doWork();
    }

    public void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        // Time Data
        if (time instanceof CreateSessionTabTimeFragment) {
            data.put("time", DateManager.jalHHsMM(((CreateSessionTabTimeFragment) time).startTime));
            data.put("duration", ((CreateSessionTabTimeFragment) time).duration);
            data.put("date_type", ((CreateSessionTabTimeFragment) time).dateType);

            if (((CreateSessionTabTimeFragment) time).dateType.equals("specific")) {
                data.put("date", ((CreateSessionTabTimeFragment) time).specifiedDate);
            } else {
                data.put("week_days", ((CreateSessionTabTimeFragment) time).patternDaysAdapter.getIds());
                data.put("repeat_status", ((CreateSessionTabTimeFragment) time).patternType);

                if (((CreateSessionTabTimeFragment) time).patternType.equals("weeks")) {
                    data.put("repeat", ((CreateSessionTabTimeFragment) time).repeatWeeks);
                } else {
                    data.put("repeat_from", ((CreateSessionTabTimeFragment) time).periodStartDate);
                    data.put("repeat_to", ((CreateSessionTabTimeFragment) time).periodEndDate);
                }
            }
        }

        // Session Data
        if (session instanceof CreateSessionTabSessionFragment)
            ((CreateSessionTabSessionFragment) session).setHashmap(data);

        // Platform Data
        if (platform instanceof CreateSessionTabPlatformFragment)
            ((CreateSessionTabPlatformFragment) platform).setHashmap(data);

        // Payment Data
        if (payment instanceof CreateSessionTabPaymentFragment)
            ((CreateSessionTabPaymentFragment) payment).setHashmap(data);

        Session.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackCreatedNewSession));

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

                                            // Time Data
                                            case "time":
                                                if (time instanceof CreateSessionTabTimeFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView, validation);
                                                break;
                                            case "duration":
                                                if (time instanceof CreateSessionTabTimeFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabTimeFragment) time).binding.durationErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.durationErrorLayout.errorTextView, validation);
                                                break;
                                            case "date_type":
                                                if (time instanceof CreateSessionTabTimeFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabTimeFragment) time).binding.dateTypeErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.dateTypeErrorLayout.errorTextView, validation);
                                                break;
                                            case "date":
                                                if (time instanceof CreateSessionTabTimeFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabTimeFragment) time).binding.specifiedDateErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.specifiedDateErrorLayout.errorTextView, validation);
                                                break;
                                            case "week_days":
                                                if (time instanceof CreateSessionTabTimeFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabTimeFragment) time).binding.patternDaysErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.patternDaysErrorLayout.errorTextView, validation);
                                                break;
                                            case "repeat_status":
                                                if (time instanceof CreateSessionTabTimeFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabTimeFragment) time).binding.patternTypeErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.patternTypeErrorLayout.errorTextView, validation);
                                                break;
                                            case "repeat":
                                                if (time instanceof CreateSessionTabTimeFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabTimeFragment) time).binding.repeatWeeksErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.repeatWeeksErrorLayout.errorTextView, validation);
                                                break;
                                            case "repeat_from":
                                                if (time instanceof CreateSessionTabTimeFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabTimeFragment) time).binding.periodStartDateErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.periodStartDateErrorLayout.errorTextView, validation);
                                                break;
                                            case "repeat_to":
                                                if (time instanceof CreateSessionTabTimeFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabTimeFragment) time).binding.periodEndDateErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.periodEndDateErrorLayout.errorTextView, validation);
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
                                                if (session instanceof CreateSessionTabSessionFragment)
                                                    ((CreateSessionTabSessionFragment) session).showValid(key, validation);

                                                break;

                                            // Platform Data
                                            case "platforms":
                                            case "pin_platform":
                                            case "identifier_platform":
                                                if (platform instanceof CreateSessionTabPlatformFragment)
                                                    ((CreateSessionTabPlatformFragment) platform).showValid(key, validation);

                                                break;

                                            // Payment Data
                                            case "payment_status":
                                                if (payment instanceof CreateSessionTabPaymentFragment)
                                                    ((CreateSessionTabPaymentFragment) payment).showValid(key, validation);

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