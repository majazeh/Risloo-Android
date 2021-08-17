package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemCenterUserBinding;

public class CenterUsersHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemCenterUserBinding binding;

    public CenterUsersHolder(SingleItemCenterUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}