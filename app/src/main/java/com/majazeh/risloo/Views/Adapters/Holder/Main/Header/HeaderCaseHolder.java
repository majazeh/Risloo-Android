package com.majazeh.risloo.Views.adapters.holder.main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableCaseBinding;

public class HeaderCaseHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableCaseBinding binding;

    public HeaderCaseHolder(HeaderItemTableCaseBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}