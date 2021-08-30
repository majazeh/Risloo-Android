package com.majazeh.risloo.Views.Adapters.Holder.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexFieldBinding;

public class IndexFieldHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexFieldBinding binding;

    public IndexFieldHolder(SingleItemIndexFieldBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}