package com.majazeh.risloo.Views.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Widgets.CustomizeDialog;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateUserFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditPersonalFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragment;
import com.majazeh.risloo.databinding.DialogDateBinding;

import java.util.Objects;

public class DateDialog extends BottomSheetDialogFragment {

    // Binding
    private DialogDateBinding binding;

    // Vars
    private int year, month, day;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return CustomizeDialog.bottomSheet(requireActivity(), dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = DialogDateBinding.inflate(inflater, viewGroup, false);

        listener();

        detector();

        setNumberPicker();

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        clearNumberPicker();
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.entryButton.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    private void listener() {
        binding.monthNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (picker == binding.monthNumberPicker) {
                if (newVal <= 6) {
                    binding.dayNumberPicker.setMaxValue(31);
                } else {
                    binding.dayNumberPicker.setMaxValue(30);
                }
            }
        });

        ClickManager.onDelayedClickListener(() -> {
            switch (Objects.requireNonNull(((MainActivity) requireActivity()).navController.getCurrentDestination()).getId()) {
                case R.id.createUserFragment:
                    CreateUserFragment createUserFragment = (CreateUserFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createUserFragment != null) {
                        createUserFragment.birthday = getDate();

                        createUserFragment.year = year;
                        createUserFragment.month = month;
                        createUserFragment.day = day;

                        createUserFragment.binding.birthdayIncludeLayout.selectTextView.setText(createUserFragment.birthday);
                    }
                    break;
                case R.id.editUserFragment:
                    EditUserFragment editUserFragment = (EditUserFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (editUserFragment != null) {
                        EditPersonalFragment editPersonalFragment = (EditPersonalFragment) editUserFragment.adapter.getRegisteredFragment(0);

                        editPersonalFragment.birthday = getDate();

                        editPersonalFragment.year = year;
                        editPersonalFragment.month = month;
                        editPersonalFragment.day = day;

                        editPersonalFragment.binding.birthdayIncludeLayout.selectTextView.setText(editPersonalFragment.birthday);
                    }
                    break;
            }

            dismiss();
        }).widget(binding.entryButton);
    }

    private void setNumberPicker() {
        binding.yearNumberPicker.setMinValue(1300);
        binding.yearNumberPicker.setMaxValue(2100);
        binding.yearNumberPicker.setValue(year);

        binding.monthNumberPicker.setMinValue(1);
        binding.monthNumberPicker.setMaxValue(12);
        binding.monthNumberPicker.setValue(month);
        binding.monthNumberPicker.setDisplayedValues(requireActivity().getResources().getStringArray(R.array.JalaliMonths));

        binding.dayNumberPicker.setMinValue(1);
        binding.dayNumberPicker.setMaxValue(31);
        binding.dayNumberPicker.setValue(day);
    }

    private void clearNumberPicker() {
        binding.yearNumberPicker.setValue(year);
        binding.monthNumberPicker.setValue(month);
        binding.dayNumberPicker.setValue(day);
    }

    private String getDate() {
        year = binding.yearNumberPicker.getValue();
        month = binding.monthNumberPicker.getValue();
        day = binding.dayNumberPicker.getValue();

        if (month < 10) {
            if (day < 10)
                return year + "-" + "0" + month + "-" + "0" + day;
            else
                return year + "-" + "0" + month + "-" + day;
        } else {
            if (day < 10)
                return year + "-" + month + "-" + "0" + day;
            else
                return year + "-" + month + "-" + day;
        }
    }

    public void setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}