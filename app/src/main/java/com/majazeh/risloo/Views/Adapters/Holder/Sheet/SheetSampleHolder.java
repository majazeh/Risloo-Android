package com.majazeh.risloo.Views.Adapters.Holder.Sheet;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemSheetSampleBinding;

public class SheetSampleHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemSheetSampleBinding binding;

    public SheetSampleHolder(SingleItemSheetSampleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}