package com.majazeh.risloo.utils.widgets;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.majazeh.risloo.utils.interfaces.DiffUtilTypeModelAdapter;
import com.mre.ligheh.Model.TypeModel.TypeModel;

public class DiffUtilTypeModelCallback extends DiffUtil.ItemCallback<TypeModel> {

    // Interfaces
    private final DiffUtilTypeModelAdapter adapter;

    /*
    ---------- Method's ----------
    */

    @Override
    public boolean areItemsTheSame(@NonNull TypeModel oldTypeModel, @NonNull TypeModel newTypeModel) {
        return adapter.areItemsTheSame(oldTypeModel, newTypeModel);
    }

    @Override
    public boolean areContentsTheSame(@NonNull TypeModel oldTypeModel, @NonNull TypeModel newTypeModel) {
        return adapter.areContentsTheSame(oldTypeModel, newTypeModel);
    }

    /*
    ---------- Custom's ----------
    */

    public DiffUtilTypeModelCallback(DiffUtilTypeModelAdapter adapter) {
        this.adapter = adapter;
    }

}