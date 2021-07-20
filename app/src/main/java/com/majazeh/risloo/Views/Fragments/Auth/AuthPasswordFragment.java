package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.os.Build;
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
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationAuthDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Widgets.CutCopyPasteEditText;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthPasswordBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class AuthPasswordFragment extends Fragment {

    // Binding
    private FragmentAuthPasswordBinding binding;

    // Vars
    private HashMap data, header;
    private AuthModel authModel;
    private String password = "", mobile = "";
    private boolean passwordVisibility = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthPasswordBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.PasswordFragmentTitle));

        binding.passwordIncludeLayout.inputEditText.setHint(getResources().getString(R.string.PasswordFragmentInput));

        binding.guideIncludeLayout.guideTextView.setHint(getResources().getString(R.string.PasswordFragmentGuide));

        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.PasswordFragmentButton));

        binding.loginLinkTextView.getRoot().setText(getResources().getString(R.string.AuthLoginLink));
        binding.registerLinkTextView.getRoot().setText(getResources().getString(R.string.AuthRegisterLink));
        binding.passwordRecoverLinkTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecoverLink));

        binding.illuImageView.getRoot().setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.illu_005, null));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.buttonTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.passwordIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.passwordIncludeLayout.inputEditText.hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(requireActivity(), binding.passwordIncludeLayout.inputEditText);
                }
            }
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
                    binding.passwordIncludeLayout.visibilityImageView.setVisibility(View.GONE);
                } else if (binding.passwordIncludeLayout.inputEditText.length() == 1) {
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
                if (binding.passwordIncludeLayout.inputEditText.length() != 0) {
                    binding.passwordIncludeLayout.visibilityImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (!passwordVisibility) {
                passwordVisibility = true;
                binding.passwordIncludeLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_light, null));

                ImageViewCompat.setImageTintList(binding.passwordIncludeLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Blue800));
                binding.passwordIncludeLayout.inputEditText.setTransformationMethod(null);
            } else {
                passwordVisibility = false;
                binding.passwordIncludeLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_slash_light, null));

                ImageViewCompat.setImageTintList(binding.passwordIncludeLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Gray600));
                binding.passwordIncludeLayout.inputEditText.setTransformationMethod(new PasswordTransformationMethod());
            }
        }).widget(binding.passwordIncludeLayout.visibilityImageView);

        ClickManager.onDelayedClickListener(() -> {
            if (binding.passwordIncludeLayout.inputEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.passwordIncludeLayout.inputEditText, binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.passwordIncludeLayout.inputEditText, binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
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

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationAuthDirections.actionGlobalAuthPasswordRecoverFragment();
            ((AuthActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void setArgs() {
        mobile = AuthPasswordFragmentArgs.fromBundle(getArguments()).getMobile();
        binding.mobileTextView.getRoot().setText(mobile);

        authModel = (AuthModel) AuthPasswordFragmentArgs.fromBundle(getArguments()).getTypeModel();
    }

    private void doWork() {
        ((AuthActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        data.put("password", password);

        if (authModel.getKey() != null)
            data.put("key", authModel.getKey());

        if (authModel.getCallback() != null)
            data.put("callback", authModel.getCallback());

        Auth.auth_theory(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                AuthModel model = (AuthModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        if (model.getUser() == null) {
                            switch (model.getTheory()) {
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
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.isNull("errors")) {
                                Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                                        if (key.equals("password")) {
                                            ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.passwordIncludeLayout.inputEditText, binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}