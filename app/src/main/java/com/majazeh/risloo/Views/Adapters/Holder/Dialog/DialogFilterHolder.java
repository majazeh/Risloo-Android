package com.majazeh.risloo.Views.adapters.holder.dialog;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemDialogFilterBinding;

public class DialogFilterHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemDialogFilterBinding binding;

    public DialogFilterHolder(SingleItemDialogFilterBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}