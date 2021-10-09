package com.majazeh.risloo.Views.Adapters.Holder.Main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexDocumentBinding;

public class IndexDocumentHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexDocumentBinding binding;

    public IndexDocumentHolder(SingleItemIndexDocumentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}