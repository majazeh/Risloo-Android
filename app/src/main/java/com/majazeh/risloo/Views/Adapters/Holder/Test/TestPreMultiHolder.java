package com.majazeh.risloo.Views.adapters.holder.test;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTestPreMultiBinding;

public class TestPreMultiHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTestPreMultiBinding binding;

    public TestPreMultiHolder(SingleItemTestPreMultiBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}