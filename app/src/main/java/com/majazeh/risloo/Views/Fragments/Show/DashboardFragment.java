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
import com.majazeh.risloo.Views.Adapters.Recycler.Cases2Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.CentersAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Index.IndexSampleAdapter;
import com.majazeh.risloo.databinding.FragmentDashboardBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.HashMap;

public class DashboardFragment extends Fragment {

    // Binding
    private FragmentDashboardBinding binding;

    // Adapters
    private Cases2Adapter cases2Adapter;
    private IndexSampleAdapter indexSampleAdapter;
    private RoomsAdapter roomsAdapter;
    private CentersAdapter centersAdapter;

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
        cases2Adapter = new Cases2Adapter(requireActivity());
        indexSampleAdapter = new IndexSampleAdapter(requireActivity());
        roomsAdapter = new RoomsAdapter(requireActivity());
        centersAdapter = new CentersAdapter(requireActivity());

        data = new HashMap<>();
        data.put("user", ((MainActivity) requireActivity()).singleton.getId());
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.casesHeaderLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentCasesHeader));
        binding.samplesHeaderLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentSamplesHeader));
        binding.roomsHeaderLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentRoomsHeader));
        binding.centersHeaderLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentCentersHeader));

        binding.samplesShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.casesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.roomsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.centersSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void getData() {
        User.dashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                UserModel model = (UserModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).singleton.update(model);

                        // Cases Data
                        if (!model.getCaseList().data().isEmpty()) {
                            cases2Adapter.setItems(model.getCaseList().data());

                            binding.casesSingleLayout.recyclerView.setAdapter(cases2Adapter);
                            binding.casesHeaderLayout.countTextView.setText(StringManager.bracing(cases2Adapter.getItemCount()));

                            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        } else if (cases2Adapter.getItemCount() == 0) {
                            binding.casesGroup.setVisibility(View.GONE);
                        }
                        binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.casesShimmerLayout.getRoot().stopShimmer();

                        // Samples Data
                        if (!model.getSampleList().data().isEmpty()) {
                            indexSampleAdapter.setItems(model.getSampleList().data());

                            binding.samplesSingleLayout.recyclerView.setAdapter(indexSampleAdapter);
                            binding.samplesHeaderLayout.countTextView.setText(StringManager.bracing(indexSampleAdapter.itemsCount()));

                            binding.samplesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        } else if (indexSampleAdapter.getItemCount() == 0) {
                            binding.samplesGroup.setVisibility(View.GONE);
                        }
                        binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.samplesShimmerLayout.getRoot().stopShimmer();

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

                        // Centers Data
                        if (!model.getCenterList().data().isEmpty()) {
                            centersAdapter.setItems(model.getCenterList().data());

                            binding.centersSingleLayout.recyclerView.setAdapter(centersAdapter);
                            binding.centersHeaderLayout.countTextView.setText(StringManager.bracing(centersAdapter.getItemCount()));

                            binding.centersSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        } else if (centersAdapter.getItemCount() == 0) {
                            binding.centersGroup.setVisibility(View.GONE);
                        }
                        binding.centersShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.centersShimmerLayout.getRoot().stopShimmer();

                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {

                        // Cases Data
                        binding.casesGroup.setVisibility(View.GONE);
                        binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.casesShimmerLayout.getRoot().stopShimmer();

                        // Samples Data
                        binding.samplesGroup.setVisibility(View.GONE);
                        binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.samplesShimmerLayout.getRoot().stopShimmer();

                        // Rooms Data
                        binding.roomsGroup.setVisibility(View.GONE);
                        binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.roomsShimmerLayout.getRoot().stopShimmer();

                        // Centers Data
                        binding.centersGroup.setVisibility(View.GONE);
                        binding.centersShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.centersShimmerLayout.getRoot().stopShimmer();

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