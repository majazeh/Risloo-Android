package com.majazeh.risloo.views.sheets;

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
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.PermissionManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.create.CreateCenterFragment;
import com.majazeh.risloo.views.fragments.main.tab.EditUserTabAvatarFragment;
import com.majazeh.risloo.views.fragments.main.tab.EditCenterTabAvatarFragment;
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

        setDialog();

        return binding.getRoot();
    }

    private void intializer() {
        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();
        child = ((ActivityMain) requireActivity()).fragmont.getChild();

        InitManager.txtTextColorBackground(binding.galleryTextView.getRoot(), getResources().getString(R.string.BottomSheetImageGallery), getResources().getColor(R.color.coolGray500), R.drawable.draw_24sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);
        InitManager.txtTextColorBackground(binding.cameraTextView.getRoot(), getResources().getString(R.string.BottomSheetImageCamera), getResources().getColor(R.color.coolGray500), R.drawable.draw_24sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);
    }

    private void listener() {
        CustomClickView.onClickListener(() -> {
            if (PermissionManager.galleryPermission(requireActivity()))
                IntentManager.gallery(requireActivity());

            dismiss();
        }).widget(binding.galleryTextView.getRoot());

        CustomClickView.onClickListener(() -> {
            if (PermissionManager.cameraPermission(requireActivity())) {
                if (current instanceof CreateCenterFragment)
                    ((CreateCenterFragment) current).avatarPath = IntentManager.camera(requireActivity());

                if (child instanceof EditCenterTabAvatarFragment)
                    ((EditCenterTabAvatarFragment) child).avatarPath = IntentManager.camera(requireActivity());

                if (child instanceof EditUserTabAvatarFragment)
                    ((EditUserTabAvatarFragment) child).avatarPath = IntentManager.camera(requireActivity());
            }

            dismiss();
        }).widget(binding.cameraTextView.getRoot());
    }

    private void setDialog() {
        if (current instanceof CreateCenterFragment)
            binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetImageCreateCenterTitle));

        if (child instanceof EditCenterTabAvatarFragment)
            binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetImageEditCenterTitle));

        if (child instanceof EditUserTabAvatarFragment)
            binding.titleTextView.getRoot().setText(getResources().getString(R.string.BottomSheetImageEditUserTitle));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}