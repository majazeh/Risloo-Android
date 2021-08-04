package com.majazeh.risloo.Views.Fragments.Create;

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
        String type = CreateSessionFragmentArgs.fromBundle(getArguments()).getType();
        TypeModel typeModel = CreateSessionFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (type.equals("case")) {
                caseModel = (CaseModel) CreateSessionFragmentArgs.fromBundle(getArguments()).getTypeModel();
                setData(caseModel);
            }
        }
    }

    private void setData(CaseModel model) {
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
                        ToastManager.showToast(requireActivity(), getResources().getString(R.string.ToastNewSessionAdded));

                        ((MainActivity) requireActivity()).navController.navigateUp();
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

                                                // Time Data
                                                case "time":
                                                    if (time instanceof CreateSessionTabTimeFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabTimeFragment) time).binding.startTimeIncludeLayout.selectTextView, ((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.startTimeErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;
                                                case "duration":
                                                    if (time instanceof CreateSessionTabTimeFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabTimeFragment) time).binding.durationIncludeLayout.inputEditText, ((CreateSessionTabTimeFragment) time).binding.durationErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.durationErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;
                                                case "date_type":
                                                    if (time instanceof CreateSessionTabTimeFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, ((CreateSessionTabTimeFragment) time).binding.dateTypeErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.dateTypeErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;
                                                case "date":
                                                    if (time instanceof CreateSessionTabTimeFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabTimeFragment) time).binding.specifiedDateIncludeLayout.selectTextView, ((CreateSessionTabTimeFragment) time).binding.specifiedDateErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.specifiedDateErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;
                                                case "week_days":
                                                    if (time instanceof CreateSessionTabTimeFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabTimeFragment) time).binding.patternDaysIncludeLayout.selectRecyclerView, ((CreateSessionTabTimeFragment) time).binding.patternDaysErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.patternDaysErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;
                                                case "repeat_status":
                                                    if (time instanceof CreateSessionTabTimeFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, ((CreateSessionTabTimeFragment) time).binding.patternTypeErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.patternTypeErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;
                                                case "repeat":
                                                    if (time instanceof CreateSessionTabTimeFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabTimeFragment) time).binding.repeatWeeksIncludeLayout.inputEditText, ((CreateSessionTabTimeFragment) time).binding.repeatWeeksErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.repeatWeeksErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;
                                                case "repeat_from":
                                                    if (time instanceof CreateSessionTabTimeFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabTimeFragment) time).binding.periodStartDateIncludeLayout.selectTextView, ((CreateSessionTabTimeFragment) time).binding.periodStartDateErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.periodStartDateErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;
                                                case "repeat_to":
                                                    if (time instanceof CreateSessionTabTimeFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabTimeFragment) time).binding.periodEndDateIncludeLayout.selectTextView, ((CreateSessionTabTimeFragment) time).binding.periodEndDateErrorLayout.getRoot(), ((CreateSessionTabTimeFragment) time).binding.periodEndDateErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;

                                                // Session Data
                                                case "status":
                                                    if (session instanceof CreateSessionTabSessionFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabSessionFragment) session).binding.statusIncludeLayout.selectSpinner, ((CreateSessionTabSessionFragment) session).binding.statusErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.statusErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;
                                                case "fields":
                                                    if (session instanceof CreateSessionTabSessionFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabSessionFragment) session).binding.axisIncludeLayout.selectRecyclerView, ((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.axisErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;
                                                case "description":
                                                    if (session instanceof CreateSessionTabSessionFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabSessionFragment) session).binding.descriptionIncludeLayout.inputEditText, ((CreateSessionTabSessionFragment) session).binding.descriptionErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.descriptionErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;
                                                case "client_reminder":
                                                    if (session instanceof CreateSessionTabSessionFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabSessionFragment) session).binding.coordinationIncludeLayout.inputEditText, ((CreateSessionTabSessionFragment) session).binding.coordinationErrorLayout.getRoot(), ((CreateSessionTabSessionFragment) session).binding.coordinationErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                    break;

                                                // Payment Data
                                                case "payment_status":
                                                    if (payment instanceof CreateSessionTabPaymentFragment)
                                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), ((CreateSessionTabPaymentFragment) payment).binding.paymentIncludeLayout.selectSpinner, ((CreateSessionTabPaymentFragment) payment).binding.paymentErrorLayout.getRoot(), ((CreateSessionTabPaymentFragment) payment).binding.paymentErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
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