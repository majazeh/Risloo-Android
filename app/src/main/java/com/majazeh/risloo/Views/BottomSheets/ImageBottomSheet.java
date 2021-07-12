package com.majazeh.risloo.Views.BottomSheets;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterAvatarFragment;
import com.majazeh.risloo.databinding.BottomSheetImageBinding;

public class ImageBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetImageBinding binding;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetImageBinding.inflate(inflater, viewGroup, false);

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
                IntentManager.gallery(requireActivity());
            }

            dismiss();
        }).widget(binding.galleryLinearLayout);

        ClickManager.onDelayedClickListener(() -> {
            if (PermissionManager.cameraPermission(requireActivity())) {
                Fragment current = ((MainActivity) requireActivity()).fragmont.getCurrent();
                Fragment child = ((MainActivity) requireActivity()).fragmont.getChild();

                if (current instanceof CreateCenterFragment)
                    ((CreateCenterFragment) current).avatarPath = IntentManager.camera(requireActivity());

                if (child instanceof EditCenterAvatarFragment)
                    ((EditCenterAvatarFragment) child).avatarPath = IntentManager.camera(requireActivity());

                if (child instanceof EditUserAvatarFragment)
                    ((EditUserAvatarFragment) child).avatarPath = IntentManager.camera(requireActivity());
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