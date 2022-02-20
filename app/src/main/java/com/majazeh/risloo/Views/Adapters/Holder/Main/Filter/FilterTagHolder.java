package com.majazeh.risloo.Views.adapters.holder.main.Filter;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemFilterTagBinding;

public class FilterTagHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemFilterTagBinding binding;

    public FilterTagHolder(SingleItemFilterTagBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}