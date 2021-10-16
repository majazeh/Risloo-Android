package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableCaseBinding;

public class TableCaseHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableCaseBinding binding;

    public TableCaseHolder(SingleItemTableCaseBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}