package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTestBinding;

public class TestsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTestBinding binding;

    public TestsHolder(SingleItemTestBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}