package com.majazeh.risloo.Views.Fragments.Test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.AnimateManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.Views.activities.TestActivity;
import com.majazeh.risloo.Views.adapters.recycler.test.TestPrerequisiteAdapter;
import com.majazeh.risloo.databinding.FragmentTestPrerequisiteBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.FormModel;

public class TestPrerequisiteFragment extends Fragment {

    // Binding
    private FragmentTestPrerequisiteBinding binding;

    // Adapters
    private TestPrerequisiteAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestPrerequisiteBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        setAnimation();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TestPrerequisiteAdapter(requireActivity());

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.PrerequisiteFragmentTitle));
        binding.descriptionTextView.getRoot().setText(getResources().getString(R.string.PrerequisiteFragmentDescription));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView.getRoot(), getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setArgs() {
        FormModel formModel = ((TestActivity) requireActivity()).formModel;
        setData(formModel);
    }

    private void setData(FormModel model) {
        List items = (List) model.getObject();

        if (!items.data().isEmpty()) {
            adapter.setItems(items.data());
            binding.listRecyclerView.getRoot().setAdapter(adapter);
        }
    }

    private void setAnimation() {
        AnimateManager.animateViewAlpha(binding.titleTextView.getRoot(), 500, 0f, 1f);
        AnimateManager.animateViewAlpha(binding.descriptionTextView.getRoot(), 500, 0f, 1f);
        AnimateManager.animateViewAlpha(binding.listRecyclerView.getRoot(), 500, 0f, 1f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}