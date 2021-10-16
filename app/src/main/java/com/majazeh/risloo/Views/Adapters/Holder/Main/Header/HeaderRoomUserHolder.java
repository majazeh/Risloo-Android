package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableRoomUserBinding;

public class HeaderRoomUserHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableRoomUserBinding binding;

    public HeaderRoomUserHolder(HeaderItemTableRoomUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}