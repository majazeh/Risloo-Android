package com.majazeh.risloo.Views.Adapters.Recycler.Main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Adapters.Holder.Main.DownloadHolder;
import com.majazeh.risloo.databinding.SingleItemDownloadBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<String> items;

    public DownloadAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public DownloadHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DownloadHolder(SingleItemDownloadBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadHolder holder, int i) {
//        String item = items.get(i);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 5;
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

    private void listener(DownloadHolder holder) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(DownloadHolder holder) {
        holder.binding.titleTextView.setText("فايل");

        Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(holder.binding.avatarImageView);
    }

}