package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemSessionBinding;

public class SessionsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemSessionBinding binding;

    public SessionsHolder(SingleItemSessionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}