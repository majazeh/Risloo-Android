package com.majazeh.risloo.Views.Fragments.Test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.Utils.Managers.AnimateManager;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.databinding.FragmentTestDescriptiveBinding;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.ItemModel;

public class TestDescriptiveFragment extends Fragment {

    // Binding
    private FragmentTestDescriptiveBinding binding;

    // Vars
    private String answer = "";
    private int key = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentTestDescriptiveBinding.inflate(inflater, viewGroup, false);

        listener();

        setArgs();

        setAnimation();

        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.answerEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.answerEditText.getRoot().hasFocus())
                ((TestActivity) requireActivity()).inputon.select(binding.answerEditText.getRoot());
            return false;
        });

        binding.answerEditText.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
            answer = binding.answerEditText.getRoot().getText().toString().trim();
        });

        binding.answerEditText.getRoot().setOnEditorActionListener((v, actionId, event) -> {
            ((TestActivity) requireActivity()).sendItem(key, answer);
            return false;
        });
    }

    private void setArgs() {
        FormModel formModel = ((TestActivity) requireActivity()).formModel;
        setData(formModel);
    }

    private void setData(FormModel model) {
        ItemModel item = (ItemModel) model.getObject();

        if (item.getCategory() != null && !item.getCategory().equals("")) {
            binding.entityTextView.getRoot().setText(item.getCategory());
            binding.entityConstraintLayout.setVisibility(View.VISIBLE);
        } else {
            binding.entityConstraintLayout.setVisibility(View.GONE);
        }

        if (item.getText() != null && !item.getText().equals("")) {
            binding.titleTextView.getRoot().setText(item.getText());
        } else {
            binding.titleTextView.getRoot().setText("نامعلوم");
        }

        if (item.getDescription() != null && !item.getDescription().equals("")) {
            binding.descriptionTextView.getRoot().setText(item.getDescription());
            binding.descriptionTextView.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.descriptionTextView.getRoot().setVisibility(View.GONE);
        }

        if (item.getUser_answered() != null && !item.getUser_answered().equals("")) {
            answer = item.getUser_answered();
            binding.answerEditText.getRoot().setText(answer);
        } else {
            binding.answerEditText.getRoot().setText("");
        }

        if (item.getIndex() != null && !item.getIndex().equals("")) {
            key = Integer.parseInt(item.getIndex());
        }
    }

    private void setAnimation() {
        AnimateManager.animateViewAlpha(binding.entityTextView.getRoot(), 500, 0f, 1f);
        AnimateManager.animateViewAlpha(binding.titleTextView.getRoot(), 500, 0f, 1f);
        AnimateManager.animateViewAlpha(binding.descriptionTextView.getRoot(), 500, 0f, 1f);
        AnimateManager.animateViewAlpha(binding.answerEditText.getRoot(), 500, 0f, 1f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}