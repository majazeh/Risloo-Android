package com.majazeh.risloo.Views.Fragments.Edit;

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
import com.majazeh.risloo.databinding.FragmentEditCenterUserBinding;

public class EditCenterUserFragment extends Fragment {

    // Binding
    private FragmentEditCenterUserBinding binding;

    // Vars
    private String type = "", name = "", status ="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCenterUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterUserFragmentTypeHeader));
        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterUserFragmentNameHeader));
        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterUserFragmentStatusHeader));

        binding.nameGuideLayout.guideTextView.setText(getResources().getString(R.string.EditCenterUserFragmentNameGuide));

        binding.statusIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditCenterUserFragmentStatusAccept));
        binding.statusIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditCenterUserFragmentStatusKick));

        InitManager.spinner(requireActivity(), binding.typeIncludeLayout.selectSpinner, R.array.UserTypes, "main");

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditCenterUserFragmentButton), getResources().getColor(R.color.White));
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
        binding.typeIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();

                if (position == 3) {
                    binding.clientGroup.setVisibility(View.VISIBLE);
                } else {
                    binding.clientGroup.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.nameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.statusIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    status = "accept";
                    break;
                case R.id.second_radioButton:
                    status = "kick";
                    break;
            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.nameIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, null, null, getResources().getString(R.string.AppInputEmpty));
            }

            if (binding.nameIncludeLayout.inputEditText.length() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.nameIncludeLayout.inputEditText, null, null);

                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getType().equals("")) {
            type = ((MainActivity) requireActivity()).singleton.getType();
            for (int i=0; i<binding.typeIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.typeIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(type)) {
                    binding.typeIncludeLayout.selectSpinner.setSelection(i);

                    if (i == 3) {
                        binding.clientGroup.setVisibility(View.VISIBLE);
                    } else {
                        binding.clientGroup.setVisibility(View.GONE);
                    }
                }
            }
        }
        if (!((MainActivity) requireActivity()).singleton.getName().equals("")) {
            name = ((MainActivity) requireActivity()).singleton.getName();
            binding.nameIncludeLayout.inputEditText.setText(name);
        }

        if (!((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
            status = ((MainActivity) requireActivity()).singleton.getStatus();
            switch (status) {
                case "accept":
                    binding.statusIncludeLayout.firstRadioButton.setChecked(true);
                    break;
                case "kick":
                    binding.statusIncludeLayout.secondRadioButton.setChecked(true);
                    break;
            }
        }
    }

    private void doWork() {
        name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}