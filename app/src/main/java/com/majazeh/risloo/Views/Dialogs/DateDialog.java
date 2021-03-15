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
import com.majazeh.risloo.Utils.Widgets.CustomizeDialog;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Edit.EditAccountFragment;
import com.majazeh.risloo.databinding.DialogDateBinding;

public class DateDialog extends BottomSheetDialogFragment {

    // Binding
    private DialogDateBinding binding;

    // Vars
    private int year, month, day;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return CustomizeDialog.bottomSheet(getActivity(), dialog);
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
            binding.dialogDateEntryButton.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    private void listener() {
        binding.dialogDateMonthNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (picker == binding.dialogDateMonthNumberPicker) {
                if (newVal <= 6) {
                    binding.dialogDateDayNumberPicker.setMaxValue(31);
                } else {
                    binding.dialogDateDayNumberPicker.setMaxValue(30);
                }
            }
        });

        binding.dialogDateEntryButton.setOnClickListener(v -> {
            binding.dialogDateEntryButton.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.dialogDateEntryButton.setClickable(true), 300);
            dismiss();

            switch (((MainActivity) requireActivity()).navController.getCurrentDestination().getId()) {
                case R.id.editAccountFragment:
                    EditAccountFragment editAccountFragment = (EditAccountFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (editAccountFragment != null) {
                        // TODO : Set Fragment TextView String
                    }
                    break;
            }
        });
    }

    private void setNumberPicker() {
        binding.dialogDateYearNumberPicker.setMinValue(1300);
        binding.dialogDateYearNumberPicker.setMaxValue(2100);
        binding.dialogDateYearNumberPicker.setValue(year);

        binding.dialogDateMonthNumberPicker.setMinValue(1);
        binding.dialogDateMonthNumberPicker.setMaxValue(12);
        binding.dialogDateMonthNumberPicker.setValue(month);
        binding.dialogDateMonthNumberPicker.setDisplayedValues(getActivity().getResources().getStringArray(R.array.JalaliMonths));

        binding.dialogDateDayNumberPicker.setMinValue(1);
        binding.dialogDateDayNumberPicker.setMaxValue(31);
        binding.dialogDateDayNumberPicker.setValue(day);
    }

    private void clearNumberPicker() {
        binding.dialogDateYearNumberPicker.setValue(year);
        binding.dialogDateMonthNumberPicker.setValue(month);
        binding.dialogDateDayNumberPicker.setValue(day);
    }

    private String getDate() {
        year = binding.dialogDateYearNumberPicker.getValue();
        month = binding.dialogDateMonthNumberPicker.getValue();
        day = binding.dialogDateDayNumberPicker.getValue();

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