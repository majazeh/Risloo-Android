package com.majazeh.risloo.Views.BottomSheets;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTabTimeFragment;
import com.majazeh.risloo.databinding.BottomSheetTimeBinding;

import java.util.Date;

import saman.zamani.persiandate.PersianDate;

public class TimeBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetTimeBinding binding;

    // Fragments
    private Fragment child;

    // Vars
    private int year, month, day, hour, minute, second;
    private String method;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetTimeBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setDialog();

        setNumberPicker();

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        clearNumberPicker();
    }

    private void initializer() {
        child = ((MainActivity) requireActivity()).fragmont.getChild();
    }

    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (child instanceof CreateSessionTabTimeFragment)
                ((CreateSessionTabTimeFragment) child).responseBottomSheet(method, getTime());

            if (child instanceof CreateSessionTabSessionFragment)
                ((CreateSessionTabSessionFragment) child).responseBottomSheet(method, getTime());

            if (child instanceof CreateScheduleTabTimeFragment)
                ((CreateScheduleTabTimeFragment) child).responseBottomSheet(method, getTime());

            if (child instanceof CreateScheduleTabSessionFragment)
                ((CreateScheduleTabSessionFragment) child).responseBottomSheet(method, getTime());

            if (child instanceof EditSessionTabTimeFragment)
                ((EditSessionTabTimeFragment) child).responseBottomSheet(method, getTime());

            if (child instanceof EditSessionTabSessionFragment)
                ((EditSessionTabSessionFragment) child).responseBottomSheet(method, getTime());

            dismiss();
        }).widget(binding.entryButton);
    }

    private void setDialog() {
        switch (method) {
            case "startTime":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetStartTimeTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetStartTimeEntry));
                break;
            case "accurateStartTime":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetAccurateStartTimeTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetAccurateStartTimeEntry));
                break;
            case "accurateEndTime":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetAccurateEndTimeTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetAccurateEndTimeEntry));
                break;
        }
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

    private String getTime() {
        hour = binding.hourNumberPicker.getValue();
        minute = binding.minuteNumberPicker.getValue();

        PersianDate persianDate = DateManager.createPersianDate(year, month, day, hour, minute, second);
        Date date = DateManager.persianToDate(persianDate);
        long timestamp = DateManager.dateToTimestamp(date);

        return String.valueOf(timestamp);
    }

    public void setTime(String timestamp, String method) {
        this.method = method;

        long value = Long.parseLong(timestamp);
        Date date = DateManager.timestampToDate(value);
        PersianDate persianDate = DateManager.dateToPersian(date);

        year = persianDate.getShYear();
        month = persianDate.getShMonth();
        day = persianDate.getShDay();
        hour = persianDate.getHour();
        minute = persianDate.getMinute();
        second = persianDate.getSecond();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}