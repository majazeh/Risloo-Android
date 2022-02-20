package com.majazeh.risloo.Views.adapters.holder.main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTablePaymentBinding;

public class HeaderPaymentHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTablePaymentBinding binding;

    public HeaderPaymentHolder(HeaderItemTablePaymentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}