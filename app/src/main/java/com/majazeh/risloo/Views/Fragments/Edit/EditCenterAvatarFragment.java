package com.majazeh.risloo.Views.Fragments.Edit;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.BitmapManager;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.FileManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Dialogs.ImageDialog;
import com.majazeh.risloo.databinding.FragmentEditCenterAvatarBinding;
import com.squareup.picasso.Picasso;

public class EditCenterAvatarFragment extends Fragment {

    // Binding
    public FragmentEditCenterAvatarBinding binding;

    // Dialogs
    private ImageDialog imageDialog;

    // Objects
    public Bitmap avatarBitmap;

    // Vars
    public String avatarPath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCenterAvatarBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        imageDialog = new ImageDialog();

        binding.avatarGuideLayout.guideTextView.setText(getResources().getString(R.string.EditCenterAvatarFragmentGuide));

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditCenterAvatarFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            imageDialog.show(requireActivity().getSupportFragmentManager(), "imageBottomSheet");
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        ClickManager.onDelayedClickListener(() -> {
            if (avatarBitmap == null) {
                Toast.makeText(requireActivity(), "exception", Toast.LENGTH_SHORT).show();
            } else {
                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            if (((MainActivity) requireActivity()).singleton.getName().equals(""))
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(getResources().getString(R.string.MainToolbar)));
            else
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(((MainActivity) requireActivity()).singleton.getName()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);

            avatarPath = ((MainActivity) requireActivity()).singleton.getAvatar();
            Picasso.get().load(avatarPath).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    private void doWork() {
        FileManager.writeBitmapToCache(requireActivity(), BitmapManager.modifyOrientation(avatarBitmap, avatarPath), "image");

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}