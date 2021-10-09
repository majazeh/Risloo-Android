package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexSessionBinding;

public class HeaderSessionHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexSessionBinding binding;

    public HeaderSessionHolder(HeaderItemIndexSessionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}