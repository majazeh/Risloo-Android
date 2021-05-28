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
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTimeFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTimeFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTimeFragment;
import com.majazeh.risloo.databinding.BottomSheetTimeBinding;

public class TimeBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetTimeBinding binding;

    // Vars
    public int hour, minute;
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
            case "startTime":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetStartTimeTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetStartTimeEntry));
                break;
            case "startAccurateTime":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetStartAccurateTimeTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetStartAccurateTimeEntry));
                break;
            case "endAccurateTime":
                binding.titleTextView.setText(getResources().getString(R.string.BottomSheetEndAccurateTimeTitle));
                binding.entryButton.setText(getResources().getString(R.string.BottomSheetEndAccurateTimeEntry));
                break;
        }
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.entryButton.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            Fragment fragment = ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);
            if (fragment != null) {

                if (fragment instanceof CreateSessionFragment) {
                    Fragment childFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null) {
                        if (childFragment instanceof CreateSessionTimeFragment)
                            ((CreateSessionTimeFragment) childFragment).responseBottomSheet(method, getTime());
                        else if (childFragment instanceof CreateSessionSessionFragment)
                            ((CreateSessionSessionFragment) childFragment).responseBottomSheet(method, getTime());
                    }

                } else if (fragment instanceof CreateScheduleFragment) {
                    Fragment childFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null) {
                        if (childFragment instanceof CreateScheduleTimeFragment)
                            ((CreateScheduleTimeFragment) childFragment).responseBottomSheet(method, getTime());
                        else if (childFragment instanceof CreateScheduleSessionFragment)
                            ((CreateScheduleSessionFragment) childFragment).responseBottomSheet(method, getTime());
                    }

                } else if (fragment instanceof EditSessionFragment) {
                    Fragment childFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null) {
                        if (childFragment instanceof EditSessionTimeFragment)
                            ((EditSessionTimeFragment) childFragment).responseBottomSheet(method, getTime());
                        else if (childFragment instanceof EditSessionSessionFragment)
                            ((EditSessionSessionFragment) childFragment).responseBottomSheet(method, getTime());
                    }
                }
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

    private String getTime() {
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

    public void setTime(int hour, int minute, String method) {
        this.hour = hour;
        this.minute = minute;
        this.method = method;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}