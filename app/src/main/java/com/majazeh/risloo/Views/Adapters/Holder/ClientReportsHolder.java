package com.majazeh.risloo.Views.Adapters.Holder;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemClientReportBinding;

public class ClientReportsHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemClientReportBinding binding;

    public ClientReportsHolder(SingleItemClientReportBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}