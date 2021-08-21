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
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
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

    // Objects
    private HashMap data, header;

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

        initializer();

        listener();

        detector();

        setDialog();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.entryButton.setBackgroundResource(R.drawable.draw_16sdp_solid_red500_ripple_red800);
        }
    }

    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

            Auth.logout(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((MainActivity) requireActivity()).singleton.logout();
                            ((MainActivity) requireActivity()).loadingDialog.dismiss();

                            IntentManager.auth(requireActivity(), "login");

                            dismiss();
                        });
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
        }).widget(binding.entryButton);
    }

    private void setDialog() {
        if (!name.equals("")) {
            binding.nameTextView.setText(name);
        } else {
            binding.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
        }

        if (!avatar.equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(avatar).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
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