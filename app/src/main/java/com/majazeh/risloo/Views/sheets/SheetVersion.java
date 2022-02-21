package com.majazeh.risloo.views.sheets;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivitySplash;
import com.majazeh.risloo.databinding.BottomSheetVersionBinding;
import com.mre.ligheh.Model.TypeModel.ClientModel;

public class SheetVersion extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetVersionBinding binding;

    // Models
    private ClientModel clientModel;

    // Vars
    private String method = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetVersionBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setDialog();

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        navigate();
    }

    private void initializer() {
        InitManager.txtTextColorBackground(binding.downloadTextView.getRoot(), getResources().getString(R.string.BottomSheetVersionDownload), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    private void listener() {
        CustomClickView.onDelayedListener(() -> IntentManager.googlePlay(requireActivity())).widget(binding.downloadTextView.getRoot());

        CustomClickView.onDelayedListener(this::dismiss).widget(binding.returnTextView.getRoot());
    }

    private void setDialog() {
        if (method.equals("force")) {
            String force = requireActivity().getResources().getString(R.string.BottomSheetVersionTitle) + " " + clientModel.getForce();

            binding.titleTextView.getRoot().setText(force);
            binding.descTextView.getRoot().setText(requireActivity().getResources().getString(R.string.BottomSheetVersionForceDesc));

            InitManager.txtTextColorBackground(binding.returnTextView.getRoot(), getResources().getString(R.string.BottomSheetVersionForceReturn), getResources().getColor(R.color.red600), R.drawable.draw_24sdp_solid_white_border_1sdp_red600_ripple_red300);
        } else {
            String current = requireActivity().getResources().getString(R.string.BottomSheetVersionTitle) + " " + clientModel.getCurrent();

            binding.titleTextView.getRoot().setText(current);
            binding.descTextView.getRoot().setText(requireActivity().getResources().getString(R.string.BottomSheetVersionCurrentDesc));

            InitManager.txtTextColorBackground(binding.returnTextView.getRoot(), getResources().getString(R.string.BottomSheetVersionCurrentReturn), getResources().getColor(R.color.coolGray500), R.drawable.draw_24sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);
        }
    }

    public void setData(ClientModel model, String method) {
        this.clientModel = model;
        this.method = method;
    }

    private void navigate() {
        switch (method) {
            case "force":
                IntentManager.finish(requireActivity());
                break;
            case "current":
                if (!((ActivitySplash) requireActivity()).singleton.getToken().equals(""))
                    IntentManager.main(requireActivity());
                else
                    IntentManager.auth(requireActivity(), "login");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}