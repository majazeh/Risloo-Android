package com.majazeh.risloo.Views.Fragments.Tab;

import android.content.Intent;
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
import com.majazeh.risloo.Utils.Managers.ResultManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.BottomSheets.ImageBottomSheet;
import com.majazeh.risloo.databinding.FragmentEditUserAvatarBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class EditUserAvatarFragment extends Fragment {

    // Binding
    private FragmentEditUserAvatarBinding binding;

    // BottomSheets
    private ImageBottomSheet imageBottomSheet;

    // Objects
    private Bitmap avatarBitmap = null;

    // Vars
    public String avatarPath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUserAvatarBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        imageBottomSheet = new ImageBottomSheet();

        binding.avatarGuideLayout.guideTextView.setText(getResources().getString(R.string.EditUserAvatarTabAvatarGuide));

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditUserAvatarTabButton), getResources().getColor(R.color.White));
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
            imageBottomSheet.show(requireActivity().getSupportFragmentManager(), "imageBottomSheet");
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        ClickManager.onDelayedClickListener(() -> {
            if (avatarBitmap == null) {
                if (!avatarPath.equals(""))
                    Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppImageNew), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppImageEmpty), Toast.LENGTH_SHORT).show();
            } else {
                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);

            avatarPath = ((MainActivity) requireActivity()).singleton.getAvatar();
            Picasso.get().load(avatarPath).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            if (((MainActivity) requireActivity()).singleton.getName().equals(""))
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(getResources().getString(R.string.AppDefaultName)));
            else
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(((MainActivity) requireActivity()).singleton.getName()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    public void responseAction(String method, Intent data) {
        ResultManager resultManager = new ResultManager();

        switch (method) {
            case "gallery":
                resultManager.galleryResult(requireActivity(), data, binding.avatarIncludeLayout.avatarCircleImageView, binding.avatarIncludeLayout.charTextView);

                avatarPath = resultManager.path;
                avatarBitmap = resultManager.bitmap;
                break;
            case "camera":
                resultManager.cameraResult(requireActivity(), avatarPath, binding.avatarIncludeLayout.avatarCircleImageView, binding.avatarIncludeLayout.charTextView);

                avatarPath = resultManager.path;
                avatarBitmap = resultManager.bitmap;
                break;
        }
    }

    private void doWork() {
        FileManager.writeBitmapToCache(requireActivity(), BitmapManager.modifyOrientation(avatarBitmap, avatarPath), "image");

        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        HashMap data = new HashMap();
        data.put("id", ((MainActivity) requireActivity()).singleton.getUserId());
        data.put("avatar", FileManager.readFileFromCache(requireActivity(), "image"));

        HashMap header = new HashMap();
        header.put("Authorization", "Bearer " + ((MainActivity) requireActivity()).singleton.getToken());

        Auth.changeAvatar(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                AuthModel authModel = (AuthModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).singleton.setAvatar(authModel.getUser().getAvatar().getMedium().getUrl());
                        ((MainActivity) requireActivity()).setData();

                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                    });
                }

                FileManager.deleteFileFromCache(requireActivity(), "image");
            }

            @Override
            public void onFailure(String response) {
                // Place Code if Needed
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}