package com.majazeh.risloo.Views.Fragments.Index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.factor.bouncy.BouncyNestedScrollView;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomPlatformsAdapter;
import com.majazeh.risloo.databinding.FragmentRoomPlatformsBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.RoomModel;

import java.util.HashMap;
import java.util.Objects;

public class RoomPlatformsFragment extends Fragment {

    // Binding
    private FragmentRoomPlatformsBinding binding;

    // Adapters
    private RoomPlatformsAdapter adapter;

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
        binding = FragmentRoomPlatformsBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new RoomPlatformsAdapter(requireActivity());

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.RoomPlatformsFragmentTitle));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.getRoot().setMOnScrollChangeListener((BouncyNestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            if (!isLoading && !Objects.requireNonNull(v).canScrollVertically(1)) {
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
        roomModel = (RoomModel) RoomPlatformsFragmentArgs.fromBundle(getArguments()).getTypeModel();
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
        Room.roomSessionPlatform(data, header, new Response() {
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
                            binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.RoomPlatformsFragmentEmpty));
                        }

                        binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(adapter.getItemCount()));

                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.indexShimmerLayout.getRoot().stopShimmer();

                        if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                            binding.indexSingleLayout.progressBar.setVisibility(View.GONE);

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

                        if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                            binding.indexSingleLayout.progressBar.setVisibility(View.GONE);

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