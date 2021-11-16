package com.majazeh.risloo.Views.Adapters.Recycler.Main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Index.IndexDownloadHolder;
import com.majazeh.risloo.databinding.SingleItemIndexDownloadBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IndexDownloadAdapter extends RecyclerView.Adapter<IndexDownloadHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<String> items;

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
//        String item = items.get(i);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 4;
    }

    public void setItems(ArrayList<String> items) {
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

    private void setData(IndexDownloadHolder holder) {
        holder.binding.titleTextView.setText("فايل");

        Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(holder.binding.avatarImageView);
    }

}