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
import com.majazeh.risloo.Views.Adapters.Recycler.OptionalsAdapter;
import com.majazeh.risloo.databinding.FragmentTestOptionalBinding;

public class TestOptionalFragment extends Fragment {

    // Binding
    private FragmentTestOptionalBinding binding;

    // Adapters
    private OptionalsAdapter optionalsAdapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;

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

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._16sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        InitManager.recyclerView(binding.optionalsSingleLayout.recyclerView, itemDecoration, layoutManager);
    }

    private void setData() {
        binding.questionTextView.setText("به مردم کنایه\u200Cهای تند و نیشدار می\u200Cزنم اگر فکر کنم که حقشان همین است.");

//        optionalsAdapter.setOptional(null);
        binding.optionalsSingleLayout.recyclerView.setAdapter(optionalsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}