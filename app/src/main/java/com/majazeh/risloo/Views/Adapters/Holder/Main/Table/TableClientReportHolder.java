package com.majazeh.risloo.views.adapters.holder.main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTableClientReportBinding;

public class TableClientReportHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTableClientReportBinding binding;

    public TableClientReportHolder(SingleItemTableClientReportBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}