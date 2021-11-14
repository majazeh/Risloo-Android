package com.majazeh.risloo.Views.Fragments.Main.Show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexRoomAdapter;
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

public class DashboardFragment extends Fragment {

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
        data.put("user", ((MainActivity) requireActivity()).singleton.getUserModel().getId());
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.passwordMissingLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentNoPasswordTitle));
        binding.centerMissingLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentNoCenterTitle));

        binding.passwordMissingLayout.actionTextView.setText(getResources().getString(R.string.DashboardFragmentNoPasswordAction));
        binding.centerMissingLayout.actionTextView.setText(getResources().getString(R.string.DashboardFragmentNoCenterAction));

        binding.schedulesHeaderLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentSchedulesHeader));
        binding.roomsHeaderLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentRoomsHeader));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.roomsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalEditUserFragment(userModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.passwordMissingLayout.actionTextView);

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCentersFragment();
            ((MainActivity) requireActivity()).navController.navigate(action);
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
            if (!model.isNo_password()) {
                binding.passwordMissingLayout.getRoot().setVisibility(View.GONE);
            } else {
                binding.passwordMissingLayout.getRoot().setVisibility(View.VISIBLE);
            }

            //  Center Missings Data
            if (model.getCenterList() != null && !model.getCenterList().data().isEmpty()) {
                for (TypeModel typeModel : model.getCenterList().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null && centerModel.getAcceptation().getAccepted_at() != 0) {
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
                InitManager.layoutTextColorResTintBackground(requireActivity(), binding.schedulesTodayLayout.getRoot(), binding.schedulesTodayLayout.titleTextView, binding.schedulesTodayLayout.avatarImageView, getResources().getString(R.string.DashboardFragmentHasSchedulesTodayTitle), getResources().getColor(R.color.Risloo500), R.drawable.ic_calendar_day_light, R.color.Risloo500, R.drawable.draw_2sdp_solid_white_border_1sdp_risloo500_ripple_risloo50);

                setDropdowns("today", model.getDalilyScheduleExports().getJSONObject("today"));
            } else {
                binding.schedulesTodayLayout.getRoot().setVisibility(View.VISIBLE);
                InitManager.layoutTextColorResTintBackground(requireActivity(), binding.schedulesTodayLayout.getRoot(), binding.schedulesTodayLayout.titleTextView, binding.schedulesTodayLayout.avatarImageView, getResources().getString(R.string.DashboardFragmentNoSchedulesTodayTitle), getResources().getColor(R.color.CoolGray400), R.drawable.ic_calendar_day_light, R.color.CoolGray400, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);
            }

            //  Tomorrow Schedules Data
            if (model.getDalilyScheduleExports() != null && model.getDalilyScheduleExports().has("tomorrow") && !model.getDalilyScheduleExports().isNull("tomorrow") && model.getDalilyScheduleExports().getJSONObject("tomorrow").length() != 0) {
                binding.schedulesTomorrowLayout.getRoot().setVisibility(View.VISIBLE);
                InitManager.layoutTextColorResTintBackground(requireActivity(), binding.schedulesTomorrowLayout.getRoot(), binding.schedulesTomorrowLayout.titleTextView, binding.schedulesTomorrowLayout.avatarImageView, getResources().getString(R.string.DashboardFragmentHasSchedulesTomorrowTitle), getResources().getColor(R.color.Risloo500), R.drawable.ic_calendar_alt_light, R.color.Risloo500, R.drawable.draw_2sdp_solid_white_border_1sdp_risloo500_ripple_risloo50);

                setDropdowns("tomorrow", model.getDalilyScheduleExports().getJSONObject("tomorrow"));
            } else {
                binding.schedulesTomorrowLayout.getRoot().setVisibility(View.VISIBLE);
                InitManager.layoutTextColorResTintBackground(requireActivity(), binding.schedulesTomorrowLayout.getRoot(), binding.schedulesTomorrowLayout.titleTextView, binding.schedulesTomorrowLayout.avatarImageView, getResources().getString(R.string.DashboardFragmentNoSchedulesTomorrowTitle), getResources().getColor(R.color.CoolGray400), R.drawable.ic_calendar_alt_light, R.color.CoolGray400, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);
            }

            //  Rooms Data
            if (model.getRoomList() != null && !model.getRoomList().data().isEmpty()) {
                ArrayList<TypeModel> items = new ArrayList<>();

                for (TypeModel typeModel : model.getRoomList().data()) {
                    RoomModel roomModel = (RoomModel) typeModel;

                    if (roomModel != null && roomModel.getRoomAcceptation() != null && roomModel.getRoomAcceptation().getPosition().equals("manager"))
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

    private void setDropdowns(String day, JSONObject data) {
        try {
            if (day.equals("today")) {
                schedulesTodayUrls = new ArrayList<>();

                if (!data.getString("svg").equals(""))
                    schedulesTodayUrls.add(data.getString("svg"));

                if (!data.getString("png").equals(""))
                    schedulesTodayUrls.add(data.getString("png"));

            } else {
                schedulesTomorrowUrls = new ArrayList<>();

                if (!data.getString("svg").equals(""))
                    schedulesTomorrowUrls.add(data.getString("svg"));

                if (!data.getString("png").equals(""))
                    schedulesTomorrowUrls.add(data.getString("png"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setPermission() {
        if (((MainActivity) requireActivity()).permissoon.showDashboardData(userModel))
            setData(userModel);
    }

    private void getData() {
        User.dashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                userModel = (UserModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).singleton.update(userModel);
                        ((MainActivity) requireActivity()).setData();

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