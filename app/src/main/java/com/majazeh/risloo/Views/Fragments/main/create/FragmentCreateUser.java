package com.majazeh.risloo.views.fragments.main.create;

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
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SheetManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.interfaces.CutCopyPasteListener;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.databinding.FragmentCreateUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class FragmentCreateUser extends Fragment {

    // Binding
    private FragmentCreateUserBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String name = "", mobile = "", email = "", password = "", birthday = "", status = "", type = "", gender = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        setPermission();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateUserFragmentNameHeader));
        binding.mobileIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateUserFragmentMobileHeader));
        binding.emailIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateUserFragmentEmailHeader));
        binding.passwordIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateUserFragmentPasswordHeader));
        binding.birthdayIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateUserFragmentBirthdayHeader));
        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateUserFragmentStatusHeader));
        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateUserFragmentTypeHeader));
        binding.genderIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateUserFragmentGenderHeader));

        binding.passwordGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateUserFragmentPasswordGuide));

        binding.statusIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateUserFragmentStatusActive));
        binding.statusIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateUserFragmentStatusWaiting));
        binding.statusIncludeLayout.thirdRadioButton.setText(getResources().getString(R.string.CreateUserFragmentStatusClosed));

        binding.typeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateUserFragmentTypeAdmin));
        binding.typeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateUserFragmentTypeClient));

        binding.genderIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateUserFragmentGenderMale));
        binding.genderIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateUserFragmentGenderFemale));

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateUserFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nameIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.nameIncludeLayout.inputEditText);
            return false;
        });

        binding.mobileIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.mobileIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.mobileIncludeLayout.inputEditText);
            return false;
        });

        binding.emailIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.emailIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.emailIncludeLayout.inputEditText);
            return false;
        });

        binding.passwordIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.passwordIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.passwordIncludeLayout.inputEditText);
            return false;
        });

        binding.nameIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.mobileIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            mobile = binding.mobileIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.emailIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            email = binding.emailIncludeLayout.inputEditText.getText().toString().trim();
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

        binding.passwordIncludeLayout.inputEditText.setOnCutCopyPasteListener(new CutCopyPasteListener() {
            @Override
            public void onCutListener() {

            }

            @Override
            public void onCopyListener() {

            }

            @Override
            public void onPasteListener() {
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
                InitManager.imgResTintTag(requireActivity(), binding.passwordIncludeLayout.visibilityImageView, R.drawable.ic_eye_light, R.color.risloo500, "visible");
            } else {
                binding.passwordIncludeLayout.inputEditText.setTransformationMethod(new PasswordTransformationMethod());
                InitManager.imgResTintTag(requireActivity(), binding.passwordIncludeLayout.visibilityImageView, R.drawable.ic_eye_slash_light, R.color.coolGray500, "invisible");
            }
        }).widget(binding.passwordIncludeLayout.visibilityImageView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showSheetDate(requireActivity(), birthday, "birthday");
        }).widget(binding.birthdayIncludeLayout.selectTextView);

        binding.statusIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    status = "active";
                    break;
                case R.id.second_radioButton:
                    status = "waiting";
                    break;
                case R.id.third_radioButton:
                    status = "closed";
                    break;
            }
        });

        binding.typeIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    type = "admin";
                    break;
                case R.id.second_radioButton:
                    type = "user";
                    break;
            }
        });

        binding.genderIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    gender = "male";
                    break;
                case R.id.second_radioButton:
                    gender = "female";
                    break;
            }
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.nameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView);
            if (binding.mobileErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.mobileErrorLayout.getRoot(), binding.mobileErrorLayout.errorTextView);
            if (binding.emailErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.emailErrorLayout.getRoot(), binding.emailErrorLayout.errorTextView);
            if (binding.passwordErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.passwordErrorLayout.getRoot(), binding.passwordErrorLayout.errorTextView);
            if (binding.birthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.birthdayErrorLayout.getRoot(), binding.birthdayErrorLayout.errorTextView);
            if (binding.statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.statusErrorLayout.getRoot(), binding.statusErrorLayout.errorTextView);
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.genderErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.genderErrorLayout.getRoot(), binding.genderErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = FragmentCreateUserArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel == null) {
            setData();
        }
    }

    private void setData() {
        birthday = String.valueOf(DateManager.currentTimestamp());
        binding.birthdayIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(birthday, "-"));
    }

    private void setPermission() {
        if (((ActivityMain) requireActivity()).permissoon.showCreateUserBirthday())
            binding.birthdayIncludeLayout.getRoot().setVisibility(View.VISIBLE);
        else
            binding.birthdayIncludeLayout.getRoot().setVisibility(View.GONE);
    }

    public void responseBottomSheet(String method, String data) {
        switch (method) {
            case "birthday":
                birthday = data;
                binding.birthdayIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(birthday, "-"));
                break;
        }
    }

    private void setHashmap() {
        if (!name.equals(""))
            data.put("name", name);
        else
            data.remove("name");

        if (!mobile.equals(""))
            data.put("mobile", mobile);
        else
            data.remove("mobile");

        if (!email.equals(""))
            data.put("email", email);
        else
            data.remove("email");

        if (!birthday.equals("") && binding.birthdayIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
            data.put("birthday", birthday);
        else
            data.remove("birthday");

        if (!password.equals(""))
            data.put("password", password);
        else
            data.remove("password");

        if (!status.equals(""))
            data.put("status", status);
        else
            data.remove("status");

        if (!type.equals(""))
            data.put("type", type);
        else
            data.remove("type");

        if (!gender.equals(""))
            data.put("gender", gender);
        else
            data.remove("gender");
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        User.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                UserModel userModel = (UserModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();
                        SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackCreatedNewUser));

                        ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentUser(userModel);
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
                                StringBuilder allErrors = new StringBuilder();

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    StringBuilder keyErrors = new StringBuilder();

                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                        String error = errorsObject.getJSONArray(key).getString(i);

                                        keyErrors.append(error);
                                        keyErrors.append("\n");

                                        allErrors.append(error);
                                        allErrors.append("\n");
                                    }

                                    switch (key) {
                                        case "name":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "mobile":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.mobileErrorLayout.getRoot(), binding.mobileErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "email":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.emailErrorLayout.getRoot(), binding.emailErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "password":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.passwordErrorLayout.getRoot(), binding.passwordErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "birthday":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.birthdayErrorLayout.getRoot(), binding.birthdayErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "status":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.statusErrorLayout.getRoot(), binding.statusErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "type":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "gender":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.genderErrorLayout.getRoot(), binding.genderErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                    }
                                }

                                SnackManager.showSnackError(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
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