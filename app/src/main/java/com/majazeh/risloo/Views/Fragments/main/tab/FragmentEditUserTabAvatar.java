package com.majazeh.risloo.views.fragments.main.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.FragmentEditUserTabAvatarBinding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.ResultManager;
import com.majazeh.risloo.utils.managers.SheetManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.managers.ToastManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditUser;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class FragmentEditUserTabAvatar extends Fragment {

    // Binding
    private FragmentEditUserTabAvatarBinding binding;

    // Fragments
    private Fragment current;

    // Objects
    private HashMap data, header;

    // Vars
    private File avatarFile = null;
    public String avatarPath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUserTabAvatarBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.avatarGuideLayout.guideTextView.setText(getResources().getString(R.string.EditUserTabAvatarAvatarGuide));

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditUserTabAvatarButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            SheetManager.showSheetImage(requireActivity());
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        CustomClickView.onDelayedListener(() -> {
            if (avatarFile == null) {
                if (!avatarPath.equals(""))
                    ToastManager.showToastError(requireActivity(), getResources().getString(R.string.ToastImageNewNotSelected));
                else
                    ToastManager.showToastError(requireActivity(), getResources().getString(R.string.ToastImageNotSelected));
            } else {
                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof FragmentEditUser) {
            UserModel model = ((FragmentEditUser) current).userModel;

            if (model.getId() != null && !model.getId().equals("")) {
                data.put("id", model.getId());
            }

            if (model.getAvatar() != null && model.getAvatar().getMedium() != null && model.getAvatar() .getMedium().getUrl() != null && !model.getAvatar().getMedium().getUrl().equals("")) {
                avatarPath = model.getAvatar().getMedium().getUrl();

                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(avatarPath).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                if (model.getName() != null && !model.getName().equals(""))
                    binding.avatarIncludeLayout.charTextView.setText(StringManager.charsFirst(model.getName()));
                else if (model.getId() != null && !model.getId().equals(""))
                    binding.avatarIncludeLayout.charTextView.setText(StringManager.charsFirst(model.getId()));
                else
                    binding.avatarIncludeLayout.charTextView.setText(StringManager.charsFirst(getResources().getString(R.string.AppDefaultUnknown)));

                Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }
        }
    }

    public void responseAction(String method, Intent data) {
        switch (method) {
            case "gallery":
                ResultManager.resultGallery(requireActivity(), data);
                break;
            case "camera":
                ResultManager.resultCamera(requireActivity(), avatarPath);
                break;
            case "crop":
                ResultManager.resultCrop(requireActivity(), data, binding.avatarIncludeLayout.avatarCircleImageView, binding.avatarIncludeLayout.charTextView);

                avatarFile = ResultManager.file;
                avatarPath = ResultManager.path;
                break;
        }
    }

    private void setHashmap() {
        if (avatarFile != null)
            data.put("avatar", avatarFile);
        else
            data.remove("avatar");
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        if (Objects.equals(data.get("id"), ((ActivityMain) requireActivity()).singleton.getUserModel().getId())) {
            Auth.changeAvatar(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel authModel = (AuthModel) object;
                    UserModel userModel = authModel.getUser();

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((ActivityMain) requireActivity()).singleton.params(userModel);
                            ((ActivityMain) requireActivity()).setData();

                            DialogManager.dismissDialogLoading();
                            SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackChangesSaved));
                        });

                        if (avatarFile != null)
                            avatarFile.delete();
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.requestValid(response, binding));
                    }
                }
            });
        } else {
            User.changeAvatar(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            DialogManager.dismissDialogLoading();
                            SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackChangesSaved));
                        });

                        if (avatarFile != null)
                            avatarFile.delete();
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.requestValid(response, binding));
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        if (avatarFile != null)
            avatarFile.delete();
    }

}