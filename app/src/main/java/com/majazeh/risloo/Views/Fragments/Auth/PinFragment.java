package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentPinBinding;

import java.util.Locale;

public class PinFragment extends Fragment {

    // Binding
    private FragmentPinBinding binding;

    // Objects
    private ClickableSpan pinLinkSpan;
    private CountDownTimer pinCountDownTimer;

    // Vars
    private String pin = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentPinBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        binding.pinIncludeLayout.inputEditText.setHint(getResources().getString(R.string.PinFragmentInput));

        binding.timerTextView.setMovementMethod(LinkMovementMethod.getInstance());

        binding.pinTextView.getRoot().setText(getResources().getString(R.string.PinFragmentButton));

        binding.loginTextView.getRoot().setText(getResources().getString(R.string.AuthLogin));
        binding.registerTextView.getRoot().setText(getResources().getString(R.string.AuthRegister));
        binding.passwordRecoverTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecover));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.pinTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.pinIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.pinIncludeLayout.inputEditText.hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(requireActivity(), binding.pinIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.pinIncludeLayout.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.pinIncludeLayout.inputEditText.length() == 6) {
                    ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.pinIncludeLayout.inputEditText, binding.pinIncludeLayout.errorImageView, binding.pinIncludeLayout.errorTextView);
                    doWork("pin");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.pinTextView.getRoot().setOnClickListener(v -> {
            binding.pinTextView.getRoot().setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.pinTextView.getRoot().setClickable(true), 300);

            if (binding.pinIncludeLayout.inputEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.pinIncludeLayout.inputEditText, binding.pinIncludeLayout.errorImageView, binding.pinIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.pinIncludeLayout.inputEditText, binding.pinIncludeLayout.errorImageView, binding.pinIncludeLayout.errorTextView);
                doWork("pin");
            }
        });

        pinLinkSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                doWork("verification");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint textPaint) {
                textPaint.setColor(getResources().getColor(R.color.Gray700));
                textPaint.setUnderlineText(false);
            }
        };

        pinCountDownTimer = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                binding.countdownTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                showTimer(false);
            }
        };

        binding.loginTextView.getRoot().setOnClickListener(v -> {
            binding.loginTextView.getRoot().setClickable(false);

            ((AuthActivity) requireActivity()).navigator(R.id.loginFragment);
        });

        binding.registerTextView.getRoot().setOnClickListener(v -> {
            binding.registerTextView.getRoot().setClickable(false);

            ((AuthActivity) requireActivity()).navigator(R.id.registerFragment);
        });

        binding.passwordRecoverTextView.getRoot().setOnClickListener(v -> {
            binding.passwordRecoverTextView.getRoot().setClickable(false);

            ((AuthActivity) requireActivity()).navigator(R.id.passwordRecoverFragment);
        });
    }

    private void setData() {
        binding.timerTextView.setText(StringManager.clickable(requireActivity().getResources().getString(R.string.PinFragmentLink), 24, 34, pinLinkSpan));
        pinCountDownTimer.start();
    }

    private void showTimer(boolean value) {
        if (value) {
            pinCountDownTimer.start();

            binding.viewFlipper.setInAnimation(requireActivity(), R.anim.slide_in_right_with_fade);
            binding.viewFlipper.setOutAnimation(requireActivity(), R.anim.slide_out_left_with_fade);

            binding.viewFlipper.showPrevious();
        } else {
            pinCountDownTimer.cancel();

            binding.viewFlipper.setInAnimation(requireActivity(), R.anim.slide_in_left_with_fade);
            binding.viewFlipper.setOutAnimation(requireActivity(), R.anim.slide_out_right_with_fade);

            binding.viewFlipper.showNext();
        }
    }

    private void doWork(String method) {
        pin = binding.pinIncludeLayout.inputEditText.getText().toString().trim();

        if (method.equals("pin")) {
            // TODO : Call Work Method



        } else if (method.equals("verification")) {



            // IF Work Done Was Success
            showTimer(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}