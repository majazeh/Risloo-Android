package com.majazeh.risloo.Views.Adapters.Holder.Main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexTagBinding;

public class IndexTagHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexTagBinding binding;

    public IndexTagHolder(SingleItemIndexTagBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}