package com.majazeh.risloo.views.fragments.main.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Create.CreatePlatformAdapter;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateSchedule;
import com.majazeh.risloo.databinding.FragmentCreateScheduleTabPlatformBinding;
import com.mre.ligheh.Model.TypeModel.RoomModel;

import java.util.HashMap;

public class FragmentCreateScheduleTabPlatform extends Fragment {

    // Binding
    private FragmentCreateScheduleTabPlatformBinding binding;

    // Adapters
    private CreatePlatformAdapter adapter;

    // Fragments
    private Fragment current;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleTabPlatformBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new CreatePlatformAdapter(requireActivity());

        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();

        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.platformsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateScheduleTabPlatformButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (current instanceof FragmentCreateSchedule)
                ((FragmentCreateSchedule) current).checkRequire();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (current instanceof FragmentCreateSchedule) {
            RoomModel model = ((FragmentCreateSchedule) current).roomModel;

            if (!model.getSessionPlatforms().data().isEmpty()) {
                adapter.setItems(model.getSessionPlatforms().data());
                binding.platformsSingleLayout.recyclerView.setAdapter(adapter);

                binding.platformsSingleLayout.emptyView.setVisibility(View.GONE);
            } else if (adapter.getItemCount() == 0) {
                binding.platformsSingleLayout.recyclerView.setAdapter(null);

                binding.platformsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                binding.platformsSingleLayout.emptyView.setText(getResources().getString(R.string.CreatePlatformAdapterEmpty));
            }
        }
    }

    public void setHashmap(HashMap data) {
        if (!adapter.platforms.isEmpty())
            data.put("platforms", adapter.platforms);
        else
            data.remove("platforms");

        if (!adapter.pinPlatform.isEmpty())
            data.put("pin_platform", adapter.pinPlatform);
        else
            data.remove("pin_platform");

        if (!adapter.identifierPlatform.isEmpty())
            data.put("identifier_platform", adapter.identifierPlatform);
        else
            data.remove("identifier_platform");
    }

    public void hideValid() {
        ((ActivityMain) requireActivity()).validatoon.hideValid(binding);
    }

    public void showValid(String key, String validation) {
        ((ActivityMain) requireActivity()).validatoon.showValid(key, validation, binding);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}