package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemCheckedBinding;

public class CheckedHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemCheckedBinding binding;

    public CheckedHolder(SingleItemCheckedBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}