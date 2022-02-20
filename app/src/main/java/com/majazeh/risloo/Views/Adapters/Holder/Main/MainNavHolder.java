package com.majazeh.risloo.Views.adapters.holder.main;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemMainNavBinding;

public class MainNavHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemMainNavBinding binding;

    public MainNavHolder(SingleItemMainNavBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}