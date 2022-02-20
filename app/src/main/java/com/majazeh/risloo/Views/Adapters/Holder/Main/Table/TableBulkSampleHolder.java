package com.majazeh.risloo.Views.adapters.holder.main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableBulkSampleBinding;

public class TableBulkSampleHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableBulkSampleBinding binding;

    public TableBulkSampleHolder(SingleItemTableBulkSampleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}