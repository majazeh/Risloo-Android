package com.majazeh.risloo.Views.Adapters.Holder.Main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexTreasuryBinding;

public class IndexTreasuryHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexTreasuryBinding binding;

    public IndexTreasuryHolder(SingleItemIndexTreasuryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}