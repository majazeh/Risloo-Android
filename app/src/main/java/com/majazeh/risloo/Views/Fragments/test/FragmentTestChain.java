package com.majazeh.risloo.views.fragments.test;

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
import com.majazeh.risloo.views.activities.ActivityTest;
import com.majazeh.risloo.views.adapters.recycler.test.TestChainAdapter;
import com.majazeh.risloo.databinding.FragmentTestChainBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.FormModel;

public class FragmentTestChain extends Fragment {

    // Binding
    private FragmentTestChainBinding binding;

    // Adapters
    private TestChainAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestChainBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        setAnimation();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TestChainAdapter(requireActivity());

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.ChainFragmentTitle));

        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.listRecyclerView.getRoot(), getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._12sdp));
    }

    private void setArgs() {
        FormModel formModel = ((ActivityTest) requireActivity()).formModel;
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
        AnimateManager.animateViewAlpha(binding.listRecyclerView.getRoot(), 500, 0f, 1f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}