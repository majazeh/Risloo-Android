package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemPlatformBinding;

public class PlatformsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemPlatformBinding binding;

    public PlatformsHolder(SingleItemPlatformBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}