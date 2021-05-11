package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthRegisterBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.HashMap;

public class AuthRegisterFragment extends Fragment {

    // Binding
    private FragmentAuthRegisterBinding binding;

    // Vars
    private String mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthRegisterBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        binding.titleTextView.getRoot().setText(getResources().getString(R.string.RegisterFragmentTitle));

        binding.mobileEditText.getRoot().setHint(getResources().getString(R.string.RegisterFragmentMobile));

        binding.guideIncludeLayout.guideTextView.setHint(getResources().getString(R.string.RegisterFragmentGuide));

        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.RegisterFragmentButton));

        binding.loginHelperTextView.getRoot().setText(getResources().getString(R.string.AuthLoginHelper));
        binding.passwordRecoverHelperTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecoverHelper));

        binding.loginLinkTextView.getRoot().setText(getResources().getString(R.string.AuthLoginLink));
        binding.passwordRecoverLinkTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecoverLink));

        binding.illuImageView.getRoot().setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.illu_002, null));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.buttonTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.mobileEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.mobileEditText.getRoot().hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(requireActivity(), binding.mobileEditText.getRoot());
                }
            }
            return false;
        });

        binding.mobileEditText.getRoot().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.mobileEditText.getRoot().length() == 11 && !binding.mobileEditText.getRoot().getText().toString().contains("+")) {
                    ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.mobileEditText.getRoot(), binding.errorIncludeLayout.errorImageView, binding.errorIncludeLayout.errorTextView);
                    doWork();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.mobileEditText.getRoot().length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.mobileEditText.getRoot(), binding.errorIncludeLayout.errorImageView, binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.mobileEditText.getRoot(), binding.errorIncludeLayout.errorImageView, binding.errorIncludeLayout.errorTextView);
                doWork();
            }
        }).widget(binding.buttonTextView.getRoot());

        ClickManager.onClickListener(() -> ((AuthActivity) requireActivity()).navigator(R.id.authLoginFragment)).widget(binding.loginLinkTextView.getRoot());
        ClickManager.onClickListener(() -> ((AuthActivity) requireActivity()).navigator(R.id.authPasswordRecoverFragment)).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void doWork() {
        mobile = binding.mobileEditText.getRoot().getText().toString().trim();
        // TODO : call work method and place mobile as it's input
        ((AuthActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        HashMap data = new HashMap();
        data.put("mobile", mobile);
        Auth.register(data, new HashMap<>(), new Response() {
            @Override
            public void onOK(Object object) {
                AuthModel model = (AuthModel) object;
                if (((AuthModel) object).getUser() == null) {
                    Bundle extras = new Bundle();
                    extras.putString("key", model.getKey());
                    extras.putString("callback", model.getCallback());
                    switch (model.getTheory()) {
                        case "password":
                            getActivity().runOnUiThread(() -> {
                                ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                                ((AuthActivity) requireActivity()).navigator(R.id.authPasswordFragment, extras);
                            });
                            break;
                        case "mobileCode":
                            getActivity().runOnUiThread(() -> {
                                ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                                ((AuthActivity) requireActivity()).navigator(R.id.authPinFragment, extras);
                            });
                            break;
                    }
                }  else {
                    getActivity().runOnUiThread(() -> ((AuthActivity) requireActivity()).login(model));
                }
            }

            @Override
            public void onFailure(String response) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}