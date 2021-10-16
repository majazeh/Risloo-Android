package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableTransactionBinding;

public class TableTransactionHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableTransactionBinding binding;

    public TableTransactionHolder(SingleItemTableTransactionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}