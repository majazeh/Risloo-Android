package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableRoomUserBinding;

public class TableRoomUserHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableRoomUserBinding binding;

    public TableRoomUserHolder(SingleItemTableRoomUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}