package com.majazeh.risloo.views.fragments.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.utils.managers.AnimateManager;
import com.majazeh.risloo.views.activities.ActivityTest;
import com.majazeh.risloo.databinding.FragmentTestDescriptionBinding;
import com.mre.ligheh.Model.TypeModel.FormModel;

import io.noties.markwon.Markwon;

public class FragmentTestDescription extends Fragment {

    // Binding
    private FragmentTestDescriptionBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestDescriptionBinding.inflate(inflater, viewGroup, false);

        setArgs();

        setAnimation();

        return binding.getRoot();
    }

    private void setArgs() {
        FormModel formModel = ((ActivityTest) requireActivity()).formModel;
        setData(formModel);
    }

    private void setData(FormModel model) {
        String description = model.getObject().toString();

        Markwon.create(requireActivity()).setMarkdown(binding.descriptionTextView,  description);
    }

    private void setAnimation() {
        AnimateManager.animateViewAlpha(binding.descriptionTextView, 500, 0f, 1f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}