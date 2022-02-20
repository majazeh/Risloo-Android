package com.majazeh.risloo.views.adapters.holder.main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexRoomBinding;

public class IndexRoomHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexRoomBinding binding;

    public IndexRoomHolder(SingleItemIndexRoomBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}