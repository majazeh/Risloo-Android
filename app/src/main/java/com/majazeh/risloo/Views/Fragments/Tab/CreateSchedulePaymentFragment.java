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
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.AxisAdapter;
import com.majazeh.risloo.databinding.FragmentCreateSchedulePaymentBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class CreateSchedulePaymentFragment extends Fragment {

    // Binding
    private FragmentCreateSchedulePaymentBinding binding;

    // Adapters
    public AxisAdapter axisAdapter;

    // Vars
    private String payment = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSchedulePaymentBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        axisAdapter = new AxisAdapter(requireActivity());

        binding.paymentIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSchedulePaymentTabPaymentHeader));

        InitManager.fixedSpinner(requireActivity(), binding.paymentIncludeLayout.selectSpinner, R.array.PaymentTypes, "main");

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.axisRecyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), 0);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateSchedulePaymentTabButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.paymentIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                payment = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (payment.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.paymentIncludeLayout.selectSpinner, binding.paymentErrorLayout.errorImageView, binding.paymentErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (!payment.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.paymentIncludeLayout.selectSpinner, binding.paymentErrorLayout.errorImageView, binding.paymentErrorLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
            payment = ((MainActivity) requireActivity()).singleton.getStatus();
            for (int i=0; i<binding.paymentIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.paymentIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(payment)) {
                    binding.paymentIncludeLayout.selectSpinner.setSelection(i);
                }
            }
        }

//        if (extras.getString("axis") != null) {
//            try {
//                JSONArray jsonArray = new JSONArray(extras.getString("axis"));
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                    Model model = new Model(jsonObject);
//
//                    axis.add(model);
//                }
//
//                setRecyclerView(axis, "axis");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
        setRecyclerView(new ArrayList<>(), new ArrayList<>());
//        }
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids) {
//        axisAdapter.setItems(items, ids);
//        binding.axisRecyclerView.setAdapter(axisAdapter);
    }

    private void doWork() {
        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}