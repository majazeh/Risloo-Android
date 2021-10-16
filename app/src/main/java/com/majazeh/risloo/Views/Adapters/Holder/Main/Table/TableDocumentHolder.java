package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

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