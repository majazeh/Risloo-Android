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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class EditPersonalFragment extends Fragment {

    // Vars
    private String name = "", mobile = "", username = "", email = "", birthday = "", status ="active", type = "admin", gender = "male";
    private int year, month, day;

    // Widgets
    private ConstraintLayout nameConstraintLayout, mobileConstraintLayout, usernameConstraintLayout, emailConstraintLayout, birthdayConstraintLayout;
    private RadioGroup statusRadioGroup, typeRadioGroup, genderRadioGroup;
    private TextView nameHeaderTextView, mobileHeaderTextView, usernameHeaderTextView, emailHeaderTextView, birthdayHeaderTextView, statusHeaderTextView, typeHeaderTextView, genderHeaderTextView;
    private EditText nameEditText, mobileEditText, usernameEditText, emailEditText;
    public TextView birthdayTextView;
    private ImageView nameErrorImageView, mobileErrorImageView, usernameErrorImageView, emailErrorImageView, birthdayErrorImageView;
    private TextView nameErrorTextView, mobileErrorTextView, usernameErrorTextView, emailErrorTextView, birthdayErrorTextView;
    private TextView usernameGuideTextView;
    private RadioButton activeRadioButton, waitingRadioButton, closedRadioButton, adminRadioButton, clientRadioButton, maleRadioButton, femaleRadioButton;
    private TextView editPersonalTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_personal, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        return view;
    }

    private void initializer(View view) {
        nameConstraintLayout = view.findViewById(R.id.fragment_edit_personal_name_editText);
        mobileConstraintLayout = view.findViewById(R.id.fragment_edit_personal_mobile_editText);
        usernameConstraintLayout = view.findViewById(R.id.fragment_edit_personal_username_editText);
        emailConstraintLayout = view.findViewById(R.id.fragment_edit_personal_email_editText);
        birthdayConstraintLayout = view.findViewById(R.id.fragment_edit_personal_birthday_selectBox);

        statusRadioGroup = view.findViewById(R.id.fragment_edit_personal_status_radioGroup);
        typeRadioGroup = view.findViewById(R.id.fragment_edit_personal_type_radioGroup);
        genderRadioGroup = view.findViewById(R.id.fragment_edit_personal_gender_radioGroup);

        nameHeaderTextView = nameConstraintLayout.findViewById(R.id.component_input_text_header_textView);
        nameHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentNameHeader));
        mobileHeaderTextView = mobileConstraintLayout.findViewById(R.id.component_input_number_header_textView);
        mobileHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentMobileHeader));
        usernameHeaderTextView = usernameConstraintLayout.findViewById(R.id.component_input_text_header_textView);
        usernameHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentUsernameHeader));
        emailHeaderTextView = emailConstraintLayout.findViewById(R.id.component_input_text_header_textView);
        emailHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentEmailHeader));
        birthdayHeaderTextView = birthdayConstraintLayout.findViewById(R.id.component_input_select_header_textView);
        birthdayHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentBirthdayHeader));
        statusHeaderTextView = statusRadioGroup.findViewById(R.id.component_radio_three_header_textView);
        statusHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentStatusHeader));
        typeHeaderTextView = typeRadioGroup.findViewById(R.id.component_radio_two_header_textView);
        typeHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentTypeHeader));
        genderHeaderTextView = genderRadioGroup.findViewById(R.id.component_radio_two_header_textView);
        genderHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentGenderHeader));

        nameEditText = nameConstraintLayout.findViewById(R.id.component_input_text_editText);
        mobileEditText = mobileConstraintLayout.findViewById(R.id.component_input_number_editText);
        usernameEditText = usernameConstraintLayout.findViewById(R.id.component_input_text_editText);
        emailEditText = emailConstraintLayout.findViewById(R.id.component_input_text_editText);

        birthdayTextView = birthdayConstraintLayout.findViewById(R.id.component_input_select_textView);

        nameErrorImageView = nameConstraintLayout.findViewById(R.id.component_input_text_error_imageView);
        mobileErrorImageView = mobileConstraintLayout.findViewById(R.id.component_input_number_error_imageView);
        usernameErrorImageView = usernameConstraintLayout.findViewById(R.id.component_input_text_error_imageView);
        emailErrorImageView = emailConstraintLayout.findViewById(R.id.component_input_text_error_imageView);
        birthdayErrorImageView = birthdayConstraintLayout.findViewById(R.id.component_input_select_error_imageView);

        nameErrorTextView = nameConstraintLayout.findViewById(R.id.component_input_text_error_textView);
        mobileErrorTextView = mobileConstraintLayout.findViewById(R.id.component_input_number_error_textView);
        usernameErrorTextView = usernameConstraintLayout.findViewById(R.id.component_input_text_error_textView);
        emailErrorTextView = emailConstraintLayout.findViewById(R.id.component_input_text_error_textView);
        birthdayErrorTextView = birthdayConstraintLayout.findViewById(R.id.component_input_select_error_textView);

        usernameGuideTextView = view.findViewById(R.id.component_guide_text_textView);
        usernameGuideTextView.setText(getResources().getString(R.string.EditPersonalFragmentUsernameHint));

        activeRadioButton = statusRadioGroup.findViewById(R.id.component_radio_three_first_radioButton);
        activeRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentStatusActive));
        waitingRadioButton = statusRadioGroup.findViewById(R.id.component_radio_three_second_radioButton);
        waitingRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentStatusWaiting));
        closedRadioButton = statusRadioGroup.findViewById(R.id.component_radio_three_third_radioButton);
        closedRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentStatusClosed));
        adminRadioButton = typeRadioGroup.findViewById(R.id.component_radio_two_first_radioButton);
        adminRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentTypeAdmin));
        clientRadioButton = typeRadioGroup.findViewById(R.id.component_radio_two_second_radioButton);
        clientRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentTypeClient));
        maleRadioButton = genderRadioGroup.findViewById(R.id.component_radio_two_first_radioButton);
        maleRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentGenderMale));
        femaleRadioButton = genderRadioGroup.findViewById(R.id.component_radio_two_second_radioButton);
        femaleRadioButton.setText(getResources().getString(R.string.EditPersonalFragmentGenderFemale));

        editPersonalTextView = view.findViewById(R.id.fragment_edit_personal_button_textView);
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
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), nameEditText);
                }
            }
            return false;
        });

        mobileEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!mobileEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), mobileEditText);
                }
            }
            return false;
        });

        usernameEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!usernameEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), usernameEditText);
                }
            }
            return false;
        });

        emailEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!emailEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), emailEditText);
                }
            }
            return false;
        });

        birthdayTextView.setOnClickListener(v -> {
            birthdayTextView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> birthdayTextView.setClickable(true), 300);

            ((MainActivity) getActivity()).dateDialog.show(getActivity().getSupportFragmentManager(), "dateSheetDialog");
            ((MainActivity) getActivity()).dateDialog.setDate(year, month, day);
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
            ((MainActivity) getActivity()).handler.postDelayed(() -> editPersonalTextView.setClickable(true), 300);

            if (nameEditText.length() == 0) {
                ((MainActivity) getActivity()).controlEditText.error(getActivity(), nameEditText, nameErrorImageView, nameErrorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (mobileEditText.length() == 0) {
                ((MainActivity) getActivity()).controlEditText.error(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (usernameEditText.length() == 0) {
                ((MainActivity) getActivity()).controlEditText.error(getActivity(), usernameEditText, usernameErrorImageView, usernameErrorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (nameEditText.length() != 0 && mobileEditText.length() != 0 && usernameEditText.length() != 0) {
                ((MainActivity) getActivity()).controlEditText.check(getActivity(), nameEditText, nameErrorImageView, nameErrorTextView);
                ((MainActivity) getActivity()).controlEditText.check(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView);
                ((MainActivity) getActivity()).controlEditText.check(getActivity(), usernameEditText, usernameErrorImageView, usernameErrorTextView);

                doWork();
            }
        });
    }

    private void setData() {
        if (!((MainActivity) getActivity()).singleton.getName().equals("")) {
            name = ((MainActivity) getActivity()).singleton.getName();
            nameEditText.setText(name);
        }
        if (!((MainActivity) getActivity()).singleton.getMobile().equals("")) {
            mobile = ((MainActivity) getActivity()).singleton.getMobile();
            mobileEditText.setText(mobile);
        }
        if (!((MainActivity) getActivity()).singleton.getUsername().equals("")) {
            username = ((MainActivity) getActivity()).singleton.getUsername();
            usernameEditText.setText(username);
        }
        if (!((MainActivity) getActivity()).singleton.getEmail().equals("")) {
            email = ((MainActivity) getActivity()).singleton.getEmail();
            emailEditText.setText(email);
        }
        if (!((MainActivity) getActivity()).singleton.getBirthday().equals("")) {
            birthday = ((MainActivity) getActivity()).singleton.getBirthday();
            birthdayTextView.setText(birthday);
        } else {
            birthday = getResources().getString(R.string.EditPersonalFragmentBirthdayDefault);
            birthdayTextView.setText(birthday);
        }

        year = Integer.parseInt(DateManager.dateToString("yyyy", DateManager.stringToDate("yyyy-MM-dd", birthday)));
        month = Integer.parseInt(DateManager.dateToString("MM", DateManager.stringToDate("yyyy-MM-dd", birthday)));
        day = Integer.parseInt(DateManager.dateToString("dd", DateManager.stringToDate("yyyy-MM-dd", birthday)));

        if (!((MainActivity) getActivity()).singleton.getStatus().equals("")) {
            status = ((MainActivity) getActivity()).singleton.getStatus();
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
        if (!((MainActivity) getActivity()).singleton.getType().equals("")) {
            type = ((MainActivity) getActivity()).singleton.getType();
            switch (type) {
                case "admin":
                    adminRadioButton.setChecked(true);
                    break;
                case "client":
                    clientRadioButton.setChecked(true);
                    break;
            }
        }
        if (!((MainActivity) getActivity()).singleton.getGender().equals("")) {
            gender = ((MainActivity) getActivity()).singleton.getGender();
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

}