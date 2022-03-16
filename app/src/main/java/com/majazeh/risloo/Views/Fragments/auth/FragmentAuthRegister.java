package com.majazeh.risloo.views.fragments.auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.FragmentAuthRegisterBinding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityAuth;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;

import java.util.HashMap;

public class FragmentAuthRegister extends Fragment {

    // Binding
    private FragmentAuthRegisterBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthRegisterBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.RegisterFragmentTitle));
        binding.mobileEditText.getRoot().setHint(getResources().getString(R.string.RegisterFragmentMobile));
        binding.guideIncludeLayout.guideTextView.setText(getResources().getString(R.string.RegisterFragmentGuide));
        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.RegisterFragmentButton));

        binding.loginHelperTextView.getRoot().setText(getResources().getString(R.string.AuthLoginHelper));
        binding.passwordRecoverHelperTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecoverHelper));

        binding.loginLinkTextView.getRoot().setText(getResources().getString(R.string.AuthLoginLink));
        binding.passwordRecoverLinkTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecoverLink));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.mobileEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.mobileEditText.getRoot().hasFocus())
                ((ActivityAuth) requireActivity()).inputon.select(binding.mobileEditText.getRoot());
            return false;
        });

        binding.mobileEditText.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
            mobile = binding.mobileEditText.getRoot().getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.mobileEditText.getRoot().length() == 0) {
                ((ActivityAuth) requireActivity()).validatoon.emptyValid(binding);
            } else {
                ((ActivityAuth) requireActivity()).validatoon.hideValid(binding);

                doWork();
            }
        }).widget(binding.buttonTextView.getRoot());

        CustomClickView.onClickListener(() -> ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthLogin()).widget(binding.loginLinkTextView.getRoot());

        CustomClickView.onClickListener(() -> ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthPasswordRecover()).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void setHashmap() {
        data.put("mobile", mobile);
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Auth.register(data, header, new Response() {
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
                                case "mobileCode":
                                    ((ActivityAuth) requireActivity()).navigatoon.navigateToFragmentAuthPin(model);
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