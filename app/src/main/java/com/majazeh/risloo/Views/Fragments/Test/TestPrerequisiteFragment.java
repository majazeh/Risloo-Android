package com.majazeh.risloo.Views.Fragments.Test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Adapters.Recycler.PrerequisitesAdapter;
import com.majazeh.risloo.databinding.FragmentTestPrerequisiteBinding;

public class TestPrerequisiteFragment extends Fragment {

    // Binding
    private FragmentTestPrerequisiteBinding binding;

    // Adapters
    private PrerequisitesAdapter prerequisitesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestPrerequisiteBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        prerequisitesAdapter = new PrerequisitesAdapter(requireActivity());

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.PrerequisiteFragmentTitle));
        binding.descriptionTextView.getRoot().setText(getResources().getString(R.string.PrerequisiteFragmentDescription));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView, getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setData() {
//        prerequisitesAdapter.setPrerequisites(null);
        binding.listRecyclerView.setAdapter(prerequisitesAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}