package com.majazeh.risloo.views.adapters.holder.main.Create;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemCreateCheckBinding;

public class CreateCheckHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemCreateCheckBinding binding;

    public CreateCheckHolder(SingleItemCreateCheckBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}