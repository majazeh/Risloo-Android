package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextPaint;
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
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationAuthDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
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
    private HashMap data, header;
    private ClickableSpan clickableSpan;
    private CountDownTimer countDownTimer;

    // Vars
    private String pin = "", mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthPinBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.PinFragmentTitle));
        binding.pinEditText.getRoot().setHint(getResources().getString(R.string.PinFragmentInput));
        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.PinFragmentButton));

        binding.timerViewFlipper.resendTextView.setMovementMethod(LinkMovementMethod.getInstance());

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
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.pinEditText.getRoot().hasFocus())
                ((AuthActivity) requireActivity()).controlEditText.select(requireActivity(), binding.pinEditText.getRoot());
            return false;
        });

        binding.pinEditText.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
            pin = binding.pinEditText.getRoot().getText().toString().trim();
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

                binding.timerViewFlipper.countdownTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                showTimer(false);
            }
        };

        ClickManager.onDelayedClickListener(() -> {
            if (binding.pinEditText.getRoot().length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
                countDownTimer.cancel();

                doWork("code");
            }
        }).widget(binding.buttonTextView.getRoot());

        ClickManager.onClickListener(() -> {
            countDownTimer.cancel();

            NavDirections action = NavigationAuthDirections.actionGlobalAuthLoginFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.loginLinkTextView.getRoot());

        ClickManager.onClickListener(() -> {
            countDownTimer.cancel();

            NavDirections action = NavigationAuthDirections.actionGlobalAuthRegisterFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.registerLinkTextView.getRoot());

        ClickManager.onClickListener(() -> {
            countDownTimer.cancel();

            NavDirections action = NavigationAuthDirections.actionGlobalAuthPasswordRecoverFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void setArgs() {
        mobile = AuthPinFragmentArgs.fromBundle(getArguments()).getMobile();
        binding.mobileTextView.getRoot().setText(mobile);

        AuthModel authModel = (AuthModel) AuthPinFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(authModel);
    }

    private void setData(AuthModel model) {
        if (model.getKey() != null && !model.getKey().equals("")) {
            data.put("key", model.getKey());
        }

        if (model.getCallback() != null && !model.getCallback().equals("")) {
            data.put("callback", model.getCallback());
        }

        startCountDownTimer();
    }

    private void startCountDownTimer() {
        binding.guideIncludeLayout.guideTextView.setText(StringManager.replace(requireActivity().getResources().getString(R.string.PinFragmentGuide), "09123456789", mobile));

        binding.timerViewFlipper.resendTextView.setText(StringManager.clickable(requireActivity().getResources().getString(R.string.PinFragmentResend), 24, 34, clickableSpan));

        countDownTimer.start();
    }

    private void showTimer(boolean bool) {
        if (bool) {
            countDownTimer.start();

            binding.timerViewFlipper.getRoot().setInAnimation(requireActivity(), R.anim.slide_in_right_with_fade);
            binding.timerViewFlipper.getRoot().setOutAnimation(requireActivity(), R.anim.slide_out_left_with_fade);

            binding.timerViewFlipper.getRoot().showPrevious();
        } else {
            countDownTimer.cancel();

            binding.timerViewFlipper.getRoot().setInAnimation(requireActivity(), R.anim.slide_in_left_with_fade);
            binding.timerViewFlipper.getRoot().setOutAnimation(requireActivity(), R.anim.slide_out_right_with_fade);

            binding.timerViewFlipper.getRoot().showNext();
        }
    }

    private void doWork(String method) {
        if (method.equals("code")) {
            ((AuthActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

            data.put("code", pin);

            Auth.auth_theory(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel model = (AuthModel) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            if (model.getUser() == null) {
                                switch (model.getTheory()) {
                                    case "password": {
                                        NavDirections action = NavigationAuthDirections.actionGlobalAuthPasswordFragment(mobile, model);

                                        ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                                        ((AuthActivity) requireActivity()).navController.navigate(action);
                                    } break;
                                    case "recovery": {
                                        NavDirections action = NavigationAuthDirections.actionGlobalAuthPasswordChangeFragment(mobile, model);

                                        ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                                        ((AuthActivity) requireActivity()).navController.navigate(action);
                                    } break;
                                    default: {
                                        ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                                    } break;
                                }
                            } else {
                                NavDirections action = NavigationAuthDirections.actionGlobalAuthSerialFragment();

                                ((AuthActivity) requireActivity()).singleton.login(model);
                                ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                                ((AuthActivity) requireActivity()).navController.navigate(action);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            try {
                                JSONObject responseObject = new JSONObject(response);
                                if (!responseObject.isNull("errors")) {
                                    JSONObject errorsObject = responseObject.getJSONObject("errors");

                                    Iterator<String> keys = (errorsObject.keys());
                                    StringBuilder errors = new StringBuilder();

                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                            String validation = errorsObject.getJSONArray(key).get(i).toString();

                                            if (key.equals("code"))
                                                ((AuthActivity) requireActivity()).controlEditText.error(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, validation);

                                            errors.append(validation);
                                            errors.append("\n");
                                        }
                                    }

                                    ToastManager.showErrorToast(requireActivity(), errors.substring(0, errors.length() - 1));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            });
        } else if (method.equals("verification")) {
            ToastManager.showDefaultToast(requireActivity(), getResources().getString(R.string.ToastFeatureNotAddedYet));

            // TODO : Place Code if Needed

            showTimer(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        countDownTimer.cancel();
    }

}