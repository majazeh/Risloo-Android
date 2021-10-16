package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

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