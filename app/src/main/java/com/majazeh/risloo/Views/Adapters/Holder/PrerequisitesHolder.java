package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemPrerequisiteBinding;

public class PrerequisitesHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemPrerequisiteBinding binding;

    public PrerequisitesHolder(SingleItemPrerequisiteBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}