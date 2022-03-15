package com.majazeh.risloo.utils.widgets.classes;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.majazeh.risloo.utils.widgets.interfaces.DiffUtilStringAdapter;

public class DiffUtilStringCallback extends DiffUtil.ItemCallback<String> {

    // Interfaces
    private final DiffUtilStringAdapter adapter;

    /*
    ---------- Method's ----------
    */

    @Override
    public boolean areItemsTheSame(@NonNull String oldString, @NonNull String newString) {
        return adapter.areItemsTheSame(oldString, newString);
    }

    @Override
    public boolean areContentsTheSame(@NonNull String oldString, @NonNull String newString) {
        return adapter.areContentsTheSame(oldString, newString);
    }

    /*
    ---------- Custom's ----------
    */

    public DiffUtilStringCallback(DiffUtilStringAdapter adapter) {
        this.adapter = adapter;
    }

}