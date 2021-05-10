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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.AxisPaymentsAdapter;
import com.majazeh.risloo.databinding.FragmentEditSessionPaymentBinding;

import java.util.ArrayList;

public class EditSessionPaymentFragment extends Fragment {

    // Binding
    private FragmentEditSessionPaymentBinding binding;

    // Adapters
    public AxisPaymentsAdapter axisPaymentsAdapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager axisPaymentslayoutManager;

    // Vars
    private ArrayList<Model> axisPayments = new ArrayList<>();
    private String payment = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionPaymentBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        axisPaymentsAdapter = new AxisPaymentsAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), 0, (int) getResources().getDimension(R.dimen._4sdp), 0);

        axisPaymentslayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.paymentIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionPaymentTabPaymentHeader));

        InitManager.spinner(requireActivity(), binding.paymentIncludeLayout.selectSpinner, R.array.PaymentTypes, "main");

        InitManager.unfixedRecyclerView(binding.axisRecyclerView, itemDecoration, axisPaymentslayoutManager);

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionPaymentTabButton), getResources().getColor(R.color.White));
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
        }).widget(binding.editTextView.getRoot());
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

//        if (extras.getString("axisPayments") != null) {
//            try {
//                JSONArray jsonArray = new JSONArray(extras.getString("axisPayments"));
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                    Model model = new Model(jsonObject);
//
//                    axisPayments.add(model);
//                }
//
//                setRecyclerView(axisPayments, "axisPayments");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
        setRecyclerView(axisPayments, new ArrayList<>());
//        }
    }

    private void setRecyclerView(ArrayList<Model> items, ArrayList<String> ids) {
        axisPaymentsAdapter.setItems(items, ids);
        binding.axisRecyclerView.setAdapter(axisPaymentsAdapter);
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