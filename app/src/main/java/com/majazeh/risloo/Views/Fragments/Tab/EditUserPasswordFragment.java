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
import android.widget.Toast;

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
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class EditUserPasswordFragment extends Fragment {

    // Binding
    private FragmentEditUserPasswordBinding binding;

    // Vars
    private String currentPassword = "", newPassword = "";
    private boolean currentPasswordVisibility = false, newPasswordVisibility = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUserPasswordBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        binding.currentPasswordIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserPasswordTabCurrentPasswordHeader));
        binding.newPasswordIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserPasswordTabNewPasswordHeader));

        binding.newPasswordGuideLayout.guideTextView.setText(getResources().getString(R.string.EditUserPasswordTabNewPasswordGuide));

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditUserPasswordTabButton), getResources().getColor(R.color.White));
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
        binding.currentPasswordIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.currentPasswordIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.currentPasswordIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.newPasswordIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.newPasswordIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.newPasswordIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.currentPasswordIncludeLayout.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.currentPasswordIncludeLayout.inputEditText.length() == 0) {
                    binding.currentPasswordIncludeLayout.visibilityImageView.setVisibility(View.INVISIBLE);
                } else if (binding.currentPasswordIncludeLayout.inputEditText.length() == 1) {
                    binding.currentPasswordIncludeLayout.visibilityImageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.newPasswordIncludeLayout.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.newPasswordIncludeLayout.inputEditText.length() == 0) {
                    binding.newPasswordIncludeLayout.visibilityImageView.setVisibility(View.INVISIBLE);
                } else if (binding.newPasswordIncludeLayout.inputEditText.length() == 1) {
                    binding.newPasswordIncludeLayout.visibilityImageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.currentPasswordIncludeLayout.inputEditText.setOnCutCopyPasteListener(new CutCopyPasteEditText.OnCutCopyPasteListener() {
            @Override
            public void onCut() {

            }

            @Override
            public void onCopy() {

            }

            @Override
            public void onPaste() {
                if (binding.currentPasswordIncludeLayout.inputEditText.length() != 0) {
                    binding.currentPasswordIncludeLayout.visibilityImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.newPasswordIncludeLayout.inputEditText.setOnCutCopyPasteListener(new CutCopyPasteEditText.OnCutCopyPasteListener() {
            @Override
            public void onCut() {

            }

            @Override
            public void onCopy() {

            }

            @Override
            public void onPaste() {
                if (binding.newPasswordIncludeLayout.inputEditText.length() != 0) {
                    binding.newPasswordIncludeLayout.visibilityImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (!currentPasswordVisibility) {
                currentPasswordVisibility = true;
                binding.currentPasswordIncludeLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_light, null));

                ImageViewCompat.setImageTintList(binding.currentPasswordIncludeLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Blue800));
                binding.currentPasswordIncludeLayout.inputEditText.setTransformationMethod(null);
            } else {
                currentPasswordVisibility = false;
                binding.currentPasswordIncludeLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_slash_light, null));

                ImageViewCompat.setImageTintList(binding.currentPasswordIncludeLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Gray600));
                binding.currentPasswordIncludeLayout.inputEditText.setTransformationMethod(new PasswordTransformationMethod());
            }
        }).widget(binding.currentPasswordIncludeLayout.visibilityImageView);

        ClickManager.onDelayedClickListener(() -> {
            if (!newPasswordVisibility) {
                newPasswordVisibility = true;
                binding.newPasswordIncludeLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_light, null));

                ImageViewCompat.setImageTintList(binding.newPasswordIncludeLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Blue800));
                binding.newPasswordIncludeLayout.inputEditText.setTransformationMethod(null);
            } else {
                newPasswordVisibility = false;
                binding.newPasswordIncludeLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_slash_light, null));

                ImageViewCompat.setImageTintList(binding.newPasswordIncludeLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Gray600));
                binding.newPasswordIncludeLayout.inputEditText.setTransformationMethod(new PasswordTransformationMethod());
            }
        }).widget(binding.newPasswordIncludeLayout.visibilityImageView);

        ClickManager.onDelayedClickListener(() -> {
            if (binding.currentPasswordIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.currentPasswordIncludeLayout.inputEditText, binding.currentPasswordErrorLayout.getRoot(), binding.currentPasswordErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.currentPasswordIncludeLayout.inputEditText, binding.currentPasswordErrorLayout.getRoot(), binding.currentPasswordErrorLayout.errorTextView);
            }
            if (binding.newPasswordIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.newPasswordIncludeLayout.inputEditText, binding.newPasswordErrorLayout.getRoot(), binding.newPasswordErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.newPasswordIncludeLayout.inputEditText, binding.newPasswordErrorLayout.getRoot(), binding.newPasswordErrorLayout.errorTextView);
            }

            if (binding.currentPasswordIncludeLayout.inputEditText.length() != 0 && binding.newPasswordIncludeLayout.inputEditText.length() != 0) {
                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void doWork() {
        currentPassword = binding.currentPasswordIncludeLayout.inputEditText.getText().toString().trim();
        newPassword = binding.newPasswordIncludeLayout.inputEditText.getText().toString().trim();

        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        HashMap data = new HashMap();
        data.put("user",  ((MainActivity) requireActivity()).singleton.getUserId());

        data.put("password",  currentPassword);
        data.put("new_password", newPassword);

        HashMap header = new HashMap();
        header.put("Authorization", "Bearer "+((MainActivity) requireActivity()).singleton.getToken());

        Auth.editPassword(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                requireActivity().runOnUiThread(() -> {
                    ((MainActivity) requireActivity()).loadingDialog.dismiss();
                    Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onFailure(String response) {
                requireActivity().runOnUiThread(() -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (!jsonObject.isNull("errors")) {
                            Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());

                            while (keys.hasNext()) {
                                String key = keys.next();
                                for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                                    if (key.equals("password")) {
                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.currentPasswordIncludeLayout.inputEditText, binding.currentPasswordErrorLayout.getRoot(), binding.currentPasswordErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                    } else if (key.equals("new_password")) {
                                        ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.newPasswordIncludeLayout.inputEditText, binding.newPasswordErrorLayout.getRoot(), binding.newPasswordErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                    }
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}