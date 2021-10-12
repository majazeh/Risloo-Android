package com.majazeh.risloo.Views.Fragments.Main.Index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexBillAdapter;
import com.majazeh.risloo.databinding.FragmentBillingsBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Billing;
import com.mre.ligheh.Model.Madule.List;

import java.util.HashMap;
import java.util.Objects;

public class BillingsFragment extends Fragment {

    // Binding
    private FragmentBillingsBinding binding;

    // Adapters
    private IndexBillAdapter adapter;

    // Objects
    private HashMap data, header;

    // Vars
    private boolean isLoading = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentBillingsBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new IndexBillAdapter(requireActivity());

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.BillingsFragmentTitle));

        binding.indexShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.filterTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                data.put("page", 1);

                if (tab.getPosition() == 0)
                    data.put("type", "");
                else if (tab.getPosition() == 1)
                    data.put("type", "creditor");

                getData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.getRoot().setMOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!isLoading && !Objects.requireNonNull(v).canScrollVertically(1)) {
                isLoading = true;

                if (data.containsKey("page"))
                    data.put("page", ((int) data.get("page")) + 1);
                else
                    data.put("page", 1);

                if (binding.indexSingleLayout.progressBar.getVisibility() == View.GONE)
                    binding.indexSingleLayout.progressBar.setVisibility(View.VISIBLE);

                getData();
            }
        });
    }

    private void getData() {
        Billing.list(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                List items = (List) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        if (Objects.equals(data.get("page"), 1))
                            adapter.clearItems();

                        if (!items.data().isEmpty()) {
                            adapter.setItems(items.data());
                            binding.indexSingleLayout.recyclerView.setAdapter(adapter);

                            binding.indexSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (adapter.itemsCount() == 0) {
                            binding.indexSingleLayout.recyclerView.setAdapter(null);

                            binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.BillingsFragmentEmpty));
                        }

                        binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(items.getTotal()));

                        hideShimmer();
                    });

                    isLoading = false;
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        hideShimmer();
                    });

                    isLoading = false;
                }
            }
        });
    }

    private void hideShimmer() {
        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.indexShimmerLayout.getRoot().stopShimmer();

        if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
            binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        isLoading = true;
    }

}