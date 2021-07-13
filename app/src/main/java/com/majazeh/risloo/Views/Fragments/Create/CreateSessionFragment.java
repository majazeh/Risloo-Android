package com.majazeh.risloo.Views.Fragments.Create;

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
import com.majazeh.risloo.Views.Adapters.Tab.CreateSessionAdapter;
import com.majazeh.risloo.databinding.FragmentCreateSessionBinding;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.HashMap;

public class CreateSessionFragment extends Fragment {

    // Binding
    public FragmentCreateSessionBinding binding;

    // Adapters
    public CreateSessionAdapter adapter;

    // Objects
    private TabLayoutMediator tabLayoutMediator;

    // Vars
    private HashMap data, header;
    private String[] tabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new CreateSessionAdapter(requireActivity());

        tabs = getResources().getStringArray(R.array.CreateSessionTabs);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
    }

    private void setArgs() {
        String type = CreateSessionFragmentArgs.fromBundle(getArguments()).getType();
        TypeModel typeModel = CreateSessionFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (type.equals("case")) {
                CaseModel caseModel = (CaseModel) CreateSessionFragmentArgs.fromBundle(getArguments()).getTypeModel();
                setData(caseModel);
            }
        }

        binding.viewPager.getRoot().setAdapter(adapter);
        tabLayoutMediator.attach();
    }

    private void setData(CaseModel model) {
        if (model.getCaseId() != null && !model.getCaseId().equals("")) {
            data.put("id", model.getCaseId());
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