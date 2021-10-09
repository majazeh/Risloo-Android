package com.majazeh.risloo.Views.Adapters.Holder.Main;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemProfileBinding;

public class ProfilesHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemProfileBinding binding;

    public ProfilesHolder(SingleItemProfileBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}