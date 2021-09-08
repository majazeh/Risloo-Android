package com.majazeh.risloo.Views.Adapters.Holder.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexBillBinding;

public class HeaderBillHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexBillBinding binding;

    public HeaderBillHolder(HeaderItemIndexBillBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}