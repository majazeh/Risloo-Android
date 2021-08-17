package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemSelectedBinding;

public class SelectedHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemSelectedBinding binding;

    public SelectedHolder(SingleItemSelectedBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}