package com.majazeh.risloo.Views.Fragments.Create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.databinding.FragmentCreateScheduleTimeBinding;

public class CreateScheduleTimeFragment extends Fragment {

    // Binding
    private FragmentCreateScheduleTimeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleTimeBinding.inflate(inflater, viewGroup, false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}