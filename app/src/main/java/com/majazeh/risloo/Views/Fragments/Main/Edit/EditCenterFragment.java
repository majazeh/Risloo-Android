package com.majazeh.risloo.Views.Fragments.Main.Edit;

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
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.adapters.tab.EditCenterAdapter;
import com.majazeh.risloo.databinding.FragmentEditCenterBinding;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.Objects;

public class EditCenterFragment extends Fragment {

    // Binding
    public FragmentEditCenterBinding binding;

    // Adapters
    public EditCenterAdapter adapter;

    // Models
    public CenterModel centerModel;
    public RoomModel roomModel;

    // Objects
    private TabLayoutMediator tabLayoutMediator;

    // Vars
    public String type = "personal_clinic";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCenterBinding.inflate(inflater, viewGroup, false);

        listener();

        setArgs();

        return binding.getRoot();
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
        TypeModel typeModel = EditCenterFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CenterModel")) {
            centerModel = (CenterModel) typeModel;
            setData(centerModel);
        } else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("RoomModel")) {
            roomModel = (RoomModel) typeModel;
            setData(roomModel);
        }
    }

    private void setData(CenterModel model) {
        if (model.getType() != null && !model.getType().equals("")) {
            type = model.getType();
        }

        adapter = new EditCenterAdapter(requireActivity(), type);
        setTabLayout();

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());

        tabLayoutMediator.attach();
    }

    private void setData(RoomModel model) {
        if (model.getType() != null && !model.getType().equals("")) {
            type = model.getType();
        }

        adapter = new EditCenterAdapter(requireActivity(), type);
        setTabLayout();

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());

        tabLayoutMediator.attach();
    }

    private void setTabLayout() {
        ArrayList<Integer> images = new ArrayList<>();

        if (type.equals("personal_clinic")) {
            images.add(R.drawable.ic_info_circle_light);

            binding.tabLayout.getRoot().setVisibility(View.GONE);
        } else {
            images.add(R.drawable.ic_info_circle_light);
            images.add(R.drawable.ic_image_light);

            binding.tabLayout.getRoot().setVisibility(View.VISIBLE);
        }

        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, i) -> {
            tab.setIcon(images.get(i));
            Objects.requireNonNull(tab.getIcon()).setColorFilter(ContextCompat.getColor(requireActivity(), R.color.coolGray400), PorterDuff.Mode.SRC_IN);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}