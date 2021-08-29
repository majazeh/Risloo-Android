package com.majazeh.risloo.Views.Adapters.Holder.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexSessionBinding;

public class IndexSessionHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexSessionBinding binding;

    public IndexSessionHolder(SingleItemIndexSessionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}