package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentEditPersonalBinding;

public class EditPersonalFragment extends Fragment {

    // Binding
    private FragmentEditPersonalBinding binding;

    // Widgets
    private RadioGroup statusRadioGroup, typeRadioGroup, genderRadioGroup;
    private TextView nameHeaderTextView, mobileHeaderTextView, usernameHeaderTextView, emailHeaderTextView, birthdayHeaderTextView, statusHeaderTextView, typeHeaderTextView, genderHeaderTextView;
    private EditText nameEditText, mobileEditText, usernameEditText, emailEditText;
    public TextView birthdayTextView;
    private ImageView nameErrorImageView, mobileErrorImageView, usernameErrorImageView, emailErrorImageView, birthdayErrorImageView;
    private TextView nameErrorTextView, mobileErrorTextView, usernameErrorTextView, emailErrorTextView, birthdayErrorTextView;
    private TextView usernameGuideTextView;
    private RadioButton activeRadioButton, waitingRadioButton, closedRadioButton, adminRadioButton, clientRadioButton, maleRadioButton, femaleRadioButton;
    private TextView editPersonalTextView;

    // Vars
    private String name = "", mobile = "", username = "", email = "", birthday = "", status ="active", type = "admin", gender = "male";
    private int year, month, day;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditPersonalBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        statusRadioGroup = binding.fragmentEditPersonalStatusRadioGroup.componentRadioThree;
        typeRadioGroup = binding.fragmentEditPersonalTypeRadioGroup.componentRadioTwo;
        genderRadioGroup = binding.fragmentEditPersonalGenderRadioGroup.componentRadioTwo;

