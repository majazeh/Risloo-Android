package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.databinding.PopItemRecyclerMultiBinding;

import org.json.JSONException;

import java.util.ArrayList;

public class RecyclerMultiAdapter extends RecyclerView.Adapter<RecyclerMultiAdapter.RecyclerMultiHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<Model> items;
    private ArrayList<String> ids = new ArrayList<>();
    private String method;

    public RecyclerMultiAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerMultiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerMultiHolder(PopItemRecyclerMultiBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerMultiHolder holder, int i) {
        Model item = items.get(i);

        detector(holder);

        listener(holder, i);

        setData(holder, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<Model> getItems() {
        return items;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setItems(ArrayList<Model> items, String method) {
        this.items = items;
        this.method = method;
        notifyDataSetChanged();
    }

    public void clearItems() {
        items.clear();
        ids.clear();
        notifyDataSetChanged();
    }

    public void addItem(Model item) {
        items.add(item);

        try {
            ids.add(item.get("id").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    public void replacePhone(int position, Model item) {
        items.set(position, item);

        try {
            ids.set(position, item.get("id").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        notifyItemChanged(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    private void removePhone(int position) {
        items.remove(position);
        ids.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    private void detector(RecyclerMultiHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);

            holder.binding.removeImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_gray50_ripple_red300);
        }
    }

    private void listener(RecyclerMultiHolder holder, int position) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            removePhone(position);
        }).widget(holder.binding.removeImageView);
    }

    private void setData(RecyclerMultiHolder holder, Model item) {
        try {
            if (method.equals("scales")) {
                ids.add(item.get("id").toString());

                holder.binding.titleTextView.setText(item.get("title").toString());
                holder.binding.subTextView.setText(item.get("subtitle").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class RecyclerMultiHolder extends RecyclerView.ViewHolder {

        private PopItemRecyclerMultiBinding binding;

        public RecyclerMultiHolder(PopItemRecyclerMultiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}