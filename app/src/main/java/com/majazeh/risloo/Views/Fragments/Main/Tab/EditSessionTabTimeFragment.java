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
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditSessionFragment;
import com.majazeh.risloo.databinding.FragmentEditSessionTabTimeBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class EditSessionTabTimeFragment extends Fragment {

    // Binding
    public FragmentEditSessionTabTimeBinding binding;

    // Fragments
    private Fragment current;

    // Vars
    public String startTime = "", duration = "60", startDate = "";

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

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionTabTimeButton), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            SheetManager.showTimeBottomSheet(requireActivity(), startTime, "startTime");
        }).widget(binding.startTimeIncludeLayout.selectTextView);

        binding.durationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.durationIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.durationIncludeLayout.inputEditText);
            return false;
        });

        binding.durationIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            duration = binding.durationIncludeLayout.inputEditText.getText().toString().trim();
        });

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}