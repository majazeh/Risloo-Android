package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemCaseBinding;

public class CasesHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemCaseBinding binding;

    public CasesHolder(SingleItemCaseBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}