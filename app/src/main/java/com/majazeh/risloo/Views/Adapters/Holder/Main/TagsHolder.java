package com.majazeh.risloo.Views.Adapters.Holder.Main;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTagBinding;

public class TagsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTagBinding binding;

    public TagsHolder(SingleItemTagBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}