package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

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