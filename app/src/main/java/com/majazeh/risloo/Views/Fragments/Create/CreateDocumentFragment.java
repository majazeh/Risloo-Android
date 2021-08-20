package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.FileManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Utils.Managers.ResultManager;
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

        detector();

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

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateDocumentFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.fileIncludeLayout.selectTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_ripple_gray300);

            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nameIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).validatoon.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
            return false;
        });

        binding.nameIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).validatoon.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        ClickManager.onDelayedClickListener(() -> {
            if (PermissionManager.filePermission(requireActivity()))
                IntentManager.file(requireActivity());
        }).widget(binding.fileIncludeLayout.selectTextView);

        ClickManager.onDelayedClickListener(() -> {
            if (binding.nameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.check(binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView);
            if (binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.check(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);
            if (binding.fileErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.check(binding.fileErrorLayout.getRoot(), binding.fileErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        String type = CreateDocumentFragmentArgs.fromBundle(getArguments()).getType();
        TypeModel typeModel = CreateDocumentFragmentArgs.fromBundle(getArguments()).getTypeModel();

        // TODO : Place Code When Needed
    }

    public void responseAction(String method, Intent data) {
        ResultManager resultManager = new ResultManager();

        switch (method) {
            case "file":
                resultManager.fileResult(requireActivity(), data, binding.fileIncludeLayout.nameTextView);

                filePath = resultManager.path;
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
//        Document.create(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
//                        ToastManager.showToast(requireActivity(), getResources().getString(R.string.ToastNewDocumentAdded));
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