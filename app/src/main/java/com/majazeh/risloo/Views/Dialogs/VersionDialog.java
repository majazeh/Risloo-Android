package com.majazeh.risloo.Views.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.ParamsManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.SplashActivity;
import com.majazeh.risloo.databinding.DialogVersionBinding;
import com.mre.ligheh.Model.TypeModel.VersionModel;

public class VersionDialog extends AppCompatDialogFragment {

    // Binding
    private DialogVersionBinding binding;

    // Models
    private VersionModel versionModel;

    // Vars
    private String method;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireActivity(), R.style.DialogTheme);

        DialogVersionBinding binding = DialogVersionBinding.inflate(LayoutInflater.from(requireActivity()));

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setAttributes(ParamsManager.matchWrapContent(dialog));

        setCancelable(false);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = DialogVersionBinding.inflate(inflater, viewGroup, false);

        listener();

        setDialog();

        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            IntentManager.googlePlay(requireActivity());
        }).widget(binding.downloadButton);

        CustomClickView.onDelayedListener(() -> {
            ((SplashActivity) requireActivity()).responseDialog(method);

            dismiss();
        }).widget(binding.returnButton);
    }

    private void setDialog() {
        if (method.equals("force")) {
            binding.titleTextView.setText(requireActivity().getResources().getString(R.string.DialogVersionTitle) + " " + versionModel.getAndroid().getForce());
            binding.descTextView.setText(requireActivity().getResources().getString(R.string.DialogVersionForceDesc));

            InitManager.txtTextColorBackground(binding.returnButton, getResources().getString(R.string.DialogVersionForceReturn), getResources().getColor(R.color.Red600), R.drawable.draw_24sdp_solid_white_border_1sdp_red600_ripple_red300);
        } else {
            binding.titleTextView.setText(requireActivity().getResources().getString(R.string.DialogVersionTitle) + " " + versionModel.getAndroid().getCurrent());
            binding.descTextView.setText(requireActivity().getResources().getString(R.string.DialogVersionCurrentDesc));

            InitManager.txtTextColorBackground(binding.returnButton, getResources().getString(R.string.DialogVersionCurrentReturn), getResources().getColor(R.color.CoolGray400), R.drawable.draw_24sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);
        }
    }

    public void setData(String method, VersionModel model) {
        this.method = method;
        this.versionModel = model;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}