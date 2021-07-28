package com.majazeh.risloo.Views.Fragments.Edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Adapters.Tab.EditCenterAdapter;
import com.majazeh.risloo.databinding.FragmentEditCenterBinding;
import com.mre.ligheh.Model.TypeModel.CenterModel;

public class EditCenterFragment extends Fragment {

    // Binding
    public FragmentEditCenterBinding binding;

    // Adapters
    public EditCenterAdapter adapter;

    // Models
    public CenterModel centerModel;

    // Objects
    private TabLayoutMediator tabLayoutMediator;

    // Vars
    private String[] tabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCenterBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        tabs = getResources().getStringArray(R.array.EditCenterTabs);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));
    }

    private void setArgs() {
        centerModel = (CenterModel) EditCenterFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData();
    }

    private void setData() {
        adapter = new EditCenterAdapter(requireActivity(), centerModel.getCenterType());

        if (centerModel.getCenterType().equals("personal_clinic"))
            binding.tabLayout.getRoot().setVisibility(View.GONE);
        else
            binding.tabLayout.getRoot().setVisibility(View.VISIBLE);

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());

        tabLayoutMediator.attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}