package com.majazeh.risloo.Views.sheets;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.databinding.BottomSheetLogoutBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class LogoutBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetLogoutBinding binding;

    // Models
    private UserModel userModel;

    // Objects
    private HashMap data, header;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetLogoutBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setDialog();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.txtTextColorBackground(binding.entryTextView.getRoot(), getResources().getString(R.string.BottomSheetLogoutEntry), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_red600_ripple_red800);
    }

    private void listener() {
        CustomClickView.onDelayedListener(this::doWork).widget(binding.entryTextView.getRoot());
    }

    private void setDialog() {
        if (!userModel.getName().equals("")) {
            binding.nameTextView.getRoot().setText(userModel.getName());
        } else if (!userModel.getId().equals("")) {
            binding.nameTextView.getRoot().setText(userModel.getId());
        } else {
            binding.nameTextView.getRoot().setText(getResources().getString(R.string.AppDefaultUnknown));
        }

        if (userModel.getAvatar() != null && userModel.getAvatar().getMedium() != null && !userModel.getAvatar().getMedium().getUrl().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(userModel.getAvatar().getMedium().getUrl()).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getRoot().getText().toString()));

            Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    public void setData(UserModel userModel) {
        this.userModel = userModel;
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        Auth.logout(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        ((MainActivity) requireActivity()).singleton.logout();

                        IntentManager.auth(requireActivity(), "login");

                        dismiss();
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