package com.majazeh.risloo.Views.Fragments.Tab;

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
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.CutCopyPasteEditText;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentEditUserPasswordBinding;

public class EditUserPasswordFragment extends Fragment {

    // Binding
    private FragmentEditUserPasswordBinding binding;

    // Vars
    private String password = "";
    private boolean passwordVisibility = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUserPasswordBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        binding.passwordIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPasswordFragmentHeader));

        binding.passwordGuideLayout.guideTextView.setText(getResources().getString(R.string.EditPasswordFragmentGuide));

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditPasswordFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.passwordIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.passwordIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.passwordIncludeLayout.inputEditText);
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
                    binding.passwordIncludeLayout.visibilityImageView.setVisibility(View.INVISIBLE);
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
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.passwordIncludeLayout.inputEditText, binding.passwordIncludeLayout.errorImageView, binding.passwordIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.passwordIncludeLayout.inputEditText, binding.passwordIncludeLayout.errorImageView, binding.passwordIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getPassword().equals("")) {
            password = ((MainActivity) requireActivity()).singleton.getPassword();
            binding.passwordIncludeLayout.inputEditText.setText(password);
        }
    }

    private void doWork() {
        password = binding.passwordIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}