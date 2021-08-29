package com.majazeh.risloo.Views.Adapters.Holder.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexUserBinding;

public class HeaderUserHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexUserBinding binding;

    public HeaderUserHolder(HeaderItemIndexUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}