package com.majazeh.risloo.Views.Fragments.Main.Tab;

import android.annotation.SuppressLint;
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
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Dialog.DialogSelectedAdapter;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateSessionFragment;
import com.majazeh.risloo.databinding.FragmentCreateSessionTabSessionBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class CreateSessionTabSessionFragment extends Fragment {

    // Binding
    public FragmentCreateSessionTabSessionBinding binding;

    // Adapters
    public DialogSelectedAdapter axisesAdapter;

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
        binding = FragmentCreateSessionTabSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        axisesAdapter = new DialogSelectedAdapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTabSessionStatusHeader));
        binding.axisIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTabSessionAxisHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTabSessionDescriptionHeader));
        binding.coordinationIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTabSessionCoordinationHeader));

        binding.axisGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSessionTabSessionAxisGuide));
        binding.coordinationGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSessionTabSessionCoordinationGuide));

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

        InitManager.input12sspSpinner(requireActivity(), binding.statusIncludeLayout.selectSpinner, R.array.SessionStatus);

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.axisIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateSessionTabSessionButton), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_lightblue500_ripple_lightblue800);
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

        binding.axisIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showSelectedDialog(requireActivity(), "axises");
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.coordinationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.coordinationIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(requireActivity(), binding.coordinationIncludeLayout.inputEditText);
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
                ((MainActivity) requireActivity()).inputon.select(requireActivity(), binding.startRelativeIncludeLayout.dayEditText);
            return false;
        });

        binding.startRelativeIncludeLayout.hourEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.startRelativeIncludeLayout.hourEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(requireActivity(), binding.startRelativeIncludeLayout.hourEditText);
            return false;
        });

        binding.startRelativeIncludeLayout.minuteEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.startRelativeIncludeLayout.minuteEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(requireActivity(), binding.startRelativeIncludeLayout.minuteEditText);
            return false;
        });

        binding.endRelativeIncludeLayout.dayEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.endRelativeIncludeLayout.dayEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(requireActivity(), binding.endRelativeIncludeLayout.dayEditText);
            return false;
        });

        binding.endRelativeIncludeLayout.hourEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.endRelativeIncludeLayout.hourEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(requireActivity(), binding.endRelativeIncludeLayout.hourEditText);
            return false;
        });

        binding.endRelativeIncludeLayout.minuteEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.endRelativeIncludeLayout.minuteEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(requireActivity(), binding.endRelativeIncludeLayout.minuteEditText);
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
            if (current instanceof CreateSessionFragment)
                ((CreateSessionFragment) current).checkRequire();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "axises");

        startAccurateTime = String.valueOf(DateManager.currentTimestamp());
        binding.startAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHsMM(startAccurateTime));

        startAccurateDate = String.valueOf(DateManager.currentTimestamp());
        binding.startAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(startAccurateDate, "-"));

        endAccurateTime = String.valueOf(DateManager.currentTimestamp());
        binding.endAccurateIncludeLayout.timeTextView.setText(DateManager.jalHHsMM(startAccurateTime));

        endAccurateDate = String.valueOf(DateManager.currentTimestamp());
        binding.endAccurateIncludeLayout.dateTextView.setText(DateManager.jalYYYYsMMsDD(endAccurateDate, "-"));

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

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
        if (method.equals("axises")) {
            axisesAdapter.setItems(items, ids, method, binding.axisIncludeLayout.countTextView);
            binding.axisIncludeLayout.selectRecyclerView.setAdapter(axisesAdapter);
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