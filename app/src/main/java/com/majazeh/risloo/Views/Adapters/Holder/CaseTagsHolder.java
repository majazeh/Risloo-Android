package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemCaseTagBinding;

public class CaseTagsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemCaseTagBinding binding;

    public CaseTagsHolder(SingleItemCaseTagBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}