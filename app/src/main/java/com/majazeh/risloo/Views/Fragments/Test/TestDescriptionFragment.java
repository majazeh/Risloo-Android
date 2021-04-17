package com.majazeh.risloo.Views.Fragments.Test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.databinding.FragmentTestDescriptionBinding;

import io.noties.markwon.Markwon;

public class TestDescriptionFragment extends Fragment {

    // Binding
    private FragmentTestDescriptionBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestDescriptionBinding.inflate(inflater, viewGroup, false);

        setData();

        return binding.getRoot();
    }

    private void setData() {
        Markwon.create(requireActivity()).setMarkdown(binding.markdownTextView,  "مارک داون");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}