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
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthPinBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class AuthPinFragment extends Fragment {

    // Binding
    private FragmentAuthPinBinding binding;

    // Objects
    private ClickableSpan clickableSpan;
    private CountDownTimer countDownTimer;

    // Vars
    private String mobile = "", pin = "";
    private String key = "", callback = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthPinBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        startCountDownTimer();

        return binding.getRoot();
    }

    private void initializer() {
        binding.titleTextView.getRoot().setText(getResources().getString(R.string.PinFragmentTitle));

        binding.pinEditText.getRoot().setHint(getResources().getString(R.string.PinFragmentInput));

        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.PinFragmentButton));

        binding.timerTextView.setMovementMethod(LinkMovementMethod.getInstance());

        binding.loginLinkTextView.getRoot().setText(getResources().getString(R.string.AuthLoginLink));
        binding.registerLinkTextView.getRoot().setText(getResources().getString(R.string.AuthRegisterLink));
        binding.passwordRecoverLinkTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecoverLink));

        binding.illuImageView.getRoot().setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.illu_007, null));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.buttonTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.pinEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.pinEditText.getRoot().hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(requireActivity(), binding.pinEditText.getRoot());
                }
            }
            return false;
        });

        binding.pinEditText.getRoot().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.pinEditText.getRoot().length() == 6) {
                    ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.pinEditText.getRoot(), binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
                    doWork("pin");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        clickableSpan = new ClickableSpan() {
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

        countDownTimer = new CountDownTimer(300000, 1000) {
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

        ClickManager.onDelayedClickListener(() -> {
            if (binding.pinEditText.getRoot().length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.pinEditText.getRoot(), binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.pinEditText.getRoot(), binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
                doWork("pin");
            }
        }).widget(binding.buttonTextView.getRoot());

        ClickManager.onClickListener(() -> {
            countDownTimer.cancel();
            ((AuthActivity) requireActivity()).navigator(R.id.authLoginFragment,null);
        }).widget(binding.loginLinkTextView.getRoot());

        ClickManager.onClickListener(() -> {
            countDownTimer.cancel();
            ((AuthActivity) requireActivity()).navigator(R.id.authRegisterFragment, null);
        }).widget(binding.registerLinkTextView.getRoot());

        ClickManager.onClickListener(() -> {
            countDownTimer.cancel();
            ((AuthActivity) requireActivity()).navigator(R.id.authPasswordRecoverFragment, null);
        }).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("mobile") != null) {
                mobile = getArguments().getString("mobile");

                binding.mobileTextView.getRoot().setText(mobile);
                binding.mobileTextView.getRoot().setVisibility(View.VISIBLE);
            } else {
                binding.mobileTextView.getRoot().setText(mobile);
                binding.mobileTextView.getRoot().setVisibility(View.GONE);
            }

            if (getArguments().getString("key") != null) {
                key = requireArguments().getString("key");
            }

            if (getArguments().getString("callback") != null) {
                callback = requireArguments().getString("callback");
            }
        }
    }

    private void startCountDownTimer() {
        binding.guideIncludeLayout.guideTextView.setText(requireActivity().getResources().getString(R.string.PinFragmentGuide1) + " " + mobile + " " + requireActivity().getResources().getString(R.string.PinFragmentGuide2));

        binding.timerTextView.setText(StringManager.clickable(requireActivity().getResources().getString(R.string.PinFragmentResend), 24, 34, clickableSpan));

        countDownTimer.start();
    }

    private void showTimer(boolean value) {
        if (value) {
            countDownTimer.start();

            binding.viewFlipper.setInAnimation(requireActivity(), R.anim.slide_in_right_with_fade);
            binding.viewFlipper.setOutAnimation(requireActivity(), R.anim.slide_out_left_with_fade);

            binding.viewFlipper.showPrevious();
        } else {
            countDownTimer.cancel();

            binding.viewFlipper.setInAnimation(requireActivity(), R.anim.slide_in_left_with_fade);
            binding.viewFlipper.setOutAnimation(requireActivity(), R.anim.slide_out_right_with_fade);

            binding.viewFlipper.showNext();
        }
    }

    private void doWork(String method) {
        countDownTimer.cancel();

        pin = binding.pinEditText.getRoot().getText().toString().trim();

        ((AuthActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        if (method.equals("pin")) {
            HashMap data = new HashMap<>();
            data.put("code", pin);
            data.put("key", key);
            data.put("callback", callback);

            Auth.auth_theory(data, new HashMap<>(), new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel model = (AuthModel) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            if (model.getUser() == null) {
                                Bundle extras = new Bundle();

                                extras.putString("mobile", mobile);
                                extras.putString("key", model.getKey());
                                extras.putString("callback", model.getCallback());

                                switch (model.getTheory()) {
                                    case "password":
                                        ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                                        ((AuthActivity) requireActivity()).navigator(R.id.authPasswordFragment, extras);
                                        break;
                                    case "recovery":
                                        ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                                        ((AuthActivity) requireActivity()).navigator(R.id.authPasswordChangeFragment, extras);
                                        break;
                                }
                            } else {
                                ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                                ((AuthActivity) requireActivity()).login(model);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (!jsonObject.isNull("errors")) {
                                    Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());

                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                                            if (key.equals("code")) {
                                                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.pinEditText.getRoot(), binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                            }
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            });
        } else if (method.equals("verification")) {

            // TODO : Place Code if Needed

            showTimer(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}