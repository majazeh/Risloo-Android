package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.AuthActivity;

public class MobileFragment extends Fragment {

    // Vars
    private String mobile = "";

    // Widgets
    private EditText mobileEditText;
    private ImageView mobileErrorImageView;
    private TextView mobileErrorTextView;
    private TextView mobileTextView;
    private TextView loginTextView, registerTextView, passwordRecoverTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mobile, viewGroup, false);

        initializer(view);

        detector();

        listener();

        return view;
    }

    private void initializer(View view) {
        mobileEditText = view.findViewById(R.id.component_auth_input_number_editText);
        mobileEditText.setHint(getResources().getString(R.string.MobileFragmentInput));

        mobileErrorImageView = view.findViewById(R.id.component_auth_input_number_error_imageView);

        mobileErrorTextView = view.findViewById(R.id.component_auth_input_number_error_textView);

        mobileTextView = view.findViewById(R.id.fragment_mobile_button_textView);
        mobileTextView.setText(getResources().getString(R.string.MobileFragmentButton));

        loginTextView = view.findViewById(R.id.fragment_mobile_login_textView);
        loginTextView.setText(getResources().getString(R.string.AuthLogin));
        registerTextView = view.findViewById(R.id.fragment_mobile_register_textView);
        registerTextView.setText(getResources().getString(R.string.AuthRegister));
        passwordRecoverTextView = view.findViewById(R.id.fragment_mobile_password_recover_textView);
        passwordRecoverTextView.setText(getResources().getString(R.string.AuthPasswordRecover));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mobileTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        mobileEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!mobileEditText.hasFocus()) {
                    ((AuthActivity) getActivity()).controlEditText.focus(mobileEditText);
                    ((AuthActivity) getActivity()).controlEditText.select(mobileEditText);
                }
            }
            return false;
        });

        mobileEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mobileEditText.length() == 11) {
                    ((AuthActivity) getActivity()).controlEditText.check(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView);
                    doWork();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mobileTextView.setOnClickListener(v -> {
            mobileTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> mobileTextView.setClickable(true), 300);

            if (mobileEditText.length() == 0) {
                ((AuthActivity) getActivity()).controlEditText.error(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) getActivity()).controlEditText.check(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView);
                doWork();
            }
        });

        loginTextView.setOnClickListener(v -> {
            loginTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> loginTextView.setClickable(true), 300);

            ((AuthActivity) getActivity()).navigator(R.id.loginFragment);
        });

        registerTextView.setOnClickListener(v -> {
            registerTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> registerTextView.setClickable(true), 300);

            ((AuthActivity) getActivity()).navigator(R.id.registerFragment);
        });

        passwordRecoverTextView.setOnClickListener(v -> {
            passwordRecoverTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> passwordRecoverTextView.setClickable(true), 300);

            ((AuthActivity) getActivity()).navigator(R.id.passwordRecoverFragment);
        });
    }

    private void doWork() {
        mobile = mobileEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

}