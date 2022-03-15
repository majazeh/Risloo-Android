package com.majazeh.risloo.views.adapters.recycler.sheet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.utils.widgets.interfaces.DiffUtilTypeModelAdapter;
import com.majazeh.risloo.utils.widgets.classes.DiffUtilTypeModelCallback;
import com.majazeh.risloo.views.adapters.holder.sheet.SheetScaleHolder;
import com.majazeh.risloo.databinding.SingleItemSheetScaleBinding;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class SheetScaleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DiffUtilTypeModelAdapter {

    // Activity
    private final Activity activity;

    // Differ
    private final AsyncListDiffer<TypeModel> differ;

    public SheetScaleAdapter(@NonNull Activity activity) {
        this.activity = activity;

        differ = new AsyncListDiffer<>(this, new DiffUtilTypeModelCallback(this));
    }

    @NonNull
    @Override
    public SheetScaleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SheetScaleHolder(SingleItemSheetScaleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ScaleModel model = (ScaleModel) differ.getCurrentList().get(i);

        setData((SheetScaleHolder) holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.differ.getCurrentList() != null)
            return differ.getCurrentList().size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        differ.submitList(items);
    }

    private void setData(SheetScaleHolder holder, ScaleModel model) {
        holder.binding.getRoot().setText(model.getTitle());
    }

    @Override
    public boolean areItemsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        ScaleModel oldModel = (ScaleModel) oldTypeModel;
        ScaleModel newModel = (ScaleModel) newTypeModel;

        return newModel.getId().equals(oldModel.getId());
    }

    @Override
    public boolean areContentsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        ScaleModel oldModel = (ScaleModel) oldTypeModel;
        ScaleModel newModel = (ScaleModel) newTypeModel;

        return newModel.compareTo(oldModel);
    }

}