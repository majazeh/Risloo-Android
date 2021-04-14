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
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.TimeBottomSheet;
import com.majazeh.risloo.databinding.FragmentCreateSessionBinding;

public class CreateSessionFragment extends Fragment {

    // Binding
    public FragmentCreateSessionBinding binding;

    // BottomSheets
    private DateBottomSheet startDateBottomSheet;
    private TimeBottomSheet startTimeBottomSheet;

    // Vars
    public String startDate = "", startTime = "", duration = "60", status = "";
    public int year, month, day, hour, minute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        startDateBottomSheet = new DateBottomSheet();
        startTimeBottomSheet = new TimeBottomSheet();

        binding.startDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionFragmentStartDateHeader));
        binding.startTimeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionFragmentStartTimeHeader));
        binding.durationIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.CreateSessionFragmentDurationHeader), 14, 21, getResources().getColor(R.color.Gray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionFragmentStatusHeader));

        binding.durationIncludeLayout.inputEditText.setText(duration);

        InitManager.spinner(requireActivity(), binding.statusIncludeLayout.selectSpinner, R.array.SessionStatus);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateSessionFragmentButton), getResources().getColor(R.color.White));
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
        ClickManager.onDelayedClickListener(() -> {
            startDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "startDateBottomSheet");
            startDateBottomSheet.setDate(year, month, day, "startDate");
        }).widget(binding.startDateIncludeLayout.selectTextView);

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

        binding.statusIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (startDate.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.startDateIncludeLayout.selectTextView, binding.startDateIncludeLayout.errorImageView, binding.startDateIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (startTime.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.startTimeIncludeLayout.selectTextView, binding.startTimeIncludeLayout.errorImageView, binding.startTimeIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.durationIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.durationIncludeLayout.inputEditText, binding.durationIncludeLayout.errorImageView, binding.durationIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (status.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.statusIncludeLayout.selectSpinner, binding.statusIncludeLayout.errorImageView, binding.statusIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (!startDate.equals("") && !startTime.equals("") && binding.durationIncludeLayout.inputEditText.length() != 0 && !status.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.startDateIncludeLayout.selectTextView, binding.startDateIncludeLayout.errorImageView, binding.startDateIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.startTimeIncludeLayout.selectTextView, binding.startTimeIncludeLayout.errorImageView, binding.startTimeIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.durationIncludeLayout.inputEditText, binding.durationIncludeLayout.errorImageView, binding.durationIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.statusIncludeLayout.selectSpinner, binding.statusIncludeLayout.errorImageView, binding.statusIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
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
        if (!((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
            status = ((MainActivity) requireActivity()).singleton.getStatus();
            for (int i=0; i<binding.statusIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.statusIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(status)) {
                    binding.statusIncludeLayout.selectSpinner.setSelection(i);
                }
            }
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