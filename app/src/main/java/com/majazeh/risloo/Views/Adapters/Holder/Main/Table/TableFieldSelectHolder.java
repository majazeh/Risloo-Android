package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

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