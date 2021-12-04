package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableFieldTextBinding;

public class TableFieldTextHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableFieldTextBinding binding;

    public TableFieldTextHolder(SingleItemTableFieldTextBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}