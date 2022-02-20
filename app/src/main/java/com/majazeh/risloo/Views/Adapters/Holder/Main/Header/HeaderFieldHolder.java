package com.majazeh.risloo.views.adapters.holder.main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableFieldBinding;

public class HeaderFieldHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableFieldBinding binding;

    public HeaderFieldHolder(HeaderItemTableFieldBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}