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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentMobileBinding;

public class MobileFragment extends Fragment {

    // Binding
    private FragmentMobileBinding binding;

    // Vars
    private String mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentMobileBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        binding.mobileIncludeLayout.inputEditText.setHint(getResources().getString(R.string.MobileFragmentInput));

        binding.mobileTextView.getRoot().setText(getResources().getString(R.string.MobileFragmentButton));

        binding.loginTextView.getRoot().setText(getResources().getString(R.string.AuthLogin));
        binding.registerTextView.getRoot().setText(getResources().getString(R.string.AuthRegister));
        binding.passwordRecoverTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecover));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.mobileTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.mobileIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.mobileIncludeLayout.inputEditText.hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(requireActivity(), binding.mobileIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.mobileIncludeLayout.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.mobileIncludeLayout.inputEditText.length() == 11) {
                    ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileIncludeLayout.errorImageView, binding.mobileIncludeLayout.errorTextView);
                    doWork();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.mobileTextView.getRoot().setOnClickListener(v -> {
            binding.mobileTextView.getRoot().setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.mobileTextView.getRoot().setClickable(true), 300);

            if (binding.mobileIncludeLayout.inputEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileIncludeLayout.errorImageView, binding.mobileIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileIncludeLayout.errorImageView, binding.mobileIncludeLayout.errorTextView);
                doWork();
            }
        });

        binding.loginTextView.getRoot().setOnClickListener(v -> {
            binding.loginTextView.getRoot().setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.loginTextView.getRoot().setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.loginFragment);
        });

        binding.registerTextView.getRoot().setOnClickListener(v -> {
            binding.registerTextView.getRoot().setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.registerTextView.getRoot().setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.registerFragment);
        });

        binding.passwordRecoverTextView.getRoot().setOnClickListener(v -> {
            binding.passwordRecoverTextView.getRoot().setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.passwordRecoverTextView.getRoot().setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.passwordRecoverFragment);
        });
    }

    private void doWork() {
        mobile = binding.mobileIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}