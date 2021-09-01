package com.majazeh.risloo.Views.Adapters.Holder.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexFieldSelectBinding;

public class IndexFieldSelectHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexFieldSelectBinding binding;

    public IndexFieldSelectHolder(SingleItemIndexFieldSelectBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}