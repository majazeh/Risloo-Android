package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

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