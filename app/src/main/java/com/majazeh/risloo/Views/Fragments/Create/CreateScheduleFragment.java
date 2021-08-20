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
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Tab.CreateScheduleAdapter;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabPlatformFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabTimeFragment;
import com.majazeh.risloo.databinding.FragmentCreateScheduleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class CreateScheduleFragment extends Fragment {

    // Binding
    public FragmentCreateScheduleBinding binding;

    // Adapters
    public CreateScheduleAdapter adapter;

    // Models
    public RoomModel roomModel;

    // Fragments
    private Fragment time, reference, session, platform, payment;

    // Objects
    private TabLayoutMediator tabLayoutMediator;
    private HashMap data, header;

    // Vars
    private String[] tabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new CreateScheduleAdapter(requireActivity());

        tabs = getResources().getStringArray(R.array.CreateScheduleTabs);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
    }

    private void setArgs() {
        String type = CreateScheduleFragmentArgs.fromBundle(getArguments()).getType();
        TypeModel typeModel = CreateScheduleFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (type.equals("room")) {
                roomModel = (RoomModel) CreateScheduleFragmentArgs.fromBundle(getArguments()).getTypeModel();
                setData(roomModel);
            }
        }
    }

    private void setData(RoomModel model) {
        if (model.getRoomId() != null && !model.getRoomId().equals("")) {
            data.put("id", model.getRoomId());
        }

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());

        tabLayoutMediator.attach();
    }

    public void checkRequire() {
        time = ((MainActivity) requireActivity()).fragmont.getTime();
        reference = ((MainActivity) requireActivity()).fragmont.getReference();
        session = ((MainActivity) requireActivity()).fragmont.getSession();
        platform = ((MainActivity) requireActivity()).fragmont.getPlatform();
        payment = ((MainActivity) requireActivity()).fragmont.getPayment();

        // Time Data
        if (time instanceof CreateScheduleTabTimeFragment) {
            if (((CreateScheduleTabTimeFragment) time).binding.startTimeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView);
            if (((CreateScheduleTabTimeFragment) time).binding.durationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabTimeFragment) time).binding.durationErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.durationErrorLayout.errorTextView);
            if (((CreateScheduleTabTimeFragment) time).binding.dateTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabTimeFragment) time).binding.dateTypeErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.dateTypeErrorLayout.errorTextView);
            if (((CreateScheduleTabTimeFragment) time).binding.specifiedDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabTimeFragment) time).binding.specifiedDateErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.specifiedDateErrorLayout.errorTextView);
            if (((CreateScheduleTabTimeFragment) time).binding.patternDaysErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabTimeFragment) time).binding.patternDaysErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.patternDaysErrorLayout.errorTextView);
            if (((CreateScheduleTabTimeFragment) time).binding.patternTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabTimeFragment) time).binding.patternTypeErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.patternTypeErrorLayout.errorTextView);
            if (((CreateScheduleTabTimeFragment) time).binding.repeatWeeksErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabTimeFragment) time).binding.repeatWeeksErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.repeatWeeksErrorLayout.errorTextView);
            if (((CreateScheduleTabTimeFragment) time).binding.periodStartDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabTimeFragment) time).binding.periodStartDateErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.periodStartDateErrorLayout.errorTextView);
            if (((CreateScheduleTabTimeFragment) time).binding.periodEndDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabTimeFragment) time).binding.periodEndDateErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.periodEndDateErrorLayout.errorTextView);
        }

        // Reference Data
        if (reference instanceof CreateScheduleTabReferenceFragment) {
            if (((CreateScheduleTabReferenceFragment) reference).binding.selectionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabReferenceFragment) reference).binding.selectionErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.selectionErrorLayout.errorTextView);
            if (((CreateScheduleTabReferenceFragment) reference).binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabReferenceFragment) reference).binding.typeErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.typeErrorLayout.errorTextView);
            if (((CreateScheduleTabReferenceFragment) reference).binding.caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.caseErrorLayout.errorTextView);
            if (((CreateScheduleTabReferenceFragment) reference).binding.bulkSessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabReferenceFragment) reference).binding.bulkSessionErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.bulkSessionErrorLayout.errorTextView);
            if (((CreateScheduleTabReferenceFragment) reference).binding.countErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabReferenceFragment) reference).binding.countErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.countErrorLayout.errorTextView);
        }

        // Session Data
        if (session instanceof CreateScheduleTabSessionFragment) {
            if (((CreateScheduleTabSessionFragment) session).binding.statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabSessionFragment) session).binding.statusErrorLayout.getRoot(), ((CreateScheduleTabSessionFragment) session).binding.statusErrorLayout.errorTextView);
            if (((CreateScheduleTabSessionFragment) session).binding.axisErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateScheduleTabSessionFragment) session).binding.axisErrorLayout.errorTextView);
            if (((CreateScheduleTabSessionFragment) session).binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabSessionFragment) session).binding.descriptionErrorLayout.getRoot(), ((CreateScheduleTabSessionFragment) session).binding.descriptionErrorLayout.errorTextView);
            if (((CreateScheduleTabSessionFragment) session).binding.coordinationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabSessionFragment) session).binding.coordinationErrorLayout.getRoot(), ((CreateScheduleTabSessionFragment) session).binding.coordinationErrorLayout.errorTextView);
        }

        // Platform Data
        if (platform instanceof CreateScheduleTabPlatformFragment) {
            if (((CreateScheduleTabPlatformFragment) platform).binding.platformsErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabPlatformFragment) platform).binding.platformsErrorLayout.getRoot(), ((CreateScheduleTabPlatformFragment) platform).binding.platformsErrorLayout.errorTextView);
            if (((CreateScheduleTabPlatformFragment) platform).binding.pinPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabPlatformFragment) platform).binding.pinPlatformErrorLayout.getRoot(), ((CreateScheduleTabPlatformFragment) platform).binding.pinPlatformErrorLayout.errorTextView);
            if (((CreateScheduleTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.getRoot(), ((CreateScheduleTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.errorTextView);
        }

        // Payment Data
        if (payment instanceof CreateScheduleTabPaymentFragment) {
            if (((CreateScheduleTabPaymentFragment) payment).binding.paymentErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(((CreateScheduleTabPaymentFragment) payment).binding.paymentErrorLayout.getRoot(), ((CreateScheduleTabPaymentFragment) payment).binding.paymentErrorLayout.errorTextView);
        }

        doWork();
    }

    public void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        // Time Data
        if (time instanceof CreateScheduleTabTimeFragment) {
            data.put("time", DateManager.jalHHsMM(((CreateScheduleTabTimeFragment) time).startTime));
            data.put("duration", ((CreateScheduleTabTimeFragment) time).duration);
            data.put("date_type", ((CreateScheduleTabTimeFragment) time).dateType);

            if (((CreateScheduleTabTimeFragment) time).dateType.equals("specific")) {
                data.put("date", ((CreateScheduleTabTimeFragment) time).specifiedDate);
            } else {
                data.put("week_days", ((CreateScheduleTabTimeFragment) time).patternDaysAdapter.getIds());
                data.put("repeat_status", ((CreateScheduleTabTimeFragment) time).patternType);

                if (((CreateScheduleTabTimeFragment) time).patternType.equals("weeks")) {
                    data.put("repeat", ((CreateScheduleTabTimeFragment) time).repeatWeeks);
                } else {
                    data.put("repeat_from", ((CreateScheduleTabTimeFragment) time).periodStartDate);
                    data.put("repeat_to", ((CreateScheduleTabTimeFragment) time).periodEndDate);
                }
            }
        }

        // Reference Data
        if (reference instanceof CreateScheduleTabReferenceFragment) {
            data.put("selection_type", SelectionManager.getSelectionType(requireActivity(), "en", ((CreateScheduleTabReferenceFragment) reference).selection));

            if (((CreateScheduleTabReferenceFragment) reference).type.equals("اعضاء ریسلو")) {
                data.put("clients_type", "risloo");
            } else if (((CreateScheduleTabReferenceFragment) reference).type.contains("مرکز")) {
                data.put("clients_type", "center");
            } else if (((CreateScheduleTabReferenceFragment) reference).type.contains("اتاق درمان")) {
                data.put("clients_type", "room");
            } else if (((CreateScheduleTabReferenceFragment) reference).type.equals("اعضاء پرونده درمانی …")) {
                data.put("clients_type", "case");
                data.put("case_id", ((CreateScheduleTabReferenceFragment) reference).caseId);
            } else if (((CreateScheduleTabReferenceFragment) reference).type.equals("ساخت پرونده جدید")) {
                data.put("clients_type", "new_case");
            }

            data.put("group_session", ((CreateScheduleTabReferenceFragment) reference).groupSession);
            if (((CreateScheduleTabReferenceFragment) reference).groupSession.equals("on")) {
                data.put("clients_number", ((CreateScheduleTabReferenceFragment) reference).count);
            }
        }

        // Session Data
        if (session instanceof CreateScheduleTabSessionFragment) {
            data.put("status", SelectionManager.getSessionStatus(requireActivity(), "en", ((CreateScheduleTabSessionFragment) session).status));
            data.put("fields", ((CreateScheduleTabSessionFragment) session).axisesAdapter.getIds());
            data.put("description", ((CreateScheduleTabSessionFragment) session).description);
            data.put("client_reminder", ((CreateScheduleTabSessionFragment) session).coordination);
        }

        // Platform Data
        if (platform instanceof CreateScheduleTabPlatformFragment) {
            data.put("platforms", ((CreateScheduleTabPlatformFragment) platform).adapter.platforms);
            data.put("pin_platform", ((CreateScheduleTabPlatformFragment) platform).adapter.pinPlatform);
            data.put("identifier_platform", ((CreateScheduleTabPlatformFragment) platform).adapter.identifierPlatform);
        }

        // Payment Data
        if (payment instanceof CreateScheduleTabPaymentFragment) {
            data.put("payment_status", SelectionManager.getPaymentStatus(requireActivity(), "en", ((CreateScheduleTabPaymentFragment) payment).payment));
            data.put("amounts", ((CreateScheduleTabPaymentFragment) payment).axisAdapter.getAmounts());
        }

        Room.createSchedule(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        ToastManager.showSuccesToast(requireActivity(), getResources().getString(R.string.ToastNewScheduleAdded));

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
                                                if (time instanceof CreateScheduleTabTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView, validation);
                                                break;
                                            case "duration":
                                                if (time instanceof CreateScheduleTabTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabTimeFragment) time).binding.durationErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.durationErrorLayout.errorTextView, validation);
                                                break;
                                            case "date_type":
                                                if (time instanceof CreateScheduleTabTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabTimeFragment) time).binding.dateTypeErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.dateTypeErrorLayout.errorTextView, validation);
                                                break;
                                            case "date":
                                                if (time instanceof CreateScheduleTabTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabTimeFragment) time).binding.specifiedDateErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.specifiedDateErrorLayout.errorTextView, validation);
                                                break;
                                            case "week_days":
                                                if (time instanceof CreateScheduleTabTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabTimeFragment) time).binding.patternDaysErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.patternDaysErrorLayout.errorTextView, validation);
                                                break;
                                            case "repeat_status":
                                                if (time instanceof CreateScheduleTabTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabTimeFragment) time).binding.patternTypeErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.patternTypeErrorLayout.errorTextView, validation);
                                                break;
                                            case "repeat":
                                                if (time instanceof CreateScheduleTabTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabTimeFragment) time).binding.repeatWeeksErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.repeatWeeksErrorLayout.errorTextView, validation);
                                                break;
                                            case "repeat_from":
                                                if (time instanceof CreateScheduleTabTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabTimeFragment) time).binding.periodStartDateErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.periodStartDateErrorLayout.errorTextView, validation);
                                                break;
                                            case "repeat_to":
                                                if (time instanceof CreateScheduleTabTimeFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabTimeFragment) time).binding.periodEndDateErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.periodEndDateErrorLayout.errorTextView, validation);
                                                break;

                                            // Reference Data
                                            case "selection_type":
                                                if (reference instanceof CreateScheduleTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabReferenceFragment) reference).binding.selectionErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.selectionErrorLayout.errorTextView, validation);
                                                break;
                                            case "clients_type":
                                                if (reference instanceof CreateScheduleTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabReferenceFragment) reference).binding.typeErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.typeErrorLayout.errorTextView, validation);
                                                break;
                                            case "case_id":
                                                if (reference instanceof CreateScheduleTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.caseErrorLayout.errorTextView, validation);
                                                break;
                                            case "group_session":
                                                if (reference instanceof CreateScheduleTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabReferenceFragment) reference).binding.bulkSessionErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.bulkSessionErrorLayout.errorTextView, validation);
                                                break;
                                            case "clients_number":
                                                if (reference instanceof CreateScheduleTabReferenceFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabReferenceFragment) reference).binding.countErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.countErrorLayout.errorTextView, validation);
                                                break;

                                            // Session Data
                                            case "status":
                                                if (session instanceof CreateScheduleTabSessionFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabSessionFragment) session).binding.statusErrorLayout.getRoot(), ((CreateScheduleTabSessionFragment) session).binding.statusErrorLayout.errorTextView, validation);
                                                break;
                                            case "fields":
                                                if (session instanceof CreateScheduleTabSessionFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateScheduleTabSessionFragment) session).binding.axisErrorLayout.errorTextView, validation);
                                                break;
                                            case "description":
                                                if (session instanceof CreateScheduleTabSessionFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabSessionFragment) session).binding.descriptionErrorLayout.getRoot(), ((CreateScheduleTabSessionFragment) session).binding.descriptionErrorLayout.errorTextView, validation);
                                                break;
                                            case "client_reminder":
                                                if (session instanceof CreateScheduleTabSessionFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabSessionFragment) session).binding.coordinationErrorLayout.getRoot(), ((CreateScheduleTabSessionFragment) session).binding.coordinationErrorLayout.errorTextView, validation);
                                                break;

                                            // Platform Data
                                            case "platforms":
                                                if (platform instanceof CreateScheduleTabPlatformFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabPlatformFragment) platform).binding.platformsErrorLayout.getRoot(), ((CreateScheduleTabPlatformFragment) platform).binding.platformsErrorLayout.errorTextView, validation);
                                                break;
                                            case "pin_platform":
                                                if (platform instanceof CreateScheduleTabPlatformFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabPlatformFragment) platform).binding.pinPlatformErrorLayout.getRoot(), ((CreateScheduleTabPlatformFragment) platform).binding.pinPlatformErrorLayout.errorTextView, validation);
                                                break;
                                            case "identifier_platform":
                                                if (platform instanceof CreateScheduleTabPlatformFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.getRoot(), ((CreateScheduleTabPlatformFragment) platform).binding.identifierPlatformErrorLayout.errorTextView, validation);
                                                break;

                                            // Payment Data
                                            case "payment_status":
                                                if (payment instanceof CreateScheduleTabPaymentFragment)
                                                    ((MainActivity) requireActivity()).controlEditText.error(((CreateScheduleTabPaymentFragment) payment).binding.paymentErrorLayout.getRoot(), ((CreateScheduleTabPaymentFragment) payment).binding.paymentErrorLayout.errorTextView, validation);
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