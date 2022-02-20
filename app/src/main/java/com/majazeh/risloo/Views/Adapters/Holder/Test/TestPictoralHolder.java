package com.majazeh.risloo.views.adapters.holder.test;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTestPictoralBinding;

public class TestPictoralHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTestPictoralBinding binding;

    public TestPictoralHolder(SingleItemTestPictoralBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}