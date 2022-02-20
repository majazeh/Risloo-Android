package com.majazeh.risloo.Views.adapters.holder.main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableSessionBinding;

public class HeaderSessionHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableSessionBinding binding;

    public HeaderSessionHolder(HeaderItemTableSessionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}