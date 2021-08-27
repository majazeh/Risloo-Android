package com.majazeh.risloo.Views.Adapters.Holder.Test;

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