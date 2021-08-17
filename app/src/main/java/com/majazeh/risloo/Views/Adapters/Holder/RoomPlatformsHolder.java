package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemRoomPlatformBinding;

public class RoomPlatformsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemRoomPlatformBinding binding;

    public RoomPlatformsHolder(SingleItemRoomPlatformBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}