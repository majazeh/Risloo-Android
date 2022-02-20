package com.majazeh.risloo.views.adapters.holder.test;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTestPreSelectBinding;

public class TestPreSelectHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTestPreSelectBinding binding;

    public TestPreSelectHolder(SingleItemTestPreSelectBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}