package com.majazeh.risloo.Views.Fragments.Auth;

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
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationAuthDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Widgets.CutCopyPasteEditText;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthPasswordChangeBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class AuthPasswordChangeFragment extends Fragment {

    // Binding
    private FragmentAuthPasswordChangeBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String password = "", mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthPasswordChangeBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.PasswordChangeFragmentTitle));
        binding.passwordIncludeLayout.inputEditText.setHint(getResources().getString(R.string.PasswordChangeFragmentInput));
        binding.guideIncludeLayout.guideTextView.setText(getResources().getString(R.string.PasswordChangeFragmentGuide));
        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.PasswordChangeFragmentButton));

        binding.loginLinkTextView.getRoot().setText(getResources().getString(R.string.AuthLoginLink));
        binding.registerLinkTextView.getRoot().setText(getResources().getString(R.string.AuthRegisterLink));
        binding.passwordRecoverLinkTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecoverLink));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.passwordIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.passwordIncludeLayout.inputEditText.hasFocus())
                ((AuthActivity) requireActivity()).inputor.select(requireActivity(), binding.passwordIncludeLayout.inputEditText);
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

        binding.passwordIncludeLayout.inputEditText.setOnCutCopyPasteListener(new CutCopyPasteEditText.OnCutCopyPasteListener() {
            @Override
            public void onCut() {

            }

            @Override
            public void onCopy() {

            }

            @Override
            public void onPaste() {
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
                InitManager.imgResTintTag(requireActivity(), binding.passwordIncludeLayout.visibilityImageView, R.drawable.ic_eye_light, R.color.Risloo500, "visible");
            } else {
                binding.passwordIncludeLayout.inputEditText.setTransformationMethod(new PasswordTransformationMethod());
                InitManager.imgResTintTag(requireActivity(), binding.passwordIncludeLayout.visibilityImageView, R.drawable.ic_eye_slash_light, R.color.CoolGray500, "invisible");
            }
        }).widget(binding.passwordIncludeLayout.visibilityImageView);

        CustomClickView.onDelayedListener(() -> {
            if (binding.passwordIncludeLayout.inputEditText.length() == 0) {
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
            NavDirections action = NavigationAuthDirections.actionGlobalAuthRegisterFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.registerLinkTextView.getRoot());

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationAuthDirections.actionGlobalAuthPasswordRecoverFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void setArgs() {
        AuthModel authModel = (AuthModel) AuthPasswordChangeFragmentArgs.fromBundle(getArguments()).getTypeModel();
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

//        if (!((AuthActivity) requireActivity()).singleton.getRegistPassword(mobile).equals("")) {
//            password = ((AuthActivity) requireActivity()).singleton.getRegistPassword(mobile);
//            binding.passwordIncludeLayout.inputEditText.setText(password);
//
//            binding.passwordIncludeLayout.visibilityImageView.setVisibility(View.VISIBLE);
//        }
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        data.put("password", password);

        Auth.auth_theory(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                AuthModel model = (AuthModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        if (model.getUser() == null) {
                            NavDirections action = null;

                            switch (model.getTheory()) {
                                case "mobileCode":
                                    action = NavigationAuthDirections.actionGlobalAuthPinFragment(model);
                                    break;
                                case "password":
                                    action = NavigationAuthDirections.actionGlobalAuthPasswordFragment(model);
                                    break;
                            }

                            ((AuthActivity) requireActivity()).navController.navigate(Objects.requireNonNull(action));
                            DialogManager.dismissLoadingDialog();
                        } else {
                            ((AuthActivity) requireActivity()).singleton.login(model);
                            ((AuthActivity) requireActivity()).singleton.regist(StringManager.mobileConvert(mobile), password);

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

                                        if (key.equals("password"))
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