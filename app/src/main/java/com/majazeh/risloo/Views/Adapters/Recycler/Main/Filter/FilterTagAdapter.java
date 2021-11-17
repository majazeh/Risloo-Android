package com.majazeh.risloo.Views.Adapters.Recycler.Main.Filter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Filter.FilterTagHolder;
import com.majazeh.risloo.Views.Fragments.Main.Show.RoomFragment;
import com.majazeh.risloo.databinding.SingleItemFilterTagBinding;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class FilterTagAdapter extends RecyclerView.Adapter<FilterTagHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private ArrayList<String> ids = new ArrayList<>();

    public FilterTagAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public FilterTagHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FilterTagHolder(SingleItemFilterTagBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilterTagHolder holder, int i) {
        TagModel model = (TagModel) items.get(i);

        intializer();

        listener(holder, model);

        setData(holder, model);
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

    public void setItems(ArrayList<TypeModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            items.clear();
            ids.clear();
            notifyDataSetChanged();
        }
    }

    private void intializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    private void listener(FilterTagHolder holder, TagModel model) {
        holder.binding.getRoot().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                ids.add(model.getId());
            else
                ids.remove(model.getId());

            ((RoomFragment) current).responseAdapter();
        });
    }

    private void setData(FilterTagHolder holder, TagModel model) {
        holder.binding.getRoot().setText(model.getTitle());
    }

}