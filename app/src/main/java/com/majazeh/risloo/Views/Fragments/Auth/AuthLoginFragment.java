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
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthLoginBinding;

public class AuthLoginFragment extends Fragment {

    // Binding
    private FragmentAuthLoginBinding binding;

    // Vars
    private String mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthLoginBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        binding.titleTextView.getRoot().setText(getResources().getString(R.string.LoginFragmentTitle));

        binding.mobileEditText.getRoot().setHint(getResources().getString(R.string.LoginFragmentInput));

        binding.guideIncludeLayout.guideTextView.setHint(getResources().getString(R.string.LoginFragmentGuide));

        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.LoginFragmentButton));

        binding.registerHelperTextView.getRoot().setText(getResources().getString(R.string.AuthRegisterHelper));
        binding.passwordRecoverHelperTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecoverHelper));

        binding.registerLinkTextView.getRoot().setText(StringManager.foreground(getResources().getString(R.string.AuthRegisterLink), 0, 5, getResources().getColor(R.color.Gray800)));
        binding.registerLinkTextView.getRoot().setTextAppearance(requireActivity(), R.style.danaDemiBoldTextStyle);
        binding.passwordRecoverLinkTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecoverLink));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.buttonTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.mobileEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.mobileEditText.getRoot().hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(requireActivity(), binding.mobileEditText.getRoot());
                }
            }
            return false;
        });

        binding.mobileEditText.getRoot().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.mobileEditText.getRoot().length() == 11 && !binding.mobileEditText.getRoot().getText().toString().contains("+")) {
                    ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.mobileEditText.getRoot(), binding.errorIncludeLayout.errorImageView, binding.errorIncludeLayout.errorTextView);
                    doWork();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.mobileEditText.getRoot().length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.mobileEditText.getRoot(), binding.errorIncludeLayout.errorImageView, binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.mobileEditText.getRoot(), binding.errorIncludeLayout.errorImageView, binding.errorIncludeLayout.errorTextView);
                doWork();
            }
        }).widget(binding.buttonTextView.getRoot());

        ClickManager.onClickListener(() -> ((AuthActivity) requireActivity()).navigator(R.id.authRegisterFragment)).widget(binding.registerLinkTextView.getRoot());
        ClickManager.onClickListener(() -> ((AuthActivity) requireActivity()).navigator(R.id.authSerialFragment)).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void doWork() {
        mobile = binding.mobileEditText.getRoot().getText().toString().trim();

        // TODO : call work method and place mobile as it's input
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}