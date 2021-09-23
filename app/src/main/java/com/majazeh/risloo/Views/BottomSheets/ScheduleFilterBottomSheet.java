package com.majazeh.risloo.Views.BottomSheets;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Sheet.SheetFilterAdapter;
import com.majazeh.risloo.Views.Fragments.Index.CenterSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomSchedulesFragment;
import com.majazeh.risloo.databinding.BottomSheetScheduleFilterBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class ScheduleFilterBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetScheduleFilterBinding binding;

    // Adapters
    private SheetFilterAdapter filterRoomAdapter, filterStatusAdapter;

    // Fragments
    private Fragment current;

    // Vars
    private ArrayList<TypeModel> rooms, status;
    private String method;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetScheduleFilterBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setDialog();

        return binding.getRoot();
    }

    private void initializer() {
        filterRoomAdapter = new SheetFilterAdapter(requireActivity());
        filterStatusAdapter = new SheetFilterAdapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.roomRecyclerView, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._4sdp), 0, 0);
        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.statusRecyclerView, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._4sdp), 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (current instanceof CenterSchedulesFragment)
                ((CenterSchedulesFragment) current).responseSheet("reset", null);

            if (current instanceof RoomSchedulesFragment)
                ((RoomSchedulesFragment) current).responseSheet("reset", null);

            dismiss();
        }).widget(binding.resetButton);

        binding.roomRecyclerView.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            v.onTouchEvent(event);
            return true;
        });

        binding.statusRecyclerView.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            v.onTouchEvent(event);
            return true;
        });
    }

    private void setDialog() {
        if (rooms != null) {
            filterRoomAdapter.setItems(rooms, "rooms");
            binding.roomRecyclerView.setAdapter(filterRoomAdapter);
        }

        if (status != null) {
            filterStatusAdapter.setItems(status, "status");
            binding.statusRecyclerView.setAdapter(filterStatusAdapter);
        }

        if (method.equals("center")) {
            binding.roomGroup.setVisibility(View.VISIBLE);
        } else {
            binding.roomGroup.setVisibility(View.GONE);
        }
    }

    public void setData(ArrayList<TypeModel> rooms, ArrayList<TypeModel> status, String method) {
        this.method = method;
        this.rooms = rooms;
        this.status = status;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}