package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.majazeh.risloo.databinding.FragmentCreatePracticeBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class CreatePracticeFragment extends Fragment {

    // Binding
    private FragmentCreatePracticeBinding binding;

    // Vars
    private HashMap data, header;
    private String sessionId = "", name = "", description = "", filePath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreatePracticeBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePracticeFragmentNameHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePracticeFragmentDescriptionHeader));
        binding.fileIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePracticeFragmentFileHeader));

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreatePracticeFragmentButton), getResources().getColor(R.color.White));
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
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.nameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.descriptionIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (PermissionManager.filePermission(requireActivity())) {
                IntentManager.file(requireActivity());
            }
        }).widget(binding.fileIncludeLayout.selectTextView);

        ClickManager.onDelayedClickListener(() -> {
            if (binding.nameIncludeLayout.inputEditText.length() == 0)
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView);
            if (binding.descriptionIncludeLayout.inputEditText.length() == 0)
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
             else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);
            if (filePath.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.fileIncludeLayout.selectTextView, binding.fileErrorLayout.getRoot(), binding.fileErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
             else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.fileIncludeLayout.selectTextView, binding.fileErrorLayout.getRoot(), binding.fileErrorLayout.errorTextView);

            if (binding.nameIncludeLayout.inputEditText.length() != 0 && binding.descriptionIncludeLayout.inputEditText.length() != 0 && !filePath.equals(""))
                doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                sessionId = getArguments().getString("id");
                data.put("id", sessionId);
            }

            if (getArguments().getString("name") != null && !getArguments().getString("name").equals("")) {
                name = getArguments().getString("name");
                binding.nameIncludeLayout.inputEditText.setText(name);
            }

            if (getArguments().getString("description") != null && !getArguments().getString("description").equals("")) {
                description = getArguments().getString("description");
                binding.descriptionIncludeLayout.inputEditText.setText(description);
            }

            if (getArguments().getString("file") != null && !getArguments().getString("file").equals("")) {
                filePath = getArguments().getString("file");
                binding.fileIncludeLayout.nameTextView.setText(filePath);
            }
        }
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
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

        data.put("name", name);
        data.put("description", description);
        data.put("file", filePath);

//        Session.addPractice(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        Bundle extras = new Bundle();
//                        extras.putString("id", sessionId);
//
//                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
//                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
//                        ((MainActivity) requireActivity()).navigator(R.id.clientReportsFragment, extras);
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(String response) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (!jsonObject.isNull("errors")) {
//                                Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());
//
//                                while (keys.hasNext()) {
//                                    String key = keys.next();
//                                    for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
//                                        switch (key) {
//                                            case "name":
//                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.getRoot(), binding.nameErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
//                                                break;
//                                            case "description":
//                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionIncludeLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
//                                                break;
//                                            case "file":
//                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.fileIncludeLayout.selectTextView, binding.fileErrorLayout.getRoot(), binding.fileErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
//                                                break;
//                                        }
//                                    }
//                                }
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