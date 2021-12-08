package com.majazeh.risloo.Utils.Interfaces;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class MyDiffUtilCallback2 extends DiffUtil.ItemCallback<String> {

    // Interfaces
    private final MyDiffUtilAdapter2 adapter;

    // Constructor
    public MyDiffUtilCallback2(MyDiffUtilAdapter2 adapter) {
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