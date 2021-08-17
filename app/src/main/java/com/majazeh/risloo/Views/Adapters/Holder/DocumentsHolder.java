package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemDocumentBinding;

public class DocumentsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemDocumentBinding binding;

    public DocumentsHolder(SingleItemDocumentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}