package com.majazeh.risloo.Views.Adapters.Holder.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexUserBinding;

public class IndexUserHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexUserBinding binding;

    public IndexUserHolder(SingleItemIndexUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}