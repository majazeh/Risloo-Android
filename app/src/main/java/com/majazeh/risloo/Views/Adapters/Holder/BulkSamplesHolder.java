package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemBulkSampleBinding;

public class BulkSamplesHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemBulkSampleBinding binding;

    public BulkSamplesHolder(SingleItemBulkSampleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}