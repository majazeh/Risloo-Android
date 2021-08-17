package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemAxisBinding;

public class AxisHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemAxisBinding binding;

    public AxisHolder(SingleItemAxisBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}