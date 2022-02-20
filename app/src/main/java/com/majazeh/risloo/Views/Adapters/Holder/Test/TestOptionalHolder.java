package com.majazeh.risloo.views.adapters.holder.test;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTestOptionalBinding;

public class TestOptionalHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTestOptionalBinding binding;

    public TestOptionalHolder(SingleItemTestOptionalBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}