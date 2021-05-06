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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
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

import java.util.Objects;

public class DateBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetDateBinding binding;

    // Vars
    public int year, month, day;
    private String method;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
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
            switch (Objects.requireNonNull(((MainActivity) requireActivity()).navController.getCurrentDestination()).getId()) {
                case R.id.createSessionFragment:
                    CreateSessionFragment createSessionFragment = (CreateSessionFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createSessionFragment != null) {
                        switch (method) {
                            case "startDate":
                                CreateSessionTimeFragment createSessionTimeFragment = (CreateSessionTimeFragment) createSessionFragment.adapter.hashMap.get(createSessionFragment.binding.viewPager.getCurrentItem());

                                createSessionTimeFragment.responseBottomSheet(method, getDate());
                                break;
                            case "startAccurateDate":
                            case "endAccurateDate":
                                CreateSessionSessionFragment createSessionSessionFragment = (CreateSessionSessionFragment) createSessionFragment.adapter.hashMap.get(createSessionFragment.binding.viewPager.getCurrentItem());

                                createSessionSessionFragment.responseBottomSheet(method, getDate());
                                break;
                        }
                    }
                    break;
                case R.id.createScheduleFragment:
                    CreateScheduleFragment createScheduleFragment = (CreateScheduleFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createScheduleFragment != null) {
                        switch (method) {
                            case "startDate":
                            case "specifiedDate":
                            case "periodStartDate":
                            case "periodEndDate":
                                CreateScheduleTimeFragment createScheduleTimeFragment = (CreateScheduleTimeFragment) createScheduleFragment.adapter.hashMap.get(createScheduleFragment.binding.viewPager.getCurrentItem());

                                createScheduleTimeFragment.responseBottomSheet(method, getDate());
                                break;
                            case "startAccurateDate":
                            case "endAccurateDate":
                                CreateScheduleSessionFragment createScheduleSessionFragment = (CreateScheduleSessionFragment) createScheduleFragment.adapter.hashMap.get(createScheduleFragment.binding.viewPager.getCurrentItem());

                                createScheduleSessionFragment.responseBottomSheet(method, getDate());
                                break;
                        }
                    }
                    break;
                case R.id.createUserFragment:
                    CreateUserFragment createUserFragment = (CreateUserFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createUserFragment != null) {
                        createUserFragment.responseBottomSheet(method, getDate());
                    }
                    break;
                case R.id.editSessionFragment:
                    EditSessionFragment editSessionFragment = (EditSessionFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (editSessionFragment != null) {
                        switch (method) {
                            case "startDate":
                                EditSessionTimeFragment editSessionTimeFragment = (EditSessionTimeFragment) editSessionFragment.adapter.hashMap.get(editSessionFragment.binding.viewPager.getCurrentItem());

                                editSessionTimeFragment.responseBottomSheet(method, getDate());
                                break;
                            case "startAccurateDate":
                            case "endAccurateDate":
                                EditSessionSessionFragment editSessionSessionFragment = (EditSessionSessionFragment) editSessionFragment.adapter.hashMap.get(editSessionFragment.binding.viewPager.getCurrentItem());

                                editSessionSessionFragment.responseBottomSheet(method, getDate());
                                break;
                        }
                    }
                    break;
                case R.id.editUserFragment:
                    EditUserFragment editUserFragment = (EditUserFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (editUserFragment != null) {
                        EditUserPersonalFragment editUserPersonalFragment = (EditUserPersonalFragment) editUserFragment.adapter.hashMap.get(editUserFragment.binding.viewPager.getCurrentItem());

                        editUserPersonalFragment.responseBottomSheet(method, getDate());
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

    public void setDate(int year, int month, int day, String method) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.method = method;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}