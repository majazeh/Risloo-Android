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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.TimeBottomSheet;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateSessionTimeBinding;

import org.json.JSONException;

import java.util.ArrayList;

public class CreateSessionTimeFragment extends Fragment {

    // Binding
    private FragmentCreateSessionTimeBinding binding;

    // Adapters
    public SelectedAdapter patternDaysAdapter;

    // Dialogs
    private SearchableDialog patternDaysDialog;

    // BottomSheets
    private TimeBottomSheet startTimeBottomSheet;
    private DateBottomSheet specifiedDateBottomSheet, periodStartDateBottomSheet, periodEndDateBottomSheet;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager patternDaysLayoutManager;

    // Vars
    private String startTime = "", duration = "60", dateType = "", patternType = "", specifiedDate = "", repeatWeeks = "1", periodStartDate = "", periodEndDate = "";
    private int startHour, startMinute, specifiedYear, periodStartYear, periodEndYear, specifiedMonth, periodStartMonth, periodEndMonth, specifiedDay, periodStartDay, periodEndDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSessionTimeBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        patternDaysAdapter = new SelectedAdapter(requireActivity());

        patternDaysDialog = new SearchableDialog();

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, (int) getResources().getDimension(R.dimen._2sdp), 0);

        patternDaysLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        startTimeBottomSheet = new TimeBottomSheet();
        specifiedDateBottomSheet = new DateBottomSheet();
        periodStartDateBottomSheet = new DateBottomSheet();
        periodEndDateBottomSheet = new DateBottomSheet();

        binding.startTimeIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.CreateSessionTimeTabStartTimeHeader), 5, 19, getResources().getColor(R.color.Gray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.durationIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.CreateSessionTimeTabDurationHeader), 14, 21, getResources().getColor(R.color.Gray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.dateTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTimeTabDateTypeHeader));
        binding.specifiedDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTimeTabSpecifiedDateHeader));
        binding.patternDaysIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTimeTabPatternDaysHeader));
        binding.patternTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTimeTabPatternTypeHeader));
        binding.repeatWeeksIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTimeTabRepeatWeeksHeader));
        binding.periodStartDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTimeTabPeriodStartDateHeader));
        binding.periodEndDateIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTimeTabPeriodEndDateHeader));

        binding.dateTypeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateSessionTimeTabDateTypeSpecified));
        binding.dateTypeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateSessionTimeTabDateTypePattern));

        binding.patternTypeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateSessionTimeTabPatternTypeRepeat));
        binding.patternTypeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateSessionTimeTabPatternTypePeriod));

        binding.durationIncludeLayout.inputEditText.setText(duration);
        binding.repeatWeeksIncludeLayout.inputEditText.setText(repeatWeeks);

        InitManager.unfixedRecyclerView(binding.patternDaysIncludeLayout.selectRecyclerView, itemDecoration, patternDaysLayoutManager);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateSessionTimeTabButton), getResources().getColor(R.color.White));
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
            startTimeBottomSheet.setTime(startHour, startMinute, "startTime");
        }).widget(binding.startTimeIncludeLayout.selectTextView);

        binding.durationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.durationIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.durationIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.dateTypeIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    dateType = "specified";

                    binding.specifiedDateGroup.setVisibility(View.VISIBLE);
                    binding.patternDateGroup.setVisibility(View.GONE);

                    if (binding.patternTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton) {
                        binding.repeatPatternGroup.setVisibility(View.GONE);
                    } else {
                        binding.periodPatternGroup.setVisibility(View.GONE);
                    }
                    break;
                case R.id.second_radioButton:
                    dateType = "pattern";

                    binding.specifiedDateGroup.setVisibility(View.GONE);
                    binding.patternDateGroup.setVisibility(View.VISIBLE);

                    if (binding.patternTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton) {
                        binding.repeatPatternGroup.setVisibility(View.VISIBLE);
                    } else {
                        binding.periodPatternGroup.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        });

        ClickManager.onDelayedClickListener(() -> {
            specifiedDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "specifiedDateBottomSheet");
            specifiedDateBottomSheet.setDate(specifiedYear, specifiedMonth, specifiedDay, "specifiedDate");
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

        ClickManager.onDelayedClickListener(() -> {
            periodStartDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "periodStartDateBottomSheet");
            periodStartDateBottomSheet.setDate(periodStartYear, periodStartMonth, periodStartDay, "periodStartDate");
        }).widget(binding.periodStartDateIncludeLayout.selectTextView);

        ClickManager.onDelayedClickListener(() -> {
            periodEndDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "periodEndDateBottomSheet");
            periodEndDateBottomSheet.setDate(periodEndYear, periodEndMonth, periodEndDay, "periodEndDate");
        }).widget(binding.periodEndDateIncludeLayout.selectTextView);

        ClickManager.onDelayedClickListener(() -> {
            if (startTime.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.startTimeIncludeLayout.selectTextView, binding.startTimeErrorLayout.errorImageView, binding.startTimeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.durationIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.durationIncludeLayout.inputEditText, binding.durationErrorLayout.errorImageView, binding.durationErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (!startTime.equals("") && binding.durationIncludeLayout.inputEditText.length() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.startTimeIncludeLayout.selectTextView, binding.startTimeErrorLayout.errorImageView, binding.startTimeErrorLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.durationIncludeLayout.inputEditText, binding.durationErrorLayout.errorImageView, binding.durationErrorLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getStartTime().equals("")) {
            startTime = ((MainActivity) requireActivity()).singleton.getStartTime();
            binding.startTimeIncludeLayout.selectTextView.setText(startTime);
        } else {
            startTime = getResources().getString(R.string.AppDefaultTime);
            binding.startTimeIncludeLayout.selectTextView.setText(startTime);
        }

        startHour = Integer.parseInt(DateManager.dateToString("hh", DateManager.stringToDate("hh:mm", startTime)));
        startMinute = Integer.parseInt(DateManager.dateToString("mm", DateManager.stringToDate("hh:mm", startTime)));

        if (!((MainActivity) requireActivity()).singleton.getDuration().equals("")) {
            duration = ((MainActivity) requireActivity()).singleton.getDuration();
            binding.durationIncludeLayout.inputEditText.setText(duration);
        }

        if (!((MainActivity) requireActivity()).singleton.getType().equals("")) {
            dateType = ((MainActivity) requireActivity()).singleton.getType();
            switch (dateType) {
                case "specified":
                    binding.dateTypeIncludeLayout.firstRadioButton.setChecked(true);

                    binding.specifiedDateGroup.setVisibility(View.VISIBLE);
                    binding.patternDateGroup.setVisibility(View.GONE);

                    if (binding.patternTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton) {
                        binding.repeatPatternGroup.setVisibility(View.GONE);
                    } else {
                        binding.periodPatternGroup.setVisibility(View.GONE);
                    }
                    break;
                case "pattern":
                    binding.dateTypeIncludeLayout.secondRadioButton.setChecked(true);

                    binding.specifiedDateGroup.setVisibility(View.GONE);
                    binding.patternDateGroup.setVisibility(View.VISIBLE);

                    if (binding.patternTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton) {
                        binding.repeatPatternGroup.setVisibility(View.VISIBLE);
                    } else {
                        binding.periodPatternGroup.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }

        if (!((MainActivity) requireActivity()).singleton.getStartDate().equals("")) {
            specifiedDate = ((MainActivity) requireActivity()).singleton.getStartDate();
            binding.specifiedDateIncludeLayout.selectTextView.setText(specifiedDate);
        } else {
            specifiedDate = getResources().getString(R.string.AppDefaultDate);
            binding.specifiedDateIncludeLayout.selectTextView.setText(specifiedDate);
        }

        specifiedYear = Integer.parseInt(DateManager.dateToString("yyyy", DateManager.stringToDate("yyyy-MM-dd", specifiedDate)));
        specifiedMonth = Integer.parseInt(DateManager.dateToString("MM", DateManager.stringToDate("yyyy-MM-dd", specifiedDate)));
        specifiedDay = Integer.parseInt(DateManager.dateToString("dd", DateManager.stringToDate("yyyy-MM-dd", specifiedDate)));

//        if (extras.getString("patternDays") != null) {
//            try {
//                JSONArray jsonArray = new JSONArray(extras.getString("patternDays"));
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                    Model model = new Model(jsonObject);
//
//                    patternDays.add(model);
//                }
//
//                setRecyclerView(patternDays, "patternDays");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "patternDays");
//        }

        if (!((MainActivity) requireActivity()).singleton.getType().equals("")) {
            patternType = ((MainActivity) requireActivity()).singleton.getType();
            switch (patternType) {
                case "repeat":
                    binding.patternTypeIncludeLayout.firstRadioButton.setChecked(true);

                    binding.repeatPatternGroup.setVisibility(View.VISIBLE);
                    binding.periodPatternGroup.setVisibility(View.GONE);
                    break;
                case "period":
                    binding.patternTypeIncludeLayout.secondRadioButton.setChecked(true);

                    binding.repeatPatternGroup.setVisibility(View.GONE);
                    binding.periodPatternGroup.setVisibility(View.VISIBLE);
                    break;
            }
        }

        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            repeatWeeks = ((MainActivity) requireActivity()).singleton.getDuration();
            binding.repeatWeeksIncludeLayout.inputEditText.setText(repeatWeeks);
        }

        if (!((MainActivity) requireActivity()).singleton.getStartDate().equals("")) {
            periodStartDate = ((MainActivity) requireActivity()).singleton.getStartDate();
            binding.periodStartDateIncludeLayout.selectTextView.setText(periodStartDate);
        } else {
            periodStartDate = getResources().getString(R.string.AppDefaultDate);
            binding.periodStartDateIncludeLayout.selectTextView.setText(periodStartDate);
        }

        periodStartYear = Integer.parseInt(DateManager.dateToString("yyyy", DateManager.stringToDate("yyyy-MM-dd", periodStartDate)));
        periodStartMonth = Integer.parseInt(DateManager.dateToString("MM", DateManager.stringToDate("yyyy-MM-dd", periodStartDate)));
        periodStartDay = Integer.parseInt(DateManager.dateToString("dd", DateManager.stringToDate("yyyy-MM-dd", periodStartDate)));

        if (!((MainActivity) requireActivity()).singleton.getStartDate().equals("")) {
            periodEndDate = ((MainActivity) requireActivity()).singleton.getStartDate();
            binding.periodEndDateIncludeLayout.selectTextView.setText(periodEndDate);
        } else {
            periodEndDate = getResources().getString(R.string.AppDefaultDate);
            binding.periodEndDateIncludeLayout.selectTextView.setText(periodEndDate);
        }

        periodEndYear = Integer.parseInt(DateManager.dateToString("yyyy", DateManager.stringToDate("yyyy-MM-dd", periodEndDate)));
        periodEndMonth = Integer.parseInt(DateManager.dateToString("MM", DateManager.stringToDate("yyyy-MM-dd", periodEndDate)));
        periodEndDay = Integer.parseInt(DateManager.dateToString("dd", DateManager.stringToDate("yyyy-MM-dd", periodEndDate)));
    }

    private void setRecyclerView(ArrayList<Model> items, ArrayList<String> ids, String method) {
        if (method.equals("patternDays")) {
            patternDaysAdapter.setItems(items, ids, method, binding.patternDaysIncludeLayout.countTextView);
            binding.patternDaysIncludeLayout.selectRecyclerView.setAdapter(patternDaysAdapter);
        }
    }

    public void responseBottomSheet(String method, String data) {
        switch (method) {
            case "startTime":
                startTime = data;

                startHour = startTimeBottomSheet.hour;
                startMinute = startTimeBottomSheet.minute;

                binding.startTimeIncludeLayout.selectTextView.setText(startTime);
                break;
            case "specifiedDate":
                specifiedDate = data;

                specifiedYear = specifiedDateBottomSheet.year;
                specifiedMonth = specifiedDateBottomSheet.month;
                specifiedDay = specifiedDateBottomSheet.day;

                binding.specifiedDateIncludeLayout.selectTextView.setText(specifiedDate);
                break;
            case "periodStartDate":
                periodStartDate = data;

                periodStartYear = periodStartDateBottomSheet.year;
                periodStartMonth = periodStartDateBottomSheet.month;
                periodStartDay = periodStartDateBottomSheet.day;

                binding.periodStartDateIncludeLayout.selectTextView.setText(periodStartDate);
                break;
            case "periodEndDate":
                periodEndDate = data;

                periodEndYear = periodEndDateBottomSheet.year;
                periodEndMonth = periodEndDateBottomSheet.month;
                periodEndDay = periodEndDateBottomSheet.day;

                binding.periodEndDateIncludeLayout.selectTextView.setText(periodEndDate);
                break;
        }
    }

    public void responseDialog(String method, Model item) {
        try {
            switch (method) {
                case "patternDays":
                    int position = patternDaysAdapter.getIds().indexOf(item.get("id").toString());

                    if (position == -1)
                        patternDaysAdapter.addItem(item);
                    else
                        patternDaysAdapter.removeItem(position);

                    if (patternDaysAdapter.getIds().size() != 0) {
                        binding.patternDaysIncludeLayout.countTextView.setVisibility(View.VISIBLE);
                        binding.patternDaysIncludeLayout.countTextView.setText("(" + patternDaysAdapter.getIds().size() + ")");
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
        duration = binding.durationIncludeLayout.inputEditText.getText().toString().trim();
        repeatWeeks = binding.repeatWeeksIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}