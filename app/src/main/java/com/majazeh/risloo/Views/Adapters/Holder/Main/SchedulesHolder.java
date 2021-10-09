package com.majazeh.risloo.Views.Adapters.Holder.Main;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemScheduleBinding;

public class SchedulesHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemScheduleBinding binding;

    public SchedulesHolder(SingleItemScheduleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}