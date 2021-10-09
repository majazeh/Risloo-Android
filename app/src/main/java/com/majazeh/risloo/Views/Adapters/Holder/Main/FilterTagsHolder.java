package com.majazeh.risloo.Views.Adapters.Holder.Main;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemFilterTagBinding;

public class FilterTagsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemFilterTagBinding binding;

    public FilterTagsHolder(SingleItemFilterTagBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}