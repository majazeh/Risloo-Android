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
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.PermissionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditUserTabAvatarFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterTabAvatarFragment;
import com.majazeh.risloo.databinding.BottomSheetImageBinding;

public class ImageBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetImageBinding binding;

    // Fragments
    private Fragment current, child;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetImageBinding.inflate(inflater, viewGroup, false);

        intializer();

        listener();

        detector();

        return binding.getRoot();
    }

    private void intializer() {
        current = ((MainActivity) requireActivity()).fragmont.getCurrent();
        child = ((MainActivity) requireActivity()).fragmont.getChild();
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.galleryLinearLayout.setBackgroundResource(R.drawable.draw_4sdp_solid_white_ripple_gray300);
            binding.cameraLinearLayout.setBackgroundResource(R.drawable.draw_4sdp_solid_white_ripple_gray300);
        }
    }

    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (PermissionManager.galleryPermission(requireActivity())) {
                IntentManager.gallery(requireActivity());
            }

            dismiss();
        }).widget(binding.galleryLinearLayout);

        CustomClickView.onDelayedListener(() -> {
            if (PermissionManager.cameraPermission(requireActivity())) {
                if (current instanceof CreateCenterFragment)
                    ((CreateCenterFragment) current).avatarPath = IntentManager.camera(requireActivity());

                if (child instanceof EditCenterTabAvatarFragment)
                    ((EditCenterTabAvatarFragment) child).avatarPath = IntentManager.camera(requireActivity());

                if (child instanceof EditUserTabAvatarFragment)
                    ((EditUserTabAvatarFragment) child).avatarPath = IntentManager.camera(requireActivity());
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