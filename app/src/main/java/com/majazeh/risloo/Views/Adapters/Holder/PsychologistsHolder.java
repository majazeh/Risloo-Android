package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemPsychologyBinding;

public class PsychologistsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemPsychologyBinding binding;

    public PsychologistsHolder(SingleItemPsychologyBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}