        nameHeaderTextView = binding.fragmentEditPersonalNameEditText.componentInputTextHeaderTextView;
        nameHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentNameHeader));
        mobileHeaderTextView = binding.fragmentEditPersonalMobileEditText.componentInputNumberHeaderTextView;
        mobileHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentMobileHeader));
        usernameHeaderTextView = binding.fragmentEditPersonalUsernameEditText.componentInputTextHeaderTextView;
        usernameHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentUsernameHeader));
        emailHeaderTextView = binding.fragmentEditPersonalEmailEditText.componentInputTextHeaderTextView;
        emailHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentEmailHeader));
        birthdayHeaderTextView = binding.fragmentEditPersonalBirthdaySelectBox.componentInputSelectHeaderTextView;
        birthdayHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentBirthdayHeader));
        statusHeaderTextView = binding.fragmentEditPersonalStatusRadioGroup.componentRadioThreeHeaderTextView;
        statusHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentStatusHeader));
        typeHeaderTextView = binding.fragmentEditPersonalTypeRadioGroup.componentRadioTwoHeaderTextView;
        typeHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentTypeHeader));
        genderHeaderTextView = binding.fragmentEditPersonalGenderRadioGroup.componentRadioTwoHeaderTextView;
        genderHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentGenderHeader));

        nameEditText = binding.fragmentEditPersonalNameEditText.componentInputTextEditText;
        mobileEditText = binding.fragmentEditPersonalMobileEditText.componentInputNumberEditText;
        usernameEditText = binding.fragmentEditPersonalUsernameEditText.componentInputTextEditText;
        emailEditText = binding.fragmentEditPersonalEmailEditText.componentInputTextEditText;

        birthdayTextView = binding.fragmentEditPersonalBirthdaySelectBox.componentInputSelectTextView;

        nameErrorImageView = binding.fragmentEditPersonalNameEditText.componentInputTextErrorImageView;
        mobileErrorImageView = binding.fragmentEditPersonalMobileEditText.componentInputNumberErrorImageView;
        usernameErrorImageView = binding.fragmentEditPersonalUsernameEditText.componentInputTextErrorImageView;
        emailErrorImageView = binding.fragmentEditPersonalEmailEditText.componentInputTextErrorImageView;
        birthdayErrorImageView = binding.fragmentEditPersonalBirthdaySelectBox.componentInputSelectErrorImageView;

        nameErrorTextView = binding.fragmentEditPersonalNameEditText.componentInputTextErrorTextView;
        mobileErrorTextView = binding.fragmentEditPersonalMobileEditText.componentInputNumberErrorTextView;
        usernameErrorTextView = binding.fragmentEditPersonalUsernameEditText.componentInputTextErrorTextView;
        emailErrorTextView = binding.fragmentEditPersonalEmailEditText.componentInputTextErrorTextView;
        birthdayErrorTextView = binding.fragmentEditPersonalBirthdaySelectBox.componentInputSelectErrorTextView;

        usernameGuideTextView = binding.fragmentEditPersonalUsernameGuideConstraintLayout.componentGuideTextTextView;
        usernameGuideTextView.setText(getResources().getString(R.string.EditPersonalFragmentUsernameHint));

        activeRadioButton = binding.fragmentEditPersonalStatusRadioGroup.componentRadioThreeFirstRadioButton;
        activeRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentStatusActive));
        waitingRadioButton = binding.fragmentEditPersonalStatusRadioGroup.componentRadioThreeSecondRadioButton;
        waitingRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentStatusWaiting));
        closedRadioButton = binding.fragmentEditPersonalStatusRadioGroup.componentRadioThreeThirdRadioButton;
        closedRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentStatusClosed));
        adminRadioButton = binding.fragmentEditPersonalTypeRadioGroup.componentRadioTwoFirstRadioButton;
        adminRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentTypeAdmin));
        clientRadioButton = binding.fragmentEditPersonalTypeRadioGroup.componentRadioTwoSecondRadioButton;
        clientRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentTypeClient));
        maleRadioButton = binding.fragmentEditPersonalGenderRadioGroup.componentRadioTwoFirstRadioButton;
        maleRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentGenderMale));
        femaleRadioButton = binding.fragmentEditPersonalGenderRadioGroup.componentRadioTwoSecondRadioButton;
        femaleRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentGenderFemale));

        editPersonalTextView = binding.fragmentEditPersonalButtonTextView.componentButtonRectangle32sdp;
        editPersonalTextView.setText(getResources().getString(R.string.EditPersonalFragmentButton));
        editPersonalTextView.setTextColor(getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            editPersonalTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            editPersonalTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        nameEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!nameEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(getActivity(), nameEditText);
                }
            }
            return false;
        });

        mobileEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!mobileEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(getActivity(), mobileEditText);
                }
            }
            return false;
        });

        usernameEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!usernameEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(getActivity(), usernameEditText);
                }
            }
            return false;
        });

        emailEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!emailEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(getActivity(), emailEditText);
                }
            }
            return false;
        });

        birthdayTextView.setOnClickListener(v -> {
            birthdayTextView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> birthdayTextView.setClickable(true), 300);

            ((MainActivity) requireActivity()).dateDialog.show(getActivity().getSupportFragmentManager(), "dateSheetDialog");
            ((MainActivity) requireActivity()).dateDialog.setDate(year, month, day);
        });

        statusRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.component_radio_three_first_radioButton:
                    status = "active";
                    break;
                case R.id.component_radio_three_second_radioButton:
                    status = "waiting";
                    break;
                case R.id.component_radio_three_third_radioButton:
                    status = "closed";
                    break;
            }
        });

        typeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.component_radio_two_first_radioButton:
                    type = "admin";
                    break;
                case R.id.component_radio_two_second_radioButton:
                    type = "client";
                    break;
            }
        });

        genderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.component_radio_two_first_radioButton:
                    gender = "male";
                    break;
                case R.id.component_radio_two_second_radioButton:
                    gender = "female";
                    break;
            }
        });

        editPersonalTextView.setOnClickListener(v -> {
            editPersonalTextView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> editPersonalTextView.setClickable(true), 300);

            if (nameEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(getActivity(), nameEditText, nameErrorImageView, nameErrorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (mobileEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (usernameEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(getActivity(), usernameEditText, usernameErrorImageView, usernameErrorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (nameEditText.length() != 0 && mobileEditText.length() != 0 && usernameEditText.length() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(getActivity(), nameEditText, nameErrorImageView, nameErrorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(getActivity(), usernameEditText, usernameErrorImageView, usernameErrorTextView);

                doWork();
            }
        });
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getName().equals("")) {
            name = ((MainActivity) requireActivity()).singleton.getName();
            nameEditText.setText(name);
        }
        if (!((MainActivity) requireActivity()).singleton.getMobile().equals("")) {
            mobile = ((MainActivity) requireActivity()).singleton.getMobile();
            mobileEditText.setText(mobile);
        }
        if (!((MainActivity) requireActivity()).singleton.getUsername().equals("")) {
            username = ((MainActivity) requireActivity()).singleton.getUsername();
            usernameEditText.setText(username);
        }
        if (!((MainActivity) requireActivity()).singleton.getEmail().equals("")) {
            email = ((MainActivity) requireActivity()).singleton.getEmail();
            emailEditText.setText(email);
        }
        if (!((MainActivity) requireActivity()).singleton.getBirthday().equals("")) {
            birthday = ((MainActivity) requireActivity()).singleton.getBirthday();
            birthdayTextView.setText(birthday);
        } else {
            birthday = getResources().getString(R.string.EditPersonalFragmentBirthdayDefault);
            birthdayTextView.setText(birthday);
        }

        year = Integer.parseInt(DateManager.dateToString("yyyy", DateManager.stringToDate("yyyy-MM-dd", birthday)));
        month = Integer.parseInt(DateManager.dateToString("MM", DateManager.stringToDate("yyyy-MM-dd", birthday)));
        day = Integer.parseInt(DateManager.dateToString("dd", DateManager.stringToDate("yyyy-MM-dd", birthday)));

        if (!((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
            status = ((MainActivity) requireActivity()).singleton.getStatus();
            switch (status) {
                case "active":
                    activeRadioButton.setChecked(true);
                    break;
                case "waiting":
                    waitingRadioButton.setChecked(true);
                    break;
                case "closed":
                    closedRadioButton.setChecked(true);
                    break;
            }
        }
        if (!((MainActivity) requireActivity()).singleton.getType().equals("")) {
            type = ((MainActivity) requireActivity()).singleton.getType();
            switch (type) {
                case "admin":
                    adminRadioButton.setChecked(true);
                    break;
                case "client":
                    clientRadioButton.setChecked(true);
                    break;
            }
        }
        if (!((MainActivity) requireActivity()).singleton.getGender().equals("")) {
            gender = ((MainActivity) requireActivity()).singleton.getGender();
            switch (gender) {
                case "male":
                    maleRadioButton.setChecked(true);
                    break;
                case "female":
                    femaleRadioButton.setChecked(true);
                    break;
            }
        }
    }

    private void doWork() {
        name = nameEditText.getText().toString().trim();
        mobile = mobileEditText.getText().toString().trim();
        username = usernameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}