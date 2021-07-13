package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemAxisBinding;

import org.json.JSONException;

import java.util.ArrayList;

public class AxisAdapter extends RecyclerView.Adapter<AxisAdapter.AxisHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private ArrayList<String> ids, titles;

    public AxisAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public AxisHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AxisHolder(SingleItemAxisBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AxisHolder holder, int i) {
        TypeModel item = items.get(i);

        listener(holder, i);

        setData(holder, i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<TypeModel> getItems() {
        return items;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setItems(ArrayList<TypeModel> items, ArrayList<String> ids, ArrayList<String> titles) {
        this.items = items;
        this.ids = ids;
        this.titles = titles;
        notifyDataSetChanged();
    }

    public void clearItems() {
        items.clear();
        ids.clear();
        titles.clear();
        notifyDataSetChanged();
    }

    public void addItem(TypeModel item) {
        try {
            items.add(item);
            ids.add(item.object.get("id").toString());
            titles.add(item.object.get("title").toString());
            notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void replaceItem(int position, TypeModel item) {
        try {
            items.set(position, item);
            ids.set(position, item.object.get("id").toString());
            titles.set(position, item.object.get("title").toString());
            notifyItemChanged(position);
            notifyItemRangeChanged(position, getItemCount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int position) {
        items.remove(position);
        ids.remove(position);
        titles.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(AxisHolder holder, int position) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!holder.binding.inputEditText.hasFocus()) {
                    ((MainActivity) activity).controlEditText.select(activity, holder.binding.inputEditText);
                }
            }
            return false;
        });

        holder.binding.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            String value = holder.binding.inputEditText.getText().toString().trim();

            titles.set(position, value);
        });
    }

    private void setData(AxisHolder holder, int position) {
        holder.binding.headerTextView.setText(activity.getResources().getString(R.string.CreateSchedulePaymentTabAxisTotal) + " " + ids.get(position));

        holder.binding.inputEditText.setText(titles.get(position));
    }

    public class AxisHolder extends RecyclerView.ViewHolder {

        private SingleItemAxisBinding binding;

        public AxisHolder(SingleItemAxisBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}