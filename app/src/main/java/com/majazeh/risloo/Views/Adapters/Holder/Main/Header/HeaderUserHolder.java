package com.majazeh.risloo.Views.adapters.holder.main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableUserBinding;

public class HeaderUserHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableUserBinding binding;

    public HeaderUserHolder(HeaderItemTableUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}