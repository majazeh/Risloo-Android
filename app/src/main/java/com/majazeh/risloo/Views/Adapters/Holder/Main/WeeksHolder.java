package com.majazeh.risloo.Views.Adapters.Holder.Main;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemWeekBinding;

public class WeeksHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemWeekBinding binding;

    public WeeksHolder(SingleItemWeekBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}