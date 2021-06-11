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
import com.majazeh.risloo.Views.Adapters.Recycler.FormsAdapter;
import com.majazeh.risloo.databinding.FragmentTestFormBinding;

public class TestFormFragment extends Fragment {

    // Binding
    private FragmentTestFormBinding binding;

    // Adapters
    private FormsAdapter formsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestFormBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        formsAdapter = new FormsAdapter(requireActivity());

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.FormFragmentTitle));
        binding.descriptionTextView.getRoot().setText(getResources().getString(R.string.FormFragmentDescription));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView, getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setData() {
//        formsAdapter.setForms(null);
        binding.listRecyclerView.setAdapter(formsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}