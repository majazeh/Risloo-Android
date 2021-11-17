package com.majazeh.risloo.Views.Adapters.Holder.Main.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexScheduleBinding;

public class IndexScheduleHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexScheduleBinding binding;

    public IndexScheduleHolder(SingleItemIndexScheduleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}