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

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.managers.DialogManager;
import com.majazeh.risloo.Utils.managers.InitManager;
import com.majazeh.risloo.Utils.managers.IntentManager;
import com.majazeh.risloo.Utils.managers.SnackManager;
import com.majazeh.risloo.Utils.widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthLoginBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class AuthLoginFragment extends Fragment {

    // Binding
    private FragmentAuthLoginBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthLoginBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.LoginFragmentTitle));
        binding.mobileEditText.getRoot().setHint(getResources().getString(R.string.LoginFragmentInput));
        binding.guideIncludeLayout.guideTextView.setText(getResources().getString(R.string.LoginFragmentGuide));
        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.LoginFragmentButton));

        binding.registerHelperTextView.getRoot().setText(getResources().getString(R.string.AuthRegisterHelper));
        binding.passwordRecoverHelperTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecoverHelper));

        InitManager.txtTextAppearance(requireActivity(), binding.registerLinkTextView.getRoot(), getResources().getString(R.string.AuthRegisterLink), R.style.danaDemiBold);
        binding.passwordRecoverLinkTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecoverLink));

        InitManager.input12sspAutoComplete(requireActivity(), binding.mobileEditText.getRoot(), ((AuthActivity) requireActivity()).singleton.getRegistMobiles());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.mobileEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.mobileEditText.getRoot().hasFocus())
                ((AuthActivity) requireActivity()).inputon.select(binding.mobileEditText.getRoot());
            return false;
        });

        binding.mobileEditText.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
            mobile = binding.mobileEditText.getRoot().getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.mobileEditText.getRoot().length() == 0) {
                ((AuthActivity) requireActivity()).validatoon.emptyValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
            } else {
                if (binding.errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                    ((AuthActivity) requireActivity()).validatoon.hideValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.buttonTextView.getRoot());

        CustomClickView.onClickListener(() -> ((AuthActivity) requireActivity()).navigatoon.navigateToAuthRegisterFragment()).widget(binding.registerLinkTextView.getRoot());

        CustomClickView.onClickListener(() -> ((AuthActivity) requireActivity()).navigatoon.navigateToAuthPasswordRecoverFragment()).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void setHashmap() {
        data.put("authorized_key", mobile);
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        setHashmap();

        Auth.auth(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                AuthModel model = (AuthModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        if (model.getUser() == null) {
                            switch (model.getTheory()) {
                                case "password":
                                    ((AuthActivity) requireActivity()).navigatoon.navigateToAuthPasswordFragment(model);
                                    break;
                                case "mobileCode":
                                    ((AuthActivity) requireActivity()).navigatoon.navigateToAuthPinFragment(model);
                                    break;
                                case "recovery":
                                    ((AuthActivity) requireActivity()).navigatoon.navigateToAuthPasswordChangeFragment(model);
                                    break;
                            }
                        } else {
                            ((AuthActivity) requireActivity()).singleton.login(model);
                            IntentManager.main(requireActivity());
                        }

                        DialogManager.dismissLoadingDialog();
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

                                    if (key.equals("authorized_key"))
                                        ((AuthActivity) requireActivity()).validatoon.showValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}