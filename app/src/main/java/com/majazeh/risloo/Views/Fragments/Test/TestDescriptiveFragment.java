package com.majazeh.risloo.Views.Fragments.Test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.AnimateManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.activities.TestActivity;
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
        binding.answerEditText.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.answerEditText.inputEditText.hasFocus())
                ((TestActivity) requireActivity()).inputon.select(binding.answerEditText.inputEditText);
            return false;
        });

        binding.answerEditText.inputEditText.setOnEditorActionListener((v, actionId, event) -> {
            answer = binding.answerEditText.inputEditText.getText().toString().trim();

            ((TestActivity) requireActivity()).sendItem(key, answer);
            ((TestActivity) requireActivity()).inputon.clear(((TestActivity) requireActivity()).inputon.editText);
            return false;
        });

        binding.answerEditText.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.answerEditText.inputEditText.hasFocus()) {
                    setEditTextCount(binding.answerEditText.inputEditText.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setArgs() {
        FormModel formModel = ((TestActivity) requireActivity()).formModel;
        setData(formModel);
    }

    private void setData(FormModel model) {
        ItemModel item = (ItemModel) model.getObject();

        if (!item.getCategory().equals("")) {
            binding.entityTextView.getRoot().setText(item.getCategory());
            binding.entityConstraintLayout.setVisibility(View.VISIBLE);
        } else {
            binding.entityConstraintLayout.setVisibility(View.GONE);
        }

        if (!item.getText().equals("")) {
            binding.titleTextView.getRoot().setText(item.getText());
        } else {
            binding.titleTextView.getRoot().setText("نامعلوم");
        }

        if (!item.getDescription().equals("")) {
            binding.descriptionTextView.getRoot().setText(item.getDescription());
            binding.descriptionTextView.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.descriptionTextView.getRoot().setVisibility(View.GONE);
        }

        if (!item.getUserAnswered().equals("")) {
            answer = item.getUserAnswered();
            binding.answerEditText.inputEditText.setText(answer);

            setEditTextCount(binding.answerEditText.inputEditText.length());
        } else {
            binding.answerEditText.inputEditText.setText("");

            setEditTextCount(0);
        }

        if (!item.getIndex().equals("")) {
            key = Integer.parseInt(item.getIndex());
        }
    }

    private void setEditTextCount(int count) {
        int availableChar = 255 - count;
        int endIndex = String.valueOf(availableChar).length();

        String value = availableChar + " / " + "255";
        binding.answerEditText.countTextView.setText(StringManager.foregroundSize(value, 0, endIndex, getResources().getColor(R.color.coolGray700), (int) getResources().getDimension(R.dimen._11ssp)));
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