package com.majazeh.risloo.Views.adapters.recycler.main.Filter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.utils.interfaces.DiffUtilTypeModelAdapter;
import com.majazeh.risloo.utils.interfaces.DiffUtilTypeModelCallback;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.holder.main.Filter.FilterTagHolder;
import com.majazeh.risloo.Views.fragments.main.show.RoomFragment;
import com.majazeh.risloo.databinding.SingleItemFilterTagBinding;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class FilterTagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DiffUtilTypeModelAdapter {

    // Activity
    private final Activity activity;

    // Differ
    private final AsyncListDiffer<TypeModel> differ;

    // Vars
    private final ArrayList<String> ids = new ArrayList<>();

    // Fragments
    private Fragment current;

    public FilterTagAdapter(@NonNull Activity activity) {
        this.activity = activity;

        differ = new AsyncListDiffer<>(this, new DiffUtilTypeModelCallback(this));
    }

    @NonNull
    @Override
    public FilterTagHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FilterTagHolder(SingleItemFilterTagBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        TagModel model = (TagModel) differ.getCurrentList().get(i);

        intializer();

        listener((FilterTagHolder) holder, model);

        setData((FilterTagHolder) holder, model);
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

    public ArrayList<String> getIds() {
        return ids;
    }

    @Override
    public boolean areItemsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        TagModel oldModel = (TagModel) oldTypeModel;
        TagModel newModel = (TagModel) newTypeModel;

        return newModel.getId().equals(oldModel.getId());
    }

    @Override
    public boolean areContentsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        TagModel oldModel = (TagModel) oldTypeModel;
        TagModel newModel = (TagModel) newTypeModel;

        return newModel.compareTo(oldModel);
    }

}