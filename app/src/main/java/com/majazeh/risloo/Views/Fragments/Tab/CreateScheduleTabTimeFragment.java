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
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.databinding.FragmentCreateScheduleTabTimeBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.TimeBottomSheet;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;

import org.json.JSONException;

import java.util.ArrayList;

public class CreateScheduleTabTimeFragment extends Fragment {

    // Binding
    public FragmentCreateScheduleTabTimeBinding binding;

    // Dialogs
    private SearchableDialog patternDaysDialog;

    // Adapters
    public SelectedAdapter patternDaysAdapter;

    // BottomSheets
    private TimeBottomSheet startTimeBottomSheet;
    private DateBottomSheet specifiedDateBottomSheet, periodStartDateBottomSheet, periodEndDateBottomSheet;

    // Fragments
    private Fragment current;

    // Vars
    public String startTime = "", duration = "60", dateType = "specific", patternType = "weeks", specifiedDate = "", repeatWeeks = "1", periodStartDate = "", periodEndDate = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleTabTimeBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        patternDaysDialog = new SearchableDialog();

        patternDaysAdapter = new SelectedAdapter(requireActivity());

        startTimeBottomSheet = new TimeBottomSheet();
        specifiedDateBottomSheet = new DateBottomSheet();
        periodStartDateBottomSheet = new DateBottomSheet();
        periodEndDateBottomSheet = new DateBottomSheet();

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        binding.startTimeIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.CreateScheduleTabTimeStartTimeHeader), 5, 19, getResources().getColor(R.color.Gray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.durationIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.CreateScheduleTabTimeDurationHeader), 14, 21, getResources().getColor(R.color.Gray500), (int) getResources().getDimension(R.dimen._9ssp)));
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

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateScheduleTabTimeButton), getResources().getColor(R.color.White));
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
        CustomClickView.onDelayedListener(() -> {
            startTimeBottomSheet.show(requireActivity().getSupportFragmentManager(), "startTimeBottomSheet");
            startTimeBottomSheet.setTime(startTime, "startTime");
        }).widget(binding.startTimeIncludeLayout.selectTextView);

        binding.durationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.durationIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputManager.select(requireActivity(), binding.durationIncludeLayout.inputEditText);
            return false;
        });

        binding.durationIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            duration = binding.durationIncludeLayout.inputEditText.getText().toString().trim();
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

        CustomClickView.onDelayedListener(() -> {
            specifiedDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "specifiedDateBottomSheet");
            specifiedDateBottomSheet.setDate(specifiedDate, "specifiedDate");
        }).widget(binding.specifiedDateIncludeLayout.selectTextView);

        binding.patternDaysIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                patternDaysDialog.show(requireActivity().getSupportFragmentManager(), "patternDaysDialog");
                patternDaysDialog.setData("patternDays");
            }
            return false;
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

        binding.repeatWeeksIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.repeatWeeksIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputManager.select(requireActivity(), binding.repeatWeeksIncludeLayout.inputEditText);
            return false;
        });

        binding.repeatWeeksIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            repeatWeeks = binding.repeatWeeksIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            periodStartDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "periodStartDateBottomSheet");
            periodStartDateBottomSheet.setDate(periodStartDate, "periodStartDate");
        }).widget(binding.periodStartDateIncludeLayout.selectTextView);

        CustomClickView.onDelayedListener(() -> {
            periodEndDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "periodEndDateBottomSheet");
            periodEndDateBottomSheet.setDate(periodEndDate, "periodEndDate");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}