package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexRoomUserBinding;

public class HeaderRoomUserHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexRoomUserBinding binding;

    public HeaderRoomUserHolder(HeaderItemIndexRoomUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}