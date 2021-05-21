package com.majazeh.risloo.Views.BottomSheets;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.BottomSheetLogoutBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class LogoutBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetLogoutBinding binding;

    // Vars
    private String name, avatar;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetLogoutBinding.inflate(inflater, viewGroup, false);

        listener();

        detector();

        setWidget();

        return binding.getRoot();
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.entryButton.setBackgroundResource(R.drawable.draw_16sdp_solid_red500_ripple_red800);
        }
    }

    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

            HashMap header = new HashMap<>();
            header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

            Auth.logout(new HashMap<>(), header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((MainActivity) requireActivity()).singleton.logOut();

                            ((MainActivity) requireActivity()).loadingDialog.dismiss();
                            IntentManager.auth(requireActivity());

                            dismiss();
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    // Place Code if Needed
                }
            });
        }).widget(binding.entryButton);
    }

    private void setWidget() {
        if (!name.equals("")) {
            binding.nameTextView.setText(((MainActivity) requireActivity()).singleton.getName());
        } else {
            binding.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
        }

        if (!avatar.equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    public void setData(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}