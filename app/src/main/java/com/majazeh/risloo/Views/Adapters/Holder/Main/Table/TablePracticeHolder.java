package com.majazeh.risloo.Views.Adapters.Holder.Main.Table;

import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTablePracticeBinding;

public class TablePracticeHolder extends RecyclerView.ViewHolder {

    // Binding
    public SingleItemTablePracticeBinding binding;

    public TablePracticeHolder(SingleItemTablePracticeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}