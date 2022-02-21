package com.majazeh.risloo.views.sheets;

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
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabTime;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabTime;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditSessionTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditSessionTabTime;
import com.majazeh.risloo.databinding.BottomSheetTimeBinding;

import java.util.Date;

import saman.zamani.persiandate.PersianDate;

public class SheetTime extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetTimeBinding binding;

    // Fragments
    private Fragment child;

    // Vars
    private int year = 0, month = 0, day = 0, hour = 0, minute = 0, second = 0;
    private String method = "";

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
        child = ((ActivityMain) requireActivity()).fragmont.getChild();

        InitManager.txtTextColorBackground(binding.entryTextView.getRoot(), getResources().getString(R.string.BottomSheetTimeEntry), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (child instanceof FragmentCreateScheduleTabTime)
                ((FragmentCreateScheduleTabTime) child).responseBottomSheet(method, getTime());

            if (child instanceof FragmentCreateScheduleTabSession)
                ((FragmentCreateScheduleTabSession) child).responseBottomSheet(method, getTime());

            if (child instanceof FragmentCreateSessionTabTime)
                ((FragmentCreateSessionTabTime) child).responseBottomSheet(method, getTime());

            if (child instanceof FragmentCreateSessionTabSession)
                ((FragmentCreateSessionTabSession) child).responseBottomSheet(method, getTime());

            if (child instanceof FragmentEditSessionTabTime)
                ((FragmentEditSessionTabTime) child).responseBottomSheet(method, getTime());

            if (child instanceof FragmentEditSessionTabSession)
                ((FragmentEditSessionTabSession) child).responseBottomSheet(method, getTime());

            dismiss();
        }).widget(binding.entryTextView.getRoot());
    }

    private void setDialog() {
        switch (method) {
            case "startTime":
                if (child instanceof FragmentCreateScheduleTabTime)
                    binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetScheduleStartTimeTitle));

                if (child instanceof FragmentCreateSessionTabTime || child instanceof FragmentEditSessionTabTime)
                    binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetSessionStartTimeTitle));

                break;
            case "accurateStartTime":
                binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetAccurateStartTimeTitle));
                break;
            case "accurateEndTime":
                binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetAccurateEndTimeTitle));
                break;
        }
    }

    private void setNumberPicker() {
        binding.hourNumberPicker.getRoot().setMinValue(0);
        binding.hourNumberPicker.getRoot().setMaxValue(23);
        binding.hourNumberPicker.getRoot().setValue(hour);

        binding.minuteNumberPicker.getRoot().setMinValue(0);
        binding.minuteNumberPicker.getRoot().setMaxValue(59);
        binding.minuteNumberPicker.getRoot().setValue(minute);
    }

    private void clearNumberPicker() {
        binding.hourNumberPicker.getRoot().setValue(hour);
        binding.minuteNumberPicker.getRoot().setValue(minute);
    }

    private String getTime() {
        hour = binding.hourNumberPicker.getRoot().getValue();
        minute = binding.minuteNumberPicker.getRoot().getValue();

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