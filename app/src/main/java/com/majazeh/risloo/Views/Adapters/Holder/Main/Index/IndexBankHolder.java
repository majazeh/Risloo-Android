package com.majazeh.risloo.Views.Adapters.Holder.Main.Index;

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