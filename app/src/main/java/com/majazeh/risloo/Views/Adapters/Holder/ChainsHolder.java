package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemChainBinding;

public class ChainsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemChainBinding binding;

    public ChainsHolder(SingleItemChainBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}