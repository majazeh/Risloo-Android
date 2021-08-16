package com.majazeh.risloo.Views.Fragments.Index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.TagsAdapter;
import com.majazeh.risloo.databinding.FragmentCenterTagsBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CenterModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CenterTagsFragment extends Fragment {

    // Binding
    private FragmentCenterTagsBinding binding;

    // Adapters
    private TagsAdapter adapter;

    // Models
    private CenterModel centerModel;

    // Objects
    private HashMap data, header;

    // Vars
    public String centerId = "", type = "";
    private boolean isLoading = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterTagsBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TagsAdapter(requireActivity());

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.CenterTagsFragmentTitle));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setArgs() {
        centerModel = (CenterModel) CenterTagsFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(centerModel);
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
        Center.tags(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                List items = (List) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        if (Objects.equals(data.get("page"), 1))
                            adapter.clearItems();

                        if (!items.data().isEmpty())
                            adapter.setItems(items.data());
                        else
                            adapter.setItems(new ArrayList<>());

                        binding.indexSingleLayout.recyclerView.setAdapter(adapter);
                        binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(adapter.getItemCount()));

                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.indexShimmerLayout.getRoot().stopShimmer();

                    });

                    isLoading = false;
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

                    isLoading = false;
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