package com.majazeh.risloo.Views.Fragments.Main.Create;

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
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentCreateClientReportBinding;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.HashMap;

public class CreateClientReportFragment extends Fragment {

    // Binding
    private FragmentCreateClientReportBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String encryption = "", description = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateClientReportBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.encryptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateClientReportFragmentEncryptionHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateClientReportFragmentDescriptionHeader));

        binding.cryptoIncludeLayout.hintTextView.setText(getResources().getString(R.string.CreateClientReportFragmentCryptoHint));

        InitManager.input12sspSpinner(requireActivity(), binding.encryptionIncludeLayout.selectSpinner, R.array.EncryptionStates);

        InitManager.txtTextColorBackground(binding.cryptoIncludeLayout.selectTextView, getResources().getString(R.string.CreateClientReportFragmentCryptoButton), getResources().getColor(R.color.Risloo500), R.drawable.draw_16sdp_solid_white_border_1sdp_risloo500_ripple_risloo50);
        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateClientReportFragmentButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.encryptionIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.encryptionIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.encryptionIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    encryption = parent.getItemAtPosition(position).toString();

                    switch(encryption) {
                        case "با رمزگذاری":
                            binding.cryptoIncludeLayout.getRoot().setVisibility(View.GONE);
                            break;
                        default:
                            binding.cryptoIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                            break;
                    }

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.cryptoIncludeLayout.selectTextView);

        CustomClickView.onDelayedListener(() -> {
            if (binding.encryptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.encryptionErrorLayout.getRoot(), binding.encryptionErrorLayout.errorTextView);
            if (binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = CreateClientReportFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CaseModel"))
                setData((CaseModel) typeModel);
            else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("SessionModel"))
                setData((SessionModel) typeModel);
        }
    }

    private void setData(CaseModel model) {
        if (model.getCaseId() != null && !model.getCaseId().equals("")) {
            data.put("id", model.getCaseId());
        }
    }

    private void setData(SessionModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }
    }

    private void setHashmap() {
        if (!encryption.equals(""))
            data.put("encryption", SelectionManager.getEncryption(requireActivity(), "en", encryption));
        else
            data.remove("encryption");

        if (!description.equals(""))
            data.put("description", description);
        else
            data.remove("description");
    }

    private void doWork() {
//        DialogManager.showLoadingDialog(requireActivity(), "");
//
//        setHashmap();
//
//        Session.addReport(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        DialogManager.dismissLoadingDialog();
//                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackCreatedNewReport));
//
//                        ((MainActivity) requireActivity()).navController.navigateUp();
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(String response) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        try {
//                            JSONObject responseObject = new JSONObject(response);
//                            if (!responseObject.isNull("errors")) {
//                                JSONObject errorsObject = responseObject.getJSONObject("errors");
//
//                                Iterator<String> keys = (errorsObject.keys());
//                                StringBuilder allErrors = new StringBuilder();
//
//                                while (keys.hasNext()) {
//                                    String key = keys.next();
//                                    StringBuilder keyErrors = new StringBuilder();
//
//                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
//                                        String error = errorsObject.getJSONArray(key).getString(i);
//
//                                        keyErrors.append(error);
//                                        keyErrors.append("\n");
//
//                                        allErrors.append(error);
//                                        allErrors.append("\n");
//                                    }
//
//                                    switch (key) {
//                                        case "encryption":
//                                            ((MainActivity) requireActivity()).validatoon.showValid(binding.encryptionErrorLayout.getRoot(), binding.encryptionErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
//                                            break;
//                                        case "description":
//                                            ((MainActivity) requireActivity()).validatoon.showValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
//                                            break;
//                                    }
//                                }
//
//                                SnackManager.showErrorSnack(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    });
//                }
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}