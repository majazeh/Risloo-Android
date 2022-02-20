package com.majazeh.risloo.views.adapters.holder.main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTablePracticeBinding;

public class HeaderPracticeHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTablePracticeBinding binding;

    public HeaderPracticeHolder(HeaderItemTablePracticeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}