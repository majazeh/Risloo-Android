package com.majazeh.risloo.Views.Adapters.Holder.Main;

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