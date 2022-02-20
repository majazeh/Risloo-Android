package com.majazeh.risloo.views.adapters.holder.main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexBankBinding;

public class IndexBankHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexBankBinding binding;

    public IndexBankHolder(SingleItemIndexBankBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}