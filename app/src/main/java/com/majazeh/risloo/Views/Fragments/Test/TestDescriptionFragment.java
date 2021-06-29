package com.majazeh.risloo.Views.Fragments.Test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.databinding.FragmentTestDescriptionBinding;
import com.mre.ligheh.Model.TypeModel.FormModel;

import io.noties.markwon.Markwon;

public class TestDescriptionFragment extends Fragment {

    // Binding
    private FragmentTestDescriptionBinding binding;

    // Vars
    private FormModel formModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestDescriptionBinding.inflate(inflater, viewGroup, false);

        setArgs();

        return binding.getRoot();
    }

    private void setArgs() {
        formModel = ((TestActivity) requireActivity()).formModel;

        setData(formModel);
    }

    private void setData(FormModel model) {
        String description = model.getObject().toString();

        Markwon.create(requireActivity()).setMarkdown(binding.markdownTextView,  description);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}