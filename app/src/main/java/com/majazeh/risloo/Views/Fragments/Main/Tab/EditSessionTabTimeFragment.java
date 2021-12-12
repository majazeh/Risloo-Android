package com.majazeh.risloo.Views.Fragments.Main.Tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditSessionFragment;
import com.majazeh.risloo.databinding.FragmentEditSessionTabTimeBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;

import java.util.HashMap;

public class EditSessionTabTimeFragment extends Fragment {

    // Binding
    private FragmentEditSessionTabTimeBinding binding;

    // Fragments
    private Fragment current;

    // Vars
    private String startTime = "", duration = "60", startDate = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionTabTimeBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        binding.startTimeIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.EditSessionTabTimeStartTimeHeader), 5, 19, getResources().getColor(R.color.CoolGray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.durationIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.EditSessionTabTimeDurationHeader), 14, 21, getResources().getColor(R.color.CoolGray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.startDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabTimeStartDateHeader));

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionTabTimeButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.durationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.durationIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(binding.durationIncludeLayout.inputEditText);
            return false;
        });

        binding.durationIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            duration = binding.durationIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showTimeBottomSheet(requireActivity(), startTime, "startTime");
        }).widget(binding.startTimeIncludeLayout.selectTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showDateBottomSheet(requireActivity(), startDate, "startDate");
        }).widget(binding.startDateIncludeLayout.selectTextView);

        CustomClickView.onDelayedListener(() -> {
            if (current instanceof EditSessionFragment)
                ((EditSessionFragment) current).checkRequire();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof EditSessionFragment) {
            SessionModel model = ((EditSessionFragment) current).sessionModel;

            if (model.getStartedAt() != 0) {
                startTime = String.valueOf(model.getStartedAt());
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

            if (model.getStartedAt() != 0) {
                startDate = String.valueOf(model.getStartedAt());
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

    public void setHashmap(HashMap data) {
        if (!startTime.equals(""))
            data.put("time", DateManager.jalHHsMM(startTime));
        else
            data.remove("time");

        if (!duration.equals(""))
            data.put("duration", duration);
        else
            data.remove("duration");

        if (!startDate.equals(""))
            data.put("date", startDate);
        else
            data.remove("date");
    }

    public void hideValid() {
        if (binding.startTimeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.startTimeErrorLayout.getRoot(), binding.startTimeErrorLayout.errorTextView);

        if (binding.durationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.durationErrorLayout.getRoot(), binding.durationErrorLayout.errorTextView);

        if (binding.startDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.startDateErrorLayout.getRoot(), binding.startDateErrorLayout.errorTextView);
    }

    public void showValid(String key, String validation) {
        switch (key) {
            case "time":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.startTimeErrorLayout.getRoot(), binding.startTimeErrorLayout.errorTextView, validation);
                break;
            case "duration":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.durationErrorLayout.getRoot(), binding.durationErrorLayout.errorTextView, validation);
                break;
            case "date":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.startDateErrorLayout.getRoot(), binding.startDateErrorLayout.errorTextView, validation);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}