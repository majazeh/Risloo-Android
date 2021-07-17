package com.majazeh.risloo.Views.Fragments.Index;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
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
//    private PlatformsAdapter adapter;

    // Vars
    private HashMap data, header;
    private CenterModel centerModel;
    private boolean isLoading = true;
    public String centerId = "", type = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterPlatformsBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
//        adapter = new PlatformsAdapter(requireActivity());

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.CenterPlatformsFragmentTitle));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.addConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_green300);
        } else {
            binding.addConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray200);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.getRoot().setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!isLoading) {
                if (!binding.getRoot().canScrollVertically(1)) {
                    isLoading = true;

                    if (data.containsKey("page"))
                        data.put("page", ((int) data.get("page")) + 1);
                    else
                        data.put("page", 1);

                    if (binding.indexSingleLayout.progressBar.getVisibility() == View.GONE)
                        binding.indexSingleLayout.progressBar.setVisibility(View.VISIBLE);

                    getData();
                }
            }
        });

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreatePlatformFragment("center", centerModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.addConstraintLayout);
    }

    private void setArgs() {
//        centerModel = (CenterModel) CenterPlatformsFragmentArgs.fromBundle(getArguments()).getTypeModel();
//
//        setData(centerModel);
    }

    private void setData(CenterModel model) {
        if (model.getCenterId() != null && !model.getCenterId().equals("")) {
            centerId = model.getCenterId();
            data.put("id", centerId);
        }

        if (model.getCenterType() != null && !model.getCenterType().equals("")) {
            type = model.getCenterType();
        }
    }

    private void getData() {
//        Center.platforms(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                List platforms = (List) object;
//
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        if (Objects.equals(data.get("page"), 1))
//                            adapter.clearPlatforms();
//
//                        if (!platforms.data().isEmpty()) {
//                            adapter.setPlatforms(platforms.data());
//                            binding.indexSingleLayout.recyclerView.setAdapter(adapter);
//
//                            binding.indexSingleLayout.textView.setVisibility(View.GONE);
//                        } else if (adapter.getItemCount() == 0) {
//                            binding.indexSingleLayout.textView.setVisibility(View.VISIBLE);
//                        }
//                        binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(adapter.getItemCount()));
//
//                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
//                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
//                        binding.indexShimmerLayout.getRoot().stopShimmer();
//
//                        if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
//                            binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
//                    });
//                    isLoading = false;
//                }
//            }
//
//            @Override
//            public void onFailure(String response) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
//                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
//                        binding.indexShimmerLayout.getRoot().stopShimmer();
//
//                        if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
//                            binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
//                    });
//                    isLoading = false;
//                }
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}