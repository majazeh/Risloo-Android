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
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.TimeBottomSheet;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateScheduleTimeBinding;

import org.json.JSONException;

import java.util.ArrayList;

public class CreateScheduleTimeFragment extends Fragment {

    // Binding
    public FragmentCreateScheduleTimeBinding binding;

    // Adapters
    public SelectedAdapter patternDaysAdapter;

    // Dialogs
    private SearchableDialog patternDaysDialog;

    // BottomSheets
    private TimeBottomSheet startTimeBottomSheet;
    private DateBottomSheet specifiedDateBottomSheet, periodStartDateBottomSheet, periodEndDateBottomSheet;

    // Vars
    public String startTime = "", duration = "60", dateType = "specified", patternType = "period", specifiedDate = "", repeatWeeks = "1", periodStartDate = "", periodEndDate = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleTimeBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        patternDaysAdapter = new SelectedAdapter(requireActivity());

        patternDaysDialog = new SearchableDialog();

        startTimeBottomSheet = new TimeBottomSheet();
        specifiedDateBottomSheet = new DateBottomSheet();
        periodStartDateBottomSheet = new DateBottomSheet();
        periodEndDateBottomSheet = new DateBottomSheet();

        binding.startTimeIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.CreateScheduleTimeTabStartTimeHeader), 5, 19, getResources().getColor(R.color.Gray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.durationIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.CreateScheduleTimeTabDurationHeader), 14, 21, getResources().getColor(R.color.Gray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.dateTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTimeTabDateTypeHeader));
        binding.specifiedDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTimeTabSpecifiedDateHeader));
        binding.patternDaysIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTimeTabPatternDaysHeader));
        binding.patternTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTimeTabPatternTypeHeader));
        binding.repeatWeeksIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTimeTabRepeatWeeksHeader));
        binding.periodStartDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTimeTabPeriodStartDateHeader));
        binding.periodEndDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTimeTabPeriodEndDateHeader));

        binding.dateTypeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateScheduleTimeTabDateTypeSpecified));
        binding.dateTypeIncludeLayout.firstRadioButton.setChecked(true);
        binding.dateTypeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateScheduleTimeTabDateTypePattern));

        binding.patternTypeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateScheduleTimeTabPatternTypeRepeat));
        binding.patternTypeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateScheduleTimeTabPatternTypePeriod));
        binding.patternTypeIncludeLayout.secondRadioButton.setChecked(true);

        binding.durationIncludeLayout.inputEditText.setText(duration);
        binding.repeatWeeksIncludeLayout.inputEditText.setText(repeatWeeks);

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.patternDaysIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateScheduleTimeTabButton), getResources().getColor(R.color.White));
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

        binding.durationIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            duration = binding.durationIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.dateTypeIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    dateType = "specified";

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

        ClickManager.onDelayedClickListener(() -> {
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
                    patternType = "repeat";

                    binding.repeatPatternGroup.setVisibility(View.VISIBLE);
                    binding.periodPatternGroup.setVisibility(View.GONE);
                    break;
                case R.id.second_radioButton:
                    patternType = "period";

                    binding.repeatPatternGroup.setVisibility(View.GONE);
                    binding.periodPatternGroup.setVisibility(View.VISIBLE);
                    break;
            }
        });

        binding.repeatWeeksIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.repeatWeeksIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.repeatWeeksIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.repeatWeeksIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            repeatWeeks = binding.repeatWeeksIncludeLayout.inputEditText.getText().toString().trim();
        });

        ClickManager.onDelayedClickListener(() -> {
            periodStartDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "periodStartDateBottomSheet");
            periodStartDateBottomSheet.setDate(periodStartDate, "periodStartDate");
        }).widget(binding.periodStartDateIncludeLayout.selectTextView);

        ClickManager.onDelayedClickListener(() -> {
            periodEndDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "periodEndDateBottomSheet");
            periodEndDateBottomSheet.setDate(periodEndDate, "periodEndDate");
        }).widget(binding.periodEndDateIncludeLayout.selectTextView);

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
                case "patternDays":
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

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        Fragment current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        if (current instanceof CreateScheduleFragment)
            ((CreateScheduleFragment) current).doWork();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}