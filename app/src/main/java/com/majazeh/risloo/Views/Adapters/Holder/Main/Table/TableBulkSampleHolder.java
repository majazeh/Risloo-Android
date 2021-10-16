package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

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