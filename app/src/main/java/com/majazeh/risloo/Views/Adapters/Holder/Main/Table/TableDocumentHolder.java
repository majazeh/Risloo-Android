package com.majazeh.risloo.Views.adapters.holder.main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableDocumentBinding;

public class TableDocumentHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableDocumentBinding binding;

    public TableDocumentHolder(SingleItemTableDocumentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}