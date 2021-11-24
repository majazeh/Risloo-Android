package com.majazeh.risloo.Views.Fragments.Main.Create;

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
import com.majazeh.risloo.Utils.Managers.FileManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Utils.Managers.ResultManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentCreateDocumentBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.HashMap;

public class CreateDocumentFragment extends Fragment {

    // Binding
    private FragmentCreateDocumentBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String name = "", description = "", filePath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateDocumentBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateDocumentFragmentNameHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateDocumentFragmentDescriptionHeader));
        binding.fileIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateDocumentFragmentFileHeader));

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateDocumentFragmentButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nameIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(binding.nameIncludeLayout.inputEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.nameIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
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
        TypeModel typeModel = CreateDocumentFragmentArgs.fromBundle(getArguments()).getTypeModel();

        // TODO : Place Code When Needed
    }

    public void responseAction(String method, Intent data) {
        switch (method) {
            case "file":
                ResultManager.fileResult(requireActivity(), data, binding.fileIncludeLayout.nameTextView);

                filePath = ResultManager.path;
                break;
        }
    }

    private void setHashmap() {
        if (!name.equals(""))
            data.put("name", name);
        else
            data.remove("name");

        if (!description.equals(""))
            data.put("description", description);
        else
            data.remove("description");

        if (!filePath.equals(""))
            data.put("file", filePath);
        else
            data.remove("file");
    }

    private void doWork() {
//        DialogManager.showLoadingDialog(requireActivity(), "");
//
//        setHashmap();
//
//        Document.create(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        DialogManager.dismissLoadingDialog();
//                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackCreatedNewDocument));
//
//                        ((MainActivity) requireActivity()).navigatoon.navigateUp();
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
//                                        case "name":
//                                            ((MainActivity) requireActivity()).validatoon.showValid(binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
//                                            break;
//                                        case "description":
//                                            ((MainActivity) requireActivity()).validatoon.showValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
//                                            break;
//                                        case "file":
//                                            ((MainActivity) requireActivity()).validatoon.showValid(binding.fileErrorLayout.getRoot(), binding.fileErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
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
        FileManager.deleteInternalCacheFolder(requireActivity(), "documents");
    }

}