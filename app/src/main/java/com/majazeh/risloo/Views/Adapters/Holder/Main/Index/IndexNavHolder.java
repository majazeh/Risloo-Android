package com.majazeh.risloo.Views.Adapters.Holder.Main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexNavBinding;

public class IndexNavHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexNavBinding binding;

    public IndexNavHolder(SingleItemIndexNavBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}