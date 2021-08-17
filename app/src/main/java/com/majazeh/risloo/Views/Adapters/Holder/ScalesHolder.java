package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemScaleBinding;

public class ScalesHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemScaleBinding binding;

    public ScalesHolder(SingleItemScaleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}