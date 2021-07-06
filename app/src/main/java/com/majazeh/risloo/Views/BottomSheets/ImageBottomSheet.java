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
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragment;
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
                if (getParent() != null) {
                    if (getParent() instanceof CreateCenterFragment)
                        ((CreateCenterFragment) getParent()).avatarPath = IntentManager.camera(requireActivity());

                    else if (getParent() instanceof EditCenterAvatarFragment)
                        ((EditCenterAvatarFragment) getParent()).avatarPath = IntentManager.camera(requireActivity());

                    else if (getParent() instanceof EditUserAvatarFragment)
                        ((EditUserAvatarFragment) getParent()).avatarPath = IntentManager.camera(requireActivity());
                }
            }

            dismiss();
        }).widget(binding.cameraLinearLayout);
    }

    private Fragment getParent() {
        Fragment fragment = ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null)
            if (fragment instanceof CreateCenterFragment)
                return (CreateCenterFragment) fragment;

            else if (fragment instanceof EditCenterFragment) {
                Fragment childFragment = ((EditCenterFragment) fragment).adapter.hashMap.get(((EditCenterFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof EditCenterAvatarFragment)
                        return (EditCenterAvatarFragment) childFragment;

            } else if (fragment instanceof EditUserFragment) {
                Fragment childFragment = ((EditUserFragment) fragment).adapter.hashMap.get(((EditUserFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof EditUserAvatarFragment)
                        return (EditUserAvatarFragment) childFragment;
            }

        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}