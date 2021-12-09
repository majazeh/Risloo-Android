package com.majazeh.risloo.Utils.Interfaces;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.mre.ligheh.Model.TypeModel.TypeModel;

public class DiffUtilTypeModelCallback extends DiffUtil.ItemCallback<TypeModel> {

    // Interfaces
    private final MyDiffUtilAdapter adapter;

    // Constructor
    public DiffUtilTypeModelCallback(MyDiffUtilAdapter adapter) {
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