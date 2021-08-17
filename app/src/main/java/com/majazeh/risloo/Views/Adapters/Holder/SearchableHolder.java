package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemSearchableBinding;

public class SearchableHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemSearchableBinding binding;

    public SearchableHolder(SingleItemSearchableBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}