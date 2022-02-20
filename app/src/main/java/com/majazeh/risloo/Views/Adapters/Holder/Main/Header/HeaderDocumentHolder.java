package com.majazeh.risloo.views.adapters.holder.main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableDocumentBinding;

public class HeaderDocumentHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableDocumentBinding binding;

    public HeaderDocumentHolder(HeaderItemTableDocumentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}