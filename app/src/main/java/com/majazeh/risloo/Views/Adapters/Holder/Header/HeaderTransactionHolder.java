package com.majazeh.risloo.Views.Adapters.Holder.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexTransactionBinding;

public class HeaderTransactionHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexTransactionBinding binding;

    public HeaderTransactionHolder(HeaderItemIndexTransactionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}