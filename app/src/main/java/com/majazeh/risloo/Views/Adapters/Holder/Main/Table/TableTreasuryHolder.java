package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableTreasuryBinding;

public class TableTreasuryHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableTreasuryBinding binding;

    public TableTreasuryHolder(SingleItemTableTreasuryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}