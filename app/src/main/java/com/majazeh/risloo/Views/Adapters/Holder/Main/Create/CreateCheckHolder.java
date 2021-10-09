package com.majazeh.risloo.Views.Adapters.Holder.Main.Create;

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