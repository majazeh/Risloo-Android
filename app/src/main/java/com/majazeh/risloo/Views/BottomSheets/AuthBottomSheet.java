package com.majazeh.risloo.Views.BottomSheets;

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
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.BottomSheetAuthBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AuthBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetAuthBinding binding;

    // Models
    private UserModel userModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String key;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetAuthBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setDialog();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
    }

    private void listener() {
        CustomClickView.onDelayedListener(this::doWork).widget(binding.entryButton);
    }

    private void setDialog() {
        if (userModel.getName() != null && !userModel.getName().equals("")) {
            binding.nameTextView.setText(userModel.getName());
        } else if (userModel.getId() != null && !userModel.getId().equals("")) {
            binding.nameTextView.setText(userModel.getId());
        } else {
            binding.nameTextView.setText(getResources().getString(R.string.AppDefaultUnknown));
        }

        if (userModel.getAvatar() != null && userModel.getAvatar().getMedium() != null && userModel.getAvatar().getMedium().getUrl() != null && !userModel.getAvatar().getMedium().getUrl().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(userModel.getAvatar().getMedium().getUrl()).placeholder(R.color.CoolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    public void setData(String key, UserModel userModel) {
        this.key = key;
        this.userModel = userModel;
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        data.put("key", key);

        Center.theory(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastNewReferenceAdded));

                        ((MainActivity) requireActivity()).navController.navigateUp();

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