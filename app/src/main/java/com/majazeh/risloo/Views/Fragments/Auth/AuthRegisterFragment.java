package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationAuthDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthRegisterBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class AuthRegisterFragment extends Fragment {

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
                ((AuthActivity) requireActivity()).inputor.select(requireActivity(), binding.mobileEditText.getRoot());
            return false;
        });

        binding.mobileEditText.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
            mobile = binding.mobileEditText.getRoot().getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.mobileEditText.getRoot().length() == 0) {
                ((AuthActivity) requireActivity()).validatoon.showValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).validatoon.hideValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
                doWork();
            }
        }).widget(binding.buttonTextView.getRoot());

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationAuthDirections.actionGlobalAuthLoginFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.loginLinkTextView.getRoot());

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationAuthDirections.actionGlobalAuthPasswordRecoverFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        data.put("mobile", mobile);

        Auth.register(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                AuthModel model = (AuthModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        if (model.getUser() == null) {
                            NavDirections action = null;

                            switch (model.getTheory()) {
                                case "password":
                                    action = NavigationAuthDirections.actionGlobalAuthPasswordFragment(mobile, model);
                                    break;
                                case "mobileCode":
                                    action = NavigationAuthDirections.actionGlobalAuthPinFragment(mobile, model);
                                    break;
                                case "recovery":
                                    action = NavigationAuthDirections.actionGlobalAuthPasswordChangeFragment(mobile, model);
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

                                        if (key.equals("mobile"))
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}