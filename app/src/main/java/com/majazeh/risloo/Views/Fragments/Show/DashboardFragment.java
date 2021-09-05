package com.majazeh.risloo.Views.Fragments.Show;

import android.os.Build;
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
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.databinding.FragmentDashboardBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.UserModel;

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

        binding.roomsHeaderLayout.titleTextView.setText(getResources().getString(R.string.DashboardFragmentRoomsHeader));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.roomsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.passwordMissingLayout.actionTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);
            binding.centerMissingLayout.actionTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);
        } else {
            binding.passwordMissingLayout.actionTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
            binding.centerMissingLayout.actionTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
        }
    }

    private void listener() {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalEditUserFragment(userModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.passwordMissingLayout.actionTextView);

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCentersFragment();
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.centerMissingLayout.actionTextView);
    }

    private void setData(UserModel model) {
        ((MainActivity) requireActivity()).singleton.update(model);

        if (model.isNo_password())
            binding.passwordMissingLayout.getRoot().setVisibility(View.VISIBLE);
        else
            binding.passwordMissingLayout.getRoot().setVisibility(View.GONE);

        if (binding.passwordMissingLayout.getRoot().getVisibility() == View.VISIBLE || binding.centerMissingLayout.getRoot().getVisibility() == View.VISIBLE)
            binding.missingSpace.setVisibility(View.VISIBLE);
        else
            binding.missingSpace.setVisibility(View.GONE);

        if (!model.getRoomList().data().isEmpty()) {
            roomsAdapter.setItems(model.getRoomList().data());

            binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);
            binding.roomsHeaderLayout.countTextView.setText(StringManager.bracing(roomsAdapter.getItemCount()));

            binding.roomsGroup.setVisibility(View.VISIBLE);
        } else if (roomsAdapter.getItemCount() == 0) {
            binding.roomsGroup.setVisibility(View.GONE);
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