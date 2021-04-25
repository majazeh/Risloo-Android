package com.majazeh.risloo.Views.Fragments.Create;

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
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.TimeBottomSheet;
import com.majazeh.risloo.databinding.FragmentCreateScheduleSessionBinding;

public class CreateScheduleSessionFragment extends Fragment {

    // Binding
    private FragmentCreateScheduleSessionBinding binding;

    // BottomSheets
    private TimeBottomSheet startAccurateTimeBottomSheet, endAccurateTimeBottomSheet;
    private DateBottomSheet startAccurateDateBottomSheet, endAccurateDateBottomSheet;

    // Vars
    private String type = "", status = "", description = "";
    private String startAccurateTime = "", startAccurateDate = "", endAccurateTime = "", endAccurateDate = "";
    private int startAccurateHour, startAccurateMinute, startAccurateYear, startAccurateMonth, startAccurateDay, endAccurateHour, endAccurateMinute, endAccurateYear, endAccurateMonth, endAccurateDay;
    private int startRelativeDay, startRelativeHour, startRelativeMinute, endRelativeDay, endRelativeHour, endRelativeMinute;
    private boolean hasEndScheduleTime = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        startAccurateTimeBottomSheet = new TimeBottomSheet();
        endAccurateTimeBottomSheet = new TimeBottomSheet();
        startAccurateDateBottomSheet = new DateBottomSheet();
        endAccurateDateBottomSheet = new DateBottomSheet();

        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleSessionFragmentTypeHeader));
        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleSessionFragmentStatusHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleSessionFragmentDescriptionHeader));

        InitManager.spinner(requireActivity(), binding.typeIncludeLayout.selectSpinner, R.array.SessionTypes, "main");
        InitManager.spinner(requireActivity(), binding.statusIncludeLayout.selectSpinner, R.array.SessionStatus, "main");

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateScheduleSessionFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.typeIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.statusIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getItemAtPosition(position).toString();

                if (status.equals("زمان\u200Cبندی شده")) {
                    binding.scheduledIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                } else {
                    binding.scheduledIncludeLayout.getRoot().setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.descriptionIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.scheduledIncludeLayout.startRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.start_accurate_radioButton:
                    binding.scheduledIncludeLayout.startAccurateGroup.setVisibility(View.VISIBLE);
                    binding.scheduledIncludeLayout.startRelativeGroup.setVisibility(View.GONE);
                    break;
                case R.id.start_relative_radioButton:
                    binding.scheduledIncludeLayout.startAccurateGroup.setVisibility(View.GONE);
                    binding.scheduledIncludeLayout.startRelativeGroup.setVisibility(View.VISIBLE);
                    break;
            }
        });

        binding.scheduledIncludeLayout.startRelativeDayEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.scheduledIncludeLayout.startRelativeDayEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.scheduledIncludeLayout.startRelativeDayEditText);
                }
            }
            return false;
        });

        binding.scheduledIncludeLayout.startRelativeHourEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.scheduledIncludeLayout.startRelativeHourEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.scheduledIncludeLayout.startRelativeHourEditText);
                }
            }
            return false;
        });

        binding.scheduledIncludeLayout.startRelativeMinuteEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.scheduledIncludeLayout.startRelativeMinuteEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.scheduledIncludeLayout.startRelativeMinuteEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            startAccurateTimeBottomSheet.show(requireActivity().getSupportFragmentManager(), "startAccurateTimeBottomSheet");
            startAccurateTimeBottomSheet.setTime(startAccurateHour, startAccurateMinute, "startAccurateTime");
        }).widget(binding.scheduledIncludeLayout.startAccurateTimeTextView);

        ClickManager.onDelayedClickListener(() -> {
            startAccurateDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "startAccurateDateBottomSheet");
            startAccurateDateBottomSheet.setDate(startAccurateYear, startAccurateMonth, startAccurateDay, "startAccurateDate");
        }).widget(binding.scheduledIncludeLayout.startAccurateDateTextView);

        binding.scheduledIncludeLayout.endRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.end_accurate_radioButton:
                    binding.scheduledIncludeLayout.endAccurateGroup.setVisibility(View.VISIBLE);
                    binding.scheduledIncludeLayout.endRelativeGroup.setVisibility(View.GONE);
                    break;
                case R.id.end_relative_radioButton:
                    binding.scheduledIncludeLayout.endAccurateGroup.setVisibility(View.GONE);
                    binding.scheduledIncludeLayout.endRelativeGroup.setVisibility(View.VISIBLE);
                    break;
            }
        });

        binding.scheduledIncludeLayout.endHintCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                hasEndScheduleTime = true;

                binding.scheduledIncludeLayout.setAlpha((float) 1);
                binding.scheduledIncludeLayout.setEnable(true);
                binding.scheduledIncludeLayout.setFocusableInTouchMode(true);
            } else {
                hasEndScheduleTime = false;

                binding.scheduledIncludeLayout.setAlpha((float) 0.4);
                binding.scheduledIncludeLayout.setEnable(false);
                binding.scheduledIncludeLayout.setFocusableInTouchMode(false);
            }
        });

        binding.scheduledIncludeLayout.endRelativeDayEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (binding.scheduledIncludeLayout.getFocusableInTouchMode()) {
                    if (!binding.scheduledIncludeLayout.endRelativeDayEditText.hasFocus()) {
                        ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.scheduledIncludeLayout.endRelativeDayEditText);
                    }
                }
            }
            return false;
        });

        binding.scheduledIncludeLayout.endRelativeHourEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (binding.scheduledIncludeLayout.getFocusableInTouchMode()) {
                    if (!binding.scheduledIncludeLayout.endRelativeHourEditText.hasFocus()) {
                        ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.scheduledIncludeLayout.endRelativeHourEditText);
                    }
                }
            }
            return false;
        });

        binding.scheduledIncludeLayout.endRelativeMinuteEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (binding.scheduledIncludeLayout.getFocusableInTouchMode()) {
                    if (!binding.scheduledIncludeLayout.endRelativeMinuteEditText.hasFocus()) {
                        ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.scheduledIncludeLayout.endRelativeMinuteEditText);
                    }
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            endAccurateTimeBottomSheet.show(requireActivity().getSupportFragmentManager(), "endAccurateTimeBottomSheet");
            endAccurateTimeBottomSheet.setTime(endAccurateHour, endAccurateMinute, "endAccurateTime");
        }).widget(binding.scheduledIncludeLayout.endAccurateTimeTextView);

        ClickManager.onDelayedClickListener(() -> {
            endAccurateDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "endAccurateDateBottomSheet");
            endAccurateDateBottomSheet.setDate(endAccurateYear, endAccurateMonth, endAccurateDay, "endAccurateDate");
        }).widget(binding.scheduledIncludeLayout.endAccurateDateTextView);

        ClickManager.onDelayedClickListener(() -> {
            if (type.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.typeIncludeLayout.selectSpinner, binding.typeIncludeLayout.errorImageView, binding.typeIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (status.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.statusIncludeLayout.selectSpinner, binding.statusIncludeLayout.errorImageView, binding.statusIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.descriptionIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionIncludeLayout.errorImageView, binding.descriptionIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (!type.equals("") && !status.equals("") && binding.descriptionIncludeLayout.inputEditText.length() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.typeIncludeLayout.selectSpinner, binding.typeIncludeLayout.errorImageView, binding.typeIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.statusIncludeLayout.selectSpinner, binding.statusIncludeLayout.errorImageView, binding.statusIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionIncludeLayout.errorImageView, binding.descriptionIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getType().equals("")) {
            type = ((MainActivity) requireActivity()).singleton.getType();
            for (int i=0; i<binding.typeIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.typeIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(status)) {
                    binding.typeIncludeLayout.selectSpinner.setSelection(i);
                }
            }
        }

        if (!((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
            status = ((MainActivity) requireActivity()).singleton.getStatus();
            for (int i=0; i<binding.statusIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.statusIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(status)) {
                    binding.statusIncludeLayout.selectSpinner.setSelection(i);
                }
            }
        }

        if (!((MainActivity) requireActivity()).singleton.getDescription().equals("")) {
            description = ((MainActivity) requireActivity()).singleton.getDescription();
            binding.descriptionIncludeLayout.inputEditText.setText(description);
        }

        if (!((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
            String startTime = ((MainActivity) requireActivity()).singleton.getStatus();
            switch (startTime) {
                case "accurate":
                    binding.scheduledIncludeLayout.startAccurateRadioButton.setChecked(true);

                    binding.scheduledIncludeLayout.startAccurateGroup.setVisibility(View.VISIBLE);
                    binding.scheduledIncludeLayout.startRelativeGroup.setVisibility(View.GONE);
                    break;
                case "relative":
                    binding.scheduledIncludeLayout.startRelativeRadioButton.setChecked(true);

                    binding.scheduledIncludeLayout.startAccurateGroup.setVisibility(View.GONE);
                    binding.scheduledIncludeLayout.startRelativeGroup.setVisibility(View.VISIBLE);
                    break;
            }
        }

        if (((MainActivity) requireActivity()).singleton.getProgress() != 0) {
            startRelativeDay = ((MainActivity) requireActivity()).singleton.getProgress();
            binding.scheduledIncludeLayout.startRelativeDayEditText.setText(String.valueOf(startRelativeDay));
        }
        if (((MainActivity) requireActivity()).singleton.getProgress() != 0) {
            startRelativeHour = ((MainActivity) requireActivity()).singleton.getProgress();
            binding.scheduledIncludeLayout.startRelativeHourEditText.setText(String.valueOf(startRelativeHour));
        }
        if (((MainActivity) requireActivity()).singleton.getProgress() != 0) {
            startRelativeMinute = ((MainActivity) requireActivity()).singleton.getProgress();
            binding.scheduledIncludeLayout.startRelativeMinuteEditText.setText(String.valueOf(startRelativeMinute));
        }

        if (!((MainActivity) requireActivity()).singleton.getStartTime().equals("")) {
            startAccurateTime = ((MainActivity) requireActivity()).singleton.getStartTime();
            binding.scheduledIncludeLayout.startAccurateTimeTextView.setText(startAccurateTime);
        } else {
            startAccurateTime = getResources().getString(R.string.AppTimeDefault);
            binding.scheduledIncludeLayout.startAccurateTimeTextView.setText(startAccurateTime);
        }

        startAccurateHour = Integer.parseInt(DateManager.dateToString("hh", DateManager.stringToDate("hh:mm", startAccurateTime)));
        startAccurateMinute = Integer.parseInt(DateManager.dateToString("mm", DateManager.stringToDate("hh:mm", startAccurateTime)));

        if (!((MainActivity) requireActivity()).singleton.getStartDate().equals("")) {
            startAccurateDate = ((MainActivity) requireActivity()).singleton.getStartDate();
            binding.scheduledIncludeLayout.startAccurateDateTextView.setText(startAccurateDate);
        } else {
            startAccurateDate = getResources().getString(R.string.AppDateDefault);
            binding.scheduledIncludeLayout.startAccurateDateTextView.setText(startAccurateDate);
        }

        startAccurateYear = Integer.parseInt(DateManager.dateToString("yyyy", DateManager.stringToDate("yyyy-MM-dd", startAccurateDate)));
        startAccurateMonth = Integer.parseInt(DateManager.dateToString("MM", DateManager.stringToDate("yyyy-MM-dd", startAccurateDate)));
        startAccurateDay = Integer.parseInt(DateManager.dateToString("dd", DateManager.stringToDate("yyyy-MM-dd", startAccurateDate)));

        if (((MainActivity) requireActivity()).singleton.getEndScheduleTime()) {
            hasEndScheduleTime = true;

            binding.scheduledIncludeLayout.setAlpha((float) 1);
            binding.scheduledIncludeLayout.setEnable(true);
            binding.scheduledIncludeLayout.setFocusableInTouchMode(true);
        } else {
            hasEndScheduleTime = false;

            binding.scheduledIncludeLayout.setAlpha((float) 0.4);
            binding.scheduledIncludeLayout.setEnable(false);
            binding.scheduledIncludeLayout.setFocusableInTouchMode(false);
        }

        if (!((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
            String endTime = ((MainActivity) requireActivity()).singleton.getStatus();
            switch (endTime) {
                case "accurate":
                    binding.scheduledIncludeLayout.endAccurateRadioButton.setChecked(true);

                    binding.scheduledIncludeLayout.endAccurateGroup.setVisibility(View.VISIBLE);
                    binding.scheduledIncludeLayout.endRelativeGroup.setVisibility(View.GONE);
                    break;
                case "relative":
                    binding.scheduledIncludeLayout.endRelativeRadioButton.setChecked(true);

                    binding.scheduledIncludeLayout.endAccurateGroup.setVisibility(View.GONE);
                    binding.scheduledIncludeLayout.endRelativeGroup.setVisibility(View.VISIBLE);
                    break;
            }
        }

        if (((MainActivity) requireActivity()).singleton.getProgress() != 0) {
            endRelativeDay = ((MainActivity) requireActivity()).singleton.getProgress();
            binding.scheduledIncludeLayout.endRelativeDayEditText.setText(String.valueOf(endRelativeDay));
        }
        if (((MainActivity) requireActivity()).singleton.getProgress() != 0) {
            endRelativeHour = ((MainActivity) requireActivity()).singleton.getProgress();
            binding.scheduledIncludeLayout.endRelativeHourEditText.setText(String.valueOf(endRelativeHour));
        }
        if (((MainActivity) requireActivity()).singleton.getProgress() != 0) {
            endRelativeMinute = ((MainActivity) requireActivity()).singleton.getProgress();
            binding.scheduledIncludeLayout.endRelativeMinuteEditText.setText(String.valueOf(endRelativeMinute));
        }

        if (!((MainActivity) requireActivity()).singleton.getStartTime().equals("")) {
            endAccurateTime = ((MainActivity) requireActivity()).singleton.getStartTime();
            binding.scheduledIncludeLayout.endAccurateTimeTextView.setText(endAccurateTime);
        } else {
            endAccurateTime = getResources().getString(R.string.AppTimeDefault);
            binding.scheduledIncludeLayout.endAccurateTimeTextView.setText(endAccurateTime);
        }

        endAccurateHour = Integer.parseInt(DateManager.dateToString("hh", DateManager.stringToDate("hh:mm", endAccurateTime)));
        endAccurateMinute = Integer.parseInt(DateManager.dateToString("mm", DateManager.stringToDate("hh:mm", endAccurateTime)));

        if (!((MainActivity) requireActivity()).singleton.getStartDate().equals("")) {
            endAccurateDate = ((MainActivity) requireActivity()).singleton.getStartDate();
            binding.scheduledIncludeLayout.endAccurateDateTextView.setText(endAccurateDate);
        } else {
            endAccurateDate = getResources().getString(R.string.AppDateDefault);
            binding.scheduledIncludeLayout.endAccurateDateTextView.setText(endAccurateDate);
        }

        endAccurateYear = Integer.parseInt(DateManager.dateToString("yyyy", DateManager.stringToDate("yyyy-MM-dd", endAccurateDate)));
        endAccurateMonth = Integer.parseInt(DateManager.dateToString("MM", DateManager.stringToDate("yyyy-MM-dd", endAccurateDate)));
        endAccurateDay = Integer.parseInt(DateManager.dateToString("dd", DateManager.stringToDate("yyyy-MM-dd", endAccurateDate)));

    }

    private void doWork() {
        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    public void responseBottomSheet(String method, String data) {
        switch (method) {
            case "startAccurateTime":
                startAccurateTime = data;

                startAccurateHour = startAccurateTimeBottomSheet.hour;
                startAccurateMinute = startAccurateTimeBottomSheet.minute;

                binding.scheduledIncludeLayout.startAccurateTimeTextView.setText(startAccurateTime);
                break;
            case "startAccurateDate":
                startAccurateDate = data;

                startAccurateYear = startAccurateDateBottomSheet.year;
                startAccurateMonth = startAccurateDateBottomSheet.month;
                startAccurateDay = startAccurateDateBottomSheet.day;

                binding.scheduledIncludeLayout.startAccurateDateTextView.setText(startAccurateDate);
                break;
            case "endAccurateTime":
                endAccurateTime = data;

                endAccurateHour = endAccurateTimeBottomSheet.hour;
                endAccurateMinute = endAccurateTimeBottomSheet.minute;

                binding.scheduledIncludeLayout.endAccurateTimeTextView.setText(endAccurateTime);
                break;
            case "endAccurateDate":
                endAccurateDate = data;

                endAccurateYear = endAccurateDateBottomSheet.year;
                endAccurateMonth = endAccurateDateBottomSheet.month;
                endAccurateDay = endAccurateDateBottomSheet.day;

                binding.scheduledIncludeLayout.endAccurateDateTextView.setText(endAccurateDate);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}