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
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.databinding.DialogTimeBinding;

import java.util.Objects;

public class TimeDialog extends BottomSheetDialogFragment {

    // Binding
    private DialogTimeBinding binding;

    // Vars
    private int hour, minute;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = DialogTimeBinding.inflate(inflater, viewGroup, false);

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
        ClickManager.onDelayedClickListener(() -> {
            switch (Objects.requireNonNull(((MainActivity) requireActivity()).navController.getCurrentDestination()).getId()) {
                case R.id.createSessionFragment:
                    CreateSessionFragment createSessionFragment = (CreateSessionFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createSessionFragment != null) {
                        createSessionFragment.startTime = getDate();

                        createSessionFragment.hour = hour;
                        createSessionFragment.minute = minute;

                        createSessionFragment.binding.startTimeIncludeLayout.selectTextView.setText(createSessionFragment.startTime);
                    }
                    break;
                case R.id.editSessionFragment:
                    EditSessionFragment editSessionFragment = (EditSessionFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (editSessionFragment != null) {
                        editSessionFragment.startTime = getDate();

                        editSessionFragment.hour = hour;
                        editSessionFragment.minute = minute;

                        editSessionFragment.binding.startTimeIncludeLayout.selectTextView.setText(editSessionFragment.startTime);
                    }
                    break;
            }

            dismiss();
        }).widget(binding.entryButton);
    }

    private void setNumberPicker() {
        binding.hourNumberPicker.setMinValue(0);
        binding.hourNumberPicker.setMaxValue(23);
        binding.hourNumberPicker.setValue(hour);

        binding.minuteNumberPicker.setMinValue(0);
        binding.minuteNumberPicker.setMaxValue(59);
        binding.minuteNumberPicker.setValue(minute);
    }

    private void clearNumberPicker() {
        binding.hourNumberPicker.setValue(hour);
        binding.minuteNumberPicker.setValue(minute);
    }

    private String getDate() {
        hour = binding.hourNumberPicker.getValue();
        minute = binding.minuteNumberPicker.getValue();

        if (hour < 10) {
            if (minute < 10)
                return "0" + hour + ":" + "0" + minute;
            else
                return "0" + hour + ":" + minute;
        } else {
            if (minute < 10)
                return hour + ":" + "0" + minute;
            else
                return hour + ":" + minute;
        }
    }

    public void setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}