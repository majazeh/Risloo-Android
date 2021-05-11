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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Widgets.CutCopyPasteEditText;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthPasswordBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.HashMap;

public class AuthPasswordFragment extends Fragment {

    // Binding
    private FragmentAuthPasswordBinding binding;

    // Vars
    private String mobile = "";
    private String password = "";
    private boolean passwordVisibility = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthPasswordBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
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
                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.passwordIncludeLayout.inputEditText, binding.errorIncludeLayout.errorImageView, binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.passwordIncludeLayout.inputEditText, binding.errorIncludeLayout.errorImageView, binding.errorIncludeLayout.errorTextView);
                doWork();
            }
        }).widget(binding.buttonTextView.getRoot());

        ClickManager.onClickListener(() -> ((AuthActivity) requireActivity()).navigator(R.id.authLoginFragment)).widget(binding.loginLinkTextView.getRoot());
        ClickManager.onClickListener(() -> ((AuthActivity) requireActivity()).navigator(R.id.authRegisterFragment)).widget(binding.registerLinkTextView.getRoot());
        ClickManager.onClickListener(() -> ((AuthActivity) requireActivity()).navigator(R.id.authPasswordRecoverFragment)).widget(binding.passwordRecoverLinkTextView.getRoot());
    }

    private void setData() {
        if (!((AuthActivity) requireActivity()).singleton.getMobile().equals("")) {
            mobile = ((AuthActivity) requireActivity()).singleton.getMobile();
            binding.mobileTextView.getRoot().setText(mobile);
        } else {
            binding.mobileTextView.getRoot().setText(mobile);
            binding.mobileTextView.getRoot().setVisibility(View.GONE);
        }
    }

    private void doWork() {
        password = binding.passwordIncludeLayout.inputEditText.getText().toString().trim();
        // TODO : call work method and place password as it's input
        ((AuthActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");
        HashMap data = new HashMap();
        data.put("password", password);
        if (getArguments().getString("key")!=null)
            data.put("key", getArguments().getString("key"));
        if (getArguments().getString("callback")!=null)
            data.put("callback", getArguments().getString("callback"));
        Auth.auth_theory(data, new HashMap<>(), new Response() {
            @Override
            public void onOK(Object object) {
                AuthModel model = (AuthModel) object;
                if (((AuthModel) object).getUser()==null) {
                    Bundle extras = new Bundle();
                    extras.putString("key", model.getKey());
                    extras.putString("callback", model.getCallback());
                    switch (model.getTheory()) {
                        case "mobileCode":
                            getActivity().runOnUiThread(() -> {
                                ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                                ((AuthActivity) requireActivity()).navigator(R.id.authPinFragment, extras);
                            });
                            break;
                    }
                } else {
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