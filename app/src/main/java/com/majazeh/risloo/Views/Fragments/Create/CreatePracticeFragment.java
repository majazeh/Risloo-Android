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
import com.majazeh.risloo.databinding.FragmentCreatePracticeBinding;

public class CreatePracticeFragment extends Fragment {

    // Binding
    private FragmentCreatePracticeBinding binding;

    // Vars
    private String name = "", description = "";
    private String filePath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreatePracticeBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
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
            if (binding.nameIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.errorImageView, binding.nameErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.descriptionIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.errorImageView, binding.descriptionErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (filePath.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.fileIncludeLayout.selectTextView, binding.fileErrorLayout.errorImageView, binding.fileErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (binding.nameIncludeLayout.inputEditText.length() != 0 && binding.descriptionIncludeLayout.inputEditText.length() != 0 && !filePath.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.errorImageView, binding.nameErrorLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.errorImageView, binding.descriptionErrorLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.fileIncludeLayout.selectTextView, binding.fileErrorLayout.errorImageView, binding.fileErrorLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getName().equals("")) {
            name = ((MainActivity) requireActivity()).singleton.getName();
            binding.nameIncludeLayout.inputEditText.setText(name);
        }
        if (!((MainActivity) requireActivity()).singleton.getDescription().equals("")) {
            description = ((MainActivity) requireActivity()).singleton.getDescription();
            binding.descriptionIncludeLayout.inputEditText.setText(description);
        }
        if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            filePath = ((MainActivity) requireActivity()).singleton.getAvatar();
            binding.fileIncludeLayout.nameTextView.setText(filePath);
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
        name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        FileManager.deleteFolderFromCache(requireActivity(), "documents");
    }

}