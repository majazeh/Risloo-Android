package com.majazeh.risloo.Views.Adapters.Holder.Test;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTestPrerequisiteBinding;

public class TestPrerequisiteHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTestPrerequisiteBinding binding;

    public TestPrerequisiteHolder(SingleItemTestPrerequisiteBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}