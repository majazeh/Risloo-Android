package com.majazeh.risloo.Views.Fragments.Create;

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
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentCreateSessionBinding;

public class CreateSessionFragment extends Fragment {

    // Binding
    private FragmentCreateSessionBinding binding;

    // Vars
    private String startTime = "", duration = "60", status = "waiting";
    private int year, month, day, hour, minute;

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
        binding.startTimeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionFragmentStartTimeHeader));
        binding.durationIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.CreateSessionFragmentDurationHeader), 14, 21, getResources().getColor(R.color.Gray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionFragmentStatusHeader));

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
            // TODO : Place Code Here
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
            // TODO : Place Code Here
        }).widget(binding.statusIncludeLayout.selectTextView);

        ClickManager.onDelayedClickListener(() -> {
            if (startTime.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.startTimeIncludeLayout.selectTextView, binding.startTimeIncludeLayout.errorImageView, binding.startTimeIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.durationIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.durationIncludeLayout.inputEditText, binding.durationIncludeLayout.errorImageView, binding.durationIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (status.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.statusIncludeLayout.selectTextView, binding.statusIncludeLayout.errorImageView, binding.statusIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (!startTime.equals("") && binding.durationIncludeLayout.inputEditText.length() != 0 && !status.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.startTimeIncludeLayout.selectTextView, binding.startTimeIncludeLayout.errorImageView, binding.startTimeIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.durationIncludeLayout.inputEditText, binding.durationIncludeLayout.errorImageView, binding.durationIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.statusIncludeLayout.selectTextView, binding.statusIncludeLayout.errorImageView, binding.statusIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getStartTime().equals("")) {
            startTime = ((MainActivity) requireActivity()).singleton.getStartTime();
            binding.startTimeIncludeLayout.selectTextView.setText(startTime);
        }
        if (!((MainActivity) requireActivity()).singleton.getDuration().equals("")) {
            duration = ((MainActivity) requireActivity()).singleton.getDuration();
            binding.durationIncludeLayout.inputEditText.setText(duration);
        }
        if (!((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
            status = ((MainActivity) requireActivity()).singleton.getStatus();
            binding.statusIncludeLayout.selectTextView.setText(status);
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