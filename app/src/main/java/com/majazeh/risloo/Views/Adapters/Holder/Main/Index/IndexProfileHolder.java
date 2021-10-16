package com.majazeh.risloo.Views.Adapters.Holder.Main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexProfileBinding;

public class IndexProfileHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexProfileBinding binding;

    public IndexProfileHolder(SingleItemIndexProfileBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}