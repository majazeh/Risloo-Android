package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemRoomUserBinding;

public class RoomUsersHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemRoomUserBinding binding;

    public RoomUsersHolder(SingleItemRoomUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}