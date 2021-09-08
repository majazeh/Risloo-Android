package com.majazeh.risloo.Views.Adapters.Holder.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexTimeBinding;

public class HeaderTimeHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexTimeBinding binding;

    public HeaderTimeHolder(HeaderItemIndexTimeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}