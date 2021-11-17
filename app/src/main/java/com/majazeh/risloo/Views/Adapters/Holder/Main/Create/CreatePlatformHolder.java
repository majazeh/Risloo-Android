package com.majazeh.risloo.Views.Adapters.Holder.Main.Create;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemCreatePlatformBinding;

public class CreatePlatformHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemCreatePlatformBinding binding;

    public CreatePlatformHolder(SingleItemCreatePlatformBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}