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
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Adapters.Tab.EditCenterAdapter;
import com.majazeh.risloo.databinding.FragmentEditCenterBinding;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

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
        if (model.getCenterType() != null && !model.getCenterType().equals(""))
            type = model.getCenterType();

        adapter = new EditCenterAdapter(requireActivity(), type);

        if (type.equals("personal_clinic"))
            binding.tabLayout.getRoot().setVisibility(View.GONE);
        else
            binding.tabLayout.getRoot().setVisibility(View.VISIBLE);

        binding.viewPager.getRoot().setAdapter(adapter);
        binding.viewPager.getRoot().setOffscreenPageLimit(adapter.getItemCount());

        tabLayoutMediator.attach();
    }

    private void setData(RoomModel model) {
        if (model.getRoomType() != null && !model.getRoomType().equals(""))
            type = model.getRoomType();

        adapter = new EditCenterAdapter(requireActivity(), type);
        binding.tabLayout.getRoot().setVisibility(View.GONE);

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