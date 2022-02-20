package com.majazeh.risloo.views.adapters.holder.main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableTransactionBinding;

public class HeaderTransactionHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableTransactionBinding binding;

    public HeaderTransactionHolder(HeaderItemTableTransactionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}