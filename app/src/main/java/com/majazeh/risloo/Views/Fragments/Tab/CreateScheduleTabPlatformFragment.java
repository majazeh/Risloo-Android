package com.majazeh.risloo.Views.Fragments.Tab;

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
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.TabPlatformsAdapter;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.databinding.FragmentCreateScheduleTabPlatformBinding;
import com.mre.ligheh.Model.TypeModel.RoomModel;

public class CreateScheduleTabPlatformFragment extends Fragment {

    // Binding
    public FragmentCreateScheduleTabPlatformBinding binding;

    // Adapters
    public TabPlatformsAdapter adapter;

    // Fragments
    private Fragment current;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleTabPlatformBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TabPlatformsAdapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.platformsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateScheduleTabPlatformButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (current instanceof CreateScheduleFragment)
                ((CreateScheduleFragment) current).checkRequire();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (current instanceof CreateScheduleFragment) {
            RoomModel model = ((CreateScheduleFragment) current).roomModel;

            if (!model.getSession_platforms().data().isEmpty()) {
                adapter.setItems(model.getSession_platforms().data());
                binding.platformsSingleLayout.recyclerView.setAdapter(adapter);

                binding.platformsSingleLayout.emptyView.setVisibility(View.GONE);
            } else if (adapter.getItemCount() == 0) {
                binding.platformsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                binding.platformsSingleLayout.emptyView.setText(getResources().getString(R.string.TabPlatformsAdapterEmpty));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}