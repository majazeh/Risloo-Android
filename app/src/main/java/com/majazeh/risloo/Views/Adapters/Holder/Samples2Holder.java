package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemSample2Binding;

public class Samples2Holder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemSample2Binding binding;

    public Samples2Holder(SingleItemSample2Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}