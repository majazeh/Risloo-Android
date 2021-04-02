package com.majazeh.risloo.Views.Dialogs;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Utils.Widgets.CustomizeDialog;
import com.majazeh.risloo.databinding.DialogImageBinding;

public class ImageDialog extends BottomSheetDialogFragment {

    // Binding
    private DialogImageBinding binding;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return CustomizeDialog.bottomSheet(requireActivity(), dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = DialogImageBinding.inflate(inflater, viewGroup, false);

        listener();

        detector();

        return binding.getRoot();
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.galleryLinearLayout.setBackgroundResource(R.drawable.draw_4sdp_solid_white_ripple_gray300);
            binding.cameraLinearLayout.setBackgroundResource(R.drawable.draw_4sdp_solid_white_ripple_gray300);
        }
    }

    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (PermissionManager.galleryPermission(requireActivity())) {
                Log.e("gallery", "hello");
            }

            dismiss();
        }).widget(binding.galleryLinearLayout);

        ClickManager.onDelayedClickListener(() -> {
            if (PermissionManager.cameraPermission(requireActivity())) {
                Log.e("camera", "hello");
            }

            dismiss();
        }).widget(binding.cameraLinearLayout);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}