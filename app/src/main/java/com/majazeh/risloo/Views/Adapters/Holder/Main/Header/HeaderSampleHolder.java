package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexSampleBinding;

public class HeaderSampleHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexSampleBinding binding;

    public HeaderSampleHolder(HeaderItemIndexSampleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}