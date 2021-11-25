package com.majazeh.risloo.Views.Fragments.Main.Index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.databinding.FragmentBalancesBinding;

public class BalancesFragment extends Fragment {

    // Binding
    private FragmentBalancesBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentBalancesBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        // TODO : Place Code When Needed
    }

    private void setData() {
        // TODO : Place Code When Needed
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}