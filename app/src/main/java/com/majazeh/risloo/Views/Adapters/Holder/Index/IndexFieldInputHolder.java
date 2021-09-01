package com.majazeh.risloo.Views.Adapters.Holder.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexFieldInputBinding;

public class IndexFieldInputHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexFieldInputBinding binding;

    public IndexFieldInputHolder(SingleItemIndexFieldInputBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}