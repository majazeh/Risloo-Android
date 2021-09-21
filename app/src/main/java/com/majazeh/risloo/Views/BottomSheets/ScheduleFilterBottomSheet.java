package com.majazeh.risloo.Views.BottomSheets;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.BottomSheetScheduleFilterBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleFilterBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetScheduleFilterBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> rooms;
    private ArrayList<String> status;

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
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
    }

    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            dismiss();
        }).widget(binding.resetButton);
    }

    private void setDialog() {
        // TODO : Place Code Here
    }

    public void setData(ArrayList<TypeModel> rooms, ArrayList<String> status) {
        this.rooms = rooms;
        this.status = status;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}