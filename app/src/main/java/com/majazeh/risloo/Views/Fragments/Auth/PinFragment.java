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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

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

    // Widgets
    private EditText pinEditText;
    private ImageView pinErrorImageView;
    private TextView pinErrorTextView;
    private ViewFlipper pinViewFlipper;
    private TextView pinCountdownTextView, pinTimerTextView;
    private TextView pinTextView, loginTextView, registerTextView, passwordRecoverTextView;

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
        pinEditText = binding.fragmentPinInputEditText.componentAuthInputNumberEditText;
        pinEditText.setHint(getResources().getString(R.string.PinFragmentInput));

        pinErrorImageView = binding.fragmentPinInputEditText.componentAuthInputNumberErrorImageView;
        pinErrorTextView = binding.fragmentPinInputEditText.componentAuthInputNumberErrorTextView;

        pinViewFlipper = binding.fragmentPinViewFlipper;

        pinCountdownTextView = binding.fragmentPinCountdownTextView;
        pinTimerTextView = binding.fragmentPinTimerTextView;
        pinTimerTextView.setMovementMethod(LinkMovementMethod.getInstance());

        pinTextView = binding.fragmentPinButtonTextView.componentAuthButton;
        pinTextView.setText(getResources().getString(R.string.PinFragmentButton));

        loginTextView = binding.fragmentPinLoginTextView.componentAuthLink;
        loginTextView.setText(getResources().getString(R.string.AuthLogin));

        registerTextView = binding.fragmentPinRegisterTextView.componentAuthLink;
        registerTextView.setText(getResources().getString(R.string.AuthRegister));

        passwordRecoverTextView = binding.fragmentPinPasswordRecoverTextView.componentAuthLink;
        passwordRecoverTextView.setText(getResources().getString(R.string.AuthPasswordRecover));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            pinTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        pinEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!pinEditText.hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(getActivity(), pinEditText);
                }
            }
            return false;
        });

        pinEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pinEditText.length() == 6) {
                    ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), pinEditText, pinErrorImageView, pinErrorTextView);
                    doWork("pin");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pinTextView.setOnClickListener(v -> {
            pinTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> pinTextView.setClickable(true), 300);

            if (pinEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(getActivity(), pinEditText, pinErrorImageView, pinErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), pinEditText, pinErrorImageView, pinErrorTextView);
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

                pinCountdownTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                showTimer(false);
            }
        };

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

    private void setData() {
        pinTimerTextView.setText(StringManager.clickable(getActivity().getResources().getString(R.string.PinFragmentLink), 24, 34, pinLinkSpan));
        pinCountDownTimer.start();
    }

    private void showTimer(boolean value) {
        if (value) {
            pinCountDownTimer.start();

            pinViewFlipper.setInAnimation(getActivity(), R.anim.slide_in_right_with_fade);
            pinViewFlipper.setOutAnimation(getActivity(), R.anim.slide_out_left_with_fade);

            pinViewFlipper.showPrevious();
        } else {
            pinCountDownTimer.cancel();

            pinViewFlipper.setInAnimation(getActivity(), R.anim.slide_in_left_with_fade);
            pinViewFlipper.setOutAnimation(getActivity(), R.anim.slide_out_right_with_fade);

            pinViewFlipper.showNext();
        }
    }

    private void doWork(String method) {
        pin = pinEditText.getText().toString().trim();

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