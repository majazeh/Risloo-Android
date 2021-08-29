package com.majazeh.risloo.Views.Adapters.Holder.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexDocumentBinding;

public class HeaderDocumentHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexDocumentBinding binding;

    public HeaderDocumentHolder(HeaderItemIndexDocumentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}