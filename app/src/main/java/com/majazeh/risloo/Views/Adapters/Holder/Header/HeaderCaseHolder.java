package com.majazeh.risloo.Views.Adapters.Holder.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexCaseBinding;

public class HeaderCaseHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexCaseBinding binding;

    public HeaderCaseHolder(HeaderItemIndexCaseBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}