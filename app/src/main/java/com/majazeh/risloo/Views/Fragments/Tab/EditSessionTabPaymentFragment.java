package com.majazeh.risloo.Views.Fragments.Tab;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.databinding.FragmentEditSessionTabPaymentBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;

public class EditSessionTabPaymentFragment extends Fragment {

    // Binding
    public FragmentEditSessionTabPaymentBinding binding;

    // Fragments
    private Fragment current;

    // Vars
    public String payment = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionTabPaymentBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        binding.paymentIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabPaymentPaymentHeader));

        InitManager.normal12sspSpinner(requireActivity(), binding.paymentIncludeLayout.selectSpinner, R.array.PaymentTypes);

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionTabPaymentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.paymentIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

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

            if (model.getPayment_status() != null && !model.getPayment_status().equals("")) {
                payment = SelectionManager.getPaymentStatus(requireActivity(), "fa", model.getPayment_status());
                for (int i = 0; i < binding.paymentIncludeLayout.selectSpinner.getCount(); i++) {
                    if (binding.paymentIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(payment)) {
                        binding.paymentIncludeLayout.selectSpinner.setSelection(i);
                    }
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}