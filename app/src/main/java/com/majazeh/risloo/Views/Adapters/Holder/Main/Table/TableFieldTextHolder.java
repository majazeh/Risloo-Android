package com.majazeh.risloo.views.adapters.holder.main.Table;

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