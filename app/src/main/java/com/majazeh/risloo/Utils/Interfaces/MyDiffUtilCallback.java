package com.majazeh.risloo.Utils.Interfaces;

import androidx.recyclerview.widget.DiffUtil;

import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class MyDiffUtilCallback extends DiffUtil.Callback {

    // Interfaces
    private final MyDiffUtilAdapter adapter;

    // Objects
    private ArrayList<TypeModel> oldList, newList;

    // Constructor
    public MyDiffUtilCallback(MyDiffUtilAdapter adapter, ArrayList<TypeModel> oldList, ArrayList<TypeModel> newList) {
        this.adapter = adapter;
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return adapter.isItemsTheSame(oldList, newList, oldItemPosition, newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return adapter.isContentsTheSame(oldList, newList, oldItemPosition, newItemPosition);
    }

}