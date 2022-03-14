package com.majazeh.risloo.views.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.ParamsManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.dialog.DialogFilterAdapter;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterSchedules;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomSchedules;
import com.majazeh.risloo.databinding.DialogScheduleFilterBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class DialogScheduleFilter extends AppCompatDialogFragment {

    // Binding
    private DialogScheduleFilterBinding binding;

    // Adapters
    private DialogFilterAdapter filterRoomAdapter, filterStatusAdapter;

    // Fragments
    private Fragment current;

    // Vars
    private ArrayList<TypeModel> rooms = new ArrayList<>(), status = new ArrayList<>();
    private String method = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireActivity(), R.style.DialogTheme);

        DialogScheduleFilterBinding binding = DialogScheduleFilterBinding.inflate(LayoutInflater.from(requireActivity()));

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setAttributes(ParamsManager.windowMatchAndWrap(dialog));

        setCancelable(true);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = DialogScheduleFilterBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setDialog();

        return binding.getRoot();
    }

    private void initializer() {
        filterRoomAdapter = new DialogFilterAdapter(requireActivity());
        filterStatusAdapter = new DialogFilterAdapter(requireActivity());

        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();

        InitManager.rcvVerticalUnfixed(requireActivity(), binding.roomRecyclerView, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._4sdp), 0, 0);
        InitManager.rcvVerticalUnfixed(requireActivity(), binding.statusRecyclerView, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._4sdp), 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (current instanceof FragmentCenterSchedules)
                ((FragmentCenterSchedules) current).responseDialog("reset", null);

            if (current instanceof FragmentRoomSchedules)
                ((FragmentRoomSchedules) current).responseDialog("reset", null);

            dismiss();
        }).widget(binding.resetButton);
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

    public void setData(String method, ArrayList<TypeModel> rooms, ArrayList<TypeModel> status) {
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