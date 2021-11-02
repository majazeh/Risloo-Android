package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
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
import androidx.navigation.NavDirections;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.majazeh.risloo.NavigationAuthDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
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
import java.util.Objects;

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
                ((AuthActivity) requireActivity()).inputon.select(requireActivity(), binding.pinEditText.getRoot());
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
                textPaint.setColor(getResources().getColor(R.color.CoolGray700));
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
                ((AuthActivity) requireActivity()).validatoon.showValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).validatoon.hideValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
                countDownTimer.cancel();

                doWork("code");
            }
        }).widget(binding.buttonTextView.getRoot());

        CustomClickView.onClickListener(() -> {
            countDownTimer.cancel();

            NavDirections action = NavigationAuthDirections.actionGlobalAuthLoginFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.loginLinkTextView.getRoot());

        CustomClickView.onClickListener(() -> {
            countDownTimer.cancel();

            NavDirections action = NavigationAuthDirections.actionGlobalAuthRegisterFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.registerLinkTextView.getRoot());

        CustomClickView.onClickListener(() -> {
            countDownTimer.cancel();

            NavDirections action = NavigationAuthDirections.actionGlobalAuthPasswordRecoverFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void setArgs() {
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

        if (model.getAuthorized_key() != null && !model.getAuthorized_key().equals("")) {
            mobile = model.getAuthorized_key();
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

    private void doWork(String method) {
        if (method.equals("code")) {
            DialogManager.showLoadingDialog(requireActivity(), "");

            data.put("code", pin);

            Auth.auth_theory(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel model = (AuthModel) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            if (model.getUser() == null) {
                                NavDirections action = null;

                                switch (model.getTheory()) {
                                    case "password":
                                        action = NavigationAuthDirections.actionGlobalAuthPasswordFragment(model);
                                        break;
                                    case "recovery":
                                        action = NavigationAuthDirections.actionGlobalAuthPasswordChangeFragment(model);
                                        break;
                                }

                                ((AuthActivity) requireActivity()).navController.navigate(Objects.requireNonNull(action));
                                DialogManager.dismissLoadingDialog();
                            } else {
                                ((AuthActivity) requireActivity()).singleton.login(model);

                                IntentManager.main(requireActivity());
                                DialogManager.dismissLoadingDialog();
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
                                                ((AuthActivity) requireActivity()).validatoon.showValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, validation);

                                            errors.append(validation);
                                            errors.append("\n");
                                        }
                                    }

                                    SnackManager.showErrorSnack(requireActivity(), errors.substring(0, errors.length() - 1));
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