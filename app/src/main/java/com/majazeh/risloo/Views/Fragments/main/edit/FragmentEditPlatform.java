package com.majazeh.risloo.views.fragments.main.edit;

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
import com.majazeh.risloo.databinding.FragmentEditPlatformBinding;
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
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;

import java.util.HashMap;

public class FragmentEditPlatform extends Fragment {

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
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPlatformFragmentTitleHeader));
        binding.sessionTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPlatformFragmentSessionTypeHeader));
        binding.indentifierTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPlatformFragmentIndetifierTypeHeader));
        binding.indentifierIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditPlatformFragmentIdentifierHeader));

        binding.indentifierGuideLayout.guideTextView.setText(getResources().getString(R.string.EditPlatformFragmentIdentifierGuide));

        binding.sessionCheckBox.getRoot().setText(getResources().getString(R.string.EditPlatformFragmentSessionCheckbox));

        DropdownManager.spinner12ssp(requireActivity(), binding.sessionTypeIncludeLayout.selectSpinner, R.array.PlatformSessions);
        DropdownManager.spinner12ssp(requireActivity(), binding.indentifierTypeIncludeLayout.selectSpinner, R.array.PlatformIndentifiers);

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditPlatformFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.indentifierIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.indentifierIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.indentifierIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.indentifierIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            indentifier = binding.indentifierIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.sessionTypeIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.indentifierTypeIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.sessionTypeIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.indentifierTypeIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

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
                binding.availableSwitchCompat.getRoot().setTextColor(getResources().getColor(R.color.emerald700));
            } else {
                available = "0";

                binding.availableSwitchCompat.getRoot().setText(getResources().getString(R.string.AppSwicthOff));
                binding.availableSwitchCompat.getRoot().setTextColor(getResources().getColor(R.color.coolGray600));
            }
        });

        CustomClickView.onDelayedListener(() -> {
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding);

            doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setArgs() {
        CenterModel centerModel = (CenterModel) FragmentEditPlatformArgs.fromBundle(getArguments()).getCenterModel();
        setData(centerModel);

        sessionPlatformModel = (SessionPlatformModel) FragmentEditPlatformArgs.fromBundle(getArguments()).getTypeModel();
        setData(sessionPlatformModel);
    }

    private void setData(CenterModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
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
            sessionType = JsonManager.getPlatformSession(requireActivity(), "fa", model.getType());
            for (int i = 0; i < binding.sessionTypeIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.sessionTypeIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(sessionType)) {
                    binding.sessionTypeIncludeLayout.selectSpinner.setSelection(i);
                }
            }
        }

        if (model.getIdentifierType() != null && !model.getIdentifierType().equals("")) {
            indentifierType = JsonManager.getPlatformIdentifier(requireActivity(), "fa", model.getIdentifierType());
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
            binding.availableSwitchCompat.getRoot().setTextColor(requireActivity().getResources().getColor(R.color.emerald700));
        }

        if (model.isSelected()) {
            createSession = "1";
            binding.sessionCheckBox.getRoot().setChecked(true);
        }
    }

    private void setHashmap() {
        if (!title.equals(""))
            data.put("title", title);
        else
            data.remove("title");

        if (!sessionType.equals(""))
            data.put("type", JsonManager.getPlatformSession(requireActivity(), "en", sessionType));
        else
            data.remove("type");

        if (!indentifierType.equals(""))
            data.put("identifier_type", JsonManager.getPlatformIdentifier(requireActivity(), "en", indentifierType));
        else
            data.remove("identifier_type");

        if (!indentifier.equals(""))
            data.put("identifier", indentifier);
        else
            data.remove("identifier");

        if (!createSession.equals(""))
            data.put("selected", createSession);
        else
            data.remove("selected");

        if (!available.equals(""))
            data.put("available", available);
        else
            data.remove("available");
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Center.editCenterSessionPlatform(data, header, new Response() {
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
                    requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.showValid(response, binding));
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