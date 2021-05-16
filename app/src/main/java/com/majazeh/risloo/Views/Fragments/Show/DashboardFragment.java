package com.majazeh.risloo.Views.Fragments.Show;

import android.os.Bundle;
import android.os.Handler;
import android.os.UserManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Main;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Cases2Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.CentersAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.SamplesAdapter;
import com.majazeh.risloo.databinding.FragmentDashboardBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.HashMap;

public class DashboardFragment extends Fragment {

    // Binding
    private FragmentDashboardBinding binding;

    // Adapters
    private SamplesAdapter samplesAdapter;
    private Cases2Adapter cases2Adapter;
    private RoomsAdapter roomsAdapter;
    private CentersAdapter centersAdapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration, itemDecoration2;
    private LinearLayoutManager samplesLayoutManager, cases2LayoutManager, roomsLayoutManager, centersLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        samplesAdapter = new SamplesAdapter(requireActivity());
        cases2Adapter = new Cases2Adapter(requireActivity());
        roomsAdapter = new RoomsAdapter(requireActivity());
        centersAdapter = new CentersAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));
        itemDecoration2 = new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0);

        samplesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        cases2LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        roomsLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        centersLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentSamplesHeader));
        binding.casesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentCasesHeader));
        binding.roomsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentRoomsHeader));
        binding.centersHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentCentersHeader));

        binding.samplesShimmerLayout.shimmerItem1.topView.setVisibility(View.GONE);

        InitManager.recyclerView(binding.samplesSingleLayout.recyclerView, itemDecoration2, samplesLayoutManager);
        InitManager.recyclerView(binding.casesSingleLayout.recyclerView, itemDecoration, cases2LayoutManager);
        InitManager.recyclerView(binding.roomsSingleLayout.recyclerView, itemDecoration, roomsLayoutManager);
        InitManager.recyclerView(binding.centersSingleLayout.recyclerView, itemDecoration, centersLayoutManager);
    }

    private void setData() {
        HashMap data = new HashMap();
        data.put("user", ((MainActivity) requireActivity()).singleton.getUserId());
        HashMap header = new HashMap();
        header.put("Authorization", "Bearer " + ((MainActivity) requireActivity()).singleton.getToken());
        User.dashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                UserModel model = (UserModel) object;

                requireActivity().runOnUiThread(() -> {
                 ((MainActivity) requireActivity()).login(model);


                    samplesAdapter.setSamples(model.getSampleList().data());
                    cases2Adapter.setCases(model.getCaseList().data());
                    roomsAdapter.setRooms(model.getRoomList().data());
                    centersAdapter.setCenters(model.getCenterList().data());
                    new Handler().postDelayed(() -> {
                        binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.samplesSingleLayout.getRoot().setVisibility(View.VISIBLE);

                        binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);

                        binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);

                        binding.centersShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.centersSingleLayout.getRoot().setVisibility(View.VISIBLE);
                    }, 1000);

                    binding.samplesSingleLayout.recyclerView.setAdapter(samplesAdapter);
                    binding.casesSingleLayout.recyclerView.setAdapter(cases2Adapter);
                    binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);
                    binding.centersSingleLayout.recyclerView.setAdapter(centersAdapter);

                    binding.samplesHeaderIncludeLayout.countTextView.setText("(" + samplesAdapter.getItemCount() + ")");
                    binding.casesHeaderIncludeLayout.countTextView.setText("(" + cases2Adapter.getItemCount() + ")");
                    binding.roomsHeaderIncludeLayout.countTextView.setText("(" + roomsAdapter.getItemCount() + ")");
                    binding.centersHeaderIncludeLayout.countTextView.setText("(" + centersAdapter.getItemCount() + ")");
                });
            }

            @Override
            public void onFailure(String response) {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}