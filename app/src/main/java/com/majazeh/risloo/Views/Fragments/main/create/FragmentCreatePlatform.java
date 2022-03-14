package com.majazeh.risloo.views.fragments.main.create;

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
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.DropdownManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.databinding.FragmentCreatePlatformBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class FragmentCreatePlatform extends Fragment {

    // Binding
    private FragmentCreatePlatformBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String title = "", sessionType = "", indentifierType = "", indentifier = "", createSession = "0", available = "0";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreatePlatformBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePlatformFragmentTitleHeader));
        binding.sessionTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePlatformFragmentSessionTypeHeader));
        binding.indentifierTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePlatformFragmentIndetifierTypeHeader));
        binding.indentifierIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePlatformFragmentIdentifierHeader));

        binding.indentifierGuideLayout.guideTextView.setText(getResources().getString(R.string.CreatePlatformFragmentIdentifierGuide));

        binding.sessionCheckBox.getRoot().setText(getResources().getString(R.string.CreatePlatformFragmentSessionCheckbox));

        DropdownManager.spinner12ssp(requireActivity(), binding.sessionTypeIncludeLayout.selectSpinner, R.array.PlatformSessions);
        DropdownManager.spinner12ssp(requireActivity(), binding.indentifierTypeIncludeLayout.selectSpinner, R.array.PlatformIndentifiers);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreatePlatformFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
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
            if (binding.titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);
            if (binding.sessionTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.sessionTypeErrorLayout.getRoot(), binding.sessionTypeErrorLayout.errorTextView);
            if (binding.indentifierTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.indentifierTypeErrorLayout.getRoot(), binding.indentifierTypeErrorLayout.errorTextView);
            if (binding.indentifierErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.indentifierErrorLayout.getRoot(), binding.indentifierErrorLayout.errorTextView);
            if (binding.sessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.sessionErrorLayout.getRoot(), binding.sessionErrorLayout.errorTextView);
            if (binding.availableErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.availableErrorLayout.getRoot(), binding.availableErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = FragmentCreatePlatformArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("CenterModel"))
                setData((CenterModel) typeModel);
        }
    }

    private void setData(CenterModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
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

        Center.createCenterSessionPlatform(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();
                        SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackCreatedNewPlatform));

                        ((ActivityMain) requireActivity()).navigatoon.navigateUp();
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
                                        case "title":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "type":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.sessionTypeErrorLayout.getRoot(), binding.sessionTypeErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "identifier_type":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.indentifierTypeErrorLayout.getRoot(), binding.indentifierTypeErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "identifier":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.indentifierErrorLayout.getRoot(), binding.indentifierErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "selected":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.sessionErrorLayout.getRoot(), binding.sessionErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "available":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.availableErrorLayout.getRoot(), binding.availableErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
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