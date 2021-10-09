package com.majazeh.risloo.Views.Fragments.Main.Index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexTreasuryAdapter;
import com.majazeh.risloo.databinding.FragmentTreasuriesBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Treasury;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Objects;

public class TreasuriesFragment extends Fragment {

    // Binding
    private FragmentTreasuriesBinding binding;

    // Adapters
    private IndexTreasuryAdapter adapter;

    // Objects
    private HashMap data, header;

    // Vars
    private boolean isLoading = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTreasuriesBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new IndexTreasuryAdapter(requireActivity());

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.TreasuriesFragmentTitle));

        binding.indexShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.imgResTintBackground(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.White, R.drawable.draw_oval_solid_green600_ripple_white);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
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

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateTreasuryFragment(((MainActivity) requireActivity()).singleton.getUserModel());
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.addImageView.getRoot());
    }

    private void getData() {
        Treasury.list(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                List items = (List) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            if (Objects.equals(data.get("page"), 1))
                                adapter.clearItems();

                            if (items.meta().has("total") && !items.meta().isNull("total"))
                                binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(items.meta().getString("total")));

                            if (!items.data().isEmpty()) {
                                adapter.setItems(items.data());
                                binding.indexSingleLayout.recyclerView.setAdapter(adapter);

                                binding.indexSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (adapter.itemsCount() == 0) {
                                binding.indexSingleLayout.recyclerView.setAdapter(null);

                                binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.TreasuriesFragmentEmpty));
                            }

                            hideShimmer();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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