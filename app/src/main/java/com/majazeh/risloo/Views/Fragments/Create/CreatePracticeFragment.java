package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.FileManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Utils.Managers.ResultManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentCreatePracticeBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.HashMap;

public class CreatePracticeFragment extends Fragment {

    // Binding
    private FragmentCreatePracticeBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String name = "", description = "", filePath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreatePracticeBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePracticeFragmentNameHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePracticeFragmentDescriptionHeader));
        binding.fileIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePracticeFragmentFileHeader));

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreatePracticeFragmentButton), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nameIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
            return false;
        });

        binding.nameIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (PermissionManager.filePermission(requireActivity()))
                IntentManager.file(requireActivity());
        }).widget(binding.fileIncludeLayout.selectTextView);

        CustomClickView.onDelayedListener(() -> {
            if (binding.nameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView);
            if (binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);
            if (binding.fileErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.fileErrorLayout.getRoot(), binding.fileErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = CreatePracticeFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.substring(typeModel.getClass().getName(), '.').equals("SessionModel"))
                setData((SessionModel) typeModel);
        }
    }

    private void setData(SessionModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }
    }

    public void responseAction(String method, Intent data) {
        switch (method) {
            case "file":
                ResultManager.fileResult(requireActivity(), data, binding.fileIncludeLayout.nameTextView);

                filePath = ResultManager.filePath;
                break;
        }
    }

    private void doWork() {
//        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");
//
//        data.put("name", name);
//        data.put("description", description);
//        data.put("file", filePath);
//
//        Session.addPractice(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
//                        ToastManager.showToast(requireActivity(), getResources().getString(R.string.ToastNewPracticeAdded));
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
//                                StringBuilder errors = new StringBuilder();
//
//                                while (keys.hasNext()) {
//                                    String key = keys.next();
//                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
//                                        String validation = errorsObject.getJSONArray(key).get(i).toString();
//
//                                        switch (key) {
//                                            case "name":
//                                                ((MainActivity) requireActivity()).controlEditText.error(binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView, validation);
//                                                break;
//                                            case "description":
//                                                ((MainActivity) requireActivity()).controlEditText.error(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, validation);
//                                                break;
//                                            case "file":
//                                                ((MainActivity) requireActivity()).controlEditText.error(binding.fileErrorLayout.getRoot(), binding.fileErrorLayout.errorTextView, validation);
//                                                break;
//                                        }
//
//                                        errors.append(validation);
//                                        errors.append("\n");
//                                    }
//                                }
//
//                                ToastManager.showToast(requireActivity(), errors.substring(0, errors.length() - 1));
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

        FileManager.deleteFolderFromCache(requireActivity(), "documents");
    }

}