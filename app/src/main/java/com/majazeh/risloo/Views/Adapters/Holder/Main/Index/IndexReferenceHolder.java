package com.majazeh.risloo.Views.Adapters.Holder.Main.Index;

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