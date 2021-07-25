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
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.TimeBottomSheet;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.databinding.FragmentEditSessionSessionBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;

public class EditSessionSessionFragment extends Fragment {

    // Binding
    public FragmentEditSessionSessionBinding binding;

    // BottomSheets
    private TimeBottomSheet startAccurateTimeBottomSheet, endAccurateTimeBottomSheet;
    private DateBottomSheet startAccurateDateBottomSheet, endAccurateDateBottomSheet;

    // Fragments
    private Fragment current;

    // Vars
    public String status = "", description = "", coordination = "";
    public String startAccurateTime = "", startAccurateDate = "", endAccurateTime = "", endAccurateDate = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        startAccurateTimeBottomSheet = new TimeBottomSheet();
        endAccurateTimeBottomSheet = new TimeBottomSheet();
        startAccurateDateBottomSheet = new DateBottomSheet();
        endAccurateDateBottomSheet = new DateBottomSheet();

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionSessionTabStatusHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionSessionTabDescriptionHeader));
        binding.coordinationIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionSessionTabCoordinationHeader));

        binding.coordinationGuideLayout.guideTextView.setText(getResources().getString(R.string.EditSessionSessionTabCoordinationGuide));

        InitManager.normal12sspSpinner(requireActivity(), binding.statusIncludeLayout.selectSpinner, R.array.SessionStatus);

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionSessionTabButton), getResources().getColor(R.color.White));
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

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.descriptionIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.coordinationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.coordinationIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.coordinationIncludeLayout.inputEditText);
                }
            }
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

            if (model.getOpens_at() != 0) {
                startAccurateTime = String.valueOf(model.getOpens_at());
                binding.scheduledIncludeLayout.startAccurateTimeTextView.setText(DateManager.jalHHsMM(startAccurateTime));
            } else {
                startAccurateTime = String.valueOf(DateManager.currentTimestamp());
                binding.scheduledIncludeLayout.startAccurateTimeTextView.setText(DateManager.jalHHsMM(startAccurateTime));
            }

            if (model.getOpens_at() != 0) {
                startAccurateDate = String.valueOf(model.getOpens_at());
                binding.scheduledIncludeLayout.startAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(startAccurateDate, "-"));
            } else {
                startAccurateDate = String.valueOf(DateManager.currentTimestamp());
                binding.scheduledIncludeLayout.startAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(startAccurateDate, "-"));
            }

            if (model.getClosed_at() != 0) {
                endAccurateTime = String.valueOf(model.getClosed_at());
                binding.scheduledIncludeLayout.endAccurateTimeTextView.setText(DateManager.jalHHsMM(endAccurateTime));
            } else {
                endAccurateTime = String.valueOf(DateManager.currentTimestamp());
                binding.scheduledIncludeLayout.endAccurateTimeTextView.setText(DateManager.jalHHsMM(endAccurateTime));
            }

            if (model.getClosed_at() != 0) {
                endAccurateDate = String.valueOf(model.getClosed_at());
                binding.scheduledIncludeLayout.endAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(endAccurateDate, "-"));
            } else {
                endAccurateDate = String.valueOf(DateManager.currentTimestamp());
                binding.scheduledIncludeLayout.endAccurateDateTextView.setText(DateManager.jalYYYYsMMsDD(endAccurateDate, "-"));
            }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}