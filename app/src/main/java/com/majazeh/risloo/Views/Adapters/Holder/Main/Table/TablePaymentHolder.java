package com.majazeh.risloo.views.adapters.holder.main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTablePaymentBinding;

public class TablePaymentHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTablePaymentBinding binding;

    public TablePaymentHolder(SingleItemTablePaymentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}