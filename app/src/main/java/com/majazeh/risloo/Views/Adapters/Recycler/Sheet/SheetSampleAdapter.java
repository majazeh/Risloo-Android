package com.majazeh.risloo.Views.Adapters.Recycler.Sheet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Views.Adapters.Holder.Sheet.SheetSampleHolder;
import com.majazeh.risloo.databinding.SingleItemSheetSampleBinding;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class SheetSampleAdapter extends RecyclerView.Adapter<SheetSampleHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public SheetSampleAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SheetSampleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SheetSampleHolder(SingleItemSheetSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SheetSampleHolder holder, int i) {
        ScaleModel model = (ScaleModel) items.get(i);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        if (this.items == null)
            this.items = items;
        else
            this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    private void setData(SheetSampleHolder holder, ScaleModel model) {
        holder.binding.getRoot().setText(model.getTitle());
    }

}