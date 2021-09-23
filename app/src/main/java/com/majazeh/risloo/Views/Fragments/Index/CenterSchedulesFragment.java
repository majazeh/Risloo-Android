package com.majazeh.risloo.Views.Fragments.Index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SchedulesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.WeeksAdapter;
import com.majazeh.risloo.databinding.FragmentCenterSchedulesBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Schedules;
import com.mre.ligheh.Model.Madule.Treasury;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CenterSchedulesFragment extends Fragment {

    // Binding
    private FragmentCenterSchedulesBinding binding;

    // Adapters
    private WeeksAdapter weeksAdapter;
    private SchedulesAdapter schedulesAdapter;

    // Models
    private CenterModel centerModel;

    // Objects
    private HashMap data, header;

    // Vars
    private long currentTimestamp = DateManager.currentTimestamp();
    public String filterRoom = "", filterStatus = "";
    public List treasuries = new List();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterSchedulesBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getTreasuries();

        return binding.getRoot();
    }

    private void initializer() {
        weeksAdapter = new WeeksAdapter(requireActivity());
        schedulesAdapter = new SchedulesAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.schedulesHeaderLayout.titleTextView.setText(getResources().getString(R.string.CenterSchedulesFragmentTitle));

        InitManager.txtTextColorBackground(binding.weekTextView.getRoot(), getResources().getString(R.string.AppDefaultWeek), getResources().getColor(R.color.Gray500), R.drawable.draw_16sdp_solid_white_border_1sdp_gray300_ripple_gray300);

        InitManager.imgResTintBackground(requireActivity(), binding.filterImageView.getRoot(), R.drawable.ic_filter_light, R.color.Gray500, R.drawable.draw_oval_solid_gray50_border_1sdp_gray200_ripple_gray300);
        InitManager.imgResTintBackground(requireActivity(), binding.backwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.Gray500, R.drawable.draw_oval_solid_white_border_1sdp_gray300_ripple_gray300);
        InitManager.imgResTintBackgroundRotate(requireActivity(), binding.forwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.Gray500, R.drawable.draw_oval_solid_white_border_1sdp_gray300_ripple_gray300, 180);

        InitManager.fixedHorizontalRecyclerView(requireActivity(), binding.weeksRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.schedulesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.weekTextView.getRoot());

        CustomClickView.onDelayedListener(() -> DialogManager.showScheduleFilterDialog(requireActivity(), "center", new ArrayList<>(), statusList())).widget(binding.filterImageView.getRoot());

        CustomClickView.onDelayedListener(() -> responseDialog("rooms", null)).widget(binding.roomFilterLayout.removeImageView);

        CustomClickView.onDelayedListener(() -> responseDialog("status", null)).widget(binding.statusFilterLayout.removeImageView);

        CustomClickView.onDelayedListener(() -> doWork(DateManager.preJalFridayTimestamp(currentTimestamp))).widget(binding.backwardImageView.getRoot());

        CustomClickView.onDelayedListener(() -> doWork(DateManager.nxtJalSaturdayTimestamp(currentTimestamp))).widget(binding.forwardImageView.getRoot());
    }

    private void setArgs() {
        centerModel = (CenterModel) CenterSchedulesFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(centerModel);
    }

    private void setData(CenterModel model) {
        if (model.getCenterId() != null && !model.getCenterId().equals("")) {
            data.put("id", model.getCenterId());
        }
    }

    private void setSchedules(long timestamp) {
        binding.weekTextView.getRoot().setText(DateManager.currentJalWeekString(timestamp));

        weeksAdapter.setTimestamps(DateManager.currentJalWeekTimestamps(timestamp));
        binding.weeksRecyclerView.setAdapter(weeksAdapter);

        Objects.requireNonNull(binding.weeksRecyclerView.getLayoutManager()).scrollToPosition(DateManager.dayNameTimestampPosition(timestamp));
    }

    private void getTreasuries() {
        Treasury.list(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                treasuries = (List) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        getSchedules(currentTimestamp);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {

                        // Schedules Data
                        binding.schedulesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.schedulesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.schedulesShimmerLayout.getRoot().stopShimmer();

                        // Weeks Data
                        binding.weeksRecyclerView.setVisibility(View.VISIBLE);
                        binding.weeksShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.weeksShimmerLayout.getRoot().stopShimmer();

                    });
                }
            }
        });
    }

    private void getSchedules(long timestamp) {
        data.put("time", timestamp);

        setSchedules(timestamp);

        Schedules.centerList(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                List items = (List) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        schedulesAdapter.clearItems();

                        if (!items.data().isEmpty()) {
                            schedulesAdapter.setItems(items.data(), binding.schedulesHeaderLayout.countTextView, binding.schedulesSingleLayout.emptyView);
                            binding.schedulesSingleLayout.recyclerView.setAdapter(schedulesAdapter);
                        } else if (schedulesAdapter.getItemCount() == 0) {
                            binding.schedulesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.schedulesSingleLayout.emptyView.setText(getResources().getString(R.string.SchedulesAdapterWeekEmpty));
                        }

                        // Schedules Data
                        binding.schedulesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.schedulesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.schedulesShimmerLayout.getRoot().stopShimmer();

                        // Weeks Data
                        binding.weeksRecyclerView.setVisibility(View.VISIBLE);
                        binding.weeksShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.weeksShimmerLayout.getRoot().stopShimmer();

                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {

                        // Schedules Data
                        binding.schedulesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.schedulesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.schedulesShimmerLayout.getRoot().stopShimmer();

                        // Weeks Data
                        binding.weeksRecyclerView.setVisibility(View.VISIBLE);
                        binding.weeksShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.weeksShimmerLayout.getRoot().stopShimmer();

                    });
                }
            }
        });
    }

    private ArrayList<TypeModel> statusList() {
        ArrayList<TypeModel> values = new ArrayList<>();
        String[] statusList = requireActivity().getResources().getStringArray(R.array.ScheduleStatus);

        for (String status : statusList) {
            try {
                TypeModel model = new TypeModel(new JSONObject().put("id", status));

                values.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return values;
    }

    private void doWork(long timestamp) {

        // Schedules Data
        binding.schedulesSingleLayout.getRoot().setVisibility(View.GONE);
        binding.schedulesShimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.schedulesShimmerLayout.getRoot().startShimmer();

        // Weeks Data
        binding.weeksRecyclerView.setVisibility(View.GONE);
        binding.weeksShimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.weeksShimmerLayout.getRoot().startShimmer();

        currentTimestamp = timestamp;
        weeksAdapter.selectedTimestamp = timestamp;
        schedulesAdapter.selectedTimestamp = timestamp;

        getSchedules(timestamp);
    }

    public void responseDialog(String method, TypeModel item) {
        switch (method) {
            case "rooms": {
                if (item != null) {
                    RoomModel model = (RoomModel) item;

                    if (!filterRoom.equals(model.getRoomId())) {
                        filterRoom = model.getRoomId();

                        binding.roomFilterLayout.titleTextView.setText(filterRoom);
                        binding.roomFilterLayout.getRoot().setVisibility(View.VISIBLE);
                    } else if (filterRoom.equals(model.getRoomId())) {
                        filterRoom = "";

                        binding.roomFilterLayout.titleTextView.setText("");
                        binding.roomFilterLayout.getRoot().setVisibility(View.GONE);
                    }
                } else {
                    filterRoom = "";

                    binding.roomFilterLayout.titleTextView.setText("");
                    binding.roomFilterLayout.getRoot().setVisibility(View.GONE);
                }

                data.put("room", filterRoom);
            } break;
            case "status": {
                try {
                    if (item != null) {
                        if (!filterStatus.equals(item.object.get("id").toString())) {
                            filterStatus = item.object.get("id").toString();

                            binding.statusFilterLayout.titleTextView.setText(filterStatus);
                            binding.statusFilterLayout.getRoot().setVisibility(View.VISIBLE);
                        } else if (filterStatus.equals(item.object.get("id").toString())) {
                            filterStatus = "";

                            binding.statusFilterLayout.titleTextView.setText("");
                            binding.statusFilterLayout.getRoot().setVisibility(View.GONE);
                        }
                    } else {
                        filterStatus = "";

                        binding.statusFilterLayout.titleTextView.setText("");
                        binding.statusFilterLayout.getRoot().setVisibility(View.GONE);
                    }

                    data.put("status", SelectionManager.getSessionStatus2(requireActivity(), "en", filterStatus));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } break;
            case "reset": {
                filterRoom = "";
                filterStatus = "";

                binding.roomFilterLayout.titleTextView.setText("");
                binding.roomFilterLayout.getRoot().setVisibility(View.GONE);

                binding.statusFilterLayout.titleTextView.setText("");
                binding.statusFilterLayout.getRoot().setVisibility(View.GONE);

                data.put("room", filterRoom);
                data.put("status", filterStatus);
            } break;
        }

        if (!filterRoom.equals("") || !filterStatus.equals("")) {
            InitManager.imgResTintBackground(requireActivity(), binding.filterImageView.getRoot(), R.drawable.ic_filter_light, R.color.Blue600, R.drawable.draw_oval_solid_gray50_border_1sdp_blue600_ripple_blue300);
            binding.filterHorizontalScrollView.setVisibility(View.VISIBLE);
        } else {
            InitManager.imgResTintBackground(requireActivity(), binding.filterImageView.getRoot(), R.drawable.ic_filter_light, R.color.Gray500, R.drawable.draw_oval_solid_gray50_border_1sdp_gray200_ripple_gray300);
            binding.filterHorizontalScrollView.setVisibility(View.GONE);
        }

        DialogManager.dismissScheduleFilterDialog();

        // Schedules Data
        binding.schedulesSingleLayout.getRoot().setVisibility(View.GONE);
        binding.schedulesShimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.schedulesShimmerLayout.getRoot().startShimmer();

        // Weeks Data
        binding.weeksRecyclerView.setVisibility(View.GONE);
        binding.weeksShimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.weeksShimmerLayout.getRoot().startShimmer();

        getSchedules(currentTimestamp);
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