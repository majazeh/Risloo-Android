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
import com.majazeh.risloo.views.adapters.tab.AdapterCreateSchedule;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabPayment;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabPlatform;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabReference;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabTime;
import com.majazeh.risloo.databinding.FragmentCreateScheduleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class FragmentCreateSchedule extends Fragment {

    // Binding
    public FragmentCreateScheduleBinding binding;

    // Adapters
    public AdapterCreateSchedule adapter;

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
        adapter = new AdapterCreateSchedule(requireActivity());

        tabs = getResources().getStringArray(R.array.CreateScheduleTabs);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());
    }

    private void setArgs() {
        TypeModel typeModel = FragmentCreateScheduleArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.substring(typeModel.getClass().getName(), '.').equals("RoomModel")) {
                roomModel = (RoomModel) typeModel;
                setData(roomModel);
            }
        }
    }

    private void setData(RoomModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());

        tabLayoutMediator.attach();
    }

    public void checkRequire() {
        time = ((ActivityMain) requireActivity()).fragmont.getTime();
        reference = ((ActivityMain) requireActivity()).fragmont.getReference();
        session = ((ActivityMain) requireActivity()).fragmont.getSession();
        platform = ((ActivityMain) requireActivity()).fragmont.getPlatform();
        payment = ((ActivityMain) requireActivity()).fragmont.getPayment();

        // Time Data
        if (time instanceof FragmentCreateScheduleTabTime)
            ((FragmentCreateScheduleTabTime) time).hideValid();

        // Reference Data
        if (reference instanceof FragmentCreateScheduleTabReference)
            ((FragmentCreateScheduleTabReference) reference).hideValid();

        // Session Data
        if (session instanceof FragmentCreateScheduleTabSession)
            ((FragmentCreateScheduleTabSession) session).hideValid();

        // Platform Data
        if (platform instanceof FragmentCreateScheduleTabPlatform)
            ((FragmentCreateScheduleTabPlatform) platform).hideValid();

        // Payment Data
        if (payment instanceof FragmentCreateScheduleTabPayment)
            ((FragmentCreateScheduleTabPayment) payment).hideValid();

        doWork();
    }

    private void setHashmap() {

        // Time Data
        if (time instanceof FragmentCreateScheduleTabTime)
            ((FragmentCreateScheduleTabTime) time).setHashmap(data);

        // Reference Data
        if (reference instanceof FragmentCreateScheduleTabReference)
            ((FragmentCreateScheduleTabReference) reference).setHashmap(data);

        // Session Data
        if (session instanceof FragmentCreateScheduleTabSession)
            ((FragmentCreateScheduleTabSession) session).setHashmap(data);

        // Platform Data
        if (platform instanceof FragmentCreateScheduleTabPlatform)
            ((FragmentCreateScheduleTabPlatform) platform).setHashmap(data);

        // Payment Data
        if (payment instanceof FragmentCreateScheduleTabPayment)
            ((FragmentCreateScheduleTabPayment) payment).setHashmap(data);
    }

    public void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Room.createSchedule(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                ScheduleModel scheduleModel = (ScheduleModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackCreatedNewSchedule));

                        ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentSession(scheduleModel);
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
                                            if (time instanceof FragmentCreateScheduleTabTime)
                                                ((FragmentCreateScheduleTabTime) time).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

                                            break;

                                        // Reference Data
                                        case "selection_type":
                                        case "clients_type":
                                        case "case_id":
                                        case "group_session":
                                        case "clients_number":
                                            if (reference instanceof FragmentCreateScheduleTabReference)
                                                ((FragmentCreateScheduleTabReference) reference).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

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
                                            if (session instanceof FragmentCreateScheduleTabSession)
                                                ((FragmentCreateScheduleTabSession) session).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

                                            break;

                                        // Platform Data
                                        case "platforms":
                                        case "pin_platform":
                                        case "identifier_platform":
                                            if (platform instanceof FragmentCreateScheduleTabPlatform)
                                                ((FragmentCreateScheduleTabPlatform) platform).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

                                            break;

                                        // Payment Data
                                        case "payment_status":
                                            if (payment instanceof FragmentCreateScheduleTabPayment)
                                                ((FragmentCreateScheduleTabPayment) payment).showValid(key, keyErrors.substring(0, keyErrors.length() - 1));

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