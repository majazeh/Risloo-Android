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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SchedulesAdapter;
import com.majazeh.risloo.databinding.FragmentRoomSchedulesBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Room;

import java.util.HashMap;

public class RoomSchedulesFragment extends Fragment {

    // Binding
    private FragmentRoomSchedulesBinding binding;

    // Adapters
    private SchedulesAdapter adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;
    private Bundle extras;

    // Vars
    private HashMap data, header;
    public String roomId = "", centerId = "", type = "room";
    private long currentTimestamp = DateManager.currentTimestamp();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomSchedulesBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        getData(currentTimestamp);

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new SchedulesAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        extras = new Bundle();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.txtTextColor(binding.weekTextView.getRoot(), getResources().getString(R.string.AppDefaultWeek), getResources().getColor(R.color.Gray500));

        InitManager.imgResTint(requireActivity(), binding.backwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.Blue600);
        InitManager.imgResTintRotate(requireActivity(), binding.forwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.Blue600, 180);

        InitManager.recyclerView(binding.indexSingleLayout.recyclerView, itemDecoration, layoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.addConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_green300);

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

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createScheduleFragment, extras)).widget(binding.addConstraintLayout);
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                roomId = requireArguments().getString("id");
                extras.putString("id", roomId);
                data.put("id", roomId);
            }

            if (getArguments().getString("center_id") != null && !getArguments().getString("center_id").equals("")) {
                centerId = getArguments().getString("center_id");
                extras.putString("center_id", centerId);
            }

            if (getArguments().getString("type") != null && !getArguments().getString("type").equals("")) {
                type = getArguments().getString("type");
                extras.putString("type", type);
            }
        }
    }

    private void setDate(long timestamp) {
        binding.weekTextView.getRoot().setText(DateManager.currentJalaliWeek(timestamp));
    }

    private void getData(long timestamp) {
        data.put("time", timestamp);

        setDate(timestamp);

        Room.schedule(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                List schedules = (List) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        adapter.clearSchedules();

                        if (!schedules.data().isEmpty()) {
                            adapter.setSchedules(schedules.data(), type);
                            binding.indexSingleLayout.recyclerView.setAdapter(adapter);

                            binding.indexSingleLayout.textView.setVisibility(View.GONE);
                        } else if (adapter.getItemCount() == 0) {
                            binding.indexSingleLayout.textView.setVisibility(View.VISIBLE);
                        }

                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.indexShimmerLayout.getRoot().stopShimmer();
                    });
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
                }
            }
        });
    }

    private void doWork(long timestamp) {
        binding.indexSingleLayout.getRoot().setVisibility(View.GONE);
        binding.indexShimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.indexShimmerLayout.getRoot().startShimmer();

        currentTimestamp = timestamp;

        getData(currentTimestamp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}