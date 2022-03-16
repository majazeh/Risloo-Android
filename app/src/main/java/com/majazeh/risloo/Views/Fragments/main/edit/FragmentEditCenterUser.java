package com.majazeh.risloo.views.fragments.main.edit;

import android.annotation.SuppressLint;
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
import com.majazeh.risloo.databinding.FragmentEditCenterUserBinding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.DropdownManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.HashMap;

public class FragmentEditCenterUser extends Fragment {

    // Binding
    private FragmentEditCenterUserBinding binding;

    // Models
    private UserModel userModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String position = "", nickname = "", status ="";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCenterUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.positionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterUserFragmentPositionHeader));
        binding.nicknameIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterUserFragmentNicknameHeader));
        binding.statusIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterUserFragmentStatusHeader));

        binding.nicknameGuideLayout.guideTextView.setText(getResources().getString(R.string.EditCenterUserFragmentNicknameGuide));

        binding.statusIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.EditCenterUserFragmentStatusAccept));
        binding.statusIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.EditCenterUserFragmentStatusKick));

        DropdownManager.spinner12ssp(requireActivity(), binding.positionIncludeLayout.selectSpinner, R.array.UserTypes);

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditCenterUserFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.nicknameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nicknameIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.nicknameIncludeLayout.inputEditText);
            return false;
        });

        binding.nicknameIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            nickname = binding.nicknameIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.positionIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.positionIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.positionIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    FragmentEditCenterUser.this.position = parent.getItemAtPosition(position).toString();

                    switch(FragmentEditCenterUser.this.position) {
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

        CustomClickView.onDelayedListener(() -> {
            ((ActivityMain) requireActivity()).validatoon.resetValid(binding);

            doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setArgs() {
        CenterModel centerModel = (CenterModel) FragmentEditCenterUserArgs.fromBundle(getArguments()).getCenterModel();
        setData(centerModel);

        userModel = (UserModel) FragmentEditCenterUserArgs.fromBundle(getArguments()).getTypeModel();
        setData(userModel);
    }

    private void setData(CenterModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }
    }

    private void setData(UserModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("userId", model.getId());
        }

        if (model.getPosition() != null && !model.getPosition().equals("")) {
            position = JsonManager.getUserType(requireActivity(), "fa", model.getPosition());
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
        if (model.getKickedAt() != 0 && model.getAcceptedAt() != 0) {
            status = "kick";
            binding.statusIncludeLayout.secondRadioButton.setChecked(true);
        } else if (model.getKickedAt() != 0) {
            status = "kick";
            binding.statusIncludeLayout.secondRadioButton.setChecked(true);
        } else if (model.getAcceptedAt() != 0) {
            status = "accept";
            binding.statusIncludeLayout.firstRadioButton.setChecked(true);
        } else {
            status = "";
            binding.statusIncludeLayout.getRoot().clearCheck();
        }
    }

    private void setHashmap() {
        if (!position.equals(""))
            data.put("position", JsonManager.getUserType(requireActivity(), "en", position));
        else
            data.remove("position");

        if (!nickname.equals(""))
            data.put("name", nickname);
        else
            data.remove("name");

        if (!status.equals(""))
            data.put("status", status);
        else
            data.remove("status");
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Center.editUser(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();
                        SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackChangesSaved));

                        ((ActivityMain) requireActivity()).navigatoon.navigateUp();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}