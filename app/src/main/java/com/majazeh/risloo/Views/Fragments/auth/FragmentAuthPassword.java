package com.majazeh.risloo.views.fragments.auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.FragmentAuthPasswordBinding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.widgets.interfaces.CutCopyPasteListener;
import com.majazeh.risloo.views.activities.ActivityAuth;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;

import java.util.HashMap;

public class FragmentAuthPassword extends Fragment {

    // Binding
    private FragmentAuthPasswordBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String password = "", mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthPasswordBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.PasswordFragmentTitle));
        binding.passwordIncludeLayout.inputEditText.setHint(getResources().getString(R.string.PasswordFragmentInput));
        binding.guideIncludeLayout.guideTextView.setText(getResources().getString(R.string.PasswordFragmentGuide));
        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.PasswordFragmentButton));

        binding.loginLinkTextView.getRoot().setText(getResources().getString(R.string.AuthLoginLink));
        binding.registerLinkTextView.getRoot().setText(getResources().getString(R.string.AuthRegisterLink));
        binding.passwordRecoverLinkTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecoverLink));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.passwordIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.passwordIncludeLayout.inputEditText.hasFocus())
                ((ActivityAuth) requireActivity()).inputon.select(binding.passwordIncludeLayout.inputEditText);
            return false;
        });

        binding.passwordIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            password = binding.passwordIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.passwordIncludeLayout.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.passwordIncludeLayout.inputEditText.length() == 0) {
                    if (binding.passwordIncludeLayout.visibilityImageView.getVisibility() != View.GONE)
                        binding.passwordIncludeLayout.visibilityImageView.setVisibility(View.GONE);
                } else if (binding.passwordIncludeLayout.inputEditText.length() == 1) {
                    if (binding.passwordIncludeLayout.visibilityImageView.getVisibility() != View.VISIBLE)
                        binding.passwordIncludeLayout.visibilityImageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.passwordIncludeLayout.inputEditText.setOnCutCopyPasteListener(new CutCopyPasteListener() {
            @Override
            public void onCutListener() {

            }

            @Override
            public void onCopyListener() {

            }

            @Override
            public void onPasteListener() {
                if (binding.passwordIncludeLayout.inputEditText.length() == 0) {
                    if (binding.passwordIncludeLayout.visibilityImageView.getVisibility() != View.GONE)
                        binding.passwordIncludeLayout.visibilityImageView.setVisibility(View.GONE);
                } else if (binding.passwordIncludeLayout.inputEditText.length() != 0) {
                    if (binding.passwordIncludeLayout.visibilityImageView.getVisibility() != View.VISIBLE)
                        binding.passwordIncludeLayout.visibilityImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.passwordIncludeLayout.visibilityImageView.getTag().equals("invisible")) {
                binding.passwordIncludeLayout.inputEditText.setTransformationMethod(null);
                InitManager.imgResTintTag(requireActivity(), binding.passwordIncludeLayout.visibilityImageView, R.drawable.ic_eye_light, R.color.risloo500, "visible");
            } else {
                binding.passwordIncludeLayout.inputEditText.setTransformationMethod(new PasswordTransformationMethod());
                InitManager.imgResTintTag(requireActivity(), binding.passwordIncludeLayout.visibilityImageView, R.drawable.ic_eye_slash_light, R.color.coolGray500, "invisible");
            }
        }).widget(binding.passwordIncludeLayout.visibilityImageView);

        CustomClickView.onDelayedListener(() -> {
            if (binding.passwordIncludeLayout.inputEditText.length() == 0) {
                ((ActivityAuth) requireActivity()).validatoon.emptyValid(binding);
            } else {
                ((ActivityAuth) requireActivity()).validatoon.hideValid(binding);

                doWork();
            }
        }).widget(binding.buttonTextView.getRoot());

        CustomClickView.onClickListener(() -> ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthLogin()).widget(binding.loginLinkTextView.getRoot());

        CustomClickView.onClickListener(() -> ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthRegister()).widget(binding.registerLinkTextView.getRoot());

        CustomClickView.onClickListener(() -> ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthPasswordRecover()).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void setArgs() {
        AuthModel authModel = (AuthModel) FragmentAuthPasswordArgs.fromBundle(getArguments()).getTypeModel();
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

//        if (!((AuthActivity) requireActivity()).singleton.getRegistPassword(mobile).equals("")) {
//            password = ((AuthActivity) requireActivity()).singleton.getRegistPassword(mobile);
//            binding.passwordIncludeLayout.inputEditText.setText(password);
//
//            binding.passwordIncludeLayout.visibilityImageView.setVisibility(View.VISIBLE);
//        }
    }

    private void setHashmap() {
        data.put("password", password);
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Auth.auth_theory(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                AuthModel model = (AuthModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        if (model.getUser() == null) {
                            switch (model.getTheory()) {
                                case "mobileCode":
                                    ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthPin(model);
                                    break;
                                case "recovery":
                                    ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthPasswordChange(model);
                                    break;
                            }
                        } else {
                            ((ActivityAuth) requireActivity()).singleton.login(model);
                            ((ActivityAuth) requireActivity()).singleton.regist(StringManager.adjustMobile(mobile), password);

                            IntentManager.main(requireActivity());
                        }

                        DialogManager.dismissDialogLoading();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> ((ActivityAuth) requireActivity()).validatoon.showValid(response, binding));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}