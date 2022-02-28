package com.majazeh.risloo.views.fragments.main.create;

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
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SelectionManager;
import com.majazeh.risloo.utils.managers.SheetManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.databinding.FragmentCreateCenterUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class FragmentCreateCenterUser extends Fragment {

    // Binding
    private FragmentCreateCenterUserBinding binding;

    // Models
    public CenterModel centerModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String type = "", mobile = "", position = "", roomId = "", nickname = "", createCase = "0";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCenterUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.mobileIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentMobileHeader));
        binding.positionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentPositionHeader));
        binding.roomIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentRoomHeader));
        binding.nicknameIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentNicknameHeader));

        binding.nicknameGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentNicknameGuide));

        binding.caseCheckBox.getRoot().setText(getResources().getString(R.string.CreateCenterUserFragmentCheckbox));

        InitManager.input12sspSpinner(requireActivity(), binding.positionIncludeLayout.selectSpinner, R.array.UserTypes);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterUserFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.mobileIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.mobileIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.mobileIncludeLayout.inputEditText);
            return false;
        });

        binding.nicknameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nicknameIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.nicknameIncludeLayout.inputEditText);
            return false;
        });

        binding.mobileIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            mobile = binding.mobileIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.nicknameIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            nickname = binding.nicknameIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.caseCheckBox.getRoot().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                createCase = "1";
            else
                createCase = "0";
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
                    FragmentCreateCenterUser.this.position = parent.getItemAtPosition(position).toString();

                    switch(FragmentCreateCenterUser.this.position) {
                        case "مراجع":
                            binding.roomIncludeLayout.getRoot().setVisibility(View.VISIBLE);

                            binding.clientGroup.setVisibility(View.VISIBLE);
                            break;
                        default:
                            binding.roomIncludeLayout.getRoot().setVisibility(View.GONE);

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

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showDialogSearchable(requireActivity(), "rooms");
        }).widget(binding.roomIncludeLayout.selectContainer);

        CustomClickView.onDelayedListener(() -> {
            if (binding.mobileErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.mobileErrorLayout.getRoot(), binding.mobileErrorLayout.errorTextView);
            if (binding.positionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.positionErrorLayout.getRoot(), binding.positionErrorLayout.errorTextView);
            if (binding.roomErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView);
            if (binding.nicknameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.nicknameErrorLayout.getRoot(), binding.nicknameErrorLayout.errorTextView);
            if (binding.caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = FragmentCreateCenterUserArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CenterModel")) {
                centerModel = (CenterModel) typeModel;
                setData(centerModel);
            }
        }
    }

    private void setData(CenterModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }

        if (model.getType() != null && !model.getType().equals("")) {
            type = model.getType();
            switch (type) {
                case "personal_clinic":
                    binding.positionIncludeLayout.getRoot().setVisibility(View.GONE);

                    binding.clientGroup.setVisibility(View.VISIBLE);
                    break;
                case "counseling_center":
                    binding.positionIncludeLayout.getRoot().setVisibility(View.VISIBLE);

                    binding.clientGroup.setVisibility(View.GONE);
                    break;
            }
        }
    }

    public void responseDialog(String method, TypeModel item) {
        try {
            switch (method) {
                case "rooms": {
                    RoomModel model = (RoomModel) item;

                    if (!roomId.equals(model.getId())) {
                        roomId = model.getId();

                        binding.roomIncludeLayout.primaryTextView.setText(model.getManager().getName());
                        binding.roomIncludeLayout.secondaryTextView.setText(model.getCenter().getDetail().getString("title"));
                    } else if (roomId.equals(model.getId())) {
                        roomId = "";

                        binding.roomIncludeLayout.primaryTextView.setText("");
                        binding.roomIncludeLayout.secondaryTextView.setText("");
                    }

                    DialogManager.dismissDialogSearchable();
                } break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setHashmap() {
        if (!mobile.equals(""))
            data.put("mobile", mobile);
        else
            data.remove("mobile");

        if (type.equals("counseling_center")) {
            if (!position.equals(""))
                data.put("position", SelectionManager.getUserType(requireActivity(), "en", position));
            else
                data.remove("position");

            if (position.equals("مراجع")) {
                if (!roomId.equals(""))
                    data.put("room_id", roomId);
                else
                    data.remove("room_id");

                if (!nickname.equals(""))
                    data.put("nickname", nickname);
                else
                    data.remove("nickname");

                if (!createCase.equals(""))
                    data.put("create_case", createCase);
                else
                    data.remove("create_case");
            }

        } else {
            if (!nickname.equals(""))
                data.put("nickname", nickname);
            else
                data.remove("nickname");

            if (!createCase.equals(""))
                data.put("create_case", createCase);
            else
                data.remove("create_case");
        }
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Center.createUser(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        AuthModel model = new AuthModel((JSONObject) object);

                        DialogManager.dismissDialogLoading();
                        SheetManager.showSheetAuth(requireActivity(), model.getKey(), ((ActivityMain) requireActivity()).singleton.getUserModel());
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
                                        case "mobile":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.mobileErrorLayout.getRoot(), binding.mobileErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "position":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.positionErrorLayout.getRoot(), binding.positionErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "room_id":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "nickname":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.nicknameErrorLayout.getRoot(), binding.nicknameErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "create_case":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
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
        userSelect = false;
    }

}