package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableBulkSampleBinding;

public class HeaderBulkSampleHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableBulkSampleBinding binding;

    public HeaderBulkSampleHolder(HeaderItemTableBulkSampleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}