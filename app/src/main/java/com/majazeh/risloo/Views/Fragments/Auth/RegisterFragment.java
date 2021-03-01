package com.majazeh.risloo.Views.Fragments.Auth;

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
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.AuthActivity;

public class RegisterFragment extends Fragment {

    // Vars
    private String name = "", mobile = "";

    // Widgets
    private EditText nameEditText, mobileEditText;
    private ImageView nameErrorImageView, mobileErrorImageView;
    private TextView nameErrorTextView, mobileErrorTextView;
    private TextView registerTextView;
    private TextView loginTextView, passwordRecoverTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, viewGroup, false);

        initializer(view);

        detector();

        listener();

        return view;
    }

    private void initializer(View view) {
        nameEditText = view.findViewById(R.id.component_auth_input_text_editText);
        nameEditText.setHint(getResources().getString(R.string.RegisterFragmentName));
        mobileEditText = view.findViewById(R.id.component_auth_input_number_editText);
        mobileEditText.setHint(getResources().getString(R.string.RegisterFragmentMobile));

        nameErrorImageView = view.findViewById(R.id.component_auth_input_text_error_imageView);
        mobileErrorImageView = view.findViewById(R.id.component_auth_input_number_error_imageView);

        nameErrorTextView = view.findViewById(R.id.component_auth_input_text_error_textView);
        mobileErrorTextView = view.findViewById(R.id.component_auth_input_number_error_textView);

        registerTextView = view.findViewById(R.id.fragment_register_button_textView);
        registerTextView.setText(getResources().getString(R.string.RegisterFragmentButton));

        loginTextView = view.findViewById(R.id.fragment_register_login_textView);
        loginTextView.setText(getResources().getString(R.string.AuthLogin));
        passwordRecoverTextView = view.findViewById(R.id.fragment_register_password_recover_textView);
        passwordRecoverTextView.setText(getResources().getString(R.string.AuthPasswordRecover));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            registerTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        nameEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!nameEditText.hasFocus()) {
                    if (((AuthActivity) getActivity()).controlEditText.input() != null && ((AuthActivity) getActivity()).controlEditText.input().hasFocus()) {
                        ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), ((AuthActivity) getActivity()).controlEditText.input());
                    }

                    ((AuthActivity) getActivity()).controlEditText.focus(nameEditText);
                    ((AuthActivity) getActivity()).controlEditText.select(nameEditText);
                }
            }
            return false;
        });

        mobileEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!mobileEditText.hasFocus()) {
                    if (((AuthActivity) getActivity()).controlEditText.input() != null && ((AuthActivity) getActivity()).controlEditText.input().hasFocus()) {
                        ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), ((AuthActivity) getActivity()).controlEditText.input());
                    }

                    ((AuthActivity) getActivity()).controlEditText.focus(mobileEditText);
                    ((AuthActivity) getActivity()).controlEditText.select(mobileEditText);
                }
            }
            return false;
        });

        registerTextView.setOnClickListener(v -> {
            registerTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> registerTextView.setClickable(true), 300);

            if (((AuthActivity) getActivity()).controlEditText.input() != null && ((AuthActivity) getActivity()).controlEditText.input().hasFocus()) {
                ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), ((AuthActivity) getActivity()).controlEditText.input());
            }

            if (nameEditText.length() == 0) {
                ((AuthActivity) getActivity()).controlEditText.error(getActivity(), nameEditText, nameErrorImageView, nameErrorTextView, "فیلد خالی است.");
            }
            if (mobileEditText.length() == 0) {
                ((AuthActivity) getActivity()).controlEditText.error(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView, "فیلد خالی است.");
            }

            if (nameEditText.length() != 0 && mobileEditText.length() != 0) {
                ((AuthActivity) getActivity()).controlEditText.check(getActivity(), nameEditText, nameErrorImageView, nameErrorTextView);
                ((AuthActivity) getActivity()).controlEditText.check(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView);

                doWork();
            }
        });

        loginTextView.setOnClickListener(v -> {
            loginTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> loginTextView.setClickable(true), 300);

            if (((AuthActivity) getActivity()).controlEditText.input() != null && ((AuthActivity) getActivity()).controlEditText.input().hasFocus()) {
                ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), ((AuthActivity) getActivity()).controlEditText.input());
            }

            ((AuthActivity) getActivity()).navController.navigate(R.id.loginFragment);
        });

        passwordRecoverTextView.setOnClickListener(v -> {
            passwordRecoverTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> passwordRecoverTextView.setClickable(true), 300);

            if (((AuthActivity) getActivity()).controlEditText.input() != null && ((AuthActivity) getActivity()).controlEditText.input().hasFocus()) {
                ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), ((AuthActivity) getActivity()).controlEditText.input());
            }

            ((AuthActivity) getActivity()).navController.navigate(R.id.passwordRecoverFragment);
        });
    }

    private void doWork() {
        name = nameEditText.getText().toString().trim();
        mobile = mobileEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

}