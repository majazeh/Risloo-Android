package com.majazeh.risloo.Views.Adapters.Holder.Main;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemRoomBinding;

public class RoomsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemRoomBinding binding;

    public RoomsHolder(SingleItemRoomBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}