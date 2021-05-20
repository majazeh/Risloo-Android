package com.majazeh.risloo.Views.Fragments.Index;

import android.os.Bundle;
import android.os.Handler;
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
import com.majazeh.risloo.Views.Adapters.Recycler.SessionReportsAdapter;
import com.majazeh.risloo.databinding.FragmentSessionReportsBinding;

public class SessionReportsFragment extends Fragment {

    // Binding
    private FragmentSessionReportsBinding binding;

    // Adapters
    private SessionReportsAdapter adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentSessionReportsBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new SessionReportsAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0);

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.SessionReportsFragmentTitle));

        binding.indexShimmerLayout.shimmerItem1.topView.setVisibility(View.GONE);

        InitManager.recyclerView(binding.indexSingleLayout.recyclerView, itemDecoration, layoutManager);
    }

    private void setData() {
//        adapter.setReports(null);
        binding.indexSingleLayout.recyclerView.setAdapter(adapter);
        binding.headerIncludeLayout.countTextView.setText("(" + adapter.getItemCount() + ")");

        new Handler().postDelayed(() -> {
            binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.indexHeaderLayout.getRoot().setVisibility(View.VISIBLE);
            binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
        }, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}