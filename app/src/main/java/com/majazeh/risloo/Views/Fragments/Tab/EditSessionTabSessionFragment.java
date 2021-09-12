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
    public String startAccurateTime = "", startAccurateDate = "", endAccurateTime = "", endAccurateDate = "";
    public String startRelativeDay = "", startRelativeHour = "", startRelativeMinute = "", endRelativeDay = "", endRelativeHour = "", endRelativeMinute = "";
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

        binding.startTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.StartTime));
        binding.startTypeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.AccurateTime));
        binding.startTypeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.RelativeTime));
        binding.startTypeIncludeLayout.secondRadioButton.setChecked(true);

        binding.endTypeIncludeLayout.headerCheckBox.setText(getResources().getString(R.string.EndTime));
        binding.endTypeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.AccurateTime));
        binding.endTypeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.RelativeTime));
        binding.endTypeIncludeLayout.secondRadioButton.setChecked(true);

        binding.startRelativeIncludeLayout.dayTextView.setText(getResources().getString(R.string.DayHint));
        binding.startRelativeIncludeLayout.hourTextView.setText(getResources().getString(R.string.HourHint));
        binding.startRelativeIncludeLayout.minuteTextView.setText(getResources().getString(R.string.MinuteStartHint));

        binding.endRelativeIncludeLayout.dayTextView.setText(getResources().getString(R.string.DayHint));
        binding.endRelativeIncludeLayout.hourTextView.setText(getResources().getString(R.string.HourHint));
        binding.endRelativeIncludeLayout.minuteTextView.setText(getResources().getString(R.string.MinuteEndHint));

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

                    if (status.equals("زمان\u200Cبندی شده")) {
                        binding.scheduledGroup.setVisibility(View.VISIBLE);

                        if (binding.startTypeIncludeLayout.firstRadioButton.isChecked()) {
                            binding.startAccurateIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.startRelativeIncludeLayout.getRoot().setVisibility(View.GONE);
                        }

                        if (binding.endTypeIncludeLayout.firstRadioButton.isChecked()) {
                            binding.endAccurateIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.endRelativeIncludeLayout.getRoot().setVisibility(View.GONE);
                        }

                    } else {
                        binding.scheduledGroup.setVisibility(View.GONE);

                        if (binding.startAccurateIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                            binding.startAccurateIncludeLayout.getRoot().setVisibility(View.GONE);
                        if (binding.endAccurateIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                            binding.endAccurateIncludeLayout.getRoot().setVisibility(View.GONE);
                    }

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

        binding.startTypeIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    startType = "absolute";

                    binding.startRelativeIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.startAccurateIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    break;
                case R.id.second_radioButton:
                    startType = "relative";

                    binding.startRelativeIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.startAccurateIncludeLayout.getRoot().setVisibility(View.GONE);
                    break;
            }
        });

        binding.endTypeIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    endType = "absolute";

                    binding.endRelativeIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.endAccurateIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    break;
                case R.id.second_radioButton:
                    endType = "relative";

                    binding.endRelativeIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.endAccurateIncludeLayout.getRoot().setVisibility(View.GONE);
                    break;
            }
        });

        // -------------------- Checkbox

        binding.endTypeIncludeLayout.headerCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            endAvailable = isChecked;

            if (isChecked) {
                binding.endTypeIncludeLayout.firstRadioButton.setAlpha((float) 1);
                binding.endTypeIncludeLayout.secondRadioButton.setAlpha((float) 1);
                binding.endRelativeIncludeLayout.getRoot().setAlpha((float) 1);
                binding.endAccurateIncludeLayout.getRoot().setAlpha((float) 1);

                binding.endTypeIncludeLayout.firstRadioButton.setEnabled(true);
                binding.endTypeIncludeLayout.secondRadioButton.setEnabled(true);
                binding.endAccurateIncludeLayout.timeTextView.setEnabled(true);
                binding.endAccurateIncludeLayout.dateTextView.setEnabled(true);

                binding.endRelativeIncludeLayout.dayEditText.setFocusableInTouchMode(true);
                binding.endRelativeIncludeLayout.hourEditText.setFocusableInTouchMode(true);
                binding.endRelativeIncludeLayout.minuteEditText.setFocusableInTouchMode(true);
            } else {
                binding.endTypeIncludeLayout.firstRadioButton.setAlpha((float) 0.4);
                binding.endTypeIncludeLayout.secondRadioButton.setAlpha((float) 0.4);
                binding.endRelativeIncludeLayout.getRoot().setAlpha((float) 0.4);
                binding.endAccurateIncludeLayout.getRoot().setAlpha((float) 0.4);

                binding.endTypeIncludeLayout.firstRadioButton.setEnabled(false);
                binding.endTypeIncludeLayout.secondRadioButton.setEnabled(false);
                binding.endAccurateIncludeLayout.timeTextView.setEnabled(false);
                binding.endAccurateIncludeLayout.dateTextView.setEnabled(false);

                binding.endRelativeIncludeLayout.dayEditText.setFocusableInTouchMode(false);
                binding.endRelativeIncludeLayout.hourEditText.setFocusableInTouchMode(false);
                binding.endRelativeIncludeLayout.minuteEditText.setFocusableInTouchMode(false);
            }
        });

        // -------------------- Touch

        binding.startRelativeIncludeLayout.dayEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.startRelativeIncludeLayout.dayEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.startRelativeIncludeLayout.dayEditText);
            return false;
        });

        binding.startRelativeIncludeLayout.hourEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.startRelativeIncludeLayout.hourEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.startRelativeIncludeLayout.hourEditText);
            return false;
        });

        binding.startRelativeIncludeLayout.minuteEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.startRelativeIncludeLayout.minuteEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.startRelativeIncludeLayout.minuteEditText);
            return false;
        });

        binding.endRelativeIncludeLayout.dayEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.endRelativeIncludeLayout.dayEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.endRelativeIncludeLayout.dayEditText);
            return false;
        });

        binding.endRelativeIncludeLayout.hourEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.endRelativeIncludeLayout.hourEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.endRelativeIncludeLayout.hourEditText);
            return false;
        });

        binding.endRelativeIncludeLayout.minuteEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.endRelativeIncludeLayout.minuteEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.endRelativeIncludeLayout.minuteEditText);
            return false;
        });

        // -------------------- Focus

        binding.startRelativeIncludeLayout.dayEditText.setOnFocusChangeListener((v, hasFocus) -> {
            startRelativeDay = binding.startRelativeIncludeLayout.dayEditText.getText().toString().trim();
        });

        binding.startRelativeIncludeLayout.hourEditText.setOnFocusChangeListener((v, hasFocus) -> {
            startRelativeHour = binding.startRelativeIncludeLayout.hourEditText.getText().toString().trim();
        });

        binding.startRelativeIncludeLayout.minuteEditText.setOnFocusChangeListener((v, hasFocus) -> {
            startRelativeMinute = binding.startRelativeIncludeLayout.minuteEditText.getText().toString().trim();
        });

        binding.endRelativeIncludeLayout.dayEditText.setOnFocusChangeListener((v, hasFocus) -> {
            endRelativeDay = binding.endRelativeIncludeLayout.dayEditText.getText().toString().trim();
        });

        binding.endRelativeIncludeLayout.hourEditText.setOnFocusChangeListener((v, hasFocus) -> {
            endRelativeHour = binding.endRelativeIncludeLayout.hourEditText.getText().toString().trim();
        });

        binding.endRelativeIncludeLayout.minuteEditText.setOnFocusChangeListener((v, hasFocus) -> {
            endRelativeMinute = binding.endRelativeIncludeLayout.minuteEditText.getText().toString().trim();
        });

        // -------------------- BottomSheet

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showTimeBottomSheet(requireActivity(), startAccurateTime, "accurateStartTime");
        }).widget(binding.startAccurateIncludeLayout.timeTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showDateBottomSheet(requireActivity(), startAccurateDate, "accurateStartDate");
        }).widget(binding.startAccurateIncludeLayout.dateTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showTimeBottomSheet(requireActivity(), endAccurateTime, "accurateEndTime");
        }).widget(binding.endAccurateIncludeLayout.timeTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showDateBottomSheet(requireActivity(), endAccurateDate, "accurateEndDate");
        }).widget(binding.endAccurateIncludeLayout.dateTextView);

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

                        if (status.equals("زمان\u200Cبندی شده")) {
                            binding.scheduledGroup.setVisibility(View.VISIBLE);

                            if (model.getOpens_at_type() != null && !model.getOpens_at_type().equals("") && model.getOpens_at_type().equals("absolute")) {
                                binding.startAccurateIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                                binding.startRelativeIncludeLayout.getRoot().setVisibility(View.GONE);
                            }

                            if (model.getClosed_at_type() != null && !model.getClosed_at_type().equals("") && model.getClosed_at_type().equals("absolute")) {
                                binding.endAccurateIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                                binding.endRelativeIncludeLayout.getRoot().setVisibility(View.GONE);
                            }

                        } else {
                            binding.scheduledGroup.setVisibility(View.GONE);
                        }
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

            if (model.getOpens_at_type() != null && !model.getOpens_at_type().equals("")) {
                startType = model.getOpens_at_type();
                switch (startType) {
                    case "absolute":
                        binding.startTypeIncludeLayout.firstRadioButton.setChecked(true);
                        break;
                    case "relative":
                        binding.startTypeIncludeLayout.secondRadioButton.setChecked(true);
                        break;
                }
            }

            if (model.getClosed_at_type() != null && !model.getClosed_at_type().equals("")) {
                endType = model.getClosed_at_type();
                switch (endType) {
                    case "absolute":
                        binding.endTypeIncludeLayout.firstRadioButton.setChecked(true);
                        break;
                    case "relative":
                        binding.endTypeIncludeLayout.secondRadioButton.setChecked(true);
                        break;
                }

                binding.endTypeIncludeLayout.headerCheckBox.setChecked(true);

                binding.endTypeIncludeLayout.firstRadioButton.setAlpha((float) 1);
                binding.endTypeIncludeLayout.secondRadioButton.setAlpha((float) 1);
                binding.endRelativeIncludeLayout.getRoot().setAlpha((float) 1);
                binding.endAccurateIncludeLayout.getRoot().setAlpha((float) 1);

                binding.endTypeIncludeLayout.firstRadioButton.setEnabled(true);
                binding.endTypeIncludeLayout.secondRadioButton.setEnabled(true);
                binding.endAccurateIncludeLayout.timeTextView.setEnabled(true);
                binding.endAccurateIncludeLayout.dateTextView.setEnabled(true);

                binding.endRelativeIncludeLayout.dayEditText.setFocusableInTouchMode(true);
                binding.endRelativeIncludeLayout.hourEditText.setFocusableInTouchMode(true);
                binding.endRelativeIncludeLayout.minuteEditText.setFocusableInTouchMode(true);

            } else {
                binding.endTypeIncludeLayout.headerCheckBox.setChecked(true);

                binding.endTypeIncludeLayout.firstRadioButton.setAlpha((float) 0.4);
                binding.endTypeIncludeLayout.secondRadioButton.setAlpha((float) 0.4);
                binding.endRelativeIncludeLayout.getRoot().setAlpha((float) 0.4);
                binding.endAccurateIncludeLayout.getRoot().setAlpha((float) 0.4);

                binding.endTypeIncludeLayout.firstRadioButton.setEnabled(false);
                binding.endTypeIncludeLayout.secondRadioButton.setEnabled(false);
                binding.endAccurateIncludeLayout.timeTextView.setEnabled(false);
                binding.endAccurateIncludeLayout.dateTextView.setEnabled(false);

                binding.endRelativeIncludeLayout.getRoot().setFocusableInTouchMode(false);
                binding.endRelativeIncludeLayout.hourEditText.setFocusableInTouchMode(false);
                binding.endRelativeIncludeLayout.minuteEditText.setFocusableInTouchMode(false);
            }

            if (model.getOpens_at() != 0 && startType.equals("absolute")) {
                startAccurateTime = String.valueOf(model.getOpens_at());
                startAccurateDate = String.valueOf(model.getOpens_at());

                binding.startAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHsMM(startAccurateTime));
                binding.startAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(startAccurateDate, "-"));
            } else {
                startAccurateTime = String.valueOf(DateManager.currentTimestamp());
                startAccurateDate = String.valueOf(DateManager.currentTimestamp());

                binding.startAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHsMM(startAccurateTime));
                binding.startAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(startAccurateDate, "-"));
            }

            if (model.getClosed_at() != 0 && endType.equals("absolute")) {
                endAccurateTime = String.valueOf(model.getClosed_at());
                endAccurateDate = String.valueOf(model.getClosed_at());

                binding.endAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHsMM(endAccurateTime));
                binding.endAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(endAccurateDate, "-"));
            } else {
                endAccurateTime = String.valueOf(DateManager.currentTimestamp());
                endAccurateDate = String.valueOf(DateManager.currentTimestamp());

                binding.endAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHsMM(endAccurateTime));
                binding.endAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(endAccurateDate, "-"));
            }
        }
    }

    public void responseBottomSheet(String method, String data) {
        switch (method) {
            case "accurateStartTime":
                startAccurateTime = data;
                binding.startAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHsMM(startAccurateTime));
                break;
            case "accurateStartDate":
                startAccurateDate = data;
                binding.startAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(startAccurateDate, "-"));
                break;
            case "accurateEndTime":
                endAccurateTime = data;
                binding.endAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHsMM(endAccurateTime));
                break;
            case "accurateEndDate":
                endAccurateDate = data;
                binding.endAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(endAccurateDate, "-"));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}