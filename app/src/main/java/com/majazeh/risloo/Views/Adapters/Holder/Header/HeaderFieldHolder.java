package com.majazeh.risloo.Views.Adapters.Holder.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexFieldBinding;

public class HeaderFieldHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexFieldBinding binding;

    public HeaderFieldHolder(HeaderItemIndexFieldBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}