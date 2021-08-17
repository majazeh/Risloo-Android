package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemUserBinding;

public class UsersHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemUserBinding binding;

    public UsersHolder(SingleItemUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}