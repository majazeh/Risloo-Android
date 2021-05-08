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
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.databinding.FragmentEditUserPersonalBinding;

public class EditUserPersonalFragment extends Fragment {

    // Binding
    private FragmentEditUserPersonalBinding binding;

    // BottomSheets
    private DateBottomSheet birthdayBottomSheet;

    // Vars
    private String name = "", mobile = "", username = "", email = "", birthday = "", status ="active", type = "admin", gender = "male";
    private int year, month, day;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUserPersonalBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        birthdayBottomSheet = new DateBottomSheet();

        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPersonalFragmentNameHeader));
        binding.mobileIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPersonalFragmentMobileHeader));
        binding.usernameIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPersonalFragmentUsernameHeader));
        binding.emailIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPersonalFragmentEmailHeader));
        binding.birthdayIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPersonalFragmentBirthdayHeader));
        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPersonalFragmentStatusHeader));
        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPersonalFragmentTypeHeader));
        binding.genderIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPersonalFragmentGenderHeader));

        binding.usernameGuideLayout.guideTextView.setText(getResources().getString(R.string.EditPersonalFragmentUsernameGuide));

        binding.statusIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentStatusActive));
        binding.statusIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentStatusWaiting));
        binding.statusIncludeLayout.thirdRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentStatusClosed));

        binding.typeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentTypeAdmin));
        binding.typeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentTypeClient));

        binding.genderIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentGenderMale));
        binding.genderIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentGenderFemale));

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditPersonalFragmentButton), getResources().getColor(R.color.White));
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
        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.nameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.mobileIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.mobileIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.mobileIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.usernameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.usernameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.usernameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.emailIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.emailIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.emailIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            birthdayBottomSheet.show(requireActivity().getSupportFragmentManager(), "birthdayBottomSheet");
            birthdayBottomSheet.setDate(year, month, day, "birthday");
        }).widget(binding.birthdayIncludeLayout.selectTextView);

        binding.statusIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    status = "active";
                    break;
                case R.id.second_radioButton:
                    status = "waiting";
                    break;
                case R.id.third_radioButton:
                    status = "closed";
                    break;
            }
        });

        binding.typeIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    type = "admin";
                    break;
                case R.id.second_radioButton:
                    type = "client";
                    break;
            }
        });

        binding.genderIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    gender = "male";
                    break;
                case R.id.second_radioButton:
                    gender = "female";
                    break;
            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.nameIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, null, null, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.mobileIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.mobileIncludeLayout.inputEditText, null, null, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.usernameIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.usernameIncludeLayout.inputEditText, null, null, getResources().getString(R.string.AppInputEmpty));
            }

            if (binding.nameIncludeLayout.inputEditText.length() != 0 && binding.mobileIncludeLayout.inputEditText.length() != 0 && binding.usernameIncludeLayout.inputEditText.length() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.nameIncludeLayout.inputEditText, null, null);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.mobileIncludeLayout.inputEditText, null, null);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.usernameIncludeLayout.inputEditText, null, null);

                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getName().equals("")) {
            name = ((MainActivity) requireActivity()).singleton.getName();
            binding.nameIncludeLayout.inputEditText.setText(name);
        }
        if (!((MainActivity) requireActivity()).singleton.getMobile().equals("")) {
            mobile = ((MainActivity) requireActivity()).singleton.getMobile();
            binding.mobileIncludeLayout.inputEditText.setText(mobile);
        }
        if (!((MainActivity) requireActivity()).singleton.getUsername().equals("")) {
            username = ((MainActivity) requireActivity()).singleton.getUsername();
            binding.usernameIncludeLayout.inputEditText.setText(username);
        }
        if (!((MainActivity) requireActivity()).singleton.getEmail().equals("")) {
            email = ((MainActivity) requireActivity()).singleton.getEmail();
            binding.emailIncludeLayout.inputEditText.setText(email);
        }
        if (!((MainActivity) requireActivity()).singleton.getBirthday().equals("")) {
            birthday = ((MainActivity) requireActivity()).singleton.getBirthday();
            binding.birthdayIncludeLayout.selectTextView.setText(birthday);
        } else {
            birthday = getResources().getString(R.string.AppDefaultDate);
            binding.birthdayIncludeLayout.selectTextView.setText(birthday);
        }

        year = Integer.parseInt(DateManager.dateToString("yyyy", DateManager.stringToDate("yyyy-MM-dd", birthday)));
        month = Integer.parseInt(DateManager.dateToString("MM", DateManager.stringToDate("yyyy-MM-dd", birthday)));
        day = Integer.parseInt(DateManager.dateToString("dd", DateManager.stringToDate("yyyy-MM-dd", birthday)));

        if (!((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
            status = ((MainActivity) requireActivity()).singleton.getStatus();
            switch (status) {
                case "active":
                    binding.statusIncludeLayout.firstRadioButton.setChecked(true);
                    break;
                case "waiting":
                    binding.statusIncludeLayout.secondRadioButton.setChecked(true);
                    break;
                case "closed":
                    binding.statusIncludeLayout.thirdRadioButton.setChecked(true);
                    break;
            }
        }
        if (!((MainActivity) requireActivity()).singleton.getType().equals("")) {
            type = ((MainActivity) requireActivity()).singleton.getType();
            switch (type) {
                case "admin":
                    binding.typeIncludeLayout.firstRadioButton.setChecked(true);
                    break;
                case "client":
                    binding.typeIncludeLayout.secondRadioButton.setChecked(true);
                    break;
            }
        }
        if (!((MainActivity) requireActivity()).singleton.getGender().equals("")) {
            gender = ((MainActivity) requireActivity()).singleton.getGender();
            switch (gender) {
                case "male":
                    binding.genderIncludeLayout.firstRadioButton.setChecked(true);
                    break;
                case "female":
                    binding.genderIncludeLayout.secondRadioButton.setChecked(true);
                    break;
            }
        }
    }

    public void responseBottomSheet(String method, String data) {
        switch (method) {
            case "birthday":
                birthday = data;

                year = birthdayBottomSheet.year;
                month = birthdayBottomSheet.month;
                day = birthdayBottomSheet.day;

                binding.birthdayIncludeLayout.selectTextView.setText(birthday);
                break;
        }
    }

    private void doWork() {
        name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        mobile = binding.mobileIncludeLayout.inputEditText.getText().toString().trim();
        username = binding.usernameIncludeLayout.inputEditText.getText().toString().trim();
        email = binding.emailIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}