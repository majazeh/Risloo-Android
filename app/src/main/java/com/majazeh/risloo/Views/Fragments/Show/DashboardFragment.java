package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.databinding.FragmentDashboardBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DashboardFragment extends Fragment {

    // Binding
    private FragmentDashboardBinding binding;

    // Adapters
    private RoomsAdapter roomsAdapter;

    // Models
    private UserModel userModel;

    // Objects
    private HashMap data, header;

    // Vars
    private ArrayList<String> schedulesTodayUrls, schedulesTomorrowUrls;
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        roomsAdapter = new RoomsAdapter(requireActivity());

        data = new HashMap<>();
        data.put("user", ((MainActivity) requireActivity()).singleton.getId());
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

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.passwordMissingLayout.actionTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);
            binding.centerMissingLayout.actionTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);

            binding.schedulesTodayLayout.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
            binding.schedulesTomorrowLayout.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
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
            // TODO : Place Code If Needed
        }).widget(binding.schedulesTodayLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            // TODO : Place Code If Needed
        }).widget(binding.schedulesTomorrowLayout.getRoot());

        binding.schedulesTodayLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.schedulesTodayLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    IntentManager.download(requireContext(), schedulesTodayUrls.get(position));

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.schedulesTomorrowLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.schedulesTomorrowLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    IntentManager.download(requireContext(), schedulesTomorrowUrls.get(position));

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(UserModel model) {
        try {
            ((MainActivity) requireActivity()).singleton.update(model);

            // Missings Data
            if (model.isNo_password())
                binding.passwordMissingLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.passwordMissingLayout.getRoot().setVisibility(View.GONE);

            // TODO : Place Center Code Here

            // Schedules Data
            if (model.getDalilyScheduleExports() != null && model.getDalilyScheduleExports().has("today") && !model.getDalilyScheduleExports().isNull("today") && model.getDalilyScheduleExports().getJSONArray("today").length() != 0) {
                binding.schedulesTodayLayout.getRoot().setVisibility(View.VISIBLE);
                binding.schedulesTodayLayout.selectGroup.setVisibility(View.VISIBLE);

                InitManager.txtTextColor(binding.schedulesTodayLayout.titleTextView, getResources().getString(R.string.DashboardFragmentHasSchedulesTodayTitle), getResources().getColor(R.color.Gray500));
                InitManager.imgResTint(requireActivity(), binding.schedulesTodayLayout.avatarImageView, R.drawable.ic_calendar_day_light, R.color.Gray500);

                setDropdowns("today", model.getDalilyScheduleExports().getJSONObject("today"));
            } else {
                binding.schedulesTodayLayout.getRoot().setVisibility(View.VISIBLE);
                binding.schedulesTodayLayout.selectGroup.setVisibility(View.GONE);

                InitManager.txtTextColor(binding.schedulesTodayLayout.titleTextView, getResources().getString(R.string.DashboardFragmentNoSchedulesTodayTitle), getResources().getColor(R.color.Gray400));
                InitManager.imgResTint(requireActivity(), binding.schedulesTodayLayout.avatarImageView, R.drawable.ic_calendar_day_light, R.color.Gray400);
            }

            if (model.getDalilyScheduleExports() != null && model.getDalilyScheduleExports().has("tomorrow") && !model.getDalilyScheduleExports().isNull("tomorrow") && model.getDalilyScheduleExports().getJSONArray("tomorrow").length() != 0) {
                binding.schedulesTomorrowLayout.getRoot().setVisibility(View.VISIBLE);
                binding.schedulesTomorrowLayout.selectGroup.setVisibility(View.VISIBLE);

                InitManager.txtTextColor(binding.schedulesTomorrowLayout.titleTextView, getResources().getString(R.string.DashboardFragmentHasSchedulesTomorrowTitle), getResources().getColor(R.color.Gray500));
                InitManager.imgResTint(requireActivity(), binding.schedulesTomorrowLayout.avatarImageView, R.drawable.ic_calendar_alt_light, R.color.Gray500);

                setDropdowns("tomorrow", model.getDalilyScheduleExports().getJSONObject("tomorrow"));
            } else {
                binding.schedulesTomorrowLayout.getRoot().setVisibility(View.VISIBLE);
                binding.schedulesTomorrowLayout.selectGroup.setVisibility(View.GONE);

                InitManager.txtTextColor(binding.schedulesTomorrowLayout.titleTextView, getResources().getString(R.string.DashboardFragmentNoSchedulesTomorrowTitle), getResources().getColor(R.color.Gray400));
                InitManager.imgResTint(requireActivity(), binding.schedulesTomorrowLayout.avatarImageView, R.drawable.ic_calendar_alt_light, R.color.Gray400);
            }

            // Rooms Data
            if (!model.getRoomList().data().isEmpty()) {
                roomsAdapter.setItems(model.getRoomList().data());
                binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);

                binding.roomsGroup.setVisibility(View.VISIBLE);
            } else if (roomsAdapter.getItemCount() == 0) {
                binding.roomsGroup.setVisibility(View.GONE);
            }

            // Header Visibility
            if (binding.schedulesTodayLayout.getRoot().getVisibility() == View.VISIBLE || binding.schedulesTomorrowLayout.getRoot().getVisibility() == View.VISIBLE)
                binding.schedulesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
            else
                binding.schedulesHeaderLayout.getRoot().setVisibility(View.GONE);

            // Space Visibility
            if (binding.passwordMissingLayout.getRoot().getVisibility() == View.VISIBLE || binding.centerMissingLayout.getRoot().getVisibility() == View.VISIBLE)
                binding.missingSpace.setVisibility(View.VISIBLE);
            else
                binding.missingSpace.setVisibility(View.GONE);

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
                ArrayList<String> options = new ArrayList<>();
                schedulesTodayUrls = new ArrayList<>();

                if (!data.getString("svg").equals("")) {
                    options.add("SVG");
                    schedulesTodayUrls.add(data.getString("svg"));
                }

                if (!data.getString("png").equals("")) {
                    options.add("PNG");
                    schedulesTodayUrls.add(data.getString("png"));
                }

                options.add("");
                schedulesTodayUrls.add("");

                InitManager.profileCustomSpinner(requireActivity(), binding.schedulesTodayLayout.selectSpinner, options);
            } else {
                ArrayList<String> options = new ArrayList<>();
                schedulesTomorrowUrls = new ArrayList<>();

                if (!data.getString("svg").equals("")) {
                    options.add("SVG");
                    schedulesTomorrowUrls.add(data.getString("svg"));
                }

                if (!data.getString("png").equals("")) {
                    options.add("PNG");
                    schedulesTomorrowUrls.add(data.getString("png"));
                }

                options.add("");
                schedulesTomorrowUrls.add("");

                InitManager.profileCustomSpinner(requireActivity(), binding.schedulesTomorrowLayout.selectSpinner, options);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        User.dashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                userModel = (UserModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        setData(userModel);

                        binding.loadingIncludeLayout.getRoot().setVisibility(View.GONE);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        // TODO : Place Code If Needed
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