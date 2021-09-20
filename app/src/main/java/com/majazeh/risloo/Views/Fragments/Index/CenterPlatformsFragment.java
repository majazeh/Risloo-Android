package com.majazeh.risloo.Views.Fragments.Index;

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
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.CenterPlatformsAdapter;
import com.majazeh.risloo.databinding.FragmentCenterPlatformsBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CenterModel;

import java.util.HashMap;
import java.util.Objects;

public class CenterPlatformsFragment extends Fragment {

    // Binding
    private FragmentCenterPlatformsBinding binding;

    // Adapters
    private CenterPlatformsAdapter adapter;

    // Models
    public CenterModel centerModel;

    // Objects
    private HashMap data, header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterPlatformsBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new CenterPlatformsAdapter(requireActivity());

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.CenterPlatformsFragmentTitle));

        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.addIncludeLayout.getRoot(), binding.addIncludeLayout.selectTextView, binding.addIncludeLayout.selectImageView, getResources().getString(R.string.CenterPlatformsFragmentAdd), getResources().getColor(R.color.Green700), R.drawable.ic_plus_light, R.color.Green700, R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_green300);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreatePlatformFragment(centerModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.addIncludeLayout.getRoot());
    }

    private void setArgs() {
        centerModel = (CenterModel) CenterPlatformsFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(centerModel);
    }

    private void setData(CenterModel model) {
        if (model.getCenterId() != null && !model.getCenterId().equals("")) {
            data.put("id", model.getCenterId());
        }
    }

    private void getData() {
        Center.centerSessionPlatform(data, header, new Response() {
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
                        } else if (adapter.getItemCount() == 0) {
                            binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.CenterPlatformsFragmentEmpty));
                        }

                        binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(adapter.getItemCount()));

                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.indexShimmerLayout.getRoot().stopShimmer();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.indexShimmerLayout.getRoot().stopShimmer();
                    });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}