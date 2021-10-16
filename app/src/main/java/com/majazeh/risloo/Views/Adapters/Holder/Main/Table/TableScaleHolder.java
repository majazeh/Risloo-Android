package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableScaleBinding;

public class TableScaleHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableScaleBinding binding;

    public TableScaleHolder(SingleItemTableScaleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}