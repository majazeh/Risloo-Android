package com.majazeh.risloo.Views.Fragments.Edit;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentEditCenterUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class EditCenterUserFragment extends Fragment {

    // Binding
    private FragmentEditCenterUserBinding binding;

    // Models
    private UserModel userModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String centerId = "", userId = "", position = "", nickname = "", status ="";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCenterUserBinding.inflate(inflater, viewGroup, false);

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

        binding.positionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterUserFragmentPositionHeader));
        binding.nicknameIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterUserFragmentNicknameHeader));
        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterUserFragmentStatusHeader));

        binding.nicknameGuideLayout.guideTextView.setText(getResources().getString(R.string.EditCenterUserFragmentNicknameGuide));

        binding.statusIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditCenterUserFragmentStatusAccept));
        binding.statusIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditCenterUserFragmentStatusKick));

        InitManager.normal12sspSpinner(requireActivity(), binding.positionIncludeLayout.selectSpinner, R.array.UserTypes);

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditCenterUserFragmentButton), getResources().getColor(R.color.White));
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
        binding.positionIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.positionIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    EditCenterUserFragment.this.position = parent.getItemAtPosition(position).toString();

                    switch(EditCenterUserFragment.this.position) {
                        case "مراجع":
                            binding.clientGroup.setVisibility(View.VISIBLE);
                            break;
                        default:
                            binding.clientGroup.setVisibility(View.GONE);
                            break;
                    }

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.nicknameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nicknameIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nicknameIncludeLayout.inputEditText);
            return false;
        });

        binding.nicknameIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            nickname = binding.nicknameIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.statusIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    status = "accept";
                    break;
                case R.id.second_radioButton:
                    status = "kick";
                    break;
            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.positionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(binding.positionErrorLayout.getRoot(), binding.positionErrorLayout.errorTextView);
            if (binding.nicknameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(binding.nicknameErrorLayout.getRoot(), binding.nicknameErrorLayout.errorTextView);
            if (binding.statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(binding.statusErrorLayout.getRoot(), binding.statusErrorLayout.errorTextView);

            doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setArgs() {
        centerId = EditCenterUserFragmentArgs.fromBundle(getArguments()).getCenterId();
        data.put("id", centerId);

        userModel = (UserModel) EditCenterUserFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(userModel);
    }

    private void setData(UserModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            userId = model.getId();
            data.put("userId", userId);
        }

        if (model.getPosition() != null && !model.getPosition().equals("")) {
            position = SelectionManager.getPosition(requireActivity(), "fa", model.getPosition());
            for (int i = 0; i < binding.positionIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.positionIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(position)) {
                    binding.positionIncludeLayout.selectSpinner.setSelection(i);

                    switch(position) {
                        case "مراجع":
                            binding.clientGroup.setVisibility(View.VISIBLE);
                            break;
                        default:
                            binding.clientGroup.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        }

        if (model.getName() != null && !model.getName().equals("")) {
            nickname = model.getName();
            binding.nicknameIncludeLayout.inputEditText.setText(nickname);
        }

        setAcceptation(model);
    }

    private void setAcceptation(UserModel model) {
        if (model.getUserKicked_at() != 0 && model.getUserAccepted_at() != 0) {
            status = "kick";
            binding.statusIncludeLayout.secondRadioButton.setChecked(true);
        } else if (model.getUserKicked_at() != 0) {
            status = "kick";
            binding.statusIncludeLayout.secondRadioButton.setChecked(true);
        } else if (model.getUserAccepted_at() != 0) {
            status = "accept";
            binding.statusIncludeLayout.firstRadioButton.setChecked(true);
        } else {
            status = "";
            binding.statusIncludeLayout.getRoot().clearCheck();
        }
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        data.put("position", SelectionManager.getPosition(requireActivity(), "en", position));
        data.put("nickname", nickname);
        data.put("status", status);

        Center.editUser(data, header, new Response() {
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
                                            case "position":
                                                ((MainActivity) requireActivity()).controlEditText.error(binding.positionErrorLayout.getRoot(), binding.positionErrorLayout.errorTextView, validation);
                                                break;
                                            case "nickname":
                                                ((MainActivity) requireActivity()).controlEditText.error(binding.nicknameErrorLayout.getRoot(), binding.nicknameErrorLayout.errorTextView, validation);
                                                break;
                                            case "status":
                                                ((MainActivity) requireActivity()).controlEditText.error(binding.statusErrorLayout.getRoot(), binding.statusErrorLayout.errorTextView, validation);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}