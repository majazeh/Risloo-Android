package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemAxisPaymentBinding;

import org.json.JSONException;

import java.util.ArrayList;

public class AxisPaymentsAdapter extends RecyclerView.Adapter<AxisPaymentsAdapter.AxisPaymentsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<Model> items;
    private ArrayList<String> ids;

    public AxisPaymentsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public AxisPaymentsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AxisPaymentsHolder(SingleItemAxisPaymentBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AxisPaymentsHolder holder, int i) {
        Model item = items.get(i);

        listener(holder, i);

        setData(holder, item, i);
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

    public void setItems(ArrayList<Model> items, ArrayList<String> ids) {
        this.items = items;
        this.ids = ids;
        notifyDataSetChanged();
    }

    public void clearItems() {
        items.clear();
        ids.clear();
        notifyDataSetChanged();
    }

    public void addItem(Model item) {
        try {
            items.add(item);
            ids.add(item.get("id").toString());
            notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void replaceItem(int position, Model item) {
        try {
            items.set(position, item);
            ids.set(position, item.get("id").toString());
            notifyItemChanged(position);
            notifyItemRangeChanged(position, getItemCount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int position) {
        items.remove(position);
        ids.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(AxisPaymentsHolder holder, int position) {
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

            ids.set(position, value);
        });
    }

    private void setData(AxisPaymentsHolder holder, Model item, int position) {
        try {
            holder.binding.headerTextView.setText(activity.getResources().getString(R.string.CreateSchedulePaymentTabAxisTotal) + " " + item.get("title").toString());

            if (!ids.get(position).equals("")) {
                holder.binding.inputEditText.setText(ids.get(position));
            } else {
                holder.binding.inputEditText.setText("");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class AxisPaymentsHolder extends RecyclerView.ViewHolder {

        private SingleItemAxisPaymentBinding binding;

        public AxisPaymentsHolder(SingleItemAxisPaymentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}