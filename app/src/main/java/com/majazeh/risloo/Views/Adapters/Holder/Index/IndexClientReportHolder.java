package com.majazeh.risloo.Views.Adapters.Holder.Index;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemIndexClientReportBinding;

public class IndexClientReportHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemIndexClientReportBinding binding;

    public IndexClientReportHolder(SingleItemIndexClientReportBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}