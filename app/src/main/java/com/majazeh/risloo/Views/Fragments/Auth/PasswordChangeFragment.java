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

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CutCopyPasteEditText;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentPasswordChangeBinding;

public class PasswordChangeFragment extends Fragment {

    // Binding
    private FragmentPasswordChangeBinding binding;

    // Vars
    private String password = "";
    private boolean passwordVisibility = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentPasswordChangeBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        binding.passwordChangeInputLayout.inputEditText.setHint(getResources().getString(R.string.PasswordChangeFragmentInput));

        binding.passwordChangeTextView.getRoot().setText(getResources().getString(R.string.PasswordChangeFragmentButton));

        binding.loginTextView.getRoot().setText(getResources().getString(R.string.AuthLogin));
        binding.registerTextView.getRoot().setText(getResources().getString(R.string.AuthRegister));
        binding.passwordRecoverTextView.getRoot().setText(getResources().getString(R.string.AuthPasswordRecover));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.passwordChangeTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.passwordChangeInputLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.passwordChangeInputLayout.inputEditText.hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(requireActivity(), binding.passwordChangeInputLayout.inputEditText);
                }
            }
            return false;
        });

        binding.passwordChangeInputLayout.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.passwordChangeInputLayout.inputEditText.length() == 0) {
                    binding.passwordChangeInputLayout.visibilityImageView.setVisibility(View.INVISIBLE);
                } else if (binding.passwordChangeInputLayout.inputEditText.length() == 1) {
                    binding.passwordChangeInputLayout.visibilityImageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.passwordChangeInputLayout.inputEditText.setOnCutCopyPasteListener(new CutCopyPasteEditText.OnCutCopyPasteListener() {
            @Override
            public void onCut() {

            }

            @Override
            public void onCopy() {

            }

            @Override
            public void onPaste() {
                if (binding.passwordChangeInputLayout.inputEditText.length() != 0) {
                    binding.passwordChangeInputLayout.visibilityImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.passwordChangeInputLayout.visibilityImageView.setOnClickListener(v -> {
            if (!passwordVisibility) {
                passwordVisibility = true;
                binding.passwordChangeInputLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_light, null));

                ImageViewCompat.setImageTintList(binding.passwordChangeInputLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Blue800));
                binding.passwordChangeInputLayout.inputEditText.setTransformationMethod(null);
            } else {
                passwordVisibility = false;
                binding.passwordChangeInputLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_slash_light, null));

                ImageViewCompat.setImageTintList(binding.passwordChangeInputLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Gray600));
                binding.passwordChangeInputLayout.inputEditText.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        binding.passwordChangeTextView.getRoot().setOnClickListener(v -> {
            binding.passwordChangeTextView.getRoot().setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.passwordChangeTextView.getRoot().setClickable(true), 300);

            if (binding.passwordChangeInputLayout.inputEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.passwordChangeInputLayout.inputEditText, binding.passwordChangeInputLayout.errorImageView, binding.passwordChangeInputLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.passwordChangeInputLayout.inputEditText, binding.passwordChangeInputLayout.errorImageView, binding.passwordChangeInputLayout.errorTextView);
                doWork();
            }
        });

        binding.loginTextView.getRoot().setOnClickListener(v -> {
            binding.loginTextView.getRoot().setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.loginTextView.getRoot().setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.loginFragment);
        });

        binding.registerTextView.getRoot().setOnClickListener(v -> {
            binding.registerTextView.getRoot().setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.registerTextView.getRoot().setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.registerFragment);
        });

        binding.passwordRecoverTextView.getRoot().setOnClickListener(v -> {
            binding.passwordRecoverTextView.getRoot().setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> binding.passwordRecoverTextView.getRoot().setClickable(true), 300);

            ((AuthActivity) requireActivity()).navigator(R.id.passwordRecoverFragment);
        });
    }

    private void doWork() {
        password = binding.passwordChangeInputLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}