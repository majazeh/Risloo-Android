package com.majazeh.risloo.Views.Fragments.Test;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Adapters.Recycler.FormsAdapter;
import com.majazeh.risloo.databinding.FragmentTestFormBinding;

public class TestFormFragment extends Fragment {

    // Binding
    private FragmentTestFormBinding binding;

    // Adapters
    private FormsAdapter formsAdapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;

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

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        InitManager.recyclerView(binding.formsSingleLayout.recyclerView, itemDecoration, layoutManager);
    }

    private void setData() {
//        formsAdapter.setForm(null);
        formsAdapter.setEditable(true);

        binding.formsSingleLayout.recyclerView.setAdapter(formsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}