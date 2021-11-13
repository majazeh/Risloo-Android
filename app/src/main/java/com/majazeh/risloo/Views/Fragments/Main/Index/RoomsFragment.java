package com.majazeh.risloo.Views.Fragments.Main.Index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentRoomsBinding;
import com.mre.ligheh.Model.TypeModel.CenterModel;

import java.util.HashMap;

public class RoomsFragment extends Fragment {

    // Binding
    private FragmentRoomsBinding binding;

    // Adapters
//    public IndexRoomAdapter adapter;

    // Models
    public CenterModel centerModel;

    // Objects
    private HashMap data, header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomsBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
//        adapter = new IndexRoomAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.RoomsFragmentTitle));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setArgs() {
        centerModel = (CenterModel) RoomsFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(centerModel);
    }

    private void setData(CenterModel model) {
        if (model.getCenterId() != null && !model.getCenterId().equals("")) {
            data.put("id", model.getCenterId());
        }
    }

    private void getData() {
//        Center.rooms(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                List items = (List) object;
//
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        adapter.clearItems();
//
//                        if (!items.data().isEmpty()) {
//                            adapter.setItems(items.data());
//                            binding.indexSingleLayout.recyclerView.setAdapter(adapter);
//
//                            binding.indexSingleLayout.emptyView.setVisibility(View.GONE);
//                        } else if (adapter.getItemCount() == 0) {
//                            binding.indexSingleLayout.recyclerView.setAdapter(null);
//
//                            binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);
//                            binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.RoomAdapterEmpty));
//                        }
//
//                        binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(adapter.getItemCount()));
//
//                        hideShimmer();
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(String response) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        hideShimmer();
//                    });
//                }
//            }
//        });
    }

    private void hideShimmer() {
        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.indexShimmerLayout.getRoot().stopShimmer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}