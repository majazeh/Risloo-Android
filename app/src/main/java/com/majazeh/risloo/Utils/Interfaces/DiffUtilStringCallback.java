package com.majazeh.risloo.Utils.Interfaces;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class DiffUtilStringCallback extends DiffUtil.ItemCallback<String> {

    // Interfaces
    private final DiffUtilStringAdapter adapter;

    // Constructor
    public DiffUtilStringCallback(DiffUtilStringAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean areItemsTheSame(@NonNull String oldString, @NonNull String newString) {
        return adapter.areItemsTheSame(oldString, newString);
    }

    @Override
    public boolean areContentsTheSame(@NonNull String oldString, @NonNull String newString) {
        return adapter.areContentsTheSame(oldString, newString);
    }

}