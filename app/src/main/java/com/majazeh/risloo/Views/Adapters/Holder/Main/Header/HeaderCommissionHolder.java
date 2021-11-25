package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableCommissionBinding;

public class HeaderCommissionHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableCommissionBinding binding;

    public HeaderCommissionHolder(HeaderItemTableCommissionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}