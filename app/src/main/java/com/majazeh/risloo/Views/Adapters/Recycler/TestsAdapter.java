package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTestBinding;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.TestsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public TestsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public TestsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TestsHolder(SingleItemTestBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestsHolder holder, int i) {
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

    private void setData(TestsHolder holder, ScaleModel model) {
        holder.binding.getRoot().setText(model.getTitle());
    }

    public class TestsHolder extends RecyclerView.ViewHolder {

        private SingleItemTestBinding binding;

        public TestsHolder(SingleItemTestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}