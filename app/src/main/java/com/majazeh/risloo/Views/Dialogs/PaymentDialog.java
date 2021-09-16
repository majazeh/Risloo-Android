package com.majazeh.risloo.Views.Dialogs;

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
import com.majazeh.risloo.Utils.Managers.ParamsManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.databinding.DialogPaymentBinding;
import com.mre.ligheh.Model.TypeModel.PaymentModel;

public class PaymentDialog extends AppCompatDialogFragment {

    // Binding
    private DialogPaymentBinding binding;

    // Models
    private PaymentModel paymentModel;

    // Vars
    private String method;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireActivity(), R.style.DialogTheme);

        DialogPaymentBinding binding = DialogPaymentBinding.inflate(LayoutInflater.from(requireActivity()));

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
        binding = DialogPaymentBinding.inflate(inflater, viewGroup, false);

        setDialog();

        return binding.getRoot();
    }

    private void setDialog() {
        if (method.equals("request")) {
            binding.loadingTextView.setText(requireActivity().getResources().getString(R.string.DialogPaymentRequest));
        } else {
            binding.loadingTextView.setText(requireActivity().getResources().getString(R.string.DialogPaymentFinalize));
        }

        if (paymentModel.getTitle() != null && !paymentModel.getTitle().equals("")) {
            binding.titleTextView.setText(paymentModel.getTitle());
        } else {
            binding.titleTextView.setText("نامعلوم");
        }

        if (paymentModel.getAmount() != 0) {
            String amount = StringManager.separate(String.valueOf(paymentModel.getAmount())) + " " + getResources().getString(R.string.MainToman);
            binding.amountTextView.setText(amount);
        } else {
            String amount = "0" + " " + getResources().getString(R.string.MainToman);
            binding.amountTextView.setText(amount);
        }
    }

    public void setData(String method, PaymentModel model) {
        this.method = method;
        this.paymentModel = model;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}