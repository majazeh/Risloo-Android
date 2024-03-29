package com.majazeh.risloo.views.fragments.main.show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.databinding.FragmentMeBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class FragmentMe extends Fragment {

    // Binding
    private FragmentMeBinding binding;

    // Models
    private UserModel userModel;

    // Objects
    private HashMap data, header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentMeBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        InitManager.imgResTintBackground(requireActivity(), binding.editImageView.getRoot(), R.drawable.ic_edit_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE)
                IntentManager.display(requireActivity(), binding.nameTextView.getText().toString(), userModel.getAvatar().getMedium().getUrl());
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        CustomClickView.onClickListener(() -> {
            ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentEditUser(userModel);
        }).widget(binding.editImageView.getRoot());

        CustomClickView.onDelayedListener(() -> {
            IntentManager.tel(requireActivity(), binding.mobileTextView.getText().toString());
        }).widget(binding.mobileTextView);

        CustomClickView.onDelayedListener(() -> {
            IntentManager.email(requireActivity(), new String[]{binding.emailTextView.getText().toString()}, "", "");
        }).widget(binding.emailTextView);
    }

    private void setArgs() {
        userModel = (UserModel) FragmentMeArgs.fromBundle(getArguments()).getTypeModel();
        setData(userModel);
    }

    private void setData(UserModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }

        if (model.getName() != null && !model.getName().equals("")) {
            binding.nameTextView.setText(model.getName());
        } else if (model.getId() != null && !model.getId().equals("")) {
            binding.nameTextView.setText(model.getId());
        } else {
            binding.nameTextView.setText(getResources().getString(R.string.AppDefaultUnknown));
        }

        if (model.getMobile() != null && !model.getMobile().equals("")) {
            binding.mobileTextView.setText(model.getMobile());
            binding.mobileGroup.setVisibility(View.VISIBLE);
        } else {
            binding.mobileGroup.setVisibility(View.GONE);
        }

        if (model.getEmail() != null && !model.getEmail().equals("")) {
            binding.emailTextView.setText(model.getEmail());
            binding.emailGroup.setVisibility(View.VISIBLE);
        } else {
            binding.emailGroup.setVisibility(View.GONE);
        }

        if (model.getAvatar() != null && model.getAvatar().getMedium() != null && model.getAvatar().getMedium().getUrl() != null && !model.getAvatar().getMedium().getUrl().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(model.getAvatar().getMedium().getUrl()).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.charsFirst(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    private void getData() {
        User.show(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                userModel = (UserModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        setData(userModel);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        // TODO : Place Code If Needed
                    });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}