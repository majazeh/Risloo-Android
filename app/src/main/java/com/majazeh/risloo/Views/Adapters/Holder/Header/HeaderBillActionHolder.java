package com.majazeh.risloo.Views.Adapters.Holder.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexBillActionBinding;

public class HeaderBillActionHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexBillActionBinding binding;

    public HeaderBillActionHolder(HeaderItemIndexBillActionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}