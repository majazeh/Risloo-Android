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
import com.majazeh.risloo.databinding.FragmentEditCenterTabAvatarBinding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.ResultManager;
import com.majazeh.risloo.utils.managers.SheetManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.managers.ToastManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditCenter;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.File;
import java.util.HashMap;

public class FragmentEditCenterTabAvatar extends Fragment {

    // Binding
    private FragmentEditCenterTabAvatarBinding binding;

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
        binding = FragmentEditCenterTabAvatarBinding.inflate(inflater, viewGroup, false);

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

        binding.avatarGuideLayout.guideTextView.setText(getResources().getString(R.string.EditCenterTabAvatarAvatarGuide));

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditCenterTabAvatarButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
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
        if (current instanceof FragmentEditCenter) {
            CenterModel model;

            if (((FragmentEditCenter) current).centerModel != null)
                model = ((FragmentEditCenter) current).centerModel;
            else
                model = ((FragmentEditCenter) current).roomModel.getCenter();

            try {
                if (model.getId() != null && !model.getId().equals("")) {
                    data.put("id", model.getId());
                }

                if (model.getDetail() != null && model.getDetail().has("avatar") && !model.getDetail().isNull("avatar") && model.getDetail().getJSONArray("avatar").length() != 0) {
                    avatarPath = model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url");

                    binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                    Picasso.get().load(avatarPath).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
                } else {
                    binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                    if (model.getDetail() != null && model.getDetail().has("title") && !model.getDetail().isNull("title") && !model.getDetail().getString("title").equals(""))
                        binding.avatarIncludeLayout.charTextView.setText(StringManager.charsFirst(model.getDetail().getString("title")));
                    else if (model.getId() != null && !model.getId().equals(""))
                        binding.avatarIncludeLayout.charTextView.setText(StringManager.charsFirst(model.getId()));
                    else
                        binding.avatarIncludeLayout.charTextView.setText(StringManager.charsFirst(getResources().getString(R.string.AppDefaultUnknown)));

                    Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
                }
            } catch (JSONException e) {
                e.printStackTrace();
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

        Center.editAvatar(data, header, new Response() {
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
                    requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.showValid(response, binding));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        if (avatarFile != null)
            avatarFile.delete();
    }

}