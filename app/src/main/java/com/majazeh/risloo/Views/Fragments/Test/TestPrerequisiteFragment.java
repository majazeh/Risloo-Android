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
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.PrerequisitesAdapter;
import com.majazeh.risloo.databinding.FragmentTestPrerequisiteBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.FormModel;

public class TestPrerequisiteFragment extends Fragment {

    // Binding
    private FragmentTestPrerequisiteBinding binding;

    // Adapters
    private PrerequisitesAdapter adapter;

    // Vars
    private FormModel formModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestPrerequisiteBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new PrerequisitesAdapter(requireActivity());

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.PrerequisiteFragmentTitle));
        binding.descriptionTextView.getRoot().setText(getResources().getString(R.string.PrerequisiteFragmentDescription));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView, getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setArgs() {
        formModel = ((TestActivity) requireActivity()).formModel;

        setData(formModel);
    }

    private void setData(FormModel model) {
        List prerequisites = (List) model.getObject();

        if (!prerequisites.data().isEmpty()) {
            adapter.setItems(prerequisites.data());
            binding.listRecyclerView.setAdapter(adapter);
        } else if (adapter.getItemCount() == 0) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}