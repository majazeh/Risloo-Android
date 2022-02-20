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
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateScheduleTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateScheduleTabTimeFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.CreateSessionTabTimeFragment;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateUserFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditUserTabPersonalFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Main.Tab.EditSessionTabTimeFragment;
import com.majazeh.risloo.databinding.BottomSheetDateBinding;

import java.util.Date;

import saman.zamani.persiandate.PersianDate;

public class DateBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetDateBinding binding;

    // Fragments
    private Fragment current, child;

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
        binding = BottomSheetDateBinding.inflate(inflater, viewGroup, false);

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
        current = ((MainActivity) requireActivity()).fragmont.getCurrent();
        child = ((MainActivity) requireActivity()).fragmont.getChild();

        InitManager.txtTextColorBackground(binding.entryTextView.getRoot(), getResources().getString(R.string.BottomSheetDateEntry), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    private void listener() {
        binding.monthNumberPicker.getRoot().setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (picker == binding.monthNumberPicker.getRoot()) {
                if (newVal <= 6)
                    binding.dayNumberPicker.getRoot().setMaxValue(31);
                else
                    binding.dayNumberPicker.getRoot().setMaxValue(30);
            }
        });

        CustomClickView.onDelayedListener(() -> {
            if (child instanceof CreateSessionTabTimeFragment)
                ((CreateSessionTabTimeFragment) child).responseBottomSheet(method, getDate());

            if (child instanceof CreateSessionTabSessionFragment)
                ((CreateSessionTabSessionFragment) child).responseBottomSheet(method, getDate());

            if (child instanceof CreateScheduleTabTimeFragment)
                ((CreateScheduleTabTimeFragment) child).responseBottomSheet(method, getDate());

            if (child instanceof CreateScheduleTabSessionFragment)
                ((CreateScheduleTabSessionFragment) child).responseBottomSheet(method, getDate());

            if (current instanceof CreateUserFragment)
                ((CreateUserFragment) current).responseBottomSheet(method, getDate());

            if (child instanceof EditSessionTabTimeFragment)
                ((EditSessionTabTimeFragment) child).responseBottomSheet(method, getDate());

            if (child instanceof EditSessionTabSessionFragment)
                ((EditSessionTabSessionFragment) child).responseBottomSheet(method, getDate());

            if (child instanceof EditUserTabPersonalFragment)
                ((EditUserTabPersonalFragment) child).responseBottomSheet(method, getDate());

            dismiss();
        }).widget(binding.entryTextView.getRoot());
    }

    private void setDialog() {
        switch (method) {
            case "birthday":
                if (child instanceof EditUserTabPersonalFragment)
                    binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetMeBirthDateTitle));

                if (current instanceof CreateUserFragment)
                    binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetUserBirthDateTitle));

                break;
            case "startDate":
                if (child instanceof EditSessionTabTimeFragment)
                    binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetSessionStartDateTitle));

                break;
            case "accurateStartDate":
                binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetAccurateStartDateTitle));
                break;
            case "accurateEndDate":
                binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetAccurateEndDateTitle));
                break;
            case "periodStartDate":
                binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetPeriodStartDateTitle));
                break;
            case "periodEndDate":
                binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetPeriodEndDateTitle));
                break;
            case "specifiedDate":
                binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetSpecifiedDateTitle));
                break;
        }
    }

    private void setNumberPicker() {
        binding.yearNumberPicker.getRoot().setMinValue(1300);
        binding.yearNumberPicker.getRoot().setMaxValue(2100);
        binding.yearNumberPicker.getRoot().setValue(year);

        binding.monthNumberPicker.getRoot().setMinValue(1);
        binding.monthNumberPicker.getRoot().setMaxValue(12);
        binding.monthNumberPicker.getRoot().setValue(month);
        binding.monthNumberPicker.getRoot().setDisplayedValues(requireActivity().getResources().getStringArray(R.array.YearMonths));

        binding.dayNumberPicker.getRoot().setMinValue(1);
        binding.dayNumberPicker.getRoot().setMaxValue(31);
        binding.dayNumberPicker.getRoot().setValue(day);
    }

    private void clearNumberPicker() {
        binding.yearNumberPicker.getRoot().setValue(year);
        binding.monthNumberPicker.getRoot().setValue(month);
        binding.dayNumberPicker.getRoot().setValue(day);
    }

    private String getDate() {
        year = binding.yearNumberPicker.getRoot().getValue();
        month = binding.monthNumberPicker.getRoot().getValue();
        day = binding.dayNumberPicker.getRoot().getValue();

        PersianDate persianDate = DateManager.createPersianDate(year, month, day, hour, minute, second);
        Date date = DateManager.persianToDate(persianDate);
        long timestamp = DateManager.dateToTimestamp(date);

        return String.valueOf(timestamp);
    }

    public void setDate(String timestamp, String method) {
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