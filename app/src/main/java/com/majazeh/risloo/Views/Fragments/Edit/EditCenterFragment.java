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

import org.json.JSONArray;
import org.json.JSONException;

public class EditCenterFragment extends Fragment {

    // Binding
    public FragmentEditCenterBinding binding;

    // Adapters
    public EditCenterAdapter adapter;

    // Objects
    private TabLayoutMediator tabLayoutMediator;

    // Vars
    private String[] tabs;
    public String centerId = "";
    public String type = "personal_clinic", managerId = "", managerName = "", title = "", address = "", description = "";
    public JSONArray phonesArray;
    public String avatarPath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCenterBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        tabs = getResources().getStringArray(R.array.EditCenterTabs);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));
    }

    private void setData() {
        if (getArguments() != null) {
            if (getArguments().getString("center_id") != null) {
                centerId = getArguments().getString("center_id");
            }

            if (getArguments().getString("type") != null) {
                type = getArguments().getString("type");
                switch (type) {
                    case "personal_clinic":
                        binding.tabLayout.getRoot().setVisibility(View.GONE);
                        break;
                    case "counseling_center":
                        binding.tabLayout.getRoot().setVisibility(View.VISIBLE);
                        break;
                }
            }

            if (getArguments().getString("manager_id") != null) {
                managerId = getArguments().getString("manager_id");
                managerName = getArguments().getString("manager_name");
            }

            if (getArguments().getString("title") != null) {
                title = getArguments().getString("title");
            }

            if (getArguments().getString("avatar") != null) {
                avatarPath = getArguments().getString("avatar");
            }

            if (getArguments().getString("address") != null) {
                address = getArguments().getString("address");
            }

            if (getArguments().getString("phones") != null) {
                try {
                    phonesArray = new JSONArray(getArguments().getString("phones"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (getArguments().getString("description") != null) {
                description = getArguments().getString("description");
            }
        }

        adapter = new EditCenterAdapter(requireActivity(), type);

        binding.viewPager.getRoot().setAdapter(adapter);
        tabLayoutMediator.attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}