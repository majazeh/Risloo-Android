package com.majazeh.risloo.Views.Adapters.Holder.Sheet;

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