package com.majazeh.risloo.views.fragments.main.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.FragmentEditUserTabPersonalBinding;
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SheetManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditUser;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.HashMap;
import java.util.Objects;

public class FragmentEditUserTabPersonal extends Fragment {

    // Binding
    private FragmentEditUserTabPersonalBinding binding;

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

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditUserTabPersonalButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
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

        binding.nameIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.mobileIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            mobile = binding.mobileIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.emailIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            email = binding.emailIncludeLayout.inputEditText.getText().toString().trim();
        });

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
            ((ActivityMain) requireActivity()).validatoon.resetValid(binding);

            doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof FragmentEditUser) {
            UserModel model = ((FragmentEditUser) current).userModel;

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

            if (model.getStatus() != null && !model.getStatus().equals("")) {
                status = model.getStatus();
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

            if (model.getType() != null && !model.getType().equals("")) {
                type = model.getType();
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
        if (current instanceof FragmentEditUser) {
            UserModel model = ((FragmentEditUser) current).userModel;

            if (((ActivityMain) requireActivity()).permissoon.showEditUserTabPersonalMobile(model))
                binding.mobileIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.mobileIncludeLayout.getRoot().setVisibility(View.GONE);

            if (((ActivityMain) requireActivity()).permissoon.showEditUserTabPersonalEmail(model))
                binding.emailIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.emailIncludeLayout.getRoot().setVisibility(View.GONE);

            if (((ActivityMain) requireActivity()).permissoon.showEditUserTabPersonalBirthday())
                binding.birthdayIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.birthdayIncludeLayout.getRoot().setVisibility(View.GONE);

            if (((ActivityMain) requireActivity()).permissoon.showEditUserTabPersonalStatus(model))
                binding.statusIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.statusIncludeLayout.getRoot().setVisibility(View.GONE);

            if (((ActivityMain) requireActivity()).permissoon.showEditUserTabPersonalType(model))
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

    private void setHashmap() {
        if (!name.equals(""))
            data.put("name", name);
        else
            data.remove("name");

        if (!mobile.equals("") && binding.mobileIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
            data.put("mobile", mobile);
        else
            data.remove("mobile");

        if (!email.equals("") && binding.emailIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
            data.put("email", email);
        else
            data.remove("email");

        if (!birthday.equals("") && binding.birthdayIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
            data.put("birthday", birthday);
        else
            data.remove("birthday");

        if (!status.equals("") && binding.statusIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
            data.put("status", status);
        else
            data.remove("status");

        if (!type.equals("") && binding.typeIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
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

        if (Objects.equals(data.get("id"), ((ActivityMain) requireActivity()).singleton.getUserModel().getId())) {
            Auth.editProfile(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel authModel = (AuthModel) object;
                    UserModel userModel = authModel.getUser();

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((ActivityMain) requireActivity()).singleton.params(userModel);
                            ((ActivityMain) requireActivity()).setData();

                            DialogManager.dismissDialogLoading();
                            SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackChangesSaved));
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.requestValid(response, binding));
                    }
                }
            });
        } else {
            User.editProfile(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            DialogManager.dismissDialogLoading();
                            SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackChangesSaved));
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.requestValid(response, binding));
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