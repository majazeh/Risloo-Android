package com.majazeh.risloo.Views.Adapters.Holder.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexScaleBinding;

public class HeaderScaleHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexScaleBinding binding;

    public HeaderScaleHolder(HeaderItemIndexScaleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}