package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    // Binding
    private FragmentRegisterBinding binding;

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
        binding.nameIncludeLayout.inputEditText.setHint(getResources().getString(R.string.RegisterFragmentName));
        binding.mobileIncludeLayout.inputEditText.setHint(getResources().getString(R.string.RegisterFragmentMobile));

        binding.registerTextView.componentAuthButton.setText(getResources().getString(R.string.RegisterFragmentButton));

        binding.loginTextView.componentAuthLink.setText(getResources().getString(R.string.AuthLogin));
        binding.passwordRecoverTextView.componentAuthLink.setText(getResources().getString(R.string.AuthPasswordRecover));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.registerTextView.componentAuthButton.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.nameIncludeLayout.inputEditText.hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(getActivity(), binding.nameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.mobileIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.mobileIncludeLayout.inputEditText.hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(getActivity(), binding.mobileIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.registerTextView.componentAuthButton.setOnClickListener(v -> {
            binding.registerTextView.componentAuthButton.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.registerTextView.componentAuthButton.setClickable(true), 300);

            if (binding.nameIncludeLayout.inputEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(getActivity(), binding.nameIncludeLayout.inputEditText, binding.nameIncludeLayout.errorImageView, binding.nameIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.mobileIncludeLayout.inputEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(getActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileIncludeLayout.errorImageView, binding.mobileIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (binding.nameIncludeLayout.inputEditText.length() != 0 && binding.mobileIncludeLayout.inputEditText.length() != 0) {
                ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), binding.nameIncludeLayout.inputEditText, binding.nameIncludeLayout.errorImageView, binding.nameIncludeLayout.errorTextView);
                ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileIncludeLayout.errorImageView, binding.mobileIncludeLayout.errorTextView);

                doWork();
            }
        });

        binding.loginTextView.componentAuthLink.setOnClickListener(v -> {
            binding.loginTextView.componentAuthLink.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.loginTextView.componentAuthLink.setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.loginFragment);
        });

        binding.passwordRecoverTextView.componentAuthLink.setOnClickListener(v -> {
            binding.passwordRecoverTextView.componentAuthLink.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.passwordRecoverTextView.componentAuthLink.setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.passwordRecoverFragment);
        });
    }

    private void doWork() {
        name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        mobile = binding.mobileIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}