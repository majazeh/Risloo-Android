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
import com.majazeh.risloo.Views.Adapters.Recycler.OptionalsAdapter;
import com.majazeh.risloo.databinding.FragmentTestOptionalBinding;

public class TestOptionalFragment extends Fragment {

    // Binding
    private FragmentTestOptionalBinding binding;

    // Adapters
    private OptionalsAdapter optionalsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestOptionalBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        optionalsAdapter = new OptionalsAdapter(requireActivity());

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView, getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setData() {
        binding.questionTextView.getRoot().setText("به مردم کنایه\u200Cهای تند و نیشدار می\u200Cزنم اگر فکر کنم که حقشان همین است.");

//        optionalsAdapter.setOptionals(null);
        binding.listRecyclerView.setAdapter(optionalsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}