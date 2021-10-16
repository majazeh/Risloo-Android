package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableScaleBinding;

public class HeaderScaleHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableScaleBinding binding;

    public HeaderScaleHolder(HeaderItemTableScaleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}