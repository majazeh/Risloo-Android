package com.majazeh.risloo.Views.BottomSheets;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTimeFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTimeFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateUserFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserPersonalFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTimeFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragment;
import com.majazeh.risloo.databinding.BottomSheetDateBinding;

import java.util.Date;

import saman.zamani.persiandate.PersianDate;

public class DateBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetDateBinding binding;

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
        binding = BottomSheetDateBinding.inflate(inflater, viewGroup, false);

        initializer();

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

    private void initializer() {
        switch (method) {
            case "startDate":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetStartDateTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetStartDateEntry));
                break;
            case "startAccurateDate":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetStartAccurateDateTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetStartAccurateDateEntry));
                break;
            case "endAccurateDate":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetEndAccurateDateTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetEndAccurateDateEntry));
                break;
            case "specifiedDate":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetSpecifiedDateTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetSpecifiedDateEntry));
                break;
            case "periodStartDate":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetPeriodStartDateTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetPeriodStartDateEntry));
                break;
            case "periodEndDate":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetPeriodEndDateTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetPeriodEndDateEntry));
                break;
            case "birthday":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetBirthdayTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetBirthdayEntry));
                break;
        }
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
            if (getParent() != null) {
                if (getParent() instanceof CreateSessionTimeFragment)
                    ((CreateSessionTimeFragment) getParent()).responseBottomSheet(method, getDate());

                else if (getParent() instanceof CreateSessionSessionFragment)
                    ((CreateSessionSessionFragment) getParent()).responseBottomSheet(method, getDate());

                else if (getParent() instanceof CreateScheduleTimeFragment)
                    ((CreateScheduleTimeFragment) getParent()).responseBottomSheet(method, getDate());

                else if (getParent() instanceof CreateScheduleSessionFragment)
                    ((CreateScheduleSessionFragment) getParent()).responseBottomSheet(method, getDate());

                else if (getParent() instanceof CreateUserFragment)
                    ((CreateUserFragment) getParent()).responseBottomSheet(method, getDate());

                else if (getParent() instanceof EditSessionTimeFragment)
                    ((EditSessionTimeFragment) getParent()).responseBottomSheet(method, getDate());

                else if (getParent() instanceof EditSessionSessionFragment)
                    ((EditSessionSessionFragment) getParent()).responseBottomSheet(method, getDate());

                else if (getParent() instanceof EditUserPersonalFragment)
                    ((EditUserPersonalFragment) getParent()).responseBottomSheet(method, getDate());
            }

            dismiss();
        }).widget(binding.entryButton);
    }

    private Fragment getParent() {
        Fragment fragment = ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null)
            if (fragment instanceof CreateSessionFragment) {
                Fragment childFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof CreateSessionTimeFragment)
                        return (CreateSessionTimeFragment) childFragment;
                    else if (childFragment instanceof CreateSessionSessionFragment)
                        return (CreateSessionSessionFragment) childFragment;

            }  else if (fragment instanceof CreateScheduleFragment) {
                Fragment childFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof CreateScheduleTimeFragment)
                        return (CreateScheduleTimeFragment) childFragment;
                    else if (childFragment instanceof CreateScheduleSessionFragment)
                        return (CreateScheduleSessionFragment) childFragment;

            } else if (fragment instanceof CreateUserFragment)
                return (CreateUserFragment) fragment;

            else if (fragment instanceof EditSessionFragment) {
                Fragment childFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof EditSessionTimeFragment)
                        return (EditSessionTimeFragment) childFragment;
                    else if (childFragment instanceof EditSessionSessionFragment)
                        return (EditSessionSessionFragment) childFragment;

            } else if (fragment instanceof EditUserFragment) {
                Fragment childFragment = ((EditUserFragment) fragment).adapter.hashMap.get(((EditUserFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof EditUserPersonalFragment)
                        return (EditUserPersonalFragment) childFragment;
            }

        return null;
    }

    private void setNumberPicker() {
        binding.yearNumberPicker.setMinValue(1300);
        binding.yearNumberPicker.setMaxValue(2100);
        binding.yearNumberPicker.setValue(year);

        binding.monthNumberPicker.setMinValue(1);
        binding.monthNumberPicker.setMaxValue(12);
        binding.monthNumberPicker.setValue(month);
        binding.monthNumberPicker.setDisplayedValues(requireActivity().getResources().getStringArray(R.array.YearMonths));

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