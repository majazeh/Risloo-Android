package com.majazeh.risloo.views.adapters.holder.main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexReferenceBinding;

public class IndexReferenceHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexReferenceBinding binding;

    public IndexReferenceHolder(SingleItemIndexReferenceBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}