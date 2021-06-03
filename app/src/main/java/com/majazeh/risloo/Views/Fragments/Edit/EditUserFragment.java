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
import com.majazeh.risloo.Views.Adapters.Tab.EditUserAdapter;
import com.majazeh.risloo.databinding.FragmentEditUserBinding;

public class EditUserFragment extends Fragment {

    // Binding
    public FragmentEditUserBinding binding;

    // Adapters
    public EditUserAdapter adapter;

    // Objects
    private TabLayoutMediator tabLayoutMediator;

    // Vars
    private String[] tabs;
    public String id = "", name = "", username = "", education = "", birthday = "", email = "", mobile = "", status = "", type = "", gender = "", publicKey = "";
    public String avatarPath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        setExtra();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new EditUserAdapter(requireActivity());

        tabs = getResources().getStringArray(R.array.EditUserTabs);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                id = getArguments().getString("id");
            }

            if (getArguments().getString("name") != null && !getArguments().getString("name").equals("")) {
                name = getArguments().getString("name");
            }

            if (getArguments().getString("username") != null && !getArguments().getString("username").equals("")) {
                username = getArguments().getString("username");
            }

            if (getArguments().getString("education") != null && !getArguments().getString("education").equals("")) {
                education = getArguments().getString("education");
            }

            if (getArguments().getString("birthday") != null && !getArguments().getString("birthday").equals("")) {
                birthday = getArguments().getString("birthday");
            }

            if (getArguments().getString("email") != null && !getArguments().getString("email").equals("")) {
                email = getArguments().getString("email");
            }

            if (getArguments().getString("mobile") != null && !getArguments().getString("mobile").equals("")) {
                mobile = getArguments().getString("mobile");
            }

            if (getArguments().getString("status") != null && !getArguments().getString("status").equals("")) {
                status = getArguments().getString("status");
            }

            if (getArguments().getString("type") != null && !getArguments().getString("type").equals("")) {
                type = getArguments().getString("type");
            }

            if (getArguments().getString("gender") != null && !getArguments().getString("gender").equals("")) {
                gender = getArguments().getString("gender");
            }

            if (getArguments().getString("public_key") != null && !getArguments().getString("public_key").equals("")) {
                publicKey = getArguments().getString("public_key");
            }

            if (getArguments().getString("avatar") != null && !getArguments().getString("avatar").equals("")) {
                avatarPath = getArguments().getString("avatar");
            }
        }

        binding.viewPager.getRoot().setAdapter(adapter);
        tabLayoutMediator.attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}