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
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Tab.CreateSessionAdapter;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTimeFragment;
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

        time = ((MainActivity) requireActivity()).fragmont.getTime();
        session = ((MainActivity) requireActivity()).fragmont.getSession();
        platform = ((MainActivity) requireActivity()).fragmont.getPlatform();
        payment = ((MainActivity) requireActivity()).fragmont.getPayment();

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
        tabLayoutMediator.attach();
    }

    private void setData(CaseModel model) {
        if (model.getCaseId() != null && !model.getCaseId().equals("")) {
            data.put("case_id", model.getCaseId());
            data.put("clients_type", "case");
        }
    }

    public void checkRequire() {
        if (time instanceof CreateSessionTimeFragment && session instanceof CreateSessionSessionFragment && payment instanceof CreateSessionPaymentFragment) {
            if (((CreateSessionTimeFragment) time).startTime.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateSessionTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateSessionTimeFragment) time).binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((CreateSessionTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateSessionTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateSessionTimeFragment) time).binding.startTimeErrorLayout.errorTextView);

            if (((CreateSessionSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView.getChildCount() == 0)
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateSessionSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateSessionSessionFragment) session).binding.axisErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), ((CreateSessionSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateSessionSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateSessionSessionFragment) session).binding.axisErrorLayout.errorTextView);

            if (!((CreateSessionTimeFragment) time).startTime.equals("") && ((CreateSessionSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView.getChildCount() != 0)
                doWork();
        }
    }

    public void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        // Time Data
        if (time instanceof CreateSessionTimeFragment) {
            data.put("time", ((CreateSessionTimeFragment) time).startTime);
            data.put("duration", ((CreateSessionTimeFragment) time).duration);
            data.put("date_type", ((CreateSessionTimeFragment) time).dateType);

            if (data.get("date_type").equals("specific")) {
                data.put("date", ((CreateSessionTimeFragment) time).specifiedDate);
            } else {
                data.put("week_days", ((CreateSessionTimeFragment) time).patternDaysAdapter.getIds());
                data.put("repeat_status", ((CreateSessionTimeFragment) time).patternType);

                if (data.get("repeat_status").equals("weeks")) {
                    data.put("repeat", ((CreateSessionTimeFragment) time).repeatWeeks);
                } else {
                    data.put("repeat_from", ((CreateSessionTimeFragment) time).periodStartDate);
                    data.put("repeat_to", ((CreateSessionTimeFragment) time).periodEndDate);
                }
            }
        }

        // Session Data
        if (session instanceof CreateSessionSessionFragment) {
            data.put("status", SelectionManager.getSessionStatus(requireActivity(), "en", ((CreateSessionSessionFragment) session).status));
            data.put("fields", ((CreateSessionSessionFragment) session).axisesAdapter.getIds());
            data.put("description", ((CreateSessionSessionFragment) session).description);
            data.put("client_reminder", ((CreateSessionSessionFragment) session).coordination);
        }

        // Payment Data
        if (payment instanceof CreateSessionPaymentFragment) {
            data.put("payment_status", SelectionManager.getPaymentStatus(requireActivity(), "en", ((CreateSessionPaymentFragment) payment).payment));
            data.put("amounts", ((CreateSessionPaymentFragment) payment).axisAdapter.getAmounts());
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
                                        if (time instanceof CreateSessionTimeFragment && session instanceof CreateSessionSessionFragment && payment instanceof CreateSessionPaymentFragment) {
                                            switch (key) {
                                                case "time":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateSessionTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateSessionTimeFragment) time).binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                                                    break;
                                                case "fields":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateSessionSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateSessionSessionFragment) session).binding.axisErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
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