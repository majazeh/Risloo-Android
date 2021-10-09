package com.majazeh.risloo.Views.Adapters.Holder.Main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexBillBinding;

public class IndexBillHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexBillBinding binding;

    public IndexBillHolder(SingleItemIndexBillBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}