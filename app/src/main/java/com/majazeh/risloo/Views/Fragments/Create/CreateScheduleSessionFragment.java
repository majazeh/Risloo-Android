package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.majazeh.risloo.databinding.FragmentCreateScheduleSessionBinding;

public class CreateScheduleSessionFragment extends Fragment {

    // Binding
    private FragmentCreateScheduleSessionBinding binding;

    // Vars
    private String type = "", status = "", description = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleSessionFragmentTypeHeader));
        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleSessionFragmentStatusHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleSessionFragmentDescriptionHeader));

        InitManager.spinner(requireActivity(), binding.typeIncludeLayout.selectSpinner, R.array.SessionTypes, "main");
        InitManager.spinner(requireActivity(), binding.statusIncludeLayout.selectSpinner, R.array.SessionStatus, "main");

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateScheduleSessionFragmentButton), getResources().getColor(R.color.White));
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
        binding.typeIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.statusIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.descriptionIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (type.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.typeIncludeLayout.selectSpinner, binding.typeIncludeLayout.errorImageView, binding.typeIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (status.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.statusIncludeLayout.selectSpinner, binding.statusIncludeLayout.errorImageView, binding.statusIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.descriptionIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionIncludeLayout.errorImageView, binding.descriptionIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (!type.equals("") && !status.equals("") && binding.descriptionIncludeLayout.inputEditText.length() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.typeIncludeLayout.selectSpinner, binding.typeIncludeLayout.errorImageView, binding.typeIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.statusIncludeLayout.selectSpinner, binding.statusIncludeLayout.errorImageView, binding.statusIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionIncludeLayout.errorImageView, binding.descriptionIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getType().equals("")) {
            type = ((MainActivity) requireActivity()).singleton.getType();
            for (int i=0; i<binding.typeIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.typeIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(status)) {
                    binding.typeIncludeLayout.selectSpinner.setSelection(i);
                }
            }
        }

        if (!((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
            status = ((MainActivity) requireActivity()).singleton.getStatus();
            for (int i=0; i<binding.statusIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.statusIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(status)) {
                    binding.statusIncludeLayout.selectSpinner.setSelection(i);
                }
            }
        }

        if (!((MainActivity) requireActivity()).singleton.getDescription().equals("")) {
            description = ((MainActivity) requireActivity()).singleton.getDescription();
            binding.descriptionIncludeLayout.inputEditText.setText(description);
        }
    }

    private void doWork() {
        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}