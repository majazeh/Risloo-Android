package com.majazeh.risloo.Views.Adapters.Holder.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexCaseBinding;

public class IndexCaseHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexCaseBinding binding;

    public IndexCaseHolder(SingleItemIndexCaseBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}