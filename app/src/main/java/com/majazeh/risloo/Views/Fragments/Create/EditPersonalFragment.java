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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class EditPersonalFragment extends Fragment {

    // Vars
    private String name = "", mobile = "", username = "", email = "";

    // Widgets
    private ConstraintLayout nameConstraintLayout, mobileConstraintLayout, usernameConstraintLayout, emailConstraintLayout;
    private TextView nameHeaderTextView, mobileHeaderTextView, usernameHeaderTextView, emailHeaderTextView;
    private EditText nameEditText, mobileEditText, usernameEditText, emailEditText;
    private ImageView nameErrorImageView, mobileErrorImageView, usernameErrorImageView, emailErrorImageView;
    private TextView nameErrorTextView, mobileErrorTextView, usernameErrorTextView, emailErrorTextView;
    private TextView usernameGuideTextView;
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

        nameHeaderTextView = nameConstraintLayout.findViewById(R.id.component_input_text_header_textView);
        nameHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentNameHeader));
        mobileHeaderTextView = mobileConstraintLayout.findViewById(R.id.component_input_number_header_textView);
        mobileHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentMobileHeader));
        usernameHeaderTextView = usernameConstraintLayout.findViewById(R.id.component_input_text_header_textView);
        usernameHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentUsernameHeader));
        emailHeaderTextView = emailConstraintLayout.findViewById(R.id.component_input_text_header_textView);
        emailHeaderTextView.setText(getResources().getString(R.string.EditPersonalFragmentEmailHeader));

        nameEditText = nameConstraintLayout.findViewById(R.id.component_input_text_editText);
        mobileEditText = mobileConstraintLayout.findViewById(R.id.component_input_number_editText);
        usernameEditText = usernameConstraintLayout.findViewById(R.id.component_input_text_editText);
        emailEditText = emailConstraintLayout.findViewById(R.id.component_input_text_editText);

        nameErrorImageView = nameConstraintLayout.findViewById(R.id.component_input_text_error_imageView);
        mobileErrorImageView = mobileConstraintLayout.findViewById(R.id.component_input_number_error_imageView);
        usernameErrorImageView = usernameConstraintLayout.findViewById(R.id.component_input_text_error_imageView);
        emailErrorImageView = emailConstraintLayout.findViewById(R.id.component_input_text_error_imageView);

        nameErrorTextView = nameConstraintLayout.findViewById(R.id.component_input_text_error_textView);
        mobileErrorTextView = mobileConstraintLayout.findViewById(R.id.component_input_number_error_textView);
        usernameErrorTextView = usernameConstraintLayout.findViewById(R.id.component_input_text_error_textView);
        emailErrorTextView = emailConstraintLayout.findViewById(R.id.component_input_text_error_textView);

        usernameGuideTextView = view.findViewById(R.id.component_main_guide_error_textView);
        usernameGuideTextView.setText(getResources().getString(R.string.EditPersonalFragmentUsernameHint));

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
                    ((MainActivity) getActivity()).controlEditText.focus(nameEditText);
                    ((MainActivity) getActivity()).controlEditText.select(nameEditText);
                }
            }
            return false;
        });

        mobileEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!mobileEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.focus(mobileEditText);
                    ((MainActivity) getActivity()).controlEditText.select(mobileEditText);
                }
            }
            return false;
        });

        usernameEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!usernameEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.focus(usernameEditText);
                    ((MainActivity) getActivity()).controlEditText.select(usernameEditText);
                }
            }
            return false;
        });

        emailEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!emailEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.focus(emailEditText);
                    ((MainActivity) getActivity()).controlEditText.select(emailEditText);
                }
            }
            return false;
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
    }

    private void doWork() {
        name = nameEditText.getText().toString().trim();
        mobile = mobileEditText.getText().toString().trim();
        username = usernameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

}