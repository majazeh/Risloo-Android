package com.majazeh.risloo.views.adapters.holder.main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableFieldSelectBinding;

public class TableFieldSelectHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableFieldSelectBinding binding;

    public TableFieldSelectHolder(SingleItemTableFieldSelectBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}