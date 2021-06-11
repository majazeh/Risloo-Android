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
import com.majazeh.risloo.Views.Adapters.Recycler.BulksAdapter;
import com.majazeh.risloo.databinding.FragmentTestBulkBinding;

public class TestBulkFragment extends Fragment {

    // Binding
    private FragmentTestBulkBinding binding;

    // Adapters
    private BulksAdapter bulksAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestBulkBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        bulksAdapter = new BulksAdapter(requireActivity());

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.BulkFragmentTitle));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView, getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._12sdp));
    }

    private void setData() {
//        bulksAdapter.setBulks(null);
        binding.listRecyclerView.setAdapter(bulksAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}