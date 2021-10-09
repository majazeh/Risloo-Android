package com.majazeh.risloo.Views.Adapters.Holder.Main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexSampleBinding;

public class IndexSampleHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexSampleBinding binding;

    public IndexSampleHolder(SingleItemIndexSampleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}