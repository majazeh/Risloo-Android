package com.majazeh.risloo.Views.Fragments.Tab;

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
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.CutCopyPasteEditText;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragment;
import com.majazeh.risloo.databinding.FragmentEditUserTabPasswordBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class EditUserTabPasswordFragment extends Fragment {

    // Binding
    private FragmentEditUserTabPasswordBinding binding;

    // Fragments
    private Fragment current;

    // Objects
    private HashMap data, header;

    // Vars
    private String mobile = "", currentPassword = "", newPassword = "";
    private boolean currentPasswordVisibility = false, newPasswordVisibility = false;

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
        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.currentPasswordIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabPasswordCurrentPasswordHeader));
        binding.newPasswordIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabPasswordNewPasswordHeader));

        binding.newPasswordGuideLayout.guideTextView.setText(getResources().getString(R.string.EditUserTabPasswordNewPasswordGuide));

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditUserTabPasswordButton), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.currentPasswordIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.currentPasswordIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.currentPasswordIncludeLayout.inputEditText);
            return false;
        });

        binding.currentPasswordIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            currentPassword = binding.currentPasswordIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.newPasswordIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.newPasswordIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.newPasswordIncludeLayout.inputEditText);
            return false;
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

        binding.currentPasswordIncludeLayout.inputEditText.setOnCutCopyPasteListener(new CutCopyPasteEditText.OnCutCopyPasteListener() {
            @Override
            public void onCut() {

            }

            @Override
            public void onCopy() {

            }

            @Override
            public void onPaste() {
                if (binding.currentPasswordIncludeLayout.inputEditText.length() == 0) {
                    if (binding.currentPasswordIncludeLayout.visibilityImageView.getVisibility() != View.GONE)
                        binding.currentPasswordIncludeLayout.visibilityImageView.setVisibility(View.GONE);
                } else if (binding.currentPasswordIncludeLayout.inputEditText.length() != 0) {
                    if (binding.currentPasswordIncludeLayout.visibilityImageView.getVisibility() != View.VISIBLE)
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
            if (!currentPasswordVisibility) {
                currentPasswordVisibility = true;
                binding.currentPasswordIncludeLayout.inputEditText.setTransformationMethod(null);

                binding.currentPasswordIncludeLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_light, null));
                ImageViewCompat.setImageTintList(binding.currentPasswordIncludeLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Blue800));
            } else {
                currentPasswordVisibility = false;
                binding.currentPasswordIncludeLayout.inputEditText.setTransformationMethod(new PasswordTransformationMethod());

                binding.currentPasswordIncludeLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_slash_light, null));
                ImageViewCompat.setImageTintList(binding.currentPasswordIncludeLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Gray600));
            }
        }).widget(binding.currentPasswordIncludeLayout.visibilityImageView);

        CustomClickView.onDelayedListener(() -> {
            if (!newPasswordVisibility) {
                newPasswordVisibility = true;
                binding.newPasswordIncludeLayout.inputEditText.setTransformationMethod(null);

                binding.newPasswordIncludeLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_light, null));
                ImageViewCompat.setImageTintList(binding.newPasswordIncludeLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Blue800));
            } else {
                newPasswordVisibility = false;
                binding.newPasswordIncludeLayout.inputEditText.setTransformationMethod(new PasswordTransformationMethod());

                binding.newPasswordIncludeLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_slash_light, null));
                ImageViewCompat.setImageTintList(binding.newPasswordIncludeLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Gray600));
            }
        }).widget(binding.newPasswordIncludeLayout.visibilityImageView);

        CustomClickView.onDelayedListener(() -> {
            if (Objects.equals(data.get("id"), ((MainActivity) requireActivity()).singleton.getId())) {
                if (binding.currentPasswordErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                    ((MainActivity) requireActivity()).validatoon.hideValid(binding.currentPasswordErrorLayout.getRoot(), binding.currentPasswordErrorLayout.errorTextView);
                if (binding.newPasswordErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                    ((MainActivity) requireActivity()).validatoon.hideValid(binding.newPasswordErrorLayout.getRoot(), binding.newPasswordErrorLayout.errorTextView);

                doWork();
            } else {
                if (binding.newPasswordErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                    ((MainActivity) requireActivity()).validatoon.hideValid(binding.newPasswordErrorLayout.getRoot(), binding.newPasswordErrorLayout.errorTextView);

                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof EditUserFragment) {
            UserModel model = ((EditUserFragment) current).userModel;

            if (model.getId() != null && !model.getId().equals("")) {
                data.put("id", model.getId());
            }

            if (model.getMobile() != null && !model.getMobile().equals("")) {
                mobile = model.getMobile();
            }
        }
    }

    private void setPermission() {
        if (current instanceof EditUserFragment) {
            UserModel model = ((EditUserFragment) current).userModel;

            if (((MainActivity) requireActivity()).permissoon.showEditUserTabPasswordCurrent(model))
                binding.currentPasswordIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.currentPasswordIncludeLayout.getRoot().setVisibility(View.GONE);
        }
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        if (Objects.equals(data.get("id"), ((MainActivity) requireActivity()).singleton.getId())) {
            data.put("password",  currentPassword);
            data.put("new_password", newPassword);

            Auth.editPassword(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((MainActivity) requireActivity()).singleton.regist(StringManager.mobileConvert(mobile), newPassword);

                            DialogManager.dismissLoadingDialog();
                            SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastChangesSaved));
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

                                            switch (key) {
                                                case "password":
                                                    ((MainActivity) requireActivity()).validatoon.showValid(binding.currentPasswordErrorLayout.getRoot(), binding.currentPasswordErrorLayout.errorTextView, validation);
                                                    break;
                                                case "new_password":
                                                    ((MainActivity) requireActivity()).validatoon.showValid(binding.newPasswordErrorLayout.getRoot(), binding.newPasswordErrorLayout.errorTextView, validation);
                                                    break;
                                            }

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
        } else {
            data.put("new_password", newPassword);

            User.editPassword(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            DialogManager.dismissLoadingDialog();
                            SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastChangesSaved));
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

                                            switch (key) {
                                                case "new_password":
                                                    ((MainActivity) requireActivity()).validatoon.showValid(binding.newPasswordErrorLayout.getRoot(), binding.newPasswordErrorLayout.errorTextView, validation);
                                                    break;
                                            }

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}