package com.majazeh.risloo.Views.Adapters.Holder.Dialog;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemDialogSearchableBinding;

public class DialogSearchableHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemDialogSearchableBinding binding;

    public DialogSearchableHolder(SingleItemDialogSearchableBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}