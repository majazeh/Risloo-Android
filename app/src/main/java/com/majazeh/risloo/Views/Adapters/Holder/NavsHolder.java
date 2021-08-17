package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemNavBinding;

public class NavsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemNavBinding binding;

    public NavsHolder(SingleItemNavBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}