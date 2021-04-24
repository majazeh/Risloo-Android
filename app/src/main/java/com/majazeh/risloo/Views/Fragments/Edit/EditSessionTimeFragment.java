package com.majazeh.risloo.Views.Fragments.Edit;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.TimeBottomSheet;
import com.majazeh.risloo.databinding.FragmentEditSessionTimeBinding;

public class EditSessionTimeFragment extends Fragment {

    // Binding
    private FragmentEditSessionTimeBinding binding;

    // BottomSheets
    private TimeBottomSheet startTimeBottomSheet;
    private DateBottomSheet startDateBottomSheet;

    // Vars
    private String startTime = "", duration = "60", startDate = "";
    private int hour, minute, year, month, day;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionTimeBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        startTimeBottomSheet = new TimeBottomSheet();
        startDateBottomSheet = new DateBottomSheet();

        binding.startTimeIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.EditSessionTimeFragmentStartTimeHeader), 5, 19, getResources().getColor(R.color.Gray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.durationIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.EditSessionTimeFragmentDurationHeader), 14, 21, getResources().getColor(R.color.Gray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.startDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTimeFragmentStartDateHeader));

        binding.durationIncludeLayout.inputEditText.setText(duration);

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionTimeFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            startTimeBottomSheet.show(requireActivity().getSupportFragmentManager(), "startTimeBottomSheet");
            startTimeBottomSheet.setTime(hour, minute, "startTime");
        }).widget(binding.startTimeIncludeLayout.selectTextView);

        binding.durationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.durationIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.durationIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            startDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "startDateBottomSheet");
            startDateBottomSheet.setDate(year, month, day, "startDate");
        }).widget(binding.startDateIncludeLayout.selectTextView);

        ClickManager.onDelayedClickListener(() -> {
            if (startTime.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.startTimeIncludeLayout.selectTextView, binding.startTimeIncludeLayout.errorImageView, binding.startTimeIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.durationIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.durationIncludeLayout.inputEditText, binding.durationIncludeLayout.errorImageView, binding.durationIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (startDate.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.startDateIncludeLayout.selectTextView, binding.startDateIncludeLayout.errorImageView, binding.startDateIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (!startTime.equals("") && binding.durationIncludeLayout.inputEditText.length() != 0 && !startDate.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.startTimeIncludeLayout.selectTextView, binding.startTimeIncludeLayout.errorImageView, binding.startTimeIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.durationIncludeLayout.inputEditText, binding.durationIncludeLayout.errorImageView, binding.durationIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.startDateIncludeLayout.selectTextView, binding.startDateIncludeLayout.errorImageView, binding.startDateIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getStartTime().equals("")) {
            startTime = ((MainActivity) requireActivity()).singleton.getStartTime();
            binding.startTimeIncludeLayout.selectTextView.setText(startTime);
        } else {
            startTime = getResources().getString(R.string.AppTimeDefault);
            binding.startTimeIncludeLayout.selectTextView.setText(startTime);
        }

        hour = Integer.parseInt(DateManager.dateToString("hh", DateManager.stringToDate("hh:mm", startTime)));
        minute = Integer.parseInt(DateManager.dateToString("mm", DateManager.stringToDate("hh:mm", startTime)));

        if (!((MainActivity) requireActivity()).singleton.getDuration().equals("")) {
            duration = ((MainActivity) requireActivity()).singleton.getDuration();
            binding.durationIncludeLayout.inputEditText.setText(duration);
        }

        if (!((MainActivity) requireActivity()).singleton.getStartDate().equals("")) {
            startDate = ((MainActivity) requireActivity()).singleton.getStartDate();
            binding.startDateIncludeLayout.selectTextView.setText(startDate);
        } else {
            startDate = getResources().getString(R.string.AppDateDefault);
            binding.startDateIncludeLayout.selectTextView.setText(startDate);
        }

        year = Integer.parseInt(DateManager.dateToString("yyyy", DateManager.stringToDate("yyyy-MM-dd", startDate)));
        month = Integer.parseInt(DateManager.dateToString("MM", DateManager.stringToDate("yyyy-MM-dd", startDate)));
        day = Integer.parseInt(DateManager.dateToString("dd", DateManager.stringToDate("yyyy-MM-dd", startDate)));
    }

    public void responseBottomSheet(String method, String data) {
        switch (method) {
            case "startTime":
                startTime = data;

                hour = startTimeBottomSheet.hour;
                minute = startTimeBottomSheet.minute;

                binding.startTimeIncludeLayout.selectTextView.setText(startTime);
                break;
            case "startDate":
                startDate = data;

                year = startDateBottomSheet.year;
                month = startDateBottomSheet.month;
                day = startDateBottomSheet.day;

                binding.startDateIncludeLayout.selectTextView.setText(startDate);
                break;
        }
    }

    private void doWork() {
        duration = binding.durationIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}