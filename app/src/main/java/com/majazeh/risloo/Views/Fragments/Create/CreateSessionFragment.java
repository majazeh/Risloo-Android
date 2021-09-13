package com.majazeh.risloo.Views.Fragments.Create;

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
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Tab.CreateSessionAdapter;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabTimeFragment;
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
            if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CaseModel"))
                setData((CaseModel) typeModel);
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
        if (session instanceof CreateSessionTabSessionFragment) {
            if (((CreateSessionTabSessionFragment) session).binding.statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabSessionFragment) session).binding.statusErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.statusErrorLayout.errorTextView);

            if (((CreateSessionTabSessionFragment) session).binding.startTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabSessionFragment) session).binding.startTypeErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.startTypeErrorLayout.errorTextView);
            if (((CreateSessionTabSessionFragment) session).binding.startRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabSessionFragment) session).binding.startRelativeErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.startRelativeErrorLayout.errorTextView);
            if (((CreateSessionTabSessionFragment) session).binding.startAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabSessionFragment) session).binding.startAccurateErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.startAccurateErrorLayout.errorTextView);
            if (((CreateSessionTabSessionFragment) session).binding.endTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabSessionFragment) session).binding.endTypeErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.endTypeErrorLayout.errorTextView);
            if (((CreateSessionTabSessionFragment) session).binding.endRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabSessionFragment) session).binding.endRelativeErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.endRelativeErrorLayout.errorTextView);
            if (((CreateSessionTabSessionFragment) session).binding.endAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabSessionFragment) session).binding.endAccurateErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.endAccurateErrorLayout.errorTextView);

            if (((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.errorTextView);
            if (((CreateSessionTabSessionFragment) session).binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabSessionFragment) session).binding.descriptionErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.descriptionErrorLayout.errorTextView);
            if (((CreateSessionTabSessionFragment) session).binding.coordinationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabSessionFragment) session).binding.coordinationErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.coordinationErrorLayout.errorTextView);
        }

        // Platform Data
        if (platform instanceof CreateSessionTabPlatformFragment) {
            if (((CreateSessionTabPlatformFragment) platform).binding.platformsErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabPlatformFragment) platform).binding.platformsErrorLayout.getRoot(), ((CreateSessionTabPlatformFragment) platform).binding.platformsErrorLayout.errorTextView);
            if (((CreateSessionTabPlatformFragment) platform).binding.pinPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabPlatformFragment) platform).binding.pinPlatformErrorLayout.getRoot(), ((CreateSessionTabPlatformFragment) platform).binding.pinPlatformErrorLayout.errorTextView);
            if (((CreateSessionTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.getRoot(), ((CreateSessionTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.errorTextView);
        }

        // Payment Data
        if (payment instanceof CreateSessionTabPaymentFragment) {
            if (((CreateSessionTabPaymentFragment) payment).binding.paymentErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(((CreateSessionTabPaymentFragment) payment).binding.paymentErrorLayout.getRoot(), ((CreateSessionTabPaymentFragment) payment).binding.paymentErrorLayout.errorTextView);
        }

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
        if (session instanceof CreateSessionTabSessionFragment) {
            data.put("status", SelectionManager.getSessionStatus(requireActivity(), "en", ((CreateSessionTabSessionFragment) session).status));

            if (data.get("status").equals("registration_awaiting")) {
                data.put("opens_at_type", ((CreateSessionTabSessionFragment) session).startType);

                if (data.get("opens_at_type").equals("relative"))
                    data.put("opens_at", DateManager.relativeTimestamp(((CreateSessionTabSessionFragment) session).startRelativeDay, ((CreateSessionTabSessionFragment) session).startRelativeHour, ((CreateSessionTabSessionFragment) session).startRelativeMinute));
                else
                    data.put("opens_at", DateManager.accurateTimestamp(((CreateSessionTabSessionFragment) session).startAccurateTime, ((CreateSessionTabSessionFragment) session).startAccurateDate));

                if (((CreateSessionTabSessionFragment) session).endAvailable) {
                    data.put("closed_at_type", ((CreateSessionTabSessionFragment) session).endType);

                    if (data.get("closed_at_type").equals("relative"))
                        data.put("closed_at", DateManager.relativeTimestamp(((CreateSessionTabSessionFragment) session).endRelativeDay, ((CreateSessionTabSessionFragment) session).endRelativeHour, ((CreateSessionTabSessionFragment) session).endRelativeMinute));
                    else
                        data.put("closed_at", DateManager.accurateTimestamp(((CreateSessionTabSessionFragment) session).endAccurateTime, ((CreateSessionTabSessionFragment) session).endAccurateDate));
                }
            }

            data.put("fields", ((CreateSessionTabSessionFragment) session).axisesAdapter.getIds());
            data.put("description", ((CreateSessionTabSessionFragment) session).description);
            data.put("client_reminder", ((CreateSessionTabSessionFragment) session).coordination);
        }

        // Platform Data
        if (platform instanceof CreateSessionTabPlatformFragment) {
            data.put("platforms", ((CreateSessionTabPlatformFragment) platform).adapter.platforms);
            data.put("pin_platform", ((CreateSessionTabPlatformFragment) platform).adapter.pinPlatform);
            data.put("identifier_platform", ((CreateSessionTabPlatformFragment) platform).adapter.identifierPlatform);
        }

        // Payment Data
        if (payment instanceof CreateSessionTabPaymentFragment) {
            data.put("payment_status", SelectionManager.getPaymentStatus(requireActivity(), "en", ((CreateSessionTabPaymentFragment) payment).payment));
            data.put("amounts", ((CreateSessionTabPaymentFragment) payment).createAxisAdapter.getAmounts());
        }

        Session.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastNewSessionAdded));

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
                                                if (session instanceof CreateSessionTabSessionFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabSessionFragment) session).binding.statusErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.statusErrorLayout.errorTextView, validation);
                                                break;

                                            case "opens_at_type":
                                                if (session instanceof CreateSessionTabSessionFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabSessionFragment) session).binding.startTypeErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.startTypeErrorLayout.errorTextView, validation);
                                                break;
                                            case "opens_at":
                                                if (session instanceof CreateSessionTabSessionFragment && data.get("opens_at_type").equals("relative"))
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabSessionFragment) session).binding.startRelativeErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.startRelativeErrorLayout.errorTextView, validation);
                                                else if (session instanceof CreateSessionTabSessionFragment && data.get("opens_at_type").equals("absolute"))
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabSessionFragment) session).binding.startAccurateErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.startAccurateErrorLayout.errorTextView, validation);
                                                break;

                                            case "closed_at_type":
                                                if (session instanceof CreateSessionTabSessionFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabSessionFragment) session).binding.endTypeErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.endTypeErrorLayout.errorTextView, validation);
                                                break;
                                            case "closed_at":
                                                if (session instanceof CreateSessionTabSessionFragment && data.get("closed_at_type").equals("relative"))
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabSessionFragment) session).binding.endRelativeErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.endRelativeErrorLayout.errorTextView, validation);
                                                else if (session instanceof CreateSessionTabSessionFragment && data.get("closed_at_type").equals("absolute"))
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabSessionFragment) session).binding.endAccurateErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.endAccurateErrorLayout.errorTextView, validation);
                                                    break;

                                            case "fields":
                                                if (session instanceof CreateSessionTabSessionFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.errorTextView, validation);
                                                break;
                                            case "description":
                                                if (session instanceof CreateSessionTabSessionFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabSessionFragment) session).binding.descriptionErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.descriptionErrorLayout.errorTextView, validation);
                                                break;
                                            case "client_reminder":
                                                if (session instanceof CreateSessionTabSessionFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabSessionFragment) session).binding.coordinationErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.coordinationErrorLayout.errorTextView, validation);
                                                break;

                                            // Platform Data
                                            case "platforms":
                                                if (platform instanceof CreateSessionTabPlatformFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabPlatformFragment) platform).binding.platformsErrorLayout.getRoot(), ((CreateSessionTabPlatformFragment) platform).binding.platformsErrorLayout.errorTextView, validation);
                                                break;
                                            case "pin_platform":
                                                if (platform instanceof CreateSessionTabPlatformFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabPlatformFragment) platform).binding.pinPlatformErrorLayout.getRoot(), ((CreateSessionTabPlatformFragment) platform).binding.pinPlatformErrorLayout.errorTextView, validation);
                                                break;
                                            case "identifier_platform":
                                                if (platform instanceof CreateSessionTabPlatformFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.getRoot(), ((CreateSessionTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.errorTextView, validation);
                                                break;

                                            // Payment Data
                                            case "payment_status":
                                                if (payment instanceof CreateSessionTabPaymentFragment)
                                                    ((MainActivity) requireActivity()).validatoon.showValid(((CreateSessionTabPaymentFragment) payment).binding.paymentErrorLayout.getRoot(), ((CreateSessionTabPaymentFragment) payment).binding.paymentErrorLayout.errorTextView, validation);
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