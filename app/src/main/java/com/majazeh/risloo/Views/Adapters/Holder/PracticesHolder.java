package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemPracticeBinding;

public class PracticesHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemPracticeBinding binding;

    public PracticesHolder(SingleItemPracticeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}