package com.majazeh.risloo.Views.Adapters.Holder.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexBulkSampleBinding;

public class HeaderBulkSampleHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexBulkSampleBinding binding;

    public HeaderBulkSampleHolder(HeaderItemIndexBulkSampleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}