package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableSessionBinding;

public class TableSessionHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableSessionBinding binding;

    public TableSessionHolder(SingleItemTableSessionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}