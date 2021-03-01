package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
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
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;

public class LoginFragment extends Fragment {

    // Vars
    private String username = "";

    // Widgets
    private EditText usernameEditText;
    private ImageView errorImageView;
    private TextView errorTextView;
    private TextView loginTextView;
    private TextView registerTextView, passwordRecoverTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, viewGroup, false);

        initializer(view);

        detector();

        listener();

        return view;
    }

    private void initializer(View view) {
        usernameEditText = view.findViewById(R.id.component_auth_input_text_editText);
        usernameEditText.setHint(getResources().getString(R.string.LoginFragmentInput));

        errorImageView = view.findViewById(R.id.component_auth_input_text_error_imageView);

        errorTextView = view.findViewById(R.id.component_auth_input_text_error_textView);

        loginTextView = view.findViewById(R.id.fragment_login_button_textView);
        loginTextView.setText(getResources().getString(R.string.LoginFragmentButton));

        registerTextView = view.findViewById(R.id.fragment_login_register_textView);
        registerTextView.setText(StringManager.foregroundStyle(getResources().getString(R.string.AuthRegister), 0, 5, getResources().getColor(R.color.Gray900), Typeface.BOLD));
        passwordRecoverTextView = view.findViewById(R.id.fragment_login_password_recover_textView);
        passwordRecoverTextView.setText(getResources().getString(R.string.AuthPasswordRecover));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            loginTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        usernameEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!usernameEditText.hasFocus()) {
                    if (((AuthActivity) getActivity()).controlEditText.input() != null && ((AuthActivity) getActivity()).controlEditText.input().hasFocus()) {
                        ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), ((AuthActivity) getActivity()).controlEditText.input());
                    }

                    ((AuthActivity) getActivity()).controlEditText.focus(usernameEditText);
                    ((AuthActivity) getActivity()).controlEditText.select(usernameEditText);
                }
            }
            return false;
        });

        loginTextView.setOnClickListener(v -> {
            loginTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> loginTextView.setClickable(true), 300);

            if (((AuthActivity) getActivity()).controlEditText.input() != null && ((AuthActivity) getActivity()).controlEditText.input().hasFocus()) {
                ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), ((AuthActivity) getActivity()).controlEditText.input());
            }

            if (usernameEditText.length() == 0) {
                ((AuthActivity) getActivity()).controlEditText.error(getActivity(), usernameEditText, errorImageView, errorTextView, "فیلد خالی است.");
            } else {
                ((AuthActivity) getActivity()).controlEditText.check(getActivity(), usernameEditText, errorImageView, errorTextView);
                doWork();
            }
        });

        registerTextView.setOnClickListener(v -> {
            registerTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> registerTextView.setClickable(true), 300);

            if (((AuthActivity) getActivity()).controlEditText.input() != null && ((AuthActivity) getActivity()).controlEditText.input().hasFocus()) {
                ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), ((AuthActivity) getActivity()).controlEditText.input());
            }

            ((AuthActivity) getActivity()).navController.navigate(R.id.registerFragment);
        });

        passwordRecoverTextView.setOnClickListener(v -> {
            passwordRecoverTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> passwordRecoverTextView.setClickable(true), 300);

            if (((AuthActivity) getActivity()).controlEditText.input() != null && ((AuthActivity) getActivity()).controlEditText.input().hasFocus()) {
                ((AuthActivity) getActivity()).controlEditText.clear(getActivity(), ((AuthActivity) getActivity()).controlEditText.input());
            }

            ((AuthActivity) getActivity()).navController.navigate(R.id.serialFragment);
        });
    }

    private void doWork() {
        username = usernameEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

}