package com.majazeh.risloo.Views.Fragments.Index;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SchedulesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.WeeksAdapter;
import com.majazeh.risloo.databinding.FragmentCenterSchedulesBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;

import java.util.HashMap;

public class CenterSchedulesFragment extends Fragment {

    // Binding
    private FragmentCenterSchedulesBinding binding;

    // Adapters
    private WeeksAdapter weeksAdapter;
    private SchedulesAdapter schedulesAdapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration, itemDecoration2;
    private LinearLayoutManager weeksLayoutManager, schedulesLayoutManager;
    private Handler handler;
    private Bundle extras;

    // Vars
    private HashMap data, header;
    public String centerId = "", type = "personal_clinic";
    private long currentTimestamp = DateManager.currentTimestamp();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterSchedulesBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        getData(currentTimestamp);

        return binding.getRoot();
    }

    private void initializer() {
        weeksAdapter = new WeeksAdapter(requireActivity());
        schedulesAdapter = new SchedulesAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("horizontalLayout", 0, 0, (int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._12sdp));
        itemDecoration2 = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));

        weeksLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        schedulesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        handler = new Handler();

        extras = new Bundle();

        data = new HashMap<>();
        data.put("id", centerId);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.txtTextColor(binding.weekTextView.getRoot(), getResources().getString(R.string.AppDefaultWeek), getResources().getColor(R.color.Gray500));

        InitManager.imgResTint(requireActivity(), binding.backwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.Blue600);
        InitManager.imgResTintRotate(requireActivity(), binding.forwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.Blue600, 180);

        InitManager.recyclerView(binding.weeksRecyclerView, itemDecoration, weeksLayoutManager);
        InitManager.recyclerView(binding.schedulesSingleLayout.recyclerView, itemDecoration2, schedulesLayoutManager);
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

        ClickManager.onDelayedClickListener(() -> doWork(DateManager.previeosJalaliFridayTimestamp(currentTimestamp))).widget(binding.backwardImageView.getRoot());

        ClickManager.onDelayedClickListener(() -> doWork(DateManager.nextJalaliSaturdayTimestamp(currentTimestamp))).widget(binding.forwardImageView.getRoot());
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                centerId = requireArguments().getString("id");
                extras.putString("id", centerId);
                data.put("id", centerId);
            }

            if (getArguments().getString("type") != null && !getArguments().getString("type").equals("")) {
                type = getArguments().getString("type");
                extras.putString("type", type);
            }
        }
    }

    private void setDate(long timestamp) {
        binding.weekTextView.getRoot().setText(DateManager.currentJalaliWeek(timestamp));

        weeksAdapter.setWeek(DateManager.currentJalaliWeekTimestamps(timestamp));
        binding.weeksRecyclerView.setAdapter(weeksAdapter);

        handler.postDelayed(() -> {
            binding.weeksRecyclerView.setVisibility(View.VISIBLE);
            binding.weeksShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.weeksShimmerLayout.getRoot().stopShimmer();
        }, 1000);
    }

    private void getData(long timestamp) {
        data.put("time", timestamp);

        setDate(timestamp);

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
        handler.removeCallbacksAndMessages(null);
    }

}