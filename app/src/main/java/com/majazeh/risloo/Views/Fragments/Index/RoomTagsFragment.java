package com.majazeh.risloo.Views.Fragments.Index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomTagsAdapter;
import com.majazeh.risloo.databinding.FragmentRoomTagsBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.RoomModel;

import java.util.HashMap;
import java.util.Objects;

public class RoomTagsFragment extends Fragment {

    // Binding
    private FragmentRoomTagsBinding binding;

    // Adapters
    private RoomTagsAdapter adapter;

    // Models
    private RoomModel roomModel;

    // Objects
    private HashMap data, header;

    // Vars
    public String roomId = "", centerId = "", type = "";
    private boolean isLoading = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomTagsBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new RoomTagsAdapter(requireActivity());

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.RoomTagsFragmentTitle));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.getRoot().setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            if (!isLoading && !v.canScrollVertically(1)) {
//                isLoading = true;
//
//                if (data.containsKey("page"))
//                    data.put("page", ((int) data.get("page")) + 1);
//                else
//                    data.put("page", 1);
//
//                if (binding.indexSingleLayout.progressBar.getVisibility() == View.GONE)
//                    binding.indexSingleLayout.progressBar.setVisibility(View.VISIBLE);
//
//                getData();
//            }
        });
    }

    private void setArgs() {
        roomModel = (RoomModel) RoomTagsFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(roomModel);
    }

    private void setData(RoomModel model) {
        if (model.getRoomId() != null && !model.getRoomId().equals("")) {
            roomId = model.getRoomId();
            data.put("id", roomId);
        }

        if (model.getRoomCenter().getCenterId() != null && !model.getRoomCenter().getCenterId().equals("")) {
            centerId = model.getRoomCenter().getCenterId();
        }

        if (model.getRoomType() != null && !model.getRoomType().equals("")) {
            type = model.getRoomType();
        }
    }

    private void getData() {
//        Room.roomTags(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                List items = (List) object;
//
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        if (Objects.equals(data.get("page"), 1))
//                            adapter.clearItems();
//
//                        if (!items.data().isEmpty()) {
//                            adapter.setItems(items.data());
                            binding.indexSingleLayout.recyclerView.setAdapter(adapter);

                            binding.indexSingleLayout.emptyView.setVisibility(View.GONE);
//                        } else if (adapter.getItemCount() == 0) {
//                            binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);
//                            binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.RoomTagsFragmentEmpty));
//                        }

                        binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(adapter.getItemCount()));

                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.indexShimmerLayout.getRoot().stopShimmer();

//                        if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
//                            binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
//
//                    });
//
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
//
//                    });
//
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