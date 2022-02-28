package com.majazeh.risloo.views.fragments.auth;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.managers.ToastManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityAuth;
import com.majazeh.risloo.databinding.FragmentAuthPinBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentAuthPin extends Fragment {

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

        listener();

        setArgs();

        startSmsRetriver();

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
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.pinEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.pinEditText.getRoot().hasFocus())
                ((ActivityAuth) requireActivity()).inputon.select(binding.pinEditText.getRoot());
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
                textPaint.setColor(getResources().getColor(R.color.coolGray700));
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

        CustomClickView.onDelayedListener(() -> {
            if (binding.pinEditText.getRoot().length() == 0) {
                ((ActivityAuth) requireActivity()).validatoon.emptyValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
            } else {
                if (binding.errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                    ((ActivityAuth) requireActivity()).validatoon.hideValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);

                doWork("code");
            }
        }).widget(binding.buttonTextView.getRoot());

        CustomClickView.onClickListener(() -> {
            countDownTimer.cancel();
            ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthLogin();
        }).widget(binding.loginLinkTextView.getRoot());

        CustomClickView.onClickListener(() -> {
            countDownTimer.cancel();
            ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthRegister();
        }).widget(binding.registerLinkTextView.getRoot());

        CustomClickView.onClickListener(() -> {
            countDownTimer.cancel();
            ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthPasswordRecover();
        }).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void setArgs() {
        AuthModel authModel = (AuthModel) FragmentAuthPinArgs.fromBundle(getArguments()).getTypeModel();
        setData(authModel);
    }

    private void setData(AuthModel model) {
        if (!model.getKey().equals("")) {
            data.put("key", model.getKey());
        }

        if (!model.getCallback().equals("")) {
            data.put("callback", model.getCallback());
        }

        if (!model.getAuthorizedKey().equals("")) {
            mobile = model.getAuthorizedKey();
            binding.mobileTextView.getRoot().setText(mobile);
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

            binding.timerViewFlipper.getRoot().setInAnimation(requireActivity(), R.anim.slide_and_fade_in_right);
            binding.timerViewFlipper.getRoot().setOutAnimation(requireActivity(), R.anim.slide_and_fade_out_left);

            binding.timerViewFlipper.getRoot().showPrevious();
        } else {
            countDownTimer.cancel();

            binding.timerViewFlipper.getRoot().setInAnimation(requireActivity(), R.anim.slide_and_fade_in_left);
            binding.timerViewFlipper.getRoot().setOutAnimation(requireActivity(), R.anim.slide_and_fade_out_right);

            binding.timerViewFlipper.getRoot().showNext();
        }
    }

    private void setHashmap(String method) {
        if (method.equals("code")) {
            data.put("code", pin);
        } else if (method.equals("verification")) {
            data.remove("code");
        }
    }

    private void doWork(String method) {
        if (method.equals("code")) {
            countDownTimer.cancel();

            DialogManager.showDialogLoading(requireActivity(), "");

            setHashmap(method);

            Auth.auth_theory(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel model = (AuthModel) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            if (model.getUser() == null) {
                                switch (model.getTheory()) {
                                    case "password":
                                        ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthPassword(model);
                                        break;
                                    case "recovery":
                                        ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthPasswordChange(model);
                                        break;
                                }
                            } else {
                                ((ActivityAuth) requireActivity()).singleton.login(model);
                                IntentManager.main(requireActivity());
                            }

                            DialogManager.dismissDialogLoading();
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
                                    StringBuilder allErrors = new StringBuilder();

                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        StringBuilder keyErrors = new StringBuilder();

                                        for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                            String error = errorsObject.getJSONArray(key).getString(i);

                                            keyErrors.append(error);
                                            keyErrors.append("\n");

                                            allErrors.append(error);
                                            allErrors.append("\n");
                                        }

                                        if (key.equals("code"))
                                            ((ActivityAuth) requireActivity()).validatoon.showValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                    }

                                    SnackManager.showErrorSnack(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            });
        } else if (method.equals("verification")) {
            ToastManager.showToastDefault(requireActivity(), getResources().getString(R.string.ToastFeatureNotAddedYet));

            // TODO : Place Code if Needed

            showTimer(true);
        }
    }

    private void startSmsRetriver() {
        SmsRetrieverClient client = SmsRetriever.getClient(requireActivity());
        Task<Void> task = client.startSmsRetriever();

        task.addOnSuccessListener(aVoid -> {
            // Note : Successfully started retriever, expect broadcast intent
        });

        task.addOnFailureListener(e -> {
            // Note : Failed to start retriever, inspect Exception for more details
        });
    }

    private void autoSmsVerificate(String code) {
        pin = code;
        binding.pinEditText.getRoot().setText(pin);

        if (binding.errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
            ((ActivityAuth) requireActivity()).validatoon.hideValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);

        doWork("code");
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                Bundle extras = intent.getExtras();
                Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

                if (status.getStatusCode() == CommonStatusCodes.SUCCESS) {
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);

                    Pattern pattern = Pattern.compile("\\d+");
                    Matcher matcher = pattern.matcher(message);

                    if (matcher.find()) {
                        String code = matcher.group(0);
                        autoSmsVerificate(code);
                    }
                } else {
                    SnackManager.showDefaultSnack(requireActivity(), "onReceiveFail: " + status.getStatusMessage());
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().registerReceiver(broadcastReceiver, new IntentFilter("com.google.android.gms.auth.api.phone.SMS_RETRIEVED"));
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        countDownTimer.cancel();
        binding = null;
    }

}