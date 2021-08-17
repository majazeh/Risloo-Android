package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemPictoralBinding;

public class PictoralsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemPictoralBinding binding;

    public PictoralsHolder(SingleItemPictoralBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}