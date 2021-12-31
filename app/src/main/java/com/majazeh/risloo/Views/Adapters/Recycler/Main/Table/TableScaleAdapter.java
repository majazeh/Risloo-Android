package com.majazeh.risloo.Views.Adapters.Recycler.Main.Table;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Utils.Interfaces.DiffUtilTypeModelAdapter;
import com.majazeh.risloo.Utils.Interfaces.DiffUtilTypeModelCallback;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Header.HeaderScaleHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Table.TableScaleHolder;
import com.majazeh.risloo.Views.Fragments.Main.Index.ScalesFragment;
import com.majazeh.risloo.databinding.HeaderItemTableScaleBinding;
import com.majazeh.risloo.databinding.SingleItemTableScaleBinding;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class TableScaleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DiffUtilTypeModelAdapter {

    // Activity
    private final Activity activity;

    // Differ
    private final AsyncListDiffer<TypeModel> differ;

    // Fragments
    private Fragment current;

    public TableScaleAdapter(@NonNull Activity activity) {
        this.activity = activity;

        differ = new AsyncListDiffer<>(this, new DiffUtilTypeModelCallback(this));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderScaleHolder(HeaderItemTableScaleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableScaleHolder(SingleItemTableScaleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof TableScaleHolder) {
            ScaleModel model = (ScaleModel) differ.getCurrentList().get(i - 1);

            initializer();

            listener((TableScaleHolder) holder, model);

            setPermission((TableScaleHolder) holder);

            setData((TableScaleHolder) holder, model);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        return 1;
    }

    @Override
    public int getItemCount() {
        if (this.differ.getCurrentList() != null)
            return differ.getCurrentList().size() + 1;
        else
            return 0;
    }

    public int itemsCount() {
        if (this.differ.getCurrentList() != null)
            return differ.getCurrentList().size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        differ.submitList(items);
    }

    private void initializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    private void listener(TableScaleHolder holder, ScaleModel model) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        CustomClickView.onClickListener(() -> ((MainActivity) activity).navigatoon.navigateToCreateSampleFragment(model)).widget(holder.binding.createTextView);
    }

    private void setPermission(TableScaleHolder holder) {
        UserModel model = ((MainActivity) activity).singleton.getUserModel();

        if (current instanceof ScalesFragment && ((MainActivity) activity).permissoon.showScalesCreateSample(model))
            holder.binding.createTextView.setVisibility(View.VISIBLE);
        else
            holder.binding.createTextView.setVisibility(View.GONE);
    }

    private void setData(TableScaleHolder holder, ScaleModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.nameTextView.setText(model.getTitle());

        if (!model.getEdition().equals("") && !model.getVersion().equals("")) {
            String edition = model.getEdition() + " - نسخه " + model.getVersion();
            holder.binding.editionTextView.setText(edition);
        } else if (!model.getVersion().equals("")) {
            String edition = "نسخه " + model.getVersion();
            holder.binding.editionTextView.setText(edition);
        } else if (!model.getEdition().equals("")) {
            holder.binding.editionTextView.setText(model.getEdition());
        } else {
            holder.binding.editionTextView.setText("-");
        }
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