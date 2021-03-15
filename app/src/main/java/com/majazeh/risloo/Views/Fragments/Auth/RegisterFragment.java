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
import com.majazeh.risloo.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    // Binding
    private FragmentRegisterBinding binding;

    // Widgets
    private EditText nameEditText, mobileEditText;
    private ImageView nameErrorImageView, mobileErrorImageView;
    private TextView nameErrorTextView, mobileErrorTextView;
    private TextView registerTextView, loginTextView, passwordRecoverTextView;

    // Vars
    private String name = "", mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        nameEditText = binding.fragmentRegisterNameEditText.componentAuthInputTextEditText;
        nameEditText.setHint(getResources().getString(R.string.RegisterFragmentName));
        mobileEditText = binding.fragmentRegisterMobileEditText.componentAuthInputNumberEditText;
        mobileEditText.setHint(getResources().getString(R.string.RegisterFragmentMobile));

        nameErrorImageView = binding.fragmentRegisterNameEditText.componentAuthInputTextErrorImageView;
        nameErrorTextView = binding.fragmentRegisterNameEditText.componentAuthInputTextErrorTextView;
        mobileErrorImageView = binding.fragmentRegisterMobileEditText.componentAuthInputNumberErrorImageView;
        mobileErrorTextView = binding.fragmentRegisterMobileEditText.componentAuthInputNumberErrorTextView;

        registerTextView = binding.fragmentRegisterButtonTextView.componentAuthButton;
        registerTextView.setText(getResources().getString(R.string.RegisterFragmentButton));

        loginTextView = binding.fragmentRegisterLoginTextView.componentAuthLink;
        loginTextView.setText(getResources().getString(R.string.AuthLogin));

        passwordRecoverTextView = binding.fragmentRegisterPasswordRecoverTextView.componentAuthLink;
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
                    ((AuthActivity) requireActivity()).controlEditText.select(getActivity(), nameEditText);
                }
            }
            return false;
        });

        mobileEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!mobileEditText.hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(getActivity(), mobileEditText);
                }
            }
            return false;
        });

        registerTextView.setOnClickListener(v -> {
            registerTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> registerTextView.setClickable(true), 300);

            if (nameEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(getActivity(), nameEditText, nameErrorImageView, nameErrorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (mobileEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (nameEditText.length() != 0 && mobileEditText.length() != 0) {
                ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), nameEditText, nameErrorImageView, nameErrorTextView);
                ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView);

                doWork();
            }
        });

        loginTextView.setOnClickListener(v -> {
            loginTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> loginTextView.setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.loginFragment);
        });

        passwordRecoverTextView.setOnClickListener(v -> {
            passwordRecoverTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> passwordRecoverTextView.setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.passwordRecoverFragment);
        });
    }

    private void doWork() {
        name = nameEditText.getText().toString().trim();
        mobile = mobileEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}