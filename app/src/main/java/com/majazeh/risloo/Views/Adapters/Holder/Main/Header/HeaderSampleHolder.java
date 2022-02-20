package com.majazeh.risloo.Views.adapters.holder.main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableSampleBinding;

public class HeaderSampleHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableSampleBinding binding;

    public HeaderSampleHolder(HeaderItemTableSampleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}