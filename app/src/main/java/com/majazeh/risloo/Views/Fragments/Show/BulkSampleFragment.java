package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.databinding.FragmentBulkSampleBinding;

public class BulkSampleFragment extends Fragment {

    // Binding
    private FragmentBulkSampleBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentBulkSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {

    }

    private void detector() {

    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {

    }

    private void setData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}