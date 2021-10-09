package com.majazeh.risloo.Views.Adapters.Holder.Main;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTabPlatformBinding;

public class TabPlatformsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTabPlatformBinding binding;

    public TabPlatformsHolder(SingleItemTabPlatformBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}