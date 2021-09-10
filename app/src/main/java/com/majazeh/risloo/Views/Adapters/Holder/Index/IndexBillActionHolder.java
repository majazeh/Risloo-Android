package com.majazeh.risloo.Views.Adapters.Holder.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexBillActionBinding;

public class IndexBillActionHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexBillActionBinding binding;

    public IndexBillActionHolder(SingleItemIndexBillActionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}