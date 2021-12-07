package com.majazeh.risloo.Utils.Interfaces;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.mre.ligheh.Model.TypeModel.TypeModel;

public class MyDiffUtilCallback extends DiffUtil.ItemCallback<TypeModel> {

    // Interfaces
    private final MyDiffUtilAdapter adapter;

    // Constructor
    public MyDiffUtilCallback(MyDiffUtilAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean areItemsTheSame(@NonNull TypeModel oldTypeModel, @NonNull TypeModel newTypeModel) {
        return adapter.areItemsTheSame(oldTypeModel, newTypeModel);
    }

    @Override
    public boolean areContentsTheSame(@NonNull TypeModel oldTypeModel, @NonNull TypeModel newTypeModel) {
        return adapter.areContentsTheSame(oldTypeModel, newTypeModel);
    }

}