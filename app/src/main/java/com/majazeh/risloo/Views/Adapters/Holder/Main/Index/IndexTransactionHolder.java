package com.majazeh.risloo.Views.Adapters.Holder.Main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexTransactionBinding;

public class IndexTransactionHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexTransactionBinding binding;

    public IndexTransactionHolder(SingleItemIndexTransactionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}