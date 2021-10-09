package com.majazeh.risloo.Views.Adapters.Holder.Main;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemCenterBinding;

public class CentersHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemCenterBinding binding;

    public CentersHolder(SingleItemCenterBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}