package com.majazeh.risloo.Views.adapters.holder.dialog;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemDialogSelectedBinding;

public class DialogSelectedHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemDialogSelectedBinding binding;

    public DialogSelectedHolder(SingleItemDialogSelectedBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}