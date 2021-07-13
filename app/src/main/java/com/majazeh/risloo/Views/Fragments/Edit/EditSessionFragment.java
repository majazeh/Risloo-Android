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
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Tab.EditSessionAdapter;
import com.majazeh.risloo.databinding.FragmentEditSessionBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;

import java.util.HashMap;

public class EditSessionFragment extends Fragment {

    // Binding
    public FragmentEditSessionBinding binding;

    // Adapters
    public EditSessionAdapter adapter;

    // Objects
    private TabLayoutMediator tabLayoutMediator;

    // Vars
    private HashMap data, header;
    private String[] tabs;
    public SessionModel sessionModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new EditSessionAdapter(requireActivity());

        tabs = getResources().getStringArray(R.array.EditSessionTabs);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
    }

    private void setArgs() {
        sessionModel = (SessionModel) EditSessionFragmentArgs.fromBundle(getArguments()).getTypeModel();

        setData(sessionModel);

        binding.viewPager.getRoot().setAdapter(adapter);
        tabLayoutMediator.attach();
    }

    private void setData(SessionModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }
    }

    public void doWork() {
        // TODO : Place Code Here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}