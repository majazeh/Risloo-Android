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
import com.majazeh.risloo.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    // Binding
    private FragmentLoginBinding binding;

    // Widgets
    private EditText usernameEditText;
    private ImageView usernameErrorImageView;
    private TextView usernameErrorTextView;
    private TextView loginTextView, registerTextView, passwordRecoverTextView;

    // Vars
    private String username = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        usernameEditText = binding.fragmentLoginInputEditText.componentAuthInputTextEditText;
        usernameEditText.setHint(getResources().getString(R.string.LoginFragmentInput));

        usernameErrorImageView = binding.fragmentLoginInputEditText.componentAuthInputTextErrorImageView;
        usernameErrorTextView = binding.fragmentLoginInputEditText.componentAuthInputTextErrorTextView;

        loginTextView = binding.fragmentLoginButtonTextView.componentAuthButton;
        loginTextView.setText(getResources().getString(R.string.LoginFragmentButton));

        registerTextView = binding.fragmentLoginRegisterTextView.componentAuthLink;
        registerTextView.setText(StringManager.foregroundStyle(getResources().getString(R.string.AuthRegister), 0, 5, getResources().getColor(R.color.Gray900), Typeface.BOLD));

        passwordRecoverTextView = binding.fragmentLoginPasswordRecoverTextView.componentAuthLink;
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
                    ((AuthActivity) requireActivity()).controlEditText.select(getActivity(), usernameEditText);
                }
            }
            return false;
        });

        loginTextView.setOnClickListener(v -> {
            loginTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> loginTextView.setClickable(true), 300);

            if (usernameEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(getActivity(), usernameEditText, usernameErrorImageView, usernameErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), usernameEditText, usernameErrorImageView, usernameErrorTextView);
                doWork();
            }
        });

        registerTextView.setOnClickListener(v -> {
            registerTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> registerTextView.setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.registerFragment);
        });

        passwordRecoverTextView.setOnClickListener(v -> {
            passwordRecoverTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> passwordRecoverTextView.setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.serialFragment);
        });
    }

    private void doWork() {
        username = usernameEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}