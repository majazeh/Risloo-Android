package com.majazeh.risloo.Views.Fragments.Create;

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
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.CutCopyPasteEditText;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentCreateUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class CreateUserFragment extends Fragment {

    // Binding
    private FragmentCreateUserBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String name = "", mobile = "", email = "", password = "", birthday = "", status = "active", type = "user", gender = "male";
    private boolean passwordVisibility = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

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
        binding.statusIncludeLayout.firstRadioButton.setChecked(true);
        binding.statusIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateUserFragmentStatusWaiting));
        binding.statusIncludeLayout.thirdRadioButton.setText(getResources().getString(R.string.CreateUserFragmentStatusClosed));

        binding.typeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateUserFragmentTypeAdmin));
        binding.typeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateUserFragmentTypeClient));
        binding.typeIncludeLayout.secondRadioButton.setChecked(true);

        binding.genderIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateUserFragmentGenderMale));
        binding.genderIncludeLayout.firstRadioButton.setChecked(true);
        binding.genderIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateUserFragmentGenderFemale));

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateUserFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nameIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
            return false;
        });

        binding.nameIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.mobileIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.mobileIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.mobileIncludeLayout.inputEditText);
            return false;
        });

        binding.mobileIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            mobile = binding.mobileIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.emailIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.emailIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.emailIncludeLayout.inputEditText);
            return false;
        });

        binding.emailIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            email = binding.emailIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.passwordIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.passwordIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.passwordIncludeLayout.inputEditText);
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

        binding.passwordIncludeLayout.inputEditText.setOnCutCopyPasteListener(new CutCopyPasteEditText.OnCutCopyPasteListener() {
            @Override
            public void onCut() {

            }

            @Override
            public void onCopy() {

            }

            @Override
            public void onPaste() {
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
            if (!passwordVisibility) {
                passwordVisibility = true;
                binding.passwordIncludeLayout.inputEditText.setTransformationMethod(null);

                binding.passwordIncludeLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_light, null));
                ImageViewCompat.setImageTintList(binding.passwordIncludeLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Blue800));
            } else {
                passwordVisibility = false;
                binding.passwordIncludeLayout.inputEditText.setTransformationMethod(new PasswordTransformationMethod());

                binding.passwordIncludeLayout.visibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_slash_light, null));
                ImageViewCompat.setImageTintList(binding.passwordIncludeLayout.visibilityImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Gray600));
            }
        }).widget(binding.passwordIncludeLayout.visibilityImageView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showDateBottomSheet(requireActivity(), birthday, "birthday");
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
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView);
            if (binding.mobileErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.mobileErrorLayout.getRoot(), binding.mobileErrorLayout.errorTextView);
            if (binding.emailErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.emailErrorLayout.getRoot(), binding.emailErrorLayout.errorTextView);
            if (binding.passwordErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.passwordErrorLayout.getRoot(), binding.passwordErrorLayout.errorTextView);
            if (binding.birthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.birthdayErrorLayout.getRoot(), binding.birthdayErrorLayout.errorTextView);
            if (binding.statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.statusErrorLayout.getRoot(), binding.statusErrorLayout.errorTextView);
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.genderErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.genderErrorLayout.getRoot(), binding.genderErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        String type = CreateUserFragmentArgs.fromBundle(getArguments()).getType();
        TypeModel typeModel = CreateUserFragmentArgs.fromBundle(getArguments()).getTypeModel();

        // TODO : Place Code When Needed
    }

    public void responseBottomSheet(String method, String data) {
        switch (method) {
            case "birthday":
                birthday = data;
                binding.birthdayIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(birthday, "-"));
                break;
        }
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        data.put("name", name);
        data.put("mobile", mobile);
        data.put("email", email);
        data.put("password", password);
        data.put("birthday", birthday);
        data.put("status", status);
        data.put("type", type);
        data.put("gender", gender);

        User.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastNewUserAdded));

                        ((MainActivity) requireActivity()).navController.navigateUp();
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
                                            case "name":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView, validation);
                                                break;
                                            case "mobile":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.mobileErrorLayout.getRoot(), binding.mobileErrorLayout.errorTextView, validation);
                                                break;
                                            case "email":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.emailErrorLayout.getRoot(), binding.emailErrorLayout.errorTextView, validation);
                                                break;
                                            case "password":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.passwordErrorLayout.getRoot(), binding.passwordErrorLayout.errorTextView, validation);
                                                break;
                                            case "birthday":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.birthdayErrorLayout.getRoot(), binding.birthdayErrorLayout.errorTextView, validation);
                                                break;
                                            case "status":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.statusErrorLayout.getRoot(), binding.statusErrorLayout.errorTextView, validation);
                                                break;
                                            case "type":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, validation);
                                                break;
                                            case "gender":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.genderErrorLayout.getRoot(), binding.genderErrorLayout.errorTextView, validation);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}