package com.majazeh.risloo.utils.interfaces;

import com.mre.ligheh.Model.TypeModel.TypeModel;

public interface DiffUtilTypeModelAdapter {

    boolean areItemsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel);

    boolean areContentsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel);

}