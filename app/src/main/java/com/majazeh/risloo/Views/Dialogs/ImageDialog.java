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

public class ImageDialog extends BottomSheetDialogFragment {
    
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
        View view = inflater.inflate(R.layout.dialog_image, viewGroup, false);

        initializer(view);

        listener();

        detector();

        return view;
    }

    private void initializer(View view) {
        galleryLinearLayout = view.findViewById(R.id.dialog_image_gallery_linearLayout);
        cameraLinearLayout = view.findViewById(R.id.dialog_image_camera_linearLayout);
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
            ((MainActivity) getActivity()).handler.postDelayed(() -> galleryLinearLayout.setClickable(true), 300);
            dismiss();

            if (PermissionManager.galleryPermission(getActivity())) {
                Log.e("gallery", "hello");
            }
        });

        cameraLinearLayout.setOnClickListener(v -> {
            cameraLinearLayout.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> cameraLinearLayout.setClickable(true), 300);
            dismiss();

            if (PermissionManager.cameraPermission(getActivity())) {
                Log.e("camera", "hello");
            }
        });
    }

}