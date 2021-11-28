package com.majazeh.risloo.Views.Adapters.Recycler.Main.Index;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Index.IndexDownloadHolder;
import com.majazeh.risloo.databinding.SingleItemIndexDownloadBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class IndexDownloadAdapter extends RecyclerView.Adapter<IndexDownloadHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<File> items;

    public IndexDownloadAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexDownloadHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexDownloadHolder(SingleItemIndexDownloadBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexDownloadHolder holder, int i) {
        File file = items.get(i);

        listener(holder);

        setData(holder, file);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<File> items) {
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

    private void listener(IndexDownloadHolder holder) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexDownloadHolder holder, File file) {
        if (file.getName().contains("."))
            holder.binding.titleTextView.setText(StringManager.sub(file.getName(), '.'));
        else
            holder.binding.titleTextView.setText(file.getName());

        holder.binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDnlHHsMM(file.lastModified(), " "));

        Picasso.get().load(Uri.fromFile(file)).placeholder(R.color.CoolGray100).into(holder.binding.avatarImageView);
    }

}