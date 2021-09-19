package com.majazeh.risloo.Views.Fragments.Edit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentEditPlatformBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class EditPlatformFragment extends Fragment {

    // Binding
    private FragmentEditPlatformBinding binding;

    // Models
    private SessionPlatformModel sessionPlatformModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String title = "", sessionType = "", indentifierType = "", indentifier = "", createSession = "0", available = "0";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditPlatformBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPlatformFragmentTitleHeader));
        binding.sessionTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPlatformFragmentSessionTypeHeader));
        binding.indentifierTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPlatformFragmentIndetifierTypeHeader));
        binding.indentifierIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPlatformFragmentIdentifierHeader));

        binding.indentifierGuideLayout.guideTextView.setText(getResources().getString(R.string.EditPlatformFragmentIdentifierGuide));

        binding.sessionCheckBox.getRoot().setText(getResources().getString(R.string.EditPlatformFragmentSessionCheckbox));

        InitManager.normal12sspSpinner(requireActivity(), binding.sessionTypeIncludeLayout.selectSpinner, R.array.PlatformSessions);
        InitManager.normal12sspSpinner(requireActivity(), binding.indentifierTypeIncludeLayout.selectSpinner, R.array.PlatformIndentifiers);

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditPlatformFragmentButton), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.sessionTypeIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.sessionTypeIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    sessionType = parent.getItemAtPosition(position).toString();

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.indentifierTypeIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.indentifierTypeIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    indentifierType = parent.getItemAtPosition(position).toString();

                    switch (indentifierType) {
                        case "آدرس اینترنتی":
                            binding.indentifierIncludeLayout.inputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                            break;
                        case "شماره تماس":
                            binding.indentifierIncludeLayout.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                            break;
                        case "متن":
                            binding.indentifierIncludeLayout.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                            break;
                    }

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.indentifierIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.indentifierIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.indentifierIncludeLayout.inputEditText);
            return false;
        });

        binding.indentifierIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            indentifier = binding.indentifierIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.sessionCheckBox.getRoot().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                createSession = "1";
            else
                createSession = "0";
        });

        binding.availableSwitchCompat.getRoot().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                available = "1";

                binding.availableSwitchCompat.getRoot().setText(getResources().getString(R.string.AppSwicthOn));
                binding.availableSwitchCompat.getRoot().setTextColor(getResources().getColor(R.color.Green700));
                binding.availableSwitchCompat.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_green50_border_1sdp_gray300);
            } else {
                available = "0";

                binding.availableSwitchCompat.getRoot().setText(getResources().getString(R.string.AppSwicthOff));
                binding.availableSwitchCompat.getRoot().setTextColor(getResources().getColor(R.color.Gray600));
                binding.availableSwitchCompat.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray300);
            }
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);
            if (binding.sessionTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.sessionTypeErrorLayout.getRoot(), binding.sessionTypeErrorLayout.errorTextView);
            if (binding.indentifierTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.indentifierTypeErrorLayout.getRoot(), binding.indentifierTypeErrorLayout.errorTextView);
            if (binding.indentifierErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.indentifierErrorLayout.getRoot(), binding.indentifierErrorLayout.errorTextView);
            if (binding.sessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.sessionErrorLayout.getRoot(), binding.sessionErrorLayout.errorTextView);
            if (binding.availableErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.availableErrorLayout.getRoot(), binding.availableErrorLayout.errorTextView);

            doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setArgs() {
        CenterModel centerModel = (CenterModel) EditPlatformFragmentArgs.fromBundle(getArguments()).getCenterModel();
        setData(centerModel);

        sessionPlatformModel = (SessionPlatformModel) EditPlatformFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(sessionPlatformModel);
    }

    private void setData(CenterModel model) {
        if (model.getCenterId() != null && !model.getCenterId().equals("")) {
            data.put("id", model.getCenterId());
        }
    }

    private void setData(SessionPlatformModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("platformId", model.getId());
        }

        if (model.getTitle() != null && !model.getTitle().equals("")) {
            title = model.getTitle();
            binding.titleIncludeLayout.inputEditText.setText(title);
        }

        if (model.getType() != null && !model.getType().equals("")) {
            sessionType = SelectionManager.getPlatformSession(requireActivity(), "fa", model.getType());
            for (int i = 0; i < binding.sessionTypeIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.sessionTypeIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(sessionType)) {
                    binding.sessionTypeIncludeLayout.selectSpinner.setSelection(i);
                }
            }
        }

        if (model.getIdentifier_type() != null && !model.getIdentifier_type().equals("")) {
            indentifierType = SelectionManager.getPlatformIdentifier(requireActivity(), "fa", model.getIdentifier_type());
            for (int i = 0; i < binding.indentifierTypeIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.indentifierTypeIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(indentifierType)) {
                    binding.indentifierTypeIncludeLayout.selectSpinner.setSelection(i);

                    switch (indentifierType) {
                        case "آدرس اینترنتی":
                            binding.indentifierIncludeLayout.inputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                            break;
                        case "شماره تماس":
                            binding.indentifierIncludeLayout.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                            break;
                        case "متن":
                            binding.indentifierIncludeLayout.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                            break;
                    }
                }
            }
        }

        if (model.getIdentifier() != null && !model.getIdentifier().equals("")) {
            indentifier = model.getIdentifier();
            binding.indentifierIncludeLayout.inputEditText.setText(indentifier);
        }

        if (model.isAvailable()) {
            available = "1";
            binding.availableSwitchCompat.getRoot().setChecked(true);

            binding.availableSwitchCompat.getRoot().setText(requireActivity().getResources().getString(R.string.AppSwicthOn));
            binding.availableSwitchCompat.getRoot().setTextColor(requireActivity().getResources().getColor(R.color.Green700));
            binding.availableSwitchCompat.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_green50_border_1sdp_gray200);
        }

        if (model.isSelected()) {
            createSession = "1";
            binding.sessionCheckBox.getRoot().setChecked(true);
        }
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        data.put("title", title);
        data.put("type", SelectionManager.getPlatformSession(requireActivity(), "en", sessionType));
        data.put("identifier_type", SelectionManager.getPlatformIdentifier(requireActivity(), "en", indentifierType));
        data.put("identifier", indentifier);
        data.put("selected", createSession);
        data.put("available", available);

        Center.editCenterSessionPlatform(data, header, new Response() {
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
                                            case "title":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, validation);
                                                break;
                                            case "type":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.sessionTypeErrorLayout.getRoot(), binding.sessionTypeErrorLayout.errorTextView, validation);
                                                break;
                                            case "identifier_type":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.indentifierTypeErrorLayout.getRoot(), binding.indentifierTypeErrorLayout.errorTextView, validation);
                                                break;
                                            case "identifier":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.indentifierErrorLayout.getRoot(), binding.indentifierErrorLayout.errorTextView, validation);
                                                break;
                                            case "selected":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.sessionErrorLayout.getRoot(), binding.sessionErrorLayout.errorTextView, validation);
                                                break;
                                            case "available":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.availableErrorLayout.getRoot(), binding.availableErrorLayout.errorTextView, validation);
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