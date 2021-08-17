package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemOptionalBinding;

public class OptionalsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemOptionalBinding binding;

    public OptionalsHolder(SingleItemOptionalBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}