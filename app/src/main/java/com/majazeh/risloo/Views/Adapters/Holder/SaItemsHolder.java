package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemSaBinding;

public class SaItemsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemSaBinding binding;

    public SaItemsHolder(SingleItemSaBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}