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
import com.majazeh.risloo.Views.Adapters.Tab.CreateSessionAdapter;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabPaymentFragment;
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

    // Objects
    private TabLayoutMediator tabLayoutMediator;

    // Fragments
    private Fragment time, session, platform, payment;

    // Vars
    private String[] tabs;
    private HashMap data, header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
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
        String type = CreateSessionFragmentArgs.fromBundle(getArguments()).getType();
        TypeModel typeModel = CreateSessionFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (type.equals("case")) {
                CaseModel caseModel = (CaseModel) CreateSessionFragmentArgs.fromBundle(getArguments()).getTypeModel();
                setData(caseModel);
            }
        }

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());
        tabLayoutMediator.attach();
    }

    private void setData(CaseModel model) {
        if (model.getCaseId() != null && !model.getCaseId().equals("")) {
            data.put("case_id", model.getCaseId());
            data.put("clients_type", "case");
        }
    }

    public void checkRequire() {
        time = ((MainActivity) requireActivity()).fragmont.getTime();
        session = ((MainActivity) requireActivity()).fragmont.getSession();
        platform = ((MainActivity) requireActivity()).fragmont.getPlatform();
        payment = ((MainActivity) requireActivity()).fragmont.getPayment();

        if (time instanceof CreateSessionTabTimeFragment && session instanceof CreateSessionTabSessionFragment) {
            if (((CreateSessionTabTimeFragment) time).startTime.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((CreateSessionTabTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView);

            if (((CreateSessionTabSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView.getChildCount() == 0)
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((CreateSessionTabSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.errorTextView);

            if (!((CreateSessionTabTimeFragment) time).startTime.equals("") && ((CreateSessionTabSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView.getChildCount() != 0)
                doWork();
        }
    }

    public void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        // Time Data
        if (time instanceof CreateSessionTabTimeFragment) {
            data.put("time", DateManager.jalHHsMM(((CreateSessionTabTimeFragment) time).startTime));
            data.put("duration", ((CreateSessionTabTimeFragment) time).duration);
            data.put("date_type", ((CreateSessionTabTimeFragment) time).dateType);

            if (data.get("date_type").equals("specific")) {
                data.put("date", ((CreateSessionTabTimeFragment) time).specifiedDate);
            } else {
                data.put("week_days", ((CreateSessionTabTimeFragment) time).patternDaysAdapter.getIds());
                data.put("repeat_status", ((CreateSessionTabTimeFragment) time).patternType);

                if (data.get("repeat_status").equals("weeks")) {
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
            data.put("fields", ((CreateSessionTabSessionFragment) session).axisesAdapter.getIds());
            data.put("description", ((CreateSessionTabSessionFragment) session).description);
            data.put("client_reminder", ((CreateSessionTabSessionFragment) session).coordination);
        }

        // Payment Data
        if (payment instanceof CreateSessionTabPaymentFragment) {
            data.put("payment_status", SelectionManager.getPaymentStatus(requireActivity(), "en", ((CreateSessionTabPaymentFragment) payment).payment));
            data.put("amounts", ((CreateSessionTabPaymentFragment) payment).axisAdapter.getAmounts());
        }

        Session.create(data, header, new Response() {
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
                                        if (time instanceof CreateSessionTabTimeFragment && session instanceof CreateSessionTabSessionFragment) {
                                            switch (key) {
                                                case "time":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                                                    break;
                                                case "fields":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
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