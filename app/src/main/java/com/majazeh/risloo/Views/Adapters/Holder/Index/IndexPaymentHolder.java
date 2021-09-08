package com.majazeh.risloo.Views.Adapters.Holder.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexPaymentBinding;

public class IndexPaymentHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexPaymentBinding binding;

    public IndexPaymentHolder(SingleItemIndexPaymentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}