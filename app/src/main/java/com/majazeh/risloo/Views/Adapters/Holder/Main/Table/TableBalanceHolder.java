package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableBalanceBinding;

public class TableBalanceHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableBalanceBinding binding;

    public TableBalanceHolder(SingleItemTableBalanceBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}