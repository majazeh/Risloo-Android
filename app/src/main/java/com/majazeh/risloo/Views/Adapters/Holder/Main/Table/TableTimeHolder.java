package com.majazeh.risloo.Views.adapters.holder.main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableTimeBinding;

public class TableTimeHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableTimeBinding binding;

    public TableTimeHolder(SingleItemTableTimeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}