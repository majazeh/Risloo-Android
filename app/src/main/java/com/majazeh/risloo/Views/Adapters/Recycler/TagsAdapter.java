package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTagBinding;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public TagsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public TagsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TagsHolder(SingleItemTagBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TagsHolder holder, int i) {
        setData(holder);
    }

    @Override
    public int getItemCount() {
        return 9;
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

    private void setData(TagsHolder holder) {
        holder.binding.indexTextView.setText(String.valueOf(holder.getBindingAdapterPosition() + 1));
        holder.binding.valueTextView.setText("");

        for (TypeModel item : items) {
            TagModel model = (TagModel) item;

            if (model.getOrder() == holder.getBindingAdapterPosition() + 1)
                holder.binding.valueTextView.setText(model.getTitle());
        }
    }

    public class TagsHolder extends RecyclerView.ViewHolder {

        private SingleItemTagBinding binding;

        public TagsHolder(SingleItemTagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}