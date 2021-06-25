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
import com.majazeh.risloo.Views.Fragments.Edit.EditUserFragment;
import com.majazeh.risloo.databinding.FragmentEditUserAvatarBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class EditUserAvatarFragment extends Fragment {

    // Binding
    private FragmentEditUserAvatarBinding binding;

    // BottomSheets
    private ImageBottomSheet imageBottomSheet;

    // Objects
    private Bitmap avatarBitmap = null;

    // Vars
    private HashMap data, header;
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

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

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
        Fragment fragment = ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null) {
            if (fragment instanceof EditUserFragment) {
                UserModel model = ((EditUserFragment) fragment).userModel;

                if (model.getId() != null && !model.getId().equals("")) {
                    data.put("id", model.getId());
                }

                if (model.getAvatar() != null && model.getAvatar().getMedium() != null && model.getAvatar() .getMedium().getUrl() != null && !model.getAvatar().getMedium().getUrl().equals("")) {
                    avatarPath = model.getAvatar().getMedium().getUrl();

                    binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                    Picasso.get().load(avatarPath).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
                } else {
                    binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                    if (!model.getName().equals(""))
                        binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(model.getName()));
                    else
                        binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(getResources().getString(R.string.AppDefaultName)));

                    Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
                }
            }
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
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        FileManager.writeBitmapToCache(requireActivity(), BitmapManager.modifyOrientation(avatarBitmap, avatarPath), "image");
        if (FileManager.readFileFromCache(requireActivity(), "image") != null) {
            data.put("avatar", FileManager.readFileFromCache(requireActivity(), "image"));
        }

        if (Objects.equals(data.get("id"), ((MainActivity) requireActivity()).singleton.getId())) {
            Auth.changeAvatar(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel authModel = (AuthModel) object;
                    UserModel userModel = authModel.getUser();

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((MainActivity) requireActivity()).singleton.update(userModel);
                            ((MainActivity) requireActivity()).setData();

                            ((MainActivity) requireActivity()).loadingDialog.dismiss();
                            Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                        });

                        FileManager.deleteFileFromCache(requireActivity(), "image");
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            // Place Code if Needed
                        });
                    }
                }
            });
        } else {
            User.changeAvatar(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((MainActivity) requireActivity()).loadingDialog.dismiss();
                            Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                        });

                        FileManager.deleteFileFromCache(requireActivity(), "image");
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            // Place Code if Needed
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}