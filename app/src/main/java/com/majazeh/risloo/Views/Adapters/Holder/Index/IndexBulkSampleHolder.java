package com.majazeh.risloo.Views.Adapters.Holder.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexBulkSampleBinding;

public class IndexBulkSampleHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexBulkSampleBinding binding;

    public IndexBulkSampleHolder(SingleItemIndexBulkSampleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}