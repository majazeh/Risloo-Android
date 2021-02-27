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

public class PasswordRecoverFragment extends Fragment {

    // Vars
    private String input = "";

    // Widgets
    private EditText inputEditText;
    private ImageView errorImageView;
    private TextView errorTextView;
    private TextView passwordRecoverTextView;
    private TextView loginTextView, registerTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_recover, viewGroup, false);

        initializer(view);

        detector();

        listener();

        return view;
    }

    private void initializer(View view) {
        inputEditText = view.findViewById(R.id.component_auth_input_text_editText);
        inputEditText.setHint(getResources().getString(R.string.PasswordRecoverInput));

        errorImageView = view.findViewById(R.id.component_auth_input_text_error_imageView);

        errorTextView = view.findViewById(R.id.component_auth_input_text_error_textView);

        passwordRecoverTextView = view.findViewById(R.id.fragment_password_recover_button_textView);
        passwordRecoverTextView.setText(getResources().getString(R.string.PasswordRecoverButton));

        loginTextView = view.findViewById(R.id.fragment_password_recover_login_textView);
        loginTextView.setText(getResources().getString(R.string.AuthLogin));
        registerTextView = view.findViewById(R.id.fragment_password_recover_register_textView);
        registerTextView.setText(getResources().getString(R.string.AuthRegister));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            passwordRecoverTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!inputEditText.hasFocus()) {
                    if (((AuthActivity) getActivity()).controlEditText.input() != null && ((AuthActivity) getActivity()).controlEditText.input().hasFocus()) {
                        ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), ((AuthActivity) getActivity()).controlEditText.input());
                    }

                    ((AuthActivity) getActivity()).controlEditText.focus(inputEditText);
                    ((AuthActivity) getActivity()).controlEditText.select(inputEditText);
                }
            }
            return false;
        });

        passwordRecoverTextView.setOnClickListener(v -> {
            passwordRecoverTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> passwordRecoverTextView.setClickable(true), 300);

            if (((AuthActivity) getActivity()).controlEditText.input() != null && ((AuthActivity) getActivity()).controlEditText.input().hasFocus()) {
                ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), ((AuthActivity) getActivity()).controlEditText.input());
            }

            if (inputEditText.length() == 0) {
                ((AuthActivity) getActivity()).controlEditText.error(getActivity(), inputEditText);
            } else {
                ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), inputEditText);
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

        registerTextView.setOnClickListener(v -> {
            registerTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> registerTextView.setClickable(true), 300);

            if (((AuthActivity) getActivity()).controlEditText.input() != null && ((AuthActivity) getActivity()).controlEditText.input().hasFocus()) {
                ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), ((AuthActivity) getActivity()).controlEditText.input());
            }

            ((AuthActivity) getActivity()).navController.navigate(R.id.registerFragment);
        });
    }

    private void doWork() {
        input = inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

}