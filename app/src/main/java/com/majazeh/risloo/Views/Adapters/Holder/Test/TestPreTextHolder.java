package com.majazeh.risloo.Views.adapters.holder.test;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTestPreTextBinding;

public class TestPreTextHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTestPreTextBinding binding;

    public TestPreTextHolder(SingleItemTestPreTextBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}