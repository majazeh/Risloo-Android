package com.majazeh.risloo.views.adapters.holder.main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableCenterUserBinding;

public class HeaderCenterUserHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableCenterUserBinding binding;

    public HeaderCenterUserHolder(HeaderItemTableCenterUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}