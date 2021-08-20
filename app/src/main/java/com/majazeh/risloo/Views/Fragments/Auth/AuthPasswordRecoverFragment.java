package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
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
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthPasswordRecoverBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class AuthPasswordRecoverFragment extends Fragment {

    // Binding
    private FragmentAuthPasswordRecoverBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthPasswordRecoverBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.PasswordRecoverFragmentTitle));
        binding.mobileEditText.getRoot().setHint(getResources().getString(R.string.PasswordRecoverFragmentInput));
        binding.guideIncludeLayout.guideTextView.setText(getResources().getString(R.string.PasswordRecoverFragmentGuide));
        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.PasswordRecoverFragmentButton));

        binding.loginHelperTextView.getRoot().setText(getResources().getString(R.string.AuthLoginHelper));
        binding.registerHelperTextView.getRoot().setText(getResources().getString(R.string.AuthRegisterHelper));

        binding.loginLinkTextView.getRoot().setText(getResources().getString(R.string.AuthLoginLink));
        binding.registerLinkTextView.getRoot().setText(getResources().getString(R.string.AuthRegisterLink));

        binding.illuImageView.getRoot().setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.illu_006, null));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.buttonTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.mobileEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.mobileEditText.getRoot().hasFocus())
                ((AuthActivity) requireActivity()).controlEditText.select(requireActivity(), binding.mobileEditText.getRoot());
            return false;
        });

        binding.mobileEditText.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
            mobile = binding.mobileEditText.getRoot().getText().toString().trim();
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.mobileEditText.getRoot().length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
                doWork();
            }
        }).widget(binding.buttonTextView.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationAuthDirections.actionGlobalAuthLoginFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.loginLinkTextView.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationAuthDirections.actionGlobalAuthRegisterFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.registerLinkTextView.getRoot());
    }

    private void doWork() {
        ((AuthActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        data.put("mobile", mobile);

        Auth.recovery(data, header, new Response() {
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
                                case "mobileCode": {
                                    NavDirections action = NavigationAuthDirections.actionGlobalAuthPinFragment(mobile, model);

                                    ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                                    ((AuthActivity) requireActivity()).navController.navigate(action);
                                } break;
                                case "recovery": {
                                    NavDirections action = NavigationAuthDirections.actionGlobalAuthPasswordChangeFragment(mobile, model);

                                    ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                                    ((AuthActivity) requireActivity()).navController.navigate(action);
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

                                        if (key.equals("mobile"))
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}