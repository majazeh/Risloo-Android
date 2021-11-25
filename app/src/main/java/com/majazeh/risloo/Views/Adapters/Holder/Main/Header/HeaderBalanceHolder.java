package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableBalanceBinding;

public class HeaderBalanceHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableBalanceBinding binding;

    public HeaderBalanceHolder(HeaderItemTableBalanceBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}