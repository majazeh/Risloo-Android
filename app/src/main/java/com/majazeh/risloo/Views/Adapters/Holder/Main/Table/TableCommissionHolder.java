package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableCommissionBinding;

public class TableCommissionHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableCommissionBinding binding;

    public TableCommissionHolder(SingleItemTableCommissionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}