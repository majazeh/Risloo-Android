package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.databinding.SingleItemPrerequisiteBinding;

public class PrerequisitesAdapter extends RecyclerView.Adapter<PrerequisitesAdapter.PrerequisitesHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Prerequisite> forms;

    public PrerequisitesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PrerequisitesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PrerequisitesHolder(SingleItemPrerequisiteBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PrerequisitesHolder holder, int i) {
//        Prerequisites prerequisite = prerequisites.get(i);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return prerequisites.size();
        return 4;
    }

//    public void setPrerequisites(ArrayList<Prerequisite> prerequisites) {
//        this.prerequisites = prerequisites;
//        notifyDataSetChanged();
//    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(PrerequisitesHolder holder) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!holder.binding.inputEditText.hasFocus()) {
                    ((TestActivity) activity).controlEditText.select(activity, holder.binding.inputEditText);
                }
            }
            return false;
        });
    }

    private void setData(PrerequisitesHolder holder) {
        holder.binding.headerTextView.setText("عنوان");
    }

    public class PrerequisitesHolder extends RecyclerView.ViewHolder {

        private SingleItemPrerequisiteBinding binding;

        public PrerequisitesHolder(SingleItemPrerequisiteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}