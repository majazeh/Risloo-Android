package com.majazeh.risloo.views.fragments.main.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.classes.ItemTouchHelperCallback;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Index.IndexRoomAdapter;
import com.majazeh.risloo.databinding.FragmentRoomsBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class FragmentRooms extends Fragment {

    // Binding
    private FragmentRoomsBinding binding;

    // Adapters
    public IndexRoomAdapter adapter;

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
        adapter = new IndexRoomAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.RoomsFragmentTitle));

        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);

        touchHelper.attachToRecyclerView(binding.indexSingleLayout.recyclerView);
    }

    private void setArgs() {
        centerModel = (CenterModel) FragmentRoomsArgs.fromBundle(getArguments()).getTypeModel();
        setData(centerModel);
    }

    private void setData(CenterModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }
    }

    private void getData() {
        Center.showDashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            adapter.clearItems();

                            List items = new List();
                            for (int i = 0; i < ((JSONObject) object).getJSONArray("data").length(); i++)
                                items.add(new RoomModel(((JSONObject) object).getJSONArray("data").getJSONObject(i)));

                            if (!items.data().isEmpty()) {
                                adapter.setItems(items.data());
                                binding.indexSingleLayout.recyclerView.setAdapter(adapter);

                                binding.indexSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (adapter.getItemCount() == 0) {
                                binding.indexSingleLayout.recyclerView.setAdapter(null);

                                binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.RoomAdapterEmpty));
                            }

                            binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(adapter.getItemCount()));

                            hideShimmer();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        hideShimmer();
                    });
                }
            }
        });
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