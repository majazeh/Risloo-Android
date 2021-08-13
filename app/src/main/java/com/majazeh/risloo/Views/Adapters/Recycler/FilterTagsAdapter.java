package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemFilterTagBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class FilterTagsAdapter extends RecyclerView.Adapter<FilterTagsAdapter.FilterTagsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private ArrayList<String> ids;

    public FilterTagsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public FilterTagsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FilterTagsHolder(SingleItemFilterTagBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilterTagsHolder holder, int i) {
//        TagModel model = (TagModel) items.get(i);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setItems(ArrayList<TypeModel> items, ArrayList<String> ids) {
        this.items = items;
        this.ids = ids;
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            items.clear();
            ids.clear();
            notifyDataSetChanged();
        }
    }

    private void listener(FilterTagsHolder holder) {
        // TODO Place Code When Needed
    }

    private void setData(FilterTagsHolder holder) {
        holder.binding.getRoot().setText("برچسب");
    }

    public class FilterTagsHolder extends RecyclerView.ViewHolder {

        private SingleItemFilterTagBinding binding;

        public FilterTagsHolder(SingleItemFilterTagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}