package com.majazeh.risloo.Views.Adapters.Holder.Sheet;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemSheetFilterBinding;

public class SheetFilterHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemSheetFilterBinding binding;

    public SheetFilterHolder(SingleItemSheetFilterBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}