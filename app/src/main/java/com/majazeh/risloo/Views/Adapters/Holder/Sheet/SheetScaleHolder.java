package com.majazeh.risloo.views.adapters.holder.sheet;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemSheetScaleBinding;

public class SheetScaleHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemSheetScaleBinding binding;

    public SheetScaleHolder(SingleItemSheetScaleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}