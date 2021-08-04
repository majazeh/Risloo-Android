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
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabTimeFragment;
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

        if (time instanceof CreateScheduleTabTimeFragment && session instanceof CreateScheduleTabSessionFragment && reference instanceof CreateScheduleTabReferenceFragment) {
            if (((CreateScheduleTabTimeFragment) time).startTime.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateScheduleTabTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateScheduleTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((CreateScheduleTabTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateScheduleTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView);

            if (((CreateScheduleTabSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView.getChildCount() == 0)
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateScheduleTabSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateScheduleTabSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateScheduleTabSessionFragment) session).binding.axisErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((CreateScheduleTabSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateScheduleTabSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateScheduleTabSessionFragment) session).binding.axisErrorLayout.errorTextView);

            if (((CreateScheduleTabReferenceFragment) reference).type.equals("case") && ((CreateScheduleTabReferenceFragment) reference).caseId.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateScheduleTabReferenceFragment) reference).binding.caseIncludeLayout.selectContainer, ((CreateScheduleTabReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.caseErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((CreateScheduleTabReferenceFragment) reference).binding.caseIncludeLayout.selectContainer, ((CreateScheduleTabReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.caseErrorLayout.errorTextView);

            if (((CreateScheduleTabReferenceFragment) reference).type.equals("case")) {
                if (!((CreateScheduleTabTimeFragment) time).startTime.equals("") && ((CreateScheduleTabSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView.getChildCount() != 0 && !((CreateScheduleTabReferenceFragment) reference).caseId.equals(""))
                    doWork();
            } else {
                if (!((CreateScheduleTabTimeFragment) time).startTime.equals("") && ((CreateScheduleTabSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView.getChildCount() != 0)
                    doWork();
            }
        }
    }

    public void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        // Time Data
        if (time instanceof CreateScheduleTabTimeFragment) {
            data.put("time", DateManager.jalHHsMM(((CreateScheduleTabTimeFragment) time).startTime));
            data.put("duration", ((CreateScheduleTabTimeFragment) time).duration);
            data.put("date_type", ((CreateScheduleTabTimeFragment) time).dateType);

            if (data.get("date_type").equals("specific")) {
                data.put("date", ((CreateScheduleTabTimeFragment) time).specifiedDate);
            } else {
                data.put("week_days", ((CreateScheduleTabTimeFragment) time).patternDaysAdapter.getIds());
                data.put("repeat_status", ((CreateScheduleTabTimeFragment) time).patternType);

                if (data.get("repeat_status").equals("weeks")) {
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
            } else if (((CreateScheduleTabReferenceFragment) reference).type.equals("ساخت پرونده جدید")) {
                data.put("clients_type", "new_case");
            }

            if (data.get("clients_type").equals("case")) {
                data.put("case_id", ((CreateScheduleTabReferenceFragment) reference).caseId);
            }

            data.put("group_session", ((CreateScheduleTabReferenceFragment) reference).groupSession);
            if (data.get("group_session").equals("on")) {
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

        // Payment Data
        if (payment instanceof CreateScheduleTabPaymentFragment) {
            data.put("payment_status", SelectionManager.getPaymentStatus(requireActivity(), "en", ((CreateScheduleTabPaymentFragment) payment).payment));
            data.put("amounts", ((CreateScheduleTabPaymentFragment) payment).axisAdapter.getAmounts());
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
                                        if (time instanceof CreateScheduleTabTimeFragment && session instanceof CreateScheduleTabSessionFragment && reference instanceof CreateScheduleTabReferenceFragment) {
                                            switch (key) {
                                                case "time":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateScheduleTabTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateScheduleTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateScheduleTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                                                    break;
                                                case "fields":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateScheduleTabSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateScheduleTabSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateScheduleTabSessionFragment) session).binding.axisErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                                                    break;
                                                case "case_id":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateScheduleTabReferenceFragment) reference).binding.caseIncludeLayout.selectContainer, ((CreateScheduleTabReferenceFragment) reference).binding.caseErrorLayout.getRoot(), ((CreateScheduleTabReferenceFragment) reference).binding.caseErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
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