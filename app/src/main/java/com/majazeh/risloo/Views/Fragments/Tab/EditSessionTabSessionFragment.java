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
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.databinding.FragmentEditSessionTabSessionBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;

public class EditSessionTabSessionFragment extends Fragment {

    // Binding
    public FragmentEditSessionTabSessionBinding binding;

    // Fragments
    private Fragment current;

    // Vars
    public String status = "", description = "", coordination = "";
    public String startType = "relative", endType = "relative";
    public String accurateStartTime = "", accurateStartDate = "", accurateEndTime = "", accurateEndDate = "";
    public String relativeStartDay = "", relativeStartHour = "", relativeStartMinute = "", relativeEndDay = "", relativeEndHour = "", relativeEndMinute = "";
    public boolean endAvailable = false;
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionTabSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabSessionStatusHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabSessionDescriptionHeader));
        binding.coordinationIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabSessionCoordinationHeader));

        binding.coordinationGuideLayout.guideTextView.setText(getResources().getString(R.string.EditSessionTabSessionCoordinationGuide));

        InitManager.normal12sspSpinner(requireActivity(), binding.statusIncludeLayout.selectSpinner, R.array.SessionStatus);

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionTabSessionButton), getResources().getColor(R.color.White));
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
        binding.statusIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.statusIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    status = parent.getItemAtPosition(position).toString();

                    if (status.equals("زمان\u200Cبندی شده"))
                        binding.scheduledIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    else
                        binding.scheduledIncludeLayout.getRoot().setVisibility(View.GONE);

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.coordinationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.coordinationIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.coordinationIncludeLayout.inputEditText);
            return false;
        });

        binding.coordinationIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            coordination = binding.coordinationIncludeLayout.inputEditText.getText().toString().trim();
        });

        // -------------------- RadioGroup

        binding.scheduledIncludeLayout.startRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.start_accurate_radioButton:
                    startType = "absolute";

                    binding.scheduledIncludeLayout.startAccurateGroup.setVisibility(View.VISIBLE);
                    binding.scheduledIncludeLayout.startRelativeGroup.setVisibility(View.GONE);
                    break;
                case R.id.start_relative_radioButton:
                    startType = "relative";

                    binding.scheduledIncludeLayout.startAccurateGroup.setVisibility(View.GONE);
                    binding.scheduledIncludeLayout.startRelativeGroup.setVisibility(View.VISIBLE);
                    break;
            }
        });

        binding.scheduledIncludeLayout.endRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.end_accurate_radioButton:
                    endType = "absolute";

                    binding.scheduledIncludeLayout.endAccurateGroup.setVisibility(View.VISIBLE);
                    binding.scheduledIncludeLayout.endRelativeGroup.setVisibility(View.GONE);
                    break;
                case R.id.end_relative_radioButton:
                    endType = "relative";

                    binding.scheduledIncludeLayout.endAccurateGroup.setVisibility(View.GONE);
                    binding.scheduledIncludeLayout.endRelativeGroup.setVisibility(View.VISIBLE);
                    break;
            }
        });

        // -------------------- Checkbox

        binding.scheduledIncludeLayout.endHintCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            endAvailable = isChecked;

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

        // -------------------- Touch

        binding.scheduledIncludeLayout.startRelativeDayEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.scheduledIncludeLayout.startRelativeDayEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.scheduledIncludeLayout.startRelativeDayEditText);
            return false;
        });

        binding.scheduledIncludeLayout.startRelativeHourEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.scheduledIncludeLayout.startRelativeHourEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.scheduledIncludeLayout.startRelativeHourEditText);
            return false;
        });

        binding.scheduledIncludeLayout.startRelativeMinuteEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.scheduledIncludeLayout.startRelativeMinuteEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.scheduledIncludeLayout.startRelativeMinuteEditText);
            return false;
        });

        binding.scheduledIncludeLayout.endRelativeDayEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.scheduledIncludeLayout.endRelativeDayEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.scheduledIncludeLayout.endRelativeDayEditText);
            return false;
        });

        binding.scheduledIncludeLayout.endRelativeHourEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.scheduledIncludeLayout.endRelativeHourEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.scheduledIncludeLayout.endRelativeHourEditText);
            return false;
        });

        binding.scheduledIncludeLayout.endRelativeMinuteEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.scheduledIncludeLayout.endRelativeMinuteEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.scheduledIncludeLayout.endRelativeMinuteEditText);
            return false;
        });

        // -------------------- Focus

        binding.scheduledIncludeLayout.startRelativeDayEditText.setOnFocusChangeListener((v, hasFocus) -> {
            relativeStartDay = binding.scheduledIncludeLayout.startRelativeDayEditText.getText().toString().trim();
        });

        binding.scheduledIncludeLayout.startRelativeHourEditText.setOnFocusChangeListener((v, hasFocus) -> {
            relativeStartHour = binding.scheduledIncludeLayout.startRelativeHourEditText.getText().toString().trim();
        });

        binding.scheduledIncludeLayout.startRelativeMinuteEditText.setOnFocusChangeListener((v, hasFocus) -> {
            relativeStartMinute = binding.scheduledIncludeLayout.startRelativeMinuteEditText.getText().toString().trim();
        });

        binding.scheduledIncludeLayout.endRelativeDayEditText.setOnFocusChangeListener((v, hasFocus) -> {
            relativeEndDay = binding.scheduledIncludeLayout.endRelativeDayEditText.getText().toString().trim();
        });

        binding.scheduledIncludeLayout.endRelativeHourEditText.setOnFocusChangeListener((v, hasFocus) -> {
            relativeEndHour = binding.scheduledIncludeLayout.endRelativeHourEditText.getText().toString().trim();
        });

        binding.scheduledIncludeLayout.endRelativeMinuteEditText.setOnFocusChangeListener((v, hasFocus) -> {
            relativeEndMinute = binding.scheduledIncludeLayout.endRelativeMinuteEditText.getText().toString().trim();
        });

        // -------------------- BottomSheet

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showTimeBottomSheet(requireActivity(), accurateStartTime, "accurateStartTime");
        }).widget(binding.scheduledIncludeLayout.startAccurateTimeTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showDateBottomSheet(requireActivity(), accurateStartDate, "accurateStartDate");
        }).widget(binding.scheduledIncludeLayout.startAccurateDateTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showTimeBottomSheet(requireActivity(), accurateEndTime, "accurateEndTime");
        }).widget(binding.scheduledIncludeLayout.endAccurateTimeTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showDateBottomSheet(requireActivity(), accurateEndDate, "accurateEndDate");
        }).widget(binding.scheduledIncludeLayout.endAccurateDateTextView);

        // -------------------- Done

        CustomClickView.onDelayedListener(() -> {
            if (current instanceof EditSessionFragment)
                ((EditSessionFragment) current).checkRequire();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof EditSessionFragment) {
            SessionModel model = ((EditSessionFragment) current).sessionModel;

            if (model.getStatus() != null && !model.getStatus().equals("")) {
                status = SelectionManager.getSessionStatus(requireActivity(), "fa", model.getStatus());
                for (int i=0; i<binding.statusIncludeLayout.selectSpinner.getCount(); i++) {
                    if (binding.statusIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(status)) {
                        binding.statusIncludeLayout.selectSpinner.setSelection(i);

                        if (status.equals("زمان\u200Cبندی شده"))
                            binding.scheduledIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                        else
                            binding.scheduledIncludeLayout.getRoot().setVisibility(View.GONE);
                    }
                }
            }

            if (model.getDescription() != null && !model.getDescription().equals("")) {
                description = model.getDescription();
                binding.descriptionIncludeLayout.inputEditText.setText(description);
            }

            if (model.getClient_reminder() != null && !model.getClient_reminder().equals("")) {
                coordination = model.getClient_reminder();
                binding.coordinationIncludeLayout.inputEditText.setText(coordination);
            }

            accurateStartTime = String.valueOf(DateManager.currentTimestamp());
            binding.scheduledIncludeLayout.startAccurateTimeTextView.setText(DateManager.jalHHsMM(accurateStartTime));

            accurateStartDate = String.valueOf(DateManager.currentTimestamp());
            binding.scheduledIncludeLayout.startAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(accurateStartDate, "-"));

            accurateEndTime = String.valueOf(DateManager.currentTimestamp());
            binding.scheduledIncludeLayout.endAccurateTimeTextView.setText(DateManager.jalHHsMM(accurateStartTime));

            accurateEndDate = String.valueOf(DateManager.currentTimestamp());
            binding.scheduledIncludeLayout.endAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(accurateEndDate, "-"));
        }
    }

    public void responseBottomSheet(String method, String data) {
        switch (method) {
            case "accurateStartTime":
                accurateStartTime = data;
                binding.scheduledIncludeLayout.startAccurateTimeTextView.setText(DateManager.jalHHsMM(accurateStartTime));
                break;
            case "accurateStartDate":
                accurateStartDate = data;
                binding.scheduledIncludeLayout.startAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(accurateStartDate, "-"));
                break;
            case "accurateEndTime":
                accurateEndTime = data;
                binding.scheduledIncludeLayout.endAccurateTimeTextView.setText(DateManager.jalHHsMM(accurateEndTime));
                break;
            case "accurateEndDate":
                accurateEndDate = data;
                binding.scheduledIncludeLayout.endAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(accurateEndDate, "-"));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}