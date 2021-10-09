package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexPracticeBinding;

public class HeaderPracticeHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexPracticeBinding binding;

    public HeaderPracticeHolder(HeaderItemIndexPracticeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}