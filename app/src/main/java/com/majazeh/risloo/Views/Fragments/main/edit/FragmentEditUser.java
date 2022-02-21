package com.majazeh.risloo.views.fragments.main.edit;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.majazeh.risloo.R;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.tab.AdapterEditUser;
import com.majazeh.risloo.databinding.FragmentEditUserBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentEditUser extends Fragment {

    // Binding
    public FragmentEditUserBinding binding;

    // Adapters
    public AdapterEditUser adapter;

    // Models
    public UserModel userModel;

    // Objects
    private TabLayoutMediator tabLayoutMediator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new AdapterEditUser(requireActivity());

        ArrayList<Integer> images = new ArrayList<>();

        images.add(R.drawable.ic_address_card_light);
        images.add(R.drawable.ic_unlock_alt_light);
        images.add(R.drawable.ic_user_circle_light);
        images.add(R.drawable.ic_key_light);

        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, i) -> {
            tab.setIcon(images.get(i));
            Objects.requireNonNull(tab.getIcon()).setColorFilter(ContextCompat.getColor(requireActivity(), R.color.coolGray400), PorterDuff.Mode.SRC_IN);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.tabLayout.getRoot().addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Objects.requireNonNull(tab.getIcon()).setColorFilter(ContextCompat.getColor(requireActivity(), R.color.lightBlue800), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Objects.requireNonNull(tab.getIcon()).setColorFilter(ContextCompat.getColor(requireActivity(), R.color.coolGray400), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setArgs() {
        userModel = (UserModel) FragmentEditUserArgs.fromBundle(getArguments()).getTypeModel();
        setData();
    }

    private void setData() {
        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());

        tabLayoutMediator.attach();

        if (((ActivityMain) requireActivity()).navigatoon.getBackstackDestinationId() == R.id.fragmentDashboard) {
            binding.viewPager.getRoot().setCurrentItem(1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}