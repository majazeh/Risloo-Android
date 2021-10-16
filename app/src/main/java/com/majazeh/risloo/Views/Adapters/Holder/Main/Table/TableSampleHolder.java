package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableSampleBinding;

public class TableSampleHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableSampleBinding binding;

    public TableSampleHolder(SingleItemTableSampleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}