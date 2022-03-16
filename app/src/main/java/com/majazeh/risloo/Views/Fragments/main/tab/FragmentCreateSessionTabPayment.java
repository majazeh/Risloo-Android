package com.majazeh.risloo.views.fragments.main.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.managers.DropdownManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Create.CreateAxisAdapter;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateSession;
import com.majazeh.risloo.databinding.FragmentCreateSessionTabPaymentBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentCreateSessionTabPayment extends Fragment {

    // Binding
    private FragmentCreateSessionTabPaymentBinding binding;

    // Adapters
    public CreateAxisAdapter axisAdapter;

    // Fragments
    private Fragment current;

    // Vars
    private String payment = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSessionTabPaymentBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        axisAdapter = new CreateAxisAdapter(requireActivity());

        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();

        binding.paymentIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTabPaymentPaymentHeader));

        DropdownManager.spinner12ssp(requireActivity(), binding.paymentIncludeLayout.selectSpinner, R.array.PaymentStatus);

        InitManager.rcvVerticalUnfixed(requireActivity(), binding.axisRecyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateSessionTabPaymentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.paymentIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.paymentIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.paymentIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    payment = parent.getItemAtPosition(position).toString();

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomClickView.onDelayedListener(() -> {
            if (current instanceof FragmentCreateSession)
                ((FragmentCreateSession) current).checkRequire();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        setRecyclerView(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, ArrayList<String> amounts) {
        axisAdapter.setItems(items, ids, amounts);
        binding.axisRecyclerView.setAdapter(axisAdapter);
    }

    public void setHashmap(HashMap data) {
        if (!payment.equals(""))
            data.put("payment_status", JsonManager.getPaymentStatus(requireActivity(), "en", payment));
        else
            data.remove("payment_status");

        if (!axisAdapter.getAmounts().isEmpty())
            data.put("amounts", axisAdapter.getAmounts());
        else
            data.remove("amounts");
    }

    public void hideValid() {
        ((ActivityMain) requireActivity()).validatoon.hideValid(binding);
    }

    public void showValid(String key, String validation) {
        ((ActivityMain) requireActivity()).validatoon.showValid(key, validation, binding);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}