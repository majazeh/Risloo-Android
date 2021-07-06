package com.majazeh.risloo.Views.Fragments.Tab;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragment;
import com.majazeh.risloo.databinding.FragmentEditUserPersonalBinding;
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

public class EditUserPersonalFragment extends Fragment {

    // Binding
    private FragmentEditUserPersonalBinding binding;

    // BottomSheets
    private DateBottomSheet birthdayBottomSheet;

    // Vars
    private HashMap data, header;
    private String name = "", mobile = "", username = "", email = "", birthday = "", status = "active", type = "user", gender = "male";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUserPersonalBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        birthdayBottomSheet = new DateBottomSheet();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserPersonalTabNameHeader));
        binding.mobileIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserPersonalTabMobileHeader));
        binding.usernameIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserPersonalTabUsernameHeader));
        binding.emailIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserPersonalTabEmailHeader));
        binding.birthdayIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserPersonalTabBirthdayHeader));
        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserPersonalTabStatusHeader));
        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserPersonalTabTypeHeader));
        binding.genderIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserPersonalTabGenderHeader));

        binding.usernameGuideLayout.guideTextView.setText(getResources().getString(R.string.EditUserPersonalTabUsernameGuide));

        binding.statusIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditUserPersonalTabStatusActive));
        binding.statusIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditUserPersonalTabStatusWaiting));
        binding.statusIncludeLayout.thirdRadioButton.setText(getResources().getString(R.string.EditUserPersonalTabStatusClosed));

        binding.typeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditUserPersonalTabTypeAdmin));
        binding.typeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditUserPersonalTabTypeClient));

        binding.genderIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditUserPersonalTabGenderMale));
        binding.genderIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditUserPersonalTabGenderFemale));

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditUserPersonalTabButton), getResources().getColor(R.color.White));
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
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.nameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.mobileIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.mobileIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.mobileIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.usernameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.usernameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.usernameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.emailIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.emailIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.emailIncludeLayout.inputEditText);
                }
            }
            return false;
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
            if (binding.nameIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView);
                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (getParent() != null) {
            UserModel model = getParent().userModel;

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

            if (model.getUsername() != null && !model.getUsername().equals("")) {
                username = model.getUsername();
                binding.usernameIncludeLayout.inputEditText.setText(username);
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

                        binding.clientGroup.setVisibility(View.VISIBLE);
                        break;
                    default:
                        binding.typeIncludeLayout.secondRadioButton.setChecked(true);

                        binding.clientGroup.setVisibility(View.GONE);
                        break;
                }
            } else {
                binding.typeIncludeLayout.secondRadioButton.setChecked(true);

                binding.clientGroup.setVisibility(View.GONE);
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

    private EditUserFragment getParent() {
        Fragment fragment = ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null)
            if (fragment instanceof EditUserFragment)
                return (EditUserFragment) fragment;

        return null;
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

        name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        mobile = binding.mobileIncludeLayout.inputEditText.getText().toString().trim();
        username = binding.usernameIncludeLayout.inputEditText.getText().toString().trim();
        email = binding.emailIncludeLayout.inputEditText.getText().toString().trim();

        data.put("name", name);
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
                            Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (!jsonObject.isNull("errors")) {
                                    Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());

                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                                            if (key.equals("name")) {
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                            }
                                        }
                                    }
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
                            Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (!jsonObject.isNull("errors")) {
                                    Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());

                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                                            if (key.equals("name")) {
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                            }
                                        }
                                    }
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