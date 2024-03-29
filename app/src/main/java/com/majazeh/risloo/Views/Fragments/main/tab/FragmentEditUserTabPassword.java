package com.majazeh.risloo.views.fragments.main.tab;

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

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.FragmentEditUserTabPasswordBinding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.widgets.interfaces.CutCopyPasteListener;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditUser;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.HashMap;
import java.util.Objects;

public class FragmentEditUserTabPassword extends Fragment {

    // Binding
    private FragmentEditUserTabPasswordBinding binding;

    // Fragments
    private Fragment current;

    // Objects
    private HashMap data, header;

    // Vars
    private String currentPassword = "", newPassword = "", mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUserTabPasswordBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        setPermission();

        return binding.getRoot();
    }

    private void initializer() {
        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.currentPasswordIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabPasswordCurrentPasswordHeader));
        binding.newPasswordIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabPasswordNewPasswordHeader));

        binding.newPasswordGuideLayout.guideTextView.setText(getResources().getString(R.string.EditUserTabPasswordNewPasswordGuide));

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditUserTabPasswordButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.currentPasswordIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.currentPasswordIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.currentPasswordIncludeLayout.inputEditText);
            return false;
        });

        binding.newPasswordIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.newPasswordIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.newPasswordIncludeLayout.inputEditText);
            return false;
        });

        binding.currentPasswordIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            currentPassword = binding.currentPasswordIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.newPasswordIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            newPassword = binding.newPasswordIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.currentPasswordIncludeLayout.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.currentPasswordIncludeLayout.inputEditText.length() == 0) {
                    if (binding.currentPasswordIncludeLayout.visibilityImageView.getVisibility() != View.GONE)
                        binding.currentPasswordIncludeLayout.visibilityImageView.setVisibility(View.GONE);
                } else if (binding.currentPasswordIncludeLayout.inputEditText.length() == 1) {
                    if (binding.currentPasswordIncludeLayout.visibilityImageView.getVisibility() != View.VISIBLE)
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
                    if (binding.newPasswordIncludeLayout.visibilityImageView.getVisibility() != View.GONE)
                        binding.newPasswordIncludeLayout.visibilityImageView.setVisibility(View.GONE);
                } else if (binding.newPasswordIncludeLayout.inputEditText.length() == 1) {
                    if (binding.newPasswordIncludeLayout.visibilityImageView.getVisibility() != View.VISIBLE)
                        binding.newPasswordIncludeLayout.visibilityImageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.currentPasswordIncludeLayout.inputEditText.setOnCutCopyPasteListener(new CutCopyPasteListener() {
            @Override
            public void onCutListener() {

            }

            @Override
            public void onCopyListener() {

            }

            @Override
            public void onPasteListener() {
                if (binding.currentPasswordIncludeLayout.inputEditText.length() == 0) {
                    if (binding.currentPasswordIncludeLayout.visibilityImageView.getVisibility() != View.GONE)
                        binding.currentPasswordIncludeLayout.visibilityImageView.setVisibility(View.GONE);
                } else if (binding.currentPasswordIncludeLayout.inputEditText.length() != 0) {
                    if (binding.currentPasswordIncludeLayout.visibilityImageView.getVisibility() != View.VISIBLE)
                        binding.currentPasswordIncludeLayout.visibilityImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.newPasswordIncludeLayout.inputEditText.setOnCutCopyPasteListener(new CutCopyPasteListener() {
            @Override
            public void onCutListener() {

            }

            @Override
            public void onCopyListener() {

            }

            @Override
            public void onPasteListener() {
                if (binding.newPasswordIncludeLayout.inputEditText.length() == 0) {
                    if (binding.newPasswordIncludeLayout.visibilityImageView.getVisibility() != View.GONE)
                        binding.newPasswordIncludeLayout.visibilityImageView.setVisibility(View.GONE);
                } else if (binding.newPasswordIncludeLayout.inputEditText.length() != 0) {
                    if (binding.newPasswordIncludeLayout.visibilityImageView.getVisibility() != View.VISIBLE)
                        binding.newPasswordIncludeLayout.visibilityImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.currentPasswordIncludeLayout.visibilityImageView.getTag().equals("invisible")) {
                binding.currentPasswordIncludeLayout.inputEditText.setTransformationMethod(null);
                InitManager.imgResTintTag(requireActivity(), binding.currentPasswordIncludeLayout.visibilityImageView, R.drawable.ic_eye_light, R.color.risloo500, "visible");
            } else {
                binding.currentPasswordIncludeLayout.inputEditText.setTransformationMethod(new PasswordTransformationMethod());
                InitManager.imgResTintTag(requireActivity(), binding.currentPasswordIncludeLayout.visibilityImageView, R.drawable.ic_eye_slash_light, R.color.coolGray500, "invisible");
            }
        }).widget(binding.currentPasswordIncludeLayout.visibilityImageView);

        CustomClickView.onDelayedListener(() -> {
            if (binding.newPasswordIncludeLayout.visibilityImageView.getTag().equals("invisible")) {
                binding.newPasswordIncludeLayout.inputEditText.setTransformationMethod(null);
                InitManager.imgResTintTag(requireActivity(), binding.newPasswordIncludeLayout.visibilityImageView, R.drawable.ic_eye_light, R.color.risloo500, "visible");
            } else {
                binding.newPasswordIncludeLayout.inputEditText.setTransformationMethod(new PasswordTransformationMethod());
                InitManager.imgResTintTag(requireActivity(), binding.newPasswordIncludeLayout.visibilityImageView, R.drawable.ic_eye_slash_light, R.color.coolGray500, "invisible");
            }
        }).widget(binding.newPasswordIncludeLayout.visibilityImageView);

        CustomClickView.onDelayedListener(() -> {
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding);

            doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof FragmentEditUser) {
            UserModel model = ((FragmentEditUser) current).userModel;

            if (model.getId() != null && !model.getId().equals("")) {
                data.put("id", model.getId());
            }

            if (model.getMobile() != null && !model.getMobile().equals("")) {
                mobile = model.getMobile();
            }
        }
    }

    private void setPermission() {
        if (current instanceof FragmentEditUser) {
            UserModel model = ((FragmentEditUser) current).userModel;

            if (((ActivityMain) requireActivity()).permissoon.showEditUserTabPasswordCurrent(model))
                binding.currentPasswordIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.currentPasswordIncludeLayout.getRoot().setVisibility(View.GONE);
        }
    }

    private void resetInputs() {
        if (Objects.equals(data.get("id"), ((ActivityMain) requireActivity()).singleton.getUserModel().getId())) {
            currentPassword = "";
            binding.currentPasswordIncludeLayout.inputEditText.setText(currentPassword);
        }

        newPassword = "";
        binding.newPasswordIncludeLayout.inputEditText.setText(newPassword);
    }

    private void setHashmap() {
        if (Objects.equals(data.get("id"), ((ActivityMain) requireActivity()).singleton.getUserModel().getId())) {
            if (!currentPassword.equals(""))
                data.put("password", currentPassword);
            else
                data.remove("password");
        }

        if (!newPassword.equals(""))
            data.put("new_password", newPassword);
        else
            data.remove("new_password");
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        if (Objects.equals(data.get("id"), ((ActivityMain) requireActivity()).singleton.getUserModel().getId())) {
            Auth.editPassword(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((ActivityMain) requireActivity()).singleton.regist(StringManager.adjustMobile(mobile), newPassword);
                            resetInputs();

                            DialogManager.dismissDialogLoading();
                            SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackChangesSaved));
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.showValid(response, binding));
                    }
                }
            });
        } else {
            User.editPassword(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            resetInputs();

                            DialogManager.dismissDialogLoading();
                            SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackChangesSaved));
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.showValid(response, binding));
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}