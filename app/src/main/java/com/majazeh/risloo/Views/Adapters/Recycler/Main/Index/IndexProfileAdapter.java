package com.majazeh.risloo.views.adapters.recycler.main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.interfaces.CustomClickView;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.views.adapters.holder.main.Index.IndexProfileHolder;
import com.majazeh.risloo.databinding.SingleItemIndexProfileBinding;
import com.mre.ligheh.Model.TypeModel.ProfileModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IndexProfileAdapter extends RecyclerView.Adapter<IndexProfileHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean showTitle = false;

    public IndexProfileAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexProfileHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexProfileHolder(SingleItemIndexProfileBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexProfileHolder holder, int i) {
        ProfileModel model = (ProfileModel) items.get(i);

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

    public void setItems(ArrayList<TypeModel> items, boolean showTitle) {
        this.showTitle = showTitle;

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

    private void listener(IndexProfileHolder holder, ProfileModel model) {
        CustomClickView.onDelayedListener(() -> {
            IntentManager.display(activity, JsonManager.getProfileExtras(activity, "fa", getFileNameSub(model.getFileName())), model.getUrl());
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexProfileHolder holder, ProfileModel model) {
        Picasso.get().load(model.getUrl()).placeholder(R.color.coolGray100).into(holder.binding.avatarImageView);

        if (showTitle) {
            holder.binding.avatarTextView.setText(JsonManager.getProfileExtras(activity, "fa", getFileNameSub(model.getFileName())));
            holder.binding.avatarTextView.setVisibility(View.VISIBLE);
        } else {
            holder.binding.avatarTextView.setText("");
            holder.binding.avatarTextView.setVisibility(View.GONE);
        }
    }

    private String getFileNameSub(String fileName) {
        if (fileName.contains("items_sort_key.png"))
            return "items_sort_key.png";
        else if (fileName.contains("items_sort_value.png"))
            return "items_sort_value.png";
        else if (fileName.contains(".nv.png"))
            return ".nv.png";
        else if (fileName.contains(".critical.png"))
            return ".critical.png";
        else if (fileName.contains(".sup.png"))
            return ".sup.png";
        else if (fileName.contains(".hl.png"))
            return ".hl.png";
        else if (fileName.contains(".si.png"))
            return ".si.png";
        else if (fileName.contains(".content.png"))
            return ".content.png";
        else
            return "نامعلوم";
    }

}