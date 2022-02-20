package com.majazeh.risloo.Views.adapters.holder.main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableBillBinding;

public class HeaderBillHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableBillBinding binding;

    public HeaderBillHolder(HeaderItemTableBillBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}