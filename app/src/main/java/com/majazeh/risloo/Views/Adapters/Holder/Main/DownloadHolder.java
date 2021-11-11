package com.majazeh.risloo.Views.Adapters.Holder.Main;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemDownloadBinding;

public class DownloadHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemDownloadBinding binding;

    public DownloadHolder(SingleItemDownloadBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}