package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemCenterPlatformBinding;

public class CenterPlatformsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemCenterPlatformBinding binding;

    public CenterPlatformsHolder(SingleItemCenterPlatformBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}