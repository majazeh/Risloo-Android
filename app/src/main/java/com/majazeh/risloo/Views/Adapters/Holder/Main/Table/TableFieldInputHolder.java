package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableFieldInputBinding;

public class TableFieldInputHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableFieldInputBinding binding;

    public TableFieldInputHolder(SingleItemTableFieldInputBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}