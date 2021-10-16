package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableCenterUserBinding;

public class TableCenterUserHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableCenterUserBinding binding;

    public TableCenterUserHolder(SingleItemTableCenterUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}