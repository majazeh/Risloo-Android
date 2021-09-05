package com.majazeh.risloo.Views.Fragments.Show;

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
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.databinding.FragmentDashboardBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.HashMap;

public class DashboardFragment extends Fragment {

    // Binding
    private FragmentDashboardBinding binding;

    // Adapters
    private RoomsAdapter roomsAdapter;

    // Objects
    private HashMap data, header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, viewGroup, false);

        initializer();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        roomsAdapter = new RoomsAdapter(requireActivity());

        data = new HashMap<>();
        data.put("user", ((MainActivity) requireActivity()).singleton.getId());
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.roomsHeaderLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentRoomsHeader));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.roomsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void getData() {
        User.dashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                UserModel model = (UserModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).singleton.update(model);

                        // Rooms Data
                        if (!model.getRoomList().data().isEmpty()) {
                            roomsAdapter.setItems(model.getRoomList().data());

                            binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);
                            binding.roomsHeaderLayout.countTextView.setText(StringManager.bracing(roomsAdapter.getItemCount()));

                            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        } else if (roomsAdapter.getItemCount() == 0) {
                            binding.roomsGroup.setVisibility(View.GONE);
                        }
                        binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.roomsShimmerLayout.getRoot().stopShimmer();

                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {

                        // Rooms Data
                        binding.roomsGroup.setVisibility(View.GONE);
                        binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.roomsShimmerLayout.getRoot().stopShimmer();

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