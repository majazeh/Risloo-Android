package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemSampleBinding;

public class SamplesHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemSampleBinding binding;

    public SamplesHolder(SingleItemSampleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}