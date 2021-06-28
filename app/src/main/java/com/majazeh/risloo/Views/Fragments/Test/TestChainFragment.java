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
import com.majazeh.risloo.Views.Adapters.Recycler.ChainsAdapter;
import com.majazeh.risloo.databinding.FragmentTestChainBinding;

public class TestChainFragment extends Fragment {

    // Binding
    private FragmentTestChainBinding binding;

    // Adapters
//    private ChainsAdapter chainsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestChainBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
//        chainsAdapter = new ChainsAdapter(requireActivity());

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.ChainFragmentTitle));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView, getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._12sdp));
    }

    private void setData() {
//        chainsAdapter.setChains(null);
//        binding.listRecyclerView.setAdapter(chainsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}