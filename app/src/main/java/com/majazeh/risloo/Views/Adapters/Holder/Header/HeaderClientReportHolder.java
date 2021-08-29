package com.majazeh.risloo.Views.Adapters.Holder.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemIndexClientReportBinding;

public class HeaderClientReportHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemIndexClientReportBinding binding;

    public HeaderClientReportHolder(HeaderItemIndexClientReportBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}