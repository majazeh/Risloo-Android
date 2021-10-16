package com.majazeh.risloo.Views.Adapters.Holder.Main.Header;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.HeaderItemTableClientReportBinding;

public class HeaderClientReportHolder extends RecyclerView.ViewHolder {

    // Binding
    public HeaderItemTableClientReportBinding binding;

    public HeaderClientReportHolder(HeaderItemTableClientReportBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}