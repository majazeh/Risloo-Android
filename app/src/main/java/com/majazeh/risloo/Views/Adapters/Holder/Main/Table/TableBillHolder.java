package com.majazeh.risloo.Views.adapters.holder.main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableBillBinding;

public class TableBillHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableBillBinding binding;

    public TableBillHolder(SingleItemTableBillBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}