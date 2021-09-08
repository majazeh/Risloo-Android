package com.majazeh.risloo.Views.Adapters.Holder.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexPaymentBinding;

public class HeaderPaymentHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexPaymentBinding binding;

    public HeaderPaymentHolder(HeaderItemIndexPaymentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}