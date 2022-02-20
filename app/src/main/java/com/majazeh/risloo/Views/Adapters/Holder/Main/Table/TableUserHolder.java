package com.majazeh.risloo.Views.adapters.holder.main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableUserBinding;

public class TableUserHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableUserBinding binding;

    public TableUserHolder(SingleItemTableUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}