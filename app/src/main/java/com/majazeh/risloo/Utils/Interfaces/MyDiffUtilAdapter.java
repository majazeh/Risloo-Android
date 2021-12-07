package com.majazeh.risloo.Utils.Interfaces;

import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public interface MyDiffUtilAdapter {

    boolean isItemsTheSame(ArrayList<TypeModel> oldList, ArrayList<TypeModel> newList, int oldItemPosition, int newItemPosition);

    boolean isContentsTheSame(ArrayList<TypeModel> oldList, ArrayList<TypeModel> newList, int oldItemPosition, int newItemPosition);

}