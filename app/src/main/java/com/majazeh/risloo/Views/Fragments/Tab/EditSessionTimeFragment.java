package com.majazeh.risloo.Views.Fragments.Tab;

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
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.mre.ligheh.Model.TypeModel.SessionModel;
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

        binding.startTimeIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.EditSessionTimeTabStartTimeHeader), 5, 19, getResources().getColor(R.color.Gray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.durationIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.EditSessionTimeTabDurationHeader), 14, 21, getResources().getColor(R.color.Gray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.startDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTimeTabStartDateHeader));

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionTimeTabButton), getResources().getColor(R.color.White));
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
            startTimeBottomSheet.setTime(startTime, "startTime");
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
            startDateBottomSheet.setDate(startDate, "startDate");
        }).widget(binding.startDateIncludeLayout.selectTextView);

        ClickManager.onDelayedClickListener(() -> {
            if (startTime.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.startTimeIncludeLayout.selectTextView, binding.startTimeErrorLayout.getRoot(), binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.startTimeIncludeLayout.selectTextView, binding.startTimeErrorLayout.getRoot(), binding.startTimeErrorLayout.errorTextView);

            if (binding.durationIncludeLayout.inputEditText.length() == 0)
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.durationIncludeLayout.inputEditText, binding.durationErrorLayout.getRoot(), binding.durationErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.durationIncludeLayout.inputEditText, binding.durationErrorLayout.getRoot(), binding.durationErrorLayout.errorTextView);

            if (!startTime.equals("") && binding.durationIncludeLayout.inputEditText.length() != 0)
                doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        Fragment current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        if (current instanceof EditSessionFragment) {
            SessionModel model = ((EditSessionFragment) current).sessionModel;

            if (model.getStarted_at() != 0) {
                startTime = String.valueOf(model.getStarted_at());
                binding.startTimeIncludeLayout.selectTextView.setText(DateManager.jalHHsMM(startTime));
            } else {
                startTime = String.valueOf(DateManager.currentTimestamp());
                binding.startTimeIncludeLayout.selectTextView.setText(DateManager.jalHHsMM(startTime));
            }

            if (model.getDuration() != 0) {
                duration = String.valueOf(model.getDuration());
                binding.durationIncludeLayout.inputEditText.setText(duration);
            } else {
                binding.durationIncludeLayout.inputEditText.setText(duration);
            }

            if (model.getStarted_at() != 0) {
                startDate = String.valueOf(model.getStarted_at());
                binding.startDateIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(startDate, "-"));
            } else {
                startDate = String.valueOf(DateManager.currentTimestamp());
                binding.startDateIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(startDate, "-"));
            }
        }
    }

    public void responseBottomSheet(String method, String data) {
        switch (method) {
            case "startTime":
                startTime = data;
                binding.startTimeIncludeLayout.selectTextView.setText(DateManager.jalHHsMM(startTime));
                break;
            case "startDate":
                startDate = data;
                binding.startDateIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(startDate, "-"));
                break;
        }
    }

    private void doWork() {
        duration = binding.durationIncludeLayout.inputEditText.getText().toString().trim();

        Fragment current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        if (current instanceof EditSessionFragment)
            ((EditSessionFragment) current).doWork();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}