package com.majazeh.risloo.Views.Fragments.Create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Tab.CreateScheduleAdapter;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSchedulePaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTimeFragment;
import com.majazeh.risloo.databinding.FragmentCreateScheduleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
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

    // Objects
    private TabLayoutMediator tabLayoutMediator;

    // Fragments
    private Fragment time, reference, session, platform, payment;

    // Vars
    private String[] tabs;
    private HashMap data, header;
    public RoomModel roomModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
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

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());
        tabLayoutMediator.attach();
    }

    private void setData(RoomModel model) {
        if (model.getRoomId() != null && !model.getRoomId().equals("")) {
            data.put("id", model.getRoomId());
        }
    }

    public void checkRequire() {
        time = ((MainActivity) requireActivity()).fragmont.getTime();
        reference = ((MainActivity) requireActivity()).fragmont.getReference();
        session = ((MainActivity) requireActivity()).fragmont.getSession();
        platform = ((MainActivity) requireActivity()).fragmont.getPlatform();
        payment = ((MainActivity) requireActivity()).fragmont.getPayment();

        if (time instanceof CreateScheduleTimeFragment && session instanceof CreateScheduleSessionFragment && reference instanceof CreateScheduleReferenceFragment) {
            if (((CreateScheduleTimeFragment) time).startTime.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateScheduleTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateScheduleTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateScheduleTimeFragment) time).binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((CreateScheduleTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateScheduleTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateScheduleTimeFragment) time).binding.startTimeErrorLayout.errorTextView);

            if (((CreateScheduleSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView.getChildCount() == 0)
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateScheduleSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateScheduleSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateScheduleSessionFragment) session).binding.axisErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((CreateScheduleSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateScheduleSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateScheduleSessionFragment) session).binding.axisErrorLayout.errorTextView);

            if (((CreateScheduleReferenceFragment) reference).type.equals("case") && ((CreateScheduleReferenceFragment) reference).caseId.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateScheduleReferenceFragment) reference).binding.caseIncludeLayout.selectContainer, ((CreateScheduleReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((CreateScheduleReferenceFragment) reference).binding.caseErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((CreateScheduleReferenceFragment) reference).binding.caseIncludeLayout.selectContainer, ((CreateScheduleReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((CreateScheduleReferenceFragment) reference).binding.caseErrorLayout.errorTextView);

            if (((CreateScheduleReferenceFragment) reference).type.equals("case")) {
                if (!((CreateScheduleTimeFragment) time).startTime.equals("") && ((CreateScheduleSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView.getChildCount() != 0 && !((CreateScheduleReferenceFragment) reference).caseId.equals(""))
                    doWork();
            } else {
                if (!((CreateScheduleTimeFragment) time).startTime.equals("") && ((CreateScheduleSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView.getChildCount() != 0)
                    doWork();
            }
        }
    }

    public void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        // Time Data
        if (time instanceof CreateScheduleTimeFragment) {
            data.put("time", DateManager.jalHHsMM(((CreateScheduleTimeFragment) time).startTime));
            data.put("duration", ((CreateScheduleTimeFragment) time).duration);
            data.put("date_type", ((CreateScheduleTimeFragment) time).dateType);

            if (data.get("date_type").equals("specific")) {
                data.put("date", ((CreateScheduleTimeFragment) time).specifiedDate);
            } else {
                data.put("week_days", ((CreateScheduleTimeFragment) time).patternDaysAdapter.getIds());
                data.put("repeat_status", ((CreateScheduleTimeFragment) time).patternType);

                if (data.get("repeat_status").equals("weeks")) {
                    data.put("repeat", ((CreateScheduleTimeFragment) time).repeatWeeks);
                } else {
                    data.put("repeat_from", ((CreateScheduleTimeFragment) time).periodStartDate);
                    data.put("repeat_to", ((CreateScheduleTimeFragment) time).periodEndDate);
                }
            }
        }

        // Reference Data
        if (reference instanceof CreateScheduleReferenceFragment) {
            data.put("selection_type", SelectionManager.getSelectionType(requireActivity(), "en", ((CreateScheduleReferenceFragment) reference).selection));

            if (((CreateScheduleReferenceFragment) reference).type.equals("اعضاء ریسلو")) {
                data.put("clients_type", "risloo");
            } else if (((CreateScheduleReferenceFragment) reference).type.contains("مرکز")) {
                data.put("clients_type", "center");
            } else if (((CreateScheduleReferenceFragment) reference).type.contains("اتاق درمان")) {
                data.put("clients_type", "room");
            } else if (((CreateScheduleReferenceFragment) reference).type.equals("اعضاء پرونده درمانی …")) {
                data.put("clients_type", "case");
            } else if (((CreateScheduleReferenceFragment) reference).type.equals("ساخت پرونده جدید")) {
                data.put("clients_type", "new_case");
            }

            if (data.get("clients_type").equals("case")) {
                data.put("case_id", ((CreateScheduleReferenceFragment) reference).caseId);
            }

            data.put("group_session", ((CreateScheduleReferenceFragment) reference).groupSession);
            if (data.get("group_session").equals("on")) {
                data.put("clients_number", ((CreateScheduleReferenceFragment) reference).count);
            }
        }

        // Session Data
        if (session instanceof CreateScheduleSessionFragment) {
            data.put("status", SelectionManager.getSessionStatus(requireActivity(), "en", ((CreateScheduleSessionFragment) session).status));
            data.put("fields", ((CreateScheduleSessionFragment) session).axisesAdapter.getIds());
            data.put("description", ((CreateScheduleSessionFragment) session).description);
            data.put("client_reminder", ((CreateScheduleSessionFragment) session).coordination);
        }

        // Payment Data
        if (payment instanceof CreateSchedulePaymentFragment) {
            data.put("payment_status", SelectionManager.getPaymentStatus(requireActivity(), "en", ((CreateSchedulePaymentFragment) payment).payment));
            data.put("amounts", ((CreateSchedulePaymentFragment) payment).axisAdapter.getAmounts());
        }

        Center.createSchedule(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.isNull("errors")) {
                                Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                                        if (time instanceof CreateScheduleTimeFragment && session instanceof CreateScheduleSessionFragment && reference instanceof CreateScheduleReferenceFragment) {
                                            switch (key) {
                                                case "time":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateScheduleTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateScheduleTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateScheduleTimeFragment) time).binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                                                    break;
                                                case "fields":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateScheduleSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateScheduleSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateScheduleSessionFragment) session).binding.axisErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                                                    break;
                                                case "case_id":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateScheduleReferenceFragment) reference).binding.caseIncludeLayout.selectContainer, ((CreateScheduleReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((CreateScheduleReferenceFragment) reference).binding.caseErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                                                    break;
                                            }
                                        }
                                    }
                                }
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