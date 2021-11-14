package com.majazeh.risloo.Views.Adapters.Holder.Main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexCenterBinding;

public class IndexCenterHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexCenterBinding binding;

    public IndexCenterHolder(SingleItemIndexCenterBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}