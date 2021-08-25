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
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.databinding.FragmentCreateSessionTabSessionBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class CreateSessionTabSessionFragment extends Fragment {

    // Binding
    public FragmentCreateSessionTabSessionBinding binding;

    // Adapters
    public SelectedAdapter axisesAdapter;

    // Fragments
    private Fragment current;

    // Vars
    public String status = "", description = "", coordination = "";
    public String accurateStartTime = "", accurateStartDate = "", accurateEndTime = "", accurateEndDate = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSessionTabSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        axisesAdapter = new SelectedAdapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTabSessionStatusHeader));
        binding.axisIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTabSessionAxisHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTabSessionDescriptionHeader));
        binding.coordinationIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSessionTabSessionCoordinationHeader));

        binding.axisGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSessionTabSessionAxisGuide));
        binding.coordinationGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSessionTabSessionCoordinationGuide));

        InitManager.normal12sspSpinner(requireActivity(), binding.statusIncludeLayout.selectSpinner, R.array.SessionStatus);

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.axisIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateSessionTabSessionButton), getResources().getColor(R.color.White));
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

        binding.axisIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showSelectedDialog(requireActivity(), "axises");
            return false;
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

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showTimeBottomSheet(requireActivity(), accurateStartTime, "accurateStartTime");
        }).widget(binding.scheduledIncludeLayout.startAccurateTimeTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showDateBottomSheet(requireActivity(), accurateStartDate, "accurateStartDate");
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

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showTimeBottomSheet(requireActivity(), accurateEndTime, "accurateEndTime");
        }).widget(binding.scheduledIncludeLayout.endAccurateTimeTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showDateBottomSheet(requireActivity(), accurateEndDate, "accurateEndDate");
        }).widget(binding.scheduledIncludeLayout.endAccurateDateTextView);

        CustomClickView.onDelayedListener(() -> {
            if (current instanceof CreateSessionFragment)
                ((CreateSessionFragment) current).checkRequire();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        accurateStartTime = String.valueOf(DateManager.currentTimestamp());
        binding.scheduledIncludeLayout.startAccurateTimeTextView.setText(DateManager.jalHHsMM(accurateStartTime));

        accurateStartDate = String.valueOf(DateManager.currentTimestamp());
        binding.scheduledIncludeLayout.startAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(accurateStartDate, "-"));

        accurateEndTime = String.valueOf(DateManager.currentTimestamp());
        binding.scheduledIncludeLayout.endAccurateTimeTextView.setText(DateManager.jalHHsMM(accurateStartTime));

        accurateEndDate = String.valueOf(DateManager.currentTimestamp());
        binding.scheduledIncludeLayout.endAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(accurateEndDate, "-"));

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