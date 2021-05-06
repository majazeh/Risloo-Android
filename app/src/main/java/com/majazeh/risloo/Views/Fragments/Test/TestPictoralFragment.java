package com.majazeh.risloo.Views.Fragments.Test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Adapters.Recycler.PictoralsAdapter;
import com.majazeh.risloo.databinding.FragmentTestPictoralBinding;
import com.squareup.picasso.Picasso;

public class TestPictoralFragment extends Fragment {

    // Binding
    private FragmentTestPictoralBinding binding;

    // Adapters
    private PictoralsAdapter pictoralsAdapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private GridLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestPictoralBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        pictoralsAdapter = new PictoralsAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("gridLayout", (int) getResources().getDimension(R.dimen._16sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));

        layoutManager = new GridLayoutManager(requireActivity(), 2, LinearLayoutManager.VERTICAL, false);

        InitManager.recyclerView(binding.listRecyclerView.getRoot(), itemDecoration, layoutManager);
    }

    private void setData() {
        Picasso.get().load(R.color.Gray100).placeholder(R.color.Gray100).into(binding.questionImageView);

//        pictoralsAdapter.setPictorals(null);
        binding.listRecyclerView.getRoot().setAdapter(pictoralsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}