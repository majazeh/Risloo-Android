package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CutCopyPasteEditText;
import com.majazeh.risloo.Views.Activities.AuthActivity;

public class PasswordFragment extends Fragment {

    // Vars
    private String password = "";
    private boolean passwordVisibility = false;

    // Widgets
    private CutCopyPasteEditText passwordEditText;
    private ImageView passwordImageView;
    private ImageView passwordErrorImageView;
    private TextView passwordErrorTextView;
    private TextView passwordTextView;
    private TextView loginTextView, registerTextView, passwordRecoverTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, viewGroup, false);

        initializer(view);

        detector();

        listener();

        return view;
    }

    private void initializer(View view) {
        passwordEditText = view.findViewById(R.id.component_auth_input_password_editText);
        passwordEditText.setHint(getResources().getString(R.string.PasswordFragmentInput));

        passwordImageView = view.findViewById(R.id.component_auth_input_password_imageView);

        passwordErrorImageView = view.findViewById(R.id.component_auth_input_password_error_imageView);

        passwordErrorTextView = view.findViewById(R.id.component_auth_input_password_error_textView);

        passwordTextView = view.findViewById(R.id.fragment_password_button_textView);
        passwordTextView.setText(getResources().getString(R.string.PasswordFragmentButton));

        loginTextView = view.findViewById(R.id.fragment_password_login_textView);
        loginTextView.setText(getResources().getString(R.string.AuthLogin));
        registerTextView = view.findViewById(R.id.fragment_password_register_textView);
        registerTextView.setText(getResources().getString(R.string.AuthRegister));
        passwordRecoverTextView = view.findViewById(R.id.fragment_password_password_recover_textView);
        passwordRecoverTextView.setText(getResources().getString(R.string.AuthPasswordRecover));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            passwordTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        passwordEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!passwordEditText.hasFocus()) {
                    ((AuthActivity) getActivity()).controlEditText.focus(passwordEditText);
                    ((AuthActivity) getActivity()).controlEditText.select(passwordEditText);
                }
            }
            return false;
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (passwordEditText.length() == 0) {
                    passwordImageView.setVisibility(View.INVISIBLE);
                } else if (passwordEditText.length() == 1) {
                    passwordImageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.setOnCutCopyPasteListener(new CutCopyPasteEditText.OnCutCopyPasteListener() {
            @Override
            public void onCut() {

            }

            @Override
            public void onCopy() {

            }

            @Override
            public void onPaste() {
                if (passwordEditText.length() != 0) {
                    passwordImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        passwordImageView.setOnClickListener(v -> {
            if (!passwordVisibility) {
                passwordVisibility = true;
                passwordImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_light, null));

                ImageViewCompat.setImageTintList(passwordImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Blue800));
                passwordEditText.setTransformationMethod(null);
            } else {
                passwordVisibility = false;
                passwordImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_slash_light, null));

                ImageViewCompat.setImageTintList(passwordImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Gray600));
                passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        passwordTextView.setOnClickListener(v -> {
            passwordTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> passwordTextView.setClickable(true), 300);

            if (passwordEditText.length() == 0) {
                ((AuthActivity) getActivity()).controlEditText.error(getActivity(), passwordEditText, passwordErrorImageView, passwordErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) getActivity()).controlEditText.check(getActivity(), passwordEditText, passwordErrorImageView, passwordErrorTextView);
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
        password = passwordEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

}