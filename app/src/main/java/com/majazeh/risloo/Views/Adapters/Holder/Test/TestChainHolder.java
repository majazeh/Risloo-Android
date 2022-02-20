package com.majazeh.risloo.Views.adapters.holder.test;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTestChainBinding;

public class TestChainHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTestChainBinding binding;

    public TestChainHolder(SingleItemTestChainBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}