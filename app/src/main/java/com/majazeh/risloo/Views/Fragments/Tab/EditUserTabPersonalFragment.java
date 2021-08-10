package com.majazeh.risloo.Views.Fragments.Tab;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Permissoon;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragment;
import com.majazeh.risloo.databinding.FragmentEditUserTabPersonalBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class EditUserTabPersonalFragment extends Fragment {

    // Binding
    private FragmentEditUserTabPersonalBinding binding;

    // BottomSheets
    private DateBottomSheet birthdayBottomSheet;

    // Fragments
    private Fragment current;

    // Objects
    private HashMap data, header;

    // Vars
    private String name = "", mobile = "", email = "", birthday = "", status = "", type = "", gender = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUserTabPersonalBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        setPermission();

        return binding.getRoot();
    }

    private void initializer() {
        birthdayBottomSheet = new DateBottomSheet();

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabPersonalNameHeader));
        binding.mobileIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabPersonalMobileHeader));
        binding.emailIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabPersonalEmailHeader));
        binding.birthdayIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabPersonalBirthdayHeader));
        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabPersonalStatusHeader));
        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabPersonalTypeHeader));
        binding.genderIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabPersonalGenderHeader));

        binding.statusIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditUserTabPersonalStatusActive));
        binding.statusIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditUserTabPersonalStatusWaiting));
        binding.statusIncludeLayout.thirdRadioButton.setText(getResources().getString(R.string.EditUserTabPersonalStatusClosed));

        binding.typeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditUserTabPersonalTypeAdmin));
        binding.typeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditUserTabPersonalTypeClient));

        binding.genderIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditUserTabPersonalGenderMale));
        binding.genderIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditUserTabPersonalGenderFemale));

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditUserTabPersonalButton), getResources().getColor(R.color.White));
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
        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nameIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
            return false;
        });

        binding.nameIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.mobileIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.mobileIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.mobileIncludeLayout.inputEditText);
            return false;
        });

        binding.mobileIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            mobile = binding.mobileIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.emailIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.emailIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.emailIncludeLayout.inputEditText);
            return false;
        });

        binding.emailIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            email = binding.emailIncludeLayout.inputEditText.getText().toString().trim();
        });

        ClickManager.onDelayedClickListener(() -> {
            birthdayBottomSheet.show(requireActivity().getSupportFragmentManager(), "birthdayBottomSheet");
            birthdayBottomSheet.setDate(birthday, "birthday");
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

        ClickManager.onDelayedClickListener(() -> {
            if (binding.nameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView);
            if (binding.mobileErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileErrorLayout.getRoot(), binding.mobileErrorLayout.errorTextView);
            if (binding.emailErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.emailIncludeLayout.inputEditText, binding.emailErrorLayout.getRoot(), binding.emailErrorLayout.errorTextView);
            if (binding.birthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.birthdayIncludeLayout.selectTextView, binding.birthdayErrorLayout.getRoot(), binding.birthdayErrorLayout.errorTextView);
            if (binding.statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), (ConstraintLayout) null, binding.statusErrorLayout.getRoot(), binding.statusErrorLayout.errorTextView);
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), (ConstraintLayout) null, binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.genderErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), (ConstraintLayout) null, binding.genderErrorLayout.getRoot(), binding.genderErrorLayout.errorTextView);

            doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof EditUserFragment) {
            UserModel model = ((EditUserFragment) current).userModel;

            if (model.getId() != null && !model.getId().equals("")) {
                data.put("id", model.getId());
            }

            if (model.getName() != null && !model.getName().equals("")) {
                name = model.getName();
                binding.nameIncludeLayout.inputEditText.setText(name);
            }

            if (model.getMobile() != null && !model.getMobile().equals("")) {
                mobile = model.getMobile();
                binding.mobileIncludeLayout.inputEditText.setText(mobile);
            }

            if (model.getEmail() != null && !model.getEmail().equals("")) {
                email = model.getEmail();
                binding.emailIncludeLayout.inputEditText.setText(email);
            }

            if (model.getBirthday() != null && !model.getBirthday().equals("")) {
                birthday = model.getBirthday();
                binding.birthdayIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(birthday, "-"));
            } else {
                birthday = String.valueOf(DateManager.currentTimestamp());
                binding.birthdayIncludeLayout.selectTextView.setText(DateManager.jalYYYYsMMsDD(birthday, "-"));
            }

            if (model.getUserStatus() != null && !model.getUserStatus().equals("")) {
                status = model.getUserStatus();
                switch (status) {
                    case "active":
                        binding.statusIncludeLayout.firstRadioButton.setChecked(true);
                        break;
                    case "waiting":
                        binding.statusIncludeLayout.secondRadioButton.setChecked(true);
                        break;
                    case "closed":
                        binding.statusIncludeLayout.thirdRadioButton.setChecked(true);
                        break;
                }
            }

            if (model.getUserType() != null && !model.getUserType().equals("")) {
                type = model.getUserType();
                switch (type) {
                    case "admin":
                        binding.typeIncludeLayout.firstRadioButton.setChecked(true);
                        break;
                    case "user":
                        binding.typeIncludeLayout.secondRadioButton.setChecked(true);
                        break;
                }
            }

            if (model.getGender() != null && !model.getGender().equals("")) {
                gender = model.getGender();
                switch (gender) {
                    case "male":
                        binding.genderIncludeLayout.firstRadioButton.setChecked(true);
                        break;
                    case "female":
                        binding.genderIncludeLayout.secondRadioButton.setChecked(true);
                        break;
                }
            }
        }
    }

    private void setPermission() {
        if (current instanceof EditUserFragment) {
            UserModel model = ((EditUserFragment) current).userModel;

            if (Permissoon.showEditUserTabPersonalMobile(model))
                binding.mobileIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.mobileIncludeLayout.getRoot().setVisibility(View.GONE);

            if (Permissoon.showEditUserTabPersonalEmail(model))
                binding.emailIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.emailIncludeLayout.getRoot().setVisibility(View.GONE);

            if (Permissoon.showEditUserTabPersonalBirthday(model))
                binding.birthdayIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.birthdayIncludeLayout.getRoot().setVisibility(View.GONE);

            if (Permissoon.showEditUserTabPersonalStatus(model))
                binding.statusIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.statusIncludeLayout.getRoot().setVisibility(View.GONE);

            if (Permissoon.showEditUserTabPersonalType(model))
                binding.typeIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.typeIncludeLayout.getRoot().setVisibility(View.GONE);
        }
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
        data.put("birthday", birthday);
        data.put("status", status);
        data.put("type", type);
        data.put("gender", gender);

        if (Objects.equals(data.get("id"), ((MainActivity) requireActivity()).singleton.getId())) {
            Auth.editProfile(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel authModel = (AuthModel) object;
                    UserModel userModel = authModel.getUser();

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((MainActivity) requireActivity()).singleton.update(userModel);
                            ((MainActivity) requireActivity()).setData();

                            ((MainActivity) requireActivity()).loadingDialog.dismiss();
                            ToastManager.showSuccesToast(requireActivity(), getResources().getString(R.string.ToastChangesSaved));
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
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView, validation);
                                                    break;
                                                case "mobile":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileErrorLayout.getRoot(), binding.mobileErrorLayout.errorTextView, validation);
                                                    break;
                                                case "email":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.emailIncludeLayout.inputEditText, binding.emailErrorLayout.getRoot(), binding.emailErrorLayout.errorTextView, validation);
                                                    break;
                                                case "birthday":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.birthdayIncludeLayout.selectTextView, binding.birthdayErrorLayout.getRoot(), binding.birthdayErrorLayout.errorTextView, validation);
                                                    break;
                                                case "status":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, binding.statusErrorLayout.getRoot(), binding.statusErrorLayout.errorTextView, validation);
                                                    break;
                                                case "type":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, validation);
                                                    break;
                                                case "gender":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, binding.genderErrorLayout.getRoot(), binding.genderErrorLayout.errorTextView, validation);
                                                    break;
                                            }

                                            errors.append(validation);
                                            errors.append("\n");
                                        }
                                    }

                                    ToastManager.showErrorToast(requireActivity(), errors.substring(0, errors.length() - 1));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            });
        } else {
            User.editProfile(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((MainActivity) requireActivity()).loadingDialog.dismiss();
                            ToastManager.showSuccesToast(requireActivity(), getResources().getString(R.string.ToastChangesSaved));
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
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView, validation);
                                                    break;
                                                case "mobile":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileErrorLayout.getRoot(), binding.mobileErrorLayout.errorTextView, validation);
                                                    break;
                                                case "email":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.emailIncludeLayout.inputEditText, binding.emailErrorLayout.getRoot(), binding.emailErrorLayout.errorTextView, validation);
                                                    break;
                                                case "birthday":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.birthdayIncludeLayout.selectTextView, binding.birthdayErrorLayout.getRoot(), binding.birthdayErrorLayout.errorTextView, validation);
                                                    break;
                                                case "status":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, binding.statusErrorLayout.getRoot(), binding.statusErrorLayout.errorTextView, validation);
                                                    break;
                                                case "type":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, validation);
                                                    break;
                                                case "gender":
                                                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (ConstraintLayout) null, binding.genderErrorLayout.getRoot(), binding.genderErrorLayout.errorTextView, validation);
                                                    break;
                                            }

                                            errors.append(validation);
                                            errors.append("\n");
                                        }
                                    }

                                    ToastManager.showErrorToast(requireActivity(), errors.substring(0, errors.length() - 1));
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