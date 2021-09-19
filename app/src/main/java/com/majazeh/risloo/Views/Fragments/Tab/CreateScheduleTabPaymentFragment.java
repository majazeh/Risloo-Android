package com.majazeh.risloo.Views.Fragments.Tab;

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
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Create.CreateAxisAdapter;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.databinding.FragmentCreateScheduleTabPaymentBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class CreateScheduleTabPaymentFragment extends Fragment {

    // Binding
    public FragmentCreateScheduleTabPaymentBinding binding;

    // Adapters
    public CreateAxisAdapter createAxisAdapter;

    // Fragments
    private Fragment current;

    // Vars
    public String payment = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleTabPaymentBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        createAxisAdapter = new CreateAxisAdapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        binding.paymentIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabPaymentPaymentHeader));

        InitManager.normal12sspSpinner(requireActivity(), binding.paymentIncludeLayout.selectSpinner, R.array.PaymentStatus);

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.axisRecyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), 0);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateScheduleTabPaymentButton), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
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
            if (current instanceof CreateScheduleFragment)
                ((CreateScheduleFragment) current).checkRequire();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        setRecyclerView(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, ArrayList<String> amounts) {
        createAxisAdapter.setItems(items, ids, amounts);
        binding.axisRecyclerView.setAdapter(createAxisAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}