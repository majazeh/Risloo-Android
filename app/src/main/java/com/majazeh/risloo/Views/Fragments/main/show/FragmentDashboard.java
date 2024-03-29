package com.majazeh.risloo.views.fragments.main.show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Index.IndexRoomAdapter;
import com.majazeh.risloo.databinding.FragmentDashboardBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentDashboard extends Fragment {

    // Binding
    private FragmentDashboardBinding binding;

    // Adapters
    private IndexRoomAdapter indexRoomAdapter;

    // Models
    private UserModel userModel;

    // Objects
    private HashMap data, header;

    // Vars
    private ArrayList<String> schedulesTodayUrls, schedulesTomorrowUrls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        indexRoomAdapter = new IndexRoomAdapter(requireActivity());

        data = new HashMap<>();
        data.put("user", ((ActivityMain) requireActivity()).singleton.getUserModel().getId());
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.passwordMissingLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentNoPasswordTitle));
        binding.centerMissingLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentNoCenterTitle));

        binding.passwordMissingLayout.actionTextView.setText(getResources().getString(R.string.DashboardFragmentNoPasswordAction));
        binding.centerMissingLayout.actionTextView.setText(getResources().getString(R.string.DashboardFragmentNoCenterAction));

        binding.schedulesHeaderLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentSchedulesHeader));
        binding.roomsHeaderLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentRoomsHeader));

        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.roomsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onClickListener(() -> {
            ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentEditUser(userModel);
        }).widget(binding.passwordMissingLayout.actionTextView);

        CustomClickView.onClickListener(() -> {
            ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentCenters();
        }).widget(binding.centerMissingLayout.actionTextView);

        CustomClickView.onClickListener(() -> {
            if (schedulesTodayUrls != null && !schedulesTodayUrls.isEmpty())
                for (String url : schedulesTodayUrls)
                    if (url.contains(".png")) {
                        IntentManager.display(requireActivity(), binding.schedulesTodayLayout.titleTextView.getText().toString(), url);
                        return;
                    }
        }).widget(binding.schedulesTodayLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            if (schedulesTomorrowUrls != null && !schedulesTomorrowUrls.isEmpty())
                for (String url : schedulesTomorrowUrls)
                    if (url.contains(".png")) {
                        IntentManager.display(requireActivity(), binding.schedulesTomorrowLayout.titleTextView.getText().toString(), url);
                        return;
                    }
        }).widget(binding.schedulesTomorrowLayout.getRoot());
    }

    private void setData(UserModel model) {
        try {

            //  Password Missings Data
            if (!model.isNoPassword()) {
                binding.passwordMissingLayout.getRoot().setVisibility(View.GONE);
            } else {
                binding.passwordMissingLayout.getRoot().setVisibility(View.VISIBLE);
            }

            //  Center Missings Data
            if (model.getCenters() != null && !model.getCenters().data().isEmpty()) {
                for (TypeModel typeModel : model.getCenters().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null && centerModel.getAcceptation().getAcceptedAt() != 0) {
                        binding.centerMissingLayout.getRoot().setVisibility(View.GONE);
                        break;
                    }
                }
            } else {
                binding.centerMissingLayout.getRoot().setVisibility(View.VISIBLE);
            }

            //  Today Schedules Data
            if (model.getDalilyScheduleExports() != null && model.getDalilyScheduleExports().has("today") && !model.getDalilyScheduleExports().isNull("today") && model.getDalilyScheduleExports().getJSONObject("today").length() != 0) {
                binding.schedulesTodayLayout.getRoot().setVisibility(View.VISIBLE);
                InitManager.lytTextColorResTintBackground(requireActivity(), binding.schedulesTodayLayout.getRoot(), binding.schedulesTodayLayout.titleTextView, binding.schedulesTodayLayout.avatarImageView, getResources().getString(R.string.DashboardFragmentHasSchedulesTodayTitle), getResources().getColor(R.color.risloo500), R.drawable.ic_calendar_day_light, R.color.risloo500, R.drawable.draw_2sdp_solid_white_border_1sdp_risloo500_ripple_risloo50);

                setDropdowns("today", model.getDalilyScheduleExports().getJSONObject("today"));
            } else {
                binding.schedulesTodayLayout.getRoot().setVisibility(View.VISIBLE);
                InitManager.lytTextColorResTintBackground(requireActivity(), binding.schedulesTodayLayout.getRoot(), binding.schedulesTodayLayout.titleTextView, binding.schedulesTodayLayout.avatarImageView, getResources().getString(R.string.DashboardFragmentNoSchedulesTodayTitle), getResources().getColor(R.color.coolGray400), R.drawable.ic_calendar_day_light, R.color.coolGray400, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);
            }

            //  Tomorrow Schedules Data
            if (model.getDalilyScheduleExports() != null && model.getDalilyScheduleExports().has("tomorrow") && !model.getDalilyScheduleExports().isNull("tomorrow") && model.getDalilyScheduleExports().getJSONObject("tomorrow").length() != 0) {
                binding.schedulesTomorrowLayout.getRoot().setVisibility(View.VISIBLE);
                InitManager.lytTextColorResTintBackground(requireActivity(), binding.schedulesTomorrowLayout.getRoot(), binding.schedulesTomorrowLayout.titleTextView, binding.schedulesTomorrowLayout.avatarImageView, getResources().getString(R.string.DashboardFragmentHasSchedulesTomorrowTitle), getResources().getColor(R.color.risloo500), R.drawable.ic_calendar_alt_light, R.color.risloo500, R.drawable.draw_2sdp_solid_white_border_1sdp_risloo500_ripple_risloo50);

                setDropdowns("tomorrow", model.getDalilyScheduleExports().getJSONObject("tomorrow"));
            } else {
                binding.schedulesTomorrowLayout.getRoot().setVisibility(View.VISIBLE);
                InitManager.lytTextColorResTintBackground(requireActivity(), binding.schedulesTomorrowLayout.getRoot(), binding.schedulesTomorrowLayout.titleTextView, binding.schedulesTomorrowLayout.avatarImageView, getResources().getString(R.string.DashboardFragmentNoSchedulesTomorrowTitle), getResources().getColor(R.color.coolGray400), R.drawable.ic_calendar_alt_light, R.color.coolGray400, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);
            }

            //  Rooms Data
            if (model.getRooms() != null && !model.getRooms().data().isEmpty()) {
                ArrayList<TypeModel> items = new ArrayList<>();

                for (TypeModel typeModel : model.getRooms().data()) {
                    RoomModel roomModel = (RoomModel) typeModel;

                    if (roomModel != null && roomModel.getAcceptation() != null && roomModel.getAcceptation().getPosition().equals("manager"))
                        items.add(roomModel);
                }

                indexRoomAdapter.setItems(items);
                binding.roomsSingleLayout.recyclerView.setAdapter(indexRoomAdapter);

                if (indexRoomAdapter.getItemCount() == 0)
                    binding.roomsGroup.setVisibility(View.GONE);
                else
                    binding.roomsGroup.setVisibility(View.VISIBLE);

            } else if (indexRoomAdapter.getItemCount() == 0) {
                binding.roomsGroup.setVisibility(View.GONE);
            }

            //  Schedules Header Visibility
            if (binding.schedulesTodayLayout.getRoot().getVisibility() == View.VISIBLE || binding.schedulesTomorrowLayout.getRoot().getVisibility() == View.VISIBLE)
                binding.schedulesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.schedulesHeaderLayout.getRoot().setVisibility(View.GONE);

            //  Missing Space Visibility
            if (binding.passwordMissingLayout.getRoot().getVisibility() == View.VISIBLE || binding.centerMissingLayout.getRoot().getVisibility() == View.VISIBLE)
                binding.missingSpace.setVisibility(View.VISIBLE);
            else
                binding.missingSpace.setVisibility(View.GONE);

            //  Schedules Space Visibility
            if (binding.passwordMissingLayout.getRoot().getVisibility() == View.VISIBLE || binding.centerMissingLayout.getRoot().getVisibility() == View.VISIBLE || binding.schedulesHeaderLayout.getRoot().getVisibility() == View.VISIBLE || binding.schedulesTodayLayout.getRoot().getVisibility() == View.VISIBLE || binding.schedulesTomorrowLayout.getRoot().getVisibility() == View.VISIBLE)
                binding.schedulesSpace.setVisibility(View.VISIBLE);
            else
                binding.schedulesSpace.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDropdowns(String day, JSONObject object) {
        try {
            if (day.equals("today")) {
                schedulesTodayUrls = new ArrayList<>();

                if (!object.getString("svg").equals(""))
                    schedulesTodayUrls.add(object.getString("svg"));

                if (!object.getString("png").equals(""))
                    schedulesTodayUrls.add(object.getString("png"));

            } else {
                schedulesTomorrowUrls = new ArrayList<>();

                if (!object.getString("svg").equals(""))
                    schedulesTomorrowUrls.add(object.getString("svg"));

                if (!object.getString("png").equals(""))
                    schedulesTomorrowUrls.add(object.getString("png"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setPermission() {
        if (((ActivityMain) requireActivity()).permissoon.showDashboardData(userModel))
            setData(userModel);
    }

    private void getData() {
        User.dashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                userModel = (UserModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((ActivityMain) requireActivity()).singleton.update(userModel);
                        ((ActivityMain) requireActivity()).setData();

                        setPermission();

                        binding.loadingIncludeLayout.getRoot().setVisibility(View.GONE);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        binding.loadingIncludeLayout.getRoot().setVisibility(View.GONE);
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