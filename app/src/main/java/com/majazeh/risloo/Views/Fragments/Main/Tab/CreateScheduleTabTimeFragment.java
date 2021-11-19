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
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Dialog.DialogSelectedAdapter;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateScheduleFragment;
import com.majazeh.risloo.databinding.FragmentCreateScheduleTabTimeBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateScheduleTabTimeFragment extends Fragment {

    // Binding
    private FragmentCreateScheduleTabTimeBinding binding;

    // Adapters
    public DialogSelectedAdapter patternDaysAdapter;

    // Fragments
    private Fragment current;

    // Vars
    private String startTime = "", duration = "60", dateType = "specific", patternType = "weeks", specifiedDate = "", repeatWeeks = "1", periodStartDate = "", periodEndDate = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleTabTimeBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        patternDaysAdapter = new DialogSelectedAdapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        binding.startTimeIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.CreateScheduleTabTimeStartTimeHeader), 5, 19, getResources().getColor(R.color.CoolGray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.durationIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.CreateScheduleTabTimeDurationHeader), 14, 21, getResources().getColor(R.color.CoolGray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.dateTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabTimeDateTypeHeader));
        binding.specifiedDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabTimeSpecifiedDateHeader));
        binding.patternDaysIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabTimePatternDaysHeader));
        binding.patternTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabTimePatternTypeHeader));
        binding.repeatWeeksIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabTimeRepeatWeeksHeader));
        binding.periodStartDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabTimePeriodStartDateHeader));
        binding.periodEndDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabTimePeriodEndDateHeader));

        binding.dateTypeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateScheduleTabTimeDateTypeSpecified));
        binding.dateTypeIncludeLayout.firstRadioButton.setChecked(true);
        binding.dateTypeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateScheduleTabTimeDateTypePattern));

        binding.patternTypeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateScheduleTabTimePatternTypeRepeat));
        binding.patternTypeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateScheduleTabTimePatternTypePeriod));
        binding.patternTypeIncludeLayout.secondRadioButton.setChecked(true);

        binding.durationIncludeLayout.inputEditText.setText(duration);
        binding.repeatWeeksIncludeLayout.inputEditText.setText(repeatWeeks);

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.patternDaysIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateScheduleTabTimeButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.durationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.durationIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(binding.durationIncludeLayout.inputEditText);
            return false;
        });

        binding.repeatWeeksIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.repeatWeeksIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(binding.repeatWeeksIncludeLayout.inputEditText);
            return false;
        });

        binding.durationIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            duration = binding.durationIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.repeatWeeksIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            repeatWeeks = binding.repeatWeeksIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.patternDaysIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showSearchableDialog(requireActivity(), "patternDays");
            return false;
        });

        binding.dateTypeIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    dateType = "specific";

                    binding.specifiedDateGroup.setVisibility(View.VISIBLE);
                    binding.patternDateGroup.setVisibility(View.GONE);

                    if (binding.patternTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton)
                        binding.repeatPatternGroup.setVisibility(View.GONE);
                    else
                        binding.periodPatternGroup.setVisibility(View.GONE);

                    break;
                case R.id.second_radioButton:
                    dateType = "pattern";

                    binding.specifiedDateGroup.setVisibility(View.GONE);
                    binding.patternDateGroup.setVisibility(View.VISIBLE);

                    if (binding.patternTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton)
                        binding.repeatPatternGroup.setVisibility(View.VISIBLE);
                    else
                        binding.periodPatternGroup.setVisibility(View.VISIBLE);

                    break;
            }
        });

        binding.patternTypeIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    patternType = "weeks";

                    binding.repeatPatternGroup.setVisibility(View.VISIBLE);
                    binding.periodPatternGroup.setVisibility(View.GONE);
                    break;
                case R.id.second_radioButton:
                    patternType = "range";

                    binding.repeatPatternGroup.setVisibility(View.GONE);
                    binding.periodPatternGroup.setVisibility(View.VISIBLE);
                    break;
            }
        });

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showTimeBottomSheet(requireActivity(), startTime, "startTime");
        }).widget(binding.startTimeIncludeLayout.selectTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showDateBottomSheet(requireActivity(), specifiedDate, "specifiedDate");
        }).widget(binding.specifiedDateIncludeLayout.selectTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showDateBottomSheet(requireActivity(), periodStartDate, "periodStartDate");
        }).widget(binding.periodStartDateIncludeLayout.selectTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showDateBottomSheet(requireActivity(), periodEndDate, "periodEndDate");
        }).widget(binding.periodEndDateIncludeLayout.selectTextView);

        CustomClickView.onDelayedListener(() -> {
            if (current instanceof CreateScheduleFragment)
                ((CreateScheduleFragment) current).checkRequire();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        startTime = String.valueOf(DateManager.currentTimestamp());
        binding.startTimeIncludeLayout.selectTextView.setText(DateManager.jalHHsMM(startTime));

        specifiedDate = String.valueOf(DateManager.currentTimestamp());
        binding.specifiedDateIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(specifiedDate, "-"));

        periodStartDate = String.valueOf(DateManager.currentTimestamp());
        binding.periodStartDateIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(periodStartDate, "-"));

        periodEndDate = String.valueOf(DateManager.currentTimestamp());
        binding.periodEndDateIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(periodEndDate, "-"));

        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "patternDays");
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
        if (method.equals("patternDays")) {
            patternDaysAdapter.setItems(items, ids, method, binding.patternDaysIncludeLayout.countTextView);
            binding.patternDaysIncludeLayout.selectRecyclerView.setAdapter(patternDaysAdapter);
        }
    }

    public void responseBottomSheet(String method, String data) {
        switch (method) {
            case "startTime":
                startTime = data;
                binding.startTimeIncludeLayout.selectTextView.setText(DateManager.jalHHsMM(startTime));
                break;
            case "specifiedDate":
                specifiedDate = data;
                binding.specifiedDateIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(specifiedDate, "-"));
                break;
            case "periodStartDate":
                periodStartDate = data;
                binding.periodStartDateIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(periodStartDate, "-"));
                break;
            case "periodEndDate":
                periodEndDate = data;
                binding.periodEndDateIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(periodEndDate, "-"));
                break;
        }
    }

    public void responseDialog(String method, TypeModel item) {
        try {
            switch (method) {
                case "patternDays": {
                    int position = patternDaysAdapter.getIds().indexOf(item.object.get("id").toString());

                    if (position == -1)
                        patternDaysAdapter.addItem(item);
                    else
                        patternDaysAdapter.removeItem(position);

                    if (patternDaysAdapter.getIds().size() != 0) {
                        binding.patternDaysIncludeLayout.countTextView.setVisibility(View.VISIBLE);
                        binding.patternDaysIncludeLayout.countTextView.setText(StringManager.bracing(patternDaysAdapter.getIds().size()));
                    } else {
                        binding.patternDaysIncludeLayout.countTextView.setVisibility(View.GONE);
                        binding.patternDaysIncludeLayout.countTextView.setText("");
                    }
                } break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
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

        if (!dateType.equals(""))
            data.put("date_type", dateType);
        else
            data.remove("date_type");

        if (dateType.equals("specific")) {

            if (!specifiedDate.equals(""))
                data.put("date", specifiedDate);
            else
                data.remove("date");

        } else {

            if (!patternDaysAdapter.getIds().isEmpty())
                data.put("week_days", patternDaysAdapter.getIds());
            else
                data.remove("week_days");

            if (!patternType.equals(""))
                data.put("repeat_status", patternType);
            else
                data.remove("repeat_status");

            if (patternType.equals("weeks")) {

                if (!repeatWeeks.equals(""))
                    data.put("repeat", repeatWeeks);
                else
                    data.remove("repeat");

            } else {

                if (!periodStartDate.equals(""))
                    data.put("repeat_from", periodStartDate);
                else
                    data.remove("repeat_from");

                if (!periodEndDate.equals(""))
                    data.put("repeat_to", periodEndDate);
                else
                    data.remove("repeat_to");
            }
        }

    }

    public void hideValid() {
        if (binding.startTimeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.startTimeErrorLayout.getRoot(), binding.startTimeErrorLayout.errorTextView);

        if (binding.durationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.durationErrorLayout.getRoot(), binding.durationErrorLayout.errorTextView);

        if (binding.dateTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.dateTypeErrorLayout.getRoot(), binding.dateTypeErrorLayout.errorTextView);

        if (binding.specifiedDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.specifiedDateErrorLayout.getRoot(), binding.specifiedDateErrorLayout.errorTextView);

        if (binding.patternDaysErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.patternDaysErrorLayout.getRoot(), binding.patternDaysErrorLayout.errorTextView);

        if (binding.patternTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.patternTypeErrorLayout.getRoot(), binding.patternTypeErrorLayout.errorTextView);

        if (binding.repeatWeeksErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.repeatWeeksErrorLayout.getRoot(), binding.repeatWeeksErrorLayout.errorTextView);

        if (binding.periodStartDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.periodStartDateErrorLayout.getRoot(), binding.periodStartDateErrorLayout.errorTextView);

        if (binding.periodEndDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.periodEndDateErrorLayout.getRoot(), binding.periodEndDateErrorLayout.errorTextView);
    }

    public void showValid(String key, String validation) {
        switch (key) {
            case "time":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.startTimeErrorLayout.getRoot(), binding.startTimeErrorLayout.errorTextView, validation);
                break;
            case "duration":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.durationErrorLayout.getRoot(), binding.durationErrorLayout.errorTextView, validation);
                break;
            case "date_type":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.dateTypeErrorLayout.getRoot(), binding.dateTypeErrorLayout.errorTextView, validation);
                break;
            case "date":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.specifiedDateErrorLayout.getRoot(), binding.specifiedDateErrorLayout.errorTextView, validation);
                break;
            case "week_days":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.patternDaysErrorLayout.getRoot(), binding.patternDaysErrorLayout.errorTextView, validation);
                break;
            case "repeat_status":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.patternTypeErrorLayout.getRoot(), binding.patternTypeErrorLayout.errorTextView, validation);
                break;
            case "repeat":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.repeatWeeksErrorLayout.getRoot(), binding.repeatWeeksErrorLayout.errorTextView, validation);
                break;
            case "repeat_from":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.periodStartDateErrorLayout.getRoot(), binding.periodStartDateErrorLayout.errorTextView, validation);
                break;
            case "repeat_to":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.periodEndDateErrorLayout.getRoot(), binding.periodEndDateErrorLayout.errorTextView, validation);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}