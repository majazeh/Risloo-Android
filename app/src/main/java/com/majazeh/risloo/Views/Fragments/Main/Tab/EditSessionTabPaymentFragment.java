package com.majazeh.risloo.Views.Fragments.Main.Tab;

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
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SelectionManager;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditSessionFragment;
import com.majazeh.risloo.databinding.FragmentEditSessionTabPaymentBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;

import java.util.HashMap;

public class EditSessionTabPaymentFragment extends Fragment {

    // Binding
    private FragmentEditSessionTabPaymentBinding binding;

    // Fragments
    private Fragment current;

    // Vars
    private String payment = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionTabPaymentBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        binding.paymentIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabPaymentPaymentHeader));

        InitManager.input12sspSpinner(requireActivity(), binding.paymentIncludeLayout.selectSpinner, R.array.PaymentStatus);

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionTabPaymentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
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
            if (current instanceof EditSessionFragment)
                ((EditSessionFragment) current).checkRequire();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof EditSessionFragment) {
            SessionModel model = ((EditSessionFragment) current).sessionModel;

            if (model.getPaymentStatus() != null && !model.getPaymentStatus().equals("")) {
                payment = SelectionManager.getPaymentStatus(requireActivity(), "fa", model.getPaymentStatus());
                for (int i = 0; i < binding.paymentIncludeLayout.selectSpinner.getCount(); i++) {
                    if (binding.paymentIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(payment)) {
                        binding.paymentIncludeLayout.selectSpinner.setSelection(i);
                    }
                }
            }
        }
    }

    public void setHashmap(HashMap data) {
        if (!payment.equals(""))
            data.put("payment_status", SelectionManager.getPaymentStatus(requireActivity(), "en", payment));
        else
            data.remove("payment_status");
    }

    public void hideValid() {
        if (binding.paymentErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.paymentErrorLayout.getRoot(), binding.paymentErrorLayout.errorTextView);
    }

    public void showValid(String key, String validation) {
        switch (key) {
            case "payment_status":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.paymentErrorLayout.getRoot(), binding.paymentErrorLayout.errorTextView, validation);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}