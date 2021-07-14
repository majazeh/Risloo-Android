package com.majazeh.risloo.Views.Fragments.Tab;

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
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.TimeBottomSheet;
import com.majazeh.risloo.Views.Dialogs.SelectedDialog;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.databinding.FragmentCreateScheduleSessionBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class CreateScheduleSessionFragment extends Fragment {

    // Binding
    private FragmentCreateScheduleSessionBinding binding;

    // Adapters
    public SelectedAdapter axisesAdapter;

    // Dialogs
    public SelectedDialog axisesDialog;

    // BottomSheets
    private TimeBottomSheet startAccurateTimeBottomSheet, endAccurateTimeBottomSheet;
    private DateBottomSheet startAccurateDateBottomSheet, endAccurateDateBottomSheet;

    // Vars
    private String status = "", description = "", coordination = "";
    private String startAccurateTime = "", startAccurateDate = "", endAccurateTime = "", endAccurateDate = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        axisesAdapter = new SelectedAdapter(requireActivity());

        axisesDialog = new SelectedDialog();

        startAccurateTimeBottomSheet = new TimeBottomSheet();
        endAccurateTimeBottomSheet = new TimeBottomSheet();
        startAccurateDateBottomSheet = new DateBottomSheet();
        endAccurateDateBottomSheet = new DateBottomSheet();

        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleSessionTabStatusHeader));
        binding.axisIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleSessionTabAxisHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleSessionTabDescriptionHeader));
        binding.coordinationIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleSessionTabCoordinationHeader));

        binding.axisGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateScheduleSessionTabAxisGuide));
        binding.coordinationGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateScheduleSessionTabCoordinationGuide));

        InitManager.fixedSpinner(requireActivity(), binding.statusIncludeLayout.selectSpinner, R.array.SessionStatus, "main");

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.axisIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateScheduleSessionTabButton), getResources().getColor(R.color.White));
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
        binding.statusIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getItemAtPosition(position).toString();

                if (status.equals("زمان\u200Cبندی شده"))
                    binding.scheduledIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                else
                    binding.scheduledIncludeLayout.getRoot().setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.axisIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                axisesDialog.show(requireActivity().getSupportFragmentManager(), "axisesDialog");
                axisesDialog.setData("axises");
            }
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.descriptionIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.coordinationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.coordinationIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.coordinationIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.scheduledIncludeLayout.startRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.start_accurate_radioButton:
                    binding.scheduledIncludeLayout.startAccurateGroup.setVisibility(View.VISIBLE);
                    binding.scheduledIncludeLayout.startRelativeGroup.setVisibility(View.GONE);
                    break;
                case R.id.start_relative_radioButton:
                    binding.scheduledIncludeLayout.startAccurateGroup.setVisibility(View.GONE);
                    binding.scheduledIncludeLayout.startRelativeGroup.setVisibility(View.VISIBLE);
                    break;
            }
        });

        binding.scheduledIncludeLayout.startRelativeDayEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.scheduledIncludeLayout.startRelativeDayEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.scheduledIncludeLayout.startRelativeDayEditText);
                }
            }
            return false;
        });

        binding.scheduledIncludeLayout.startRelativeHourEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.scheduledIncludeLayout.startRelativeHourEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.scheduledIncludeLayout.startRelativeHourEditText);
                }
            }
            return false;
        });

        binding.scheduledIncludeLayout.startRelativeMinuteEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.scheduledIncludeLayout.startRelativeMinuteEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.scheduledIncludeLayout.startRelativeMinuteEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            startAccurateTimeBottomSheet.show(requireActivity().getSupportFragmentManager(), "startAccurateTimeBottomSheet");
            startAccurateTimeBottomSheet.setTime(startAccurateTime, "startAccurateTime");
        }).widget(binding.scheduledIncludeLayout.startAccurateTimeTextView);

        ClickManager.onDelayedClickListener(() -> {
            startAccurateDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "startAccurateDateBottomSheet");
            startAccurateDateBottomSheet.setDate(startAccurateDate, "startAccurateDate");
        }).widget(binding.scheduledIncludeLayout.startAccurateDateTextView);

        binding.scheduledIncludeLayout.endRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.end_accurate_radioButton:
                    binding.scheduledIncludeLayout.endAccurateGroup.setVisibility(View.VISIBLE);
                    binding.scheduledIncludeLayout.endRelativeGroup.setVisibility(View.GONE);
                    break;
                case R.id.end_relative_radioButton:
                    binding.scheduledIncludeLayout.endAccurateGroup.setVisibility(View.GONE);
                    binding.scheduledIncludeLayout.endRelativeGroup.setVisibility(View.VISIBLE);
                    break;
            }
        });

        binding.scheduledIncludeLayout.endHintCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.scheduledIncludeLayout.setAlpha((float) 1);
                binding.scheduledIncludeLayout.setEnable(true);
                binding.scheduledIncludeLayout.setFocusableInTouchMode(true);
            } else {
                binding.scheduledIncludeLayout.setAlpha((float) 0.4);
                binding.scheduledIncludeLayout.setEnable(false);
                binding.scheduledIncludeLayout.setFocusableInTouchMode(false);
            }
        });

        binding.scheduledIncludeLayout.endRelativeDayEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (binding.scheduledIncludeLayout.getFocusableInTouchMode()) {
                    if (!binding.scheduledIncludeLayout.endRelativeDayEditText.hasFocus()) {
                        ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.scheduledIncludeLayout.endRelativeDayEditText);
                    }
                }
            }
            return false;
        });

        binding.scheduledIncludeLayout.endRelativeHourEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (binding.scheduledIncludeLayout.getFocusableInTouchMode()) {
                    if (!binding.scheduledIncludeLayout.endRelativeHourEditText.hasFocus()) {
                        ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.scheduledIncludeLayout.endRelativeHourEditText);
                    }
                }
            }
            return false;
        });

        binding.scheduledIncludeLayout.endRelativeMinuteEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (binding.scheduledIncludeLayout.getFocusableInTouchMode()) {
                    if (!binding.scheduledIncludeLayout.endRelativeMinuteEditText.hasFocus()) {
                        ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.scheduledIncludeLayout.endRelativeMinuteEditText);
                    }
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            endAccurateTimeBottomSheet.show(requireActivity().getSupportFragmentManager(), "endAccurateTimeBottomSheet");
            endAccurateTimeBottomSheet.setTime(endAccurateTime, "endAccurateTime");
        }).widget(binding.scheduledIncludeLayout.endAccurateTimeTextView);

        ClickManager.onDelayedClickListener(() -> {
            endAccurateDateBottomSheet.show(requireActivity().getSupportFragmentManager(), "endAccurateDateBottomSheet");
            endAccurateDateBottomSheet.setDate(endAccurateDate, "endAccurateDate");
        }).widget(binding.scheduledIncludeLayout.endAccurateDateTextView);

        ClickManager.onDelayedClickListener(() -> {
            if (status.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.statusIncludeLayout.selectSpinner, binding.statusErrorLayout.getRoot(), binding.statusErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.statusIncludeLayout.selectSpinner, binding.statusErrorLayout.getRoot(), binding.statusErrorLayout.errorTextView);

            if (binding.descriptionIncludeLayout.inputEditText.length() == 0)
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);

            if (binding.coordinationIncludeLayout.inputEditText.length() == 0)
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.coordinationIncludeLayout.inputEditText, binding.coordinationErrorLayout.getRoot(), binding.coordinationErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.coordinationIncludeLayout.inputEditText, binding.coordinationErrorLayout.getRoot(), binding.coordinationErrorLayout.errorTextView);

            if (!status.equals("") && binding.descriptionIncludeLayout.inputEditText.length() != 0 && binding.coordinationIncludeLayout.inputEditText.length() != 0)
                doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        startAccurateTime = String.valueOf(DateManager.currentTimestamp());
        binding.scheduledIncludeLayout.startAccurateTimeTextView.setText(DateManager.jalHHsMM(startAccurateTime));

        startAccurateDate = String.valueOf(DateManager.currentTimestamp());
        binding.scheduledIncludeLayout.startAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(startAccurateDate, "-"));

        endAccurateTime = String.valueOf(DateManager.currentTimestamp());
        binding.scheduledIncludeLayout.endAccurateTimeTextView.setText(DateManager.jalHHsMM(startAccurateTime));

        endAccurateDate = String.valueOf(DateManager.currentTimestamp());
        binding.scheduledIncludeLayout.endAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(endAccurateDate, "-"));

        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "axises");
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
        if (method.equals("axises")) {
            axisesAdapter.setItems(items, ids, method, binding.axisIncludeLayout.countTextView);
            binding.axisIncludeLayout.selectRecyclerView.setAdapter(axisesAdapter);
        }
    }

    public void responseBottomSheet(String method, String data) {
        switch (method) {
            case "startAccurateTime":
                startAccurateTime = data;
                binding.scheduledIncludeLayout.startAccurateTimeTextView.setText(DateManager.jalHHsMM(startAccurateTime));
                break;
            case "startAccurateDate":
                startAccurateDate = data;
                binding.scheduledIncludeLayout.startAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(startAccurateDate, "-"));
                break;
            case "endAccurateTime":
                endAccurateTime = data;
                binding.scheduledIncludeLayout.endAccurateTimeTextView.setText(DateManager.jalHHsMM(endAccurateTime));
                break;
            case "endAccurateDate":
                endAccurateDate = data;
                binding.scheduledIncludeLayout.endAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(endAccurateDate, "-"));
                break;
        }
    }

    private void doWork() {
        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        coordination = binding.coordinationIncludeLayout.inputEditText.getText().toString().trim();

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