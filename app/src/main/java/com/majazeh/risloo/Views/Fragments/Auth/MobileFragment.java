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
import com.majazeh.risloo.databinding.FragmentMobileBinding;

public class MobileFragment extends Fragment {

    // Binding
    private FragmentMobileBinding binding;

    // Widgets
    private EditText mobileEditText;
    private ImageView mobileErrorImageView;
    private TextView mobileErrorTextView;
    private TextView mobileTextView, loginTextView, registerTextView, passwordRecoverTextView;

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
        mobileEditText = binding.fragmentMobileInputEditText.componentAuthInputNumberEditText;
        mobileEditText.setHint(getResources().getString(R.string.MobileFragmentInput));

        mobileErrorImageView = binding.fragmentMobileInputEditText.componentAuthInputNumberErrorImageView;
        mobileErrorTextView = binding.fragmentMobileInputEditText.componentAuthInputNumberErrorTextView;

        mobileTextView = binding.fragmentMobileButtonTextView.componentAuthButton;
        mobileTextView.setText(getResources().getString(R.string.MobileFragmentButton));

        loginTextView = binding.fragmentMobileLoginTextView.componentAuthLink;
        loginTextView.setText(getResources().getString(R.string.AuthLogin));

        registerTextView = binding.fragmentMobileRegisterTextView.componentAuthLink;
        registerTextView.setText(getResources().getString(R.string.AuthRegister));

        passwordRecoverTextView = binding.fragmentMobilePasswordRecoverTextView.componentAuthLink;
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
                    ((AuthActivity) requireActivity()).controlEditText.select(getActivity(), mobileEditText);
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
                    ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView);
                    doWork();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mobileTextView.setOnClickListener(v -> {
            mobileTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> mobileTextView.setClickable(true), 300);

            if (mobileEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), mobileEditText, mobileErrorImageView, mobileErrorTextView);
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
        mobile = mobileEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}