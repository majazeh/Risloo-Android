package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableTimeBinding;

public class HeaderTimeHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableTimeBinding binding;

    public HeaderTimeHolder(HeaderItemTableTimeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}