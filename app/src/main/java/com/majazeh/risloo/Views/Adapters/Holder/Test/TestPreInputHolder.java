package com.majazeh.risloo.Views.Adapters.Holder.Test;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTestPreInputBinding;

public class TestPreInputHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTestPreInputBinding binding;

    public TestPreInputHolder(SingleItemTestPreInputBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}