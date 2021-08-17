package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemReferenceBinding;

public class ReferencesHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemReferenceBinding binding;

    public ReferencesHolder(SingleItemReferenceBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}