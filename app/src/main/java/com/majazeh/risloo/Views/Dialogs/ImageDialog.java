package com.majazeh.risloo.Views.Dialogs;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Utils.Widgets.CustomizeDialog;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.DialogImageBinding;

public class ImageDialog extends BottomSheetDialogFragment {

    // Binding
    private DialogImageBinding binding;

    // Widgets
    private LinearLayout galleryLinearLayout, cameraLinearLayout;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return CustomizeDialog.bottomSheet(getActivity(), dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = DialogImageBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        detector();

        return binding.getRoot();
    }

    private void initializer() {
        galleryLinearLayout = binding.dialogImageGalleryLinearLayout;
        cameraLinearLayout = binding.dialogImageCameraLinearLayout;
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            galleryLinearLayout.setBackgroundResource(R.drawable.draw_4sdp_solid_white_ripple_gray300);
            cameraLinearLayout.setBackgroundResource(R.drawable.draw_4sdp_solid_white_ripple_gray300);
        }
    }

    private void listener() {
        galleryLinearLayout.setOnClickListener(v -> {
            galleryLinearLayout.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> galleryLinearLayout.setClickable(true), 300);
            dismiss();

            if (PermissionManager.galleryPermission(getActivity())) {
                Log.e("gallery", "hello");
            }
        });

        cameraLinearLayout.setOnClickListener(v -> {
            cameraLinearLayout.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> cameraLinearLayout.setClickable(true), 300);
            dismiss();

            if (PermissionManager.cameraPermission(getActivity())) {
                Log.e("camera", "hello");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}