package com.majazeh.risloo.views.fragments.main.index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.interfaces.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Index.IndexScheduleAdapter;
import com.majazeh.risloo.views.adapters.recycler.main.Index.IndexDayAdapter;
import com.majazeh.risloo.databinding.FragmentCenterSchedulesBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Schedules;
import com.mre.ligheh.Model.Madule.Treasury;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class FragmentCenterSchedules extends Fragment {

    // Binding
    private FragmentCenterSchedulesBinding binding;

    // Adapters
    private IndexScheduleAdapter indexScheduleAdapter;
    private IndexDayAdapter indexDayAdapter;

    // Models
    private CenterModel centerModel;

    // Objects
    private HashMap data, header;

    // Vars
    private long currentTimestamp = DateManager.currentTimestamp();
    public List treasuries = new List(), filterableRooms = new List(), filterableStatus = new List();
    public String filterRoom = "", filterStatus = "";

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
        indexScheduleAdapter = new IndexScheduleAdapter(requireActivity());
        indexDayAdapter = new IndexDayAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.CenterSchedulesFragmentTitle));

        InitManager.imgResTintBackground(requireActivity(), binding.filterImageView.getRoot(), R.drawable.ic_filter_light, R.color.coolGray500, R.drawable.draw_oval_solid_coolgray50_border_1sdp_coolgray200_ripple_coolgray300);
        InitManager.imgResTintBackground(requireActivity(), binding.backwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300);
        InitManager.imgResTintBackgroundRotate(requireActivity(), binding.forwardImageView.getRoot(), R.drawable.ic_angle_right_regular, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, 180);

        InitManager.txtTextColorBackground(binding.weekTextView.getRoot(), getResources().getString(R.string.AppDefaultWeek), getResources().getColor(R.color.coolGray500), R.drawable.draw_24sdp_solid_white_border_1sdp_coolgray300_ripple_coolgray300);

        InitManager.rcvHorizontalFixed(requireActivity(), binding.daysRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.schedulesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> DialogManager.showDialogScheduleFilter(requireActivity(), "center", filterableRooms.data(), filterableStatus.data())).widget(binding.filterImageView.getRoot());

        CustomClickView.onDelayedListener(() -> responseDialog("rooms", null)).widget(binding.filterIncludeLayout.roomFilterLayout.removeImageView);

        CustomClickView.onDelayedListener(() -> responseDialog("status", null)).widget(binding.filterIncludeLayout.statusFilterLayout.removeImageView);

        CustomClickView.onDelayedListener(() -> changeTimestamp(DateManager.timestampPreFriday(currentTimestamp))).widget(binding.backwardImageView.getRoot());

        CustomClickView.onDelayedListener(() -> changeTimestamp(DateManager.timestampNxtSaturday(currentTimestamp))).widget(binding.forwardImageView.getRoot());

        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.weekTextView.getRoot());
    }

    private void setArgs() {
        centerModel = (CenterModel) FragmentCenterSchedulesArgs.fromBundle(getArguments()).getTypeModel();
        setData(centerModel);
    }

    private void setData(CenterModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }
    }

    private void setWeek(long timestamp) {
        binding.weekTextView.getRoot().setText(DateManager.jalWeekString(timestamp));

        indexDayAdapter.setTimestamps(DateManager.jalWeekTimestamps(timestamp));
        binding.daysRecyclerView.setAdapter(indexDayAdapter);

        Objects.requireNonNull(binding.daysRecyclerView.getLayoutManager()).scrollToPosition(DateManager.positionTimestampDayName(timestamp));
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
                        hideShimmer();
                    });
                }
            }
        });
    }

    private void getSchedules(long timestamp) {
        data.put("time", timestamp);

        setWeek(timestamp);

        Schedules.centerList(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                List items = (List) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            indexScheduleAdapter.clearItems();

                            filterableRooms = new List();
                            for (int i = 0; i < ((JSONArray) items.filterAllowed("room")).length(); i++)
                                filterableRooms.add(new RoomModel(((JSONArray) items.filterAllowed("room")).getJSONObject(i)));

                            filterableStatus = new List();
                            for (int i = 0; i < ((JSONArray) items.filterAllowed("status")).length(); i++)
                                filterableStatus.add(new TypeModel(new JSONObject().put("id", ((JSONArray) items.filterAllowed("status")).getString(i))));

                            if (!items.data().isEmpty()) {
                                indexScheduleAdapter.setItems(items.data(), binding.headerIncludeLayout.countTextView, binding.schedulesSingleLayout.emptyView);
                                binding.schedulesSingleLayout.recyclerView.setAdapter(indexScheduleAdapter);
                            } else if (indexScheduleAdapter.getItemCount() == 0) {
                                binding.schedulesSingleLayout.recyclerView.setAdapter(null);

                                binding.schedulesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                binding.schedulesSingleLayout.emptyView.setText(getResources().getString(R.string.ScheduleAdapterWeekEmpty));
                            }

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

    private void changeTimestamp(long timestamp) {
        currentTimestamp = timestamp;
        indexDayAdapter.selectedTimestamp = timestamp;
        indexScheduleAdapter.selectedTimestamp = timestamp;

        showShimmer();

        getSchedules(timestamp);
    }

    public void responseDialog(String method, TypeModel item) {
        switch (method) {
            case "rooms": {
                if (item != null) {
                    RoomModel model = (RoomModel) item;

                    if (!filterRoom.equals(model.getId())) {
                        filterRoom = model.getId();

                        if (model.getManager() != null && !model.getManager().getName().equals(""))
                            binding.filterIncludeLayout.roomFilterLayout.titleTextView.setText(model.getManager().getName());
                        else
                            binding.filterIncludeLayout.roomFilterLayout.titleTextView.setText(requireActivity().getResources().getString(R.string.DialogScheduleFilterHint) + " " + filterRoom);

                        binding.filterIncludeLayout.roomFilterLayout.getRoot().setVisibility(View.VISIBLE);
                    } else if (filterRoom.equals(model.getId())) {
                        filterRoom = "";

                        binding.filterIncludeLayout.roomFilterLayout.titleTextView.setText("");
                        binding.filterIncludeLayout.roomFilterLayout.getRoot().setVisibility(View.GONE);
                    }
                } else {
                    filterRoom = "";

                    binding.filterIncludeLayout.roomFilterLayout.titleTextView.setText("");
                    binding.filterIncludeLayout.roomFilterLayout.getRoot().setVisibility(View.GONE);
                }

                data.put("room", filterRoom);
            } break;
            case "status": {
                try {
                    if (item != null) {
                        if (!filterStatus.equals(item.object.get("id").toString())) {
                            filterStatus = item.object.get("id").toString();

                            binding.filterIncludeLayout.statusFilterLayout.titleTextView.setText(JsonManager.getSessionStatus2(requireActivity(), "fa", filterStatus));
                            binding.filterIncludeLayout.statusFilterLayout.getRoot().setVisibility(View.VISIBLE);
                        } else if (filterStatus.equals(item.object.get("id").toString())) {
                            filterStatus = "";

                            binding.filterIncludeLayout.statusFilterLayout.titleTextView.setText("");
                            binding.filterIncludeLayout.statusFilterLayout.getRoot().setVisibility(View.GONE);
                        }
                    } else {
                        filterStatus = "";

                        binding.filterIncludeLayout.statusFilterLayout.titleTextView.setText("");
                        binding.filterIncludeLayout.statusFilterLayout.getRoot().setVisibility(View.GONE);
                    }

                    data.put("status", filterStatus);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } break;
            case "reset": {
                filterRoom = "";
                filterStatus = "";

                binding.filterIncludeLayout.roomFilterLayout.titleTextView.setText("");
                binding.filterIncludeLayout.statusFilterLayout.titleTextView.setText("");

                binding.filterIncludeLayout.roomFilterLayout.getRoot().setVisibility(View.GONE);
                binding.filterIncludeLayout.statusFilterLayout.getRoot().setVisibility(View.GONE);

                data.put("room", filterRoom);
                data.put("status", filterStatus);
            } break;
        }

        if (!filterRoom.equals("") || !filterStatus.equals("")) {
            InitManager.imgResTintBackground(requireActivity(), binding.filterImageView.getRoot(), R.drawable.ic_filter_light, R.color.risloo500, R.drawable.draw_oval_solid_coolgray50_border_1sdp_risloo500_ripple_risloo50);
            binding.filterIncludeLayout.getRoot().setVisibility(View.VISIBLE);
        } else {
            InitManager.imgResTintBackground(requireActivity(), binding.filterImageView.getRoot(), R.drawable.ic_filter_light, R.color.coolGray500, R.drawable.draw_oval_solid_coolgray50_border_1sdp_coolgray200_ripple_coolgray300);
            binding.filterIncludeLayout.getRoot().setVisibility(View.GONE);
        }

        DialogManager.dismissDialogScheduleFilter();

        showShimmer();

        getSchedules(currentTimestamp);
    }

    public void responseAdapter(long timestamp) {
        indexScheduleAdapter.setTimestamp(timestamp);
    }

    private void showShimmer() {

        // Days Data
        binding.daysRecyclerView.setVisibility(View.GONE);
        binding.daysShimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.daysShimmerLayout.getRoot().startShimmer();

        // Schedules Data
        binding.schedulesSingleLayout.getRoot().setVisibility(View.GONE);
        binding.schedulesShimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.schedulesShimmerLayout.getRoot().startShimmer();

    }

    private void hideShimmer() {

        // Days Data
        binding.daysRecyclerView.setVisibility(View.VISIBLE);
        binding.daysShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.daysShimmerLayout.getRoot().stopShimmer();

        // Schedules Data
        binding.schedulesSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.schedulesShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.schedulesShimmerLayout.getRoot().stopShimmer();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}