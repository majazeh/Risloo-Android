package com.majazeh.risloo.views.adapters.holder.main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableTreasuryBinding;

public class HeaderTreasuryHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableTreasuryBinding binding;

    public HeaderTreasuryHolder(HeaderItemTableTreasuryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}