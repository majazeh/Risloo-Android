package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemFieldBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class FieldsAdapter extends RecyclerView.Adapter<FieldsAdapter.FieldsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> fields;
    private boolean editable = false;

    public FieldsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public FieldsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FieldsHolder(SingleItemFieldBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FieldsHolder holder, int i) {
//        String field = fields.get(i);
//
//        listener(holder, field);
//
//        setData(holder, field);
    }

    @Override
    public int getItemCount() {
        if (this.fields != null)
            return fields.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> fields) {
        if (this.fields == null)
            this.fields = fields;
        else
            this.fields.addAll(fields);
        notifyDataSetChanged();
    }

    public void clearFields() {
        if (this.fields != null) {
            this.fields.clear();
            notifyDataSetChanged();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(FieldsHolder holder, String field) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (editable) {
                    if (!holder.binding.inputEditText.hasFocus()) {
                        ((MainActivity) activity).controlEditText.select(activity, holder.binding.inputEditText);
                    }
                }
            }
            return false;
        });
    }

    private void setData(FieldsHolder holder, String field) {
        if (editable) {
            holder.binding.inputEditText.setFocusableInTouchMode(true);
            holder.binding.selectSpinner.setEnabled(true);
        } else {
            holder.binding.inputEditText.setFocusableInTouchMode(false);
            holder.binding.selectSpinner.setEnabled(false);
        }

        holder.binding.headerTextView.setText(field);
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyDataSetChanged();
    }

    public class FieldsHolder extends RecyclerView.ViewHolder {

        private SingleItemFieldBinding binding;

        public FieldsHolder(SingleItemFieldBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}