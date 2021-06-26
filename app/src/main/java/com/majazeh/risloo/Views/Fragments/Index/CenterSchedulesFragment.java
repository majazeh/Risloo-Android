package com.majazeh.risloo.Views.Fragments.Index;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SchedulesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.WeeksAdapter;
import com.majazeh.risloo.databinding.FragmentCenterSchedulesBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CenterModel;

import java.util.HashMap;

public class CenterSchedulesFragment extends Fragment {

    // Binding
    private FragmentCenterSchedulesBinding binding;

    // Adapters
    private WeeksAdapter weeksAdapter;
    private SchedulesAdapter schedulesAdapter;

    // Vars
    private HashMap data, header;
    private CenterModel centerModel;
    private long currentTimestamp = DateManager.currentTimestamp();
    public String centerId = "", type = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterSchedulesBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        getData(currentTimestamp);

        return binding.getRoot();
    }

    private void initializer() {
        weeksAdapter = new WeeksAdapter(requireActivity());
        schedulesAdapter = new SchedulesAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.txtTextColor(binding.weekTextView.getRoot(), getResources().getString(R.string.AppDefaultWeek), getResources().getColor(R.color.Gray500));

        InitManager.imgResTint(requireActivity(), binding.backwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.Blue600);
        InitManager.imgResTintRotate(requireActivity(), binding.forwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.Blue600, 180);

        InitManager.fixedHorizontalRecyclerView(requireActivity(), binding.weeksRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.schedulesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.weekTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);

            binding.backwardImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);
            binding.forwardImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);
        } else {
            binding.weekTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);

            binding.backwardImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);
            binding.forwardImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.weekTextView.getRoot());

        ClickManager.onDelayedClickListener(() -> doWork(DateManager.preJalFridayTimestamp(currentTimestamp))).widget(binding.backwardImageView.getRoot());

        ClickManager.onDelayedClickListener(() -> doWork(DateManager.nxtJalSaturdayTimestamp(currentTimestamp))).widget(binding.forwardImageView.getRoot());
    }

    private void setArgs() {
        centerModel = (CenterModel) CenterSchedulesFragmentArgs.fromBundle(getArguments()).getTypeModel();

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

    private void setSchedules(long timestamp) {
        binding.weekTextView.getRoot().setText(DateManager.currentJalWeekString(timestamp));

        weeksAdapter.setWeek(DateManager.currentJalWeekTimestamps(timestamp));
        binding.weeksRecyclerView.setAdapter(weeksAdapter);
    }

    private void getData(long timestamp) {
        data.put("time", timestamp);

        setSchedules(timestamp);

        Center.schedule(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                List schedules = (List) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        schedulesAdapter.clearSchedules();

                        if (!schedules.data().isEmpty()) {
                            schedulesAdapter.setSchedules(schedules.data(), type);
                            binding.schedulesSingleLayout.recyclerView.setAdapter(schedulesAdapter);

                            binding.schedulesSingleLayout.textView.setVisibility(View.GONE);
                        } else if (schedulesAdapter.getItemCount() == 0) {
                            binding.schedulesSingleLayout.textView.setVisibility(View.VISIBLE);
                        }

                        binding.weeksRecyclerView.setVisibility(View.VISIBLE);
                        binding.weeksShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.weeksShimmerLayout.getRoot().stopShimmer();

                        binding.schedulesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.schedulesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.schedulesShimmerLayout.getRoot().stopShimmer();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        binding.weeksRecyclerView.setVisibility(View.VISIBLE);
                        binding.weeksShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.weeksShimmerLayout.getRoot().stopShimmer();

                        binding.schedulesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.schedulesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.schedulesShimmerLayout.getRoot().stopShimmer();
                    });
                }
            }
        });
    }

    private void doWork(long timestamp) {
        binding.weeksRecyclerView.setVisibility(View.GONE);
        binding.weeksShimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.weeksShimmerLayout.getRoot().startShimmer();

        binding.schedulesSingleLayout.getRoot().setVisibility(View.GONE);
        binding.schedulesShimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.schedulesShimmerLayout.getRoot().startShimmer();

        currentTimestamp = timestamp;

        getData(timestamp);
    }

    public void responseAdapter(long timestamp) {
        schedulesAdapter.setTimestamp(timestamp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}