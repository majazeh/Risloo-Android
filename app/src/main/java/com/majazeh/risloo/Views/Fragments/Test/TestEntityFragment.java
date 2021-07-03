package com.majazeh.risloo.Views.Fragments.Test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.databinding.FragmentTestEntityBinding;
import com.mre.ligheh.Model.TypeModel.EntityModel;
import com.mre.ligheh.Model.TypeModel.FormModel;

public class TestEntityFragment extends Fragment {

    // Binding
    private FragmentTestEntityBinding binding;

    // Vars
    private FormModel formModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestEntityBinding.inflate(inflater, viewGroup, false);

        setArgs();

        return binding.getRoot();
    }

    private void setArgs() {
        formModel = ((TestActivity) requireActivity()).formModel;

        setData(formModel);
    }

    private void setData(FormModel model) {
        EntityModel entity = (EntityModel) model.getObject();

        if (entity.getTitle() != null && !entity.getTitle().equals("")) {
            binding.titleTextView.getRoot().setText(entity.getTitle());
            binding.titleTextView.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.titleTextView.getRoot().setVisibility(View.GONE);
        }

        if (entity.getDescription() != null && !entity.getDescription().equals("")) {
            binding.descriptionTextView.getRoot().setText(entity.getDescription());
            binding.descriptionTextView.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.descriptionTextView.getRoot().setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}