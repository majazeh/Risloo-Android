package com.majazeh.risloo.Views.adapters.holder.main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableFieldMultiBinding;

public class TableFieldMultiHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableFieldMultiBinding binding;

    public TableFieldMultiHolder(SingleItemTableFieldMultiBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}