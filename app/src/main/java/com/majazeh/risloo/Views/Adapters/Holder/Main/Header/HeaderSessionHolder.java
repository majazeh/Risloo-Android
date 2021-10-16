package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

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