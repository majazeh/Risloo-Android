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
import com.majazeh.risloo.databinding.FragmentPasswordChangeBinding;

public class PasswordChangeFragment extends Fragment {

    // Binding
    private FragmentPasswordChangeBinding binding;

    // Widgets
    private CutCopyPasteEditText passwordEditText;
    private ImageView passwordVisibilityImageView;
    private ImageView passwordErrorImageView;
    private TextView passwordErrorTextView;
    private TextView passwordChangeTextView, loginTextView, registerTextView, passwordRecoverTextView;

    // Vars
    private String password = "";
    private boolean passwordVisibility = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentPasswordChangeBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        passwordEditText = binding.fragmentPasswordChangeInputEditText.componentAuthInputPasswordEditText;
        passwordEditText.setHint(getResources().getString(R.string.PasswordChangeFragmentInput));

        passwordVisibilityImageView = binding.fragmentPasswordChangeInputEditText.componentAuthInputPasswordVisibilityImageView;
        passwordErrorImageView = binding.fragmentPasswordChangeInputEditText.componentAuthInputPasswordErrorImageView;
        passwordErrorTextView = binding.fragmentPasswordChangeInputEditText.componentAuthInputPasswordErrorTextView;

        passwordChangeTextView = binding.fragmentPasswordChangeButtonTextView.componentAuthButton;
        passwordChangeTextView.setText(getResources().getString(R.string.PasswordChangeFragmentButton));

        loginTextView = binding.fragmentPasswordChangeLoginTextView.componentAuthLink;
        loginTextView.setText(getResources().getString(R.string.AuthLogin));

        registerTextView = binding.fragmentPasswordChangeRegisterTextView.componentAuthLink;
        registerTextView.setText(getResources().getString(R.string.AuthRegister));

        passwordRecoverTextView = binding.fragmentPasswordChangePasswordRecoverTextView.componentAuthLink;
        passwordRecoverTextView.setText(getResources().getString(R.string.AuthPasswordRecover));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            passwordChangeTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        passwordEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!passwordEditText.hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(getActivity(), passwordEditText);
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
                    passwordVisibilityImageView.setVisibility(View.INVISIBLE);
                } else if (passwordEditText.length() == 1) {
                    passwordVisibilityImageView.setVisibility(View.VISIBLE);
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
                    passwordVisibilityImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        passwordVisibilityImageView.setOnClickListener(v -> {
            if (!passwordVisibility) {
                passwordVisibility = true;
                passwordVisibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_light, null));

                ImageViewCompat.setImageTintList(passwordVisibilityImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Blue800));
                passwordEditText.setTransformationMethod(null);
            } else {
                passwordVisibility = false;
                passwordVisibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_slash_light, null));

                ImageViewCompat.setImageTintList(passwordVisibilityImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Gray600));
                passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        passwordChangeTextView.setOnClickListener(v -> {
            passwordChangeTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> passwordChangeTextView.setClickable(true), 300);

            if (passwordEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(getActivity(), passwordEditText, passwordErrorImageView, passwordErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), passwordEditText, passwordErrorImageView, passwordErrorTextView);
                doWork();
            }
        });

        loginTextView.setOnClickListener(v -> {
            loginTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> loginTextView.setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.loginFragment);
        });

        registerTextView.setOnClickListener(v -> {
            registerTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> registerTextView.setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.registerFragment);
        });

        passwordRecoverTextView.setOnClickListener(v -> {
            passwordRecoverTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> passwordRecoverTextView.setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.passwordRecoverFragment);
        });
    }

    private void doWork() {
        password = passwordEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}