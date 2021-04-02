package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemFormBinding;

public class FormsAdapter extends RecyclerView.Adapter<FormsAdapter.FormsHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Form> forms;
    private boolean editable = false;

    public FormsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public FormsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FormsHolder(SingleItemFormBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FormsHolder holder, int i) {
//        Forms form = forms.get(i);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return forms.size();
        return 5;
    }

//    public void setForm(ArrayList<Form> forms) {
//        this.forms = forms;
//        notifyDataSetChanged();
//    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(FormsHolder holder) {
        if (editable) {
            holder.binding.inputEditText.setOnTouchListener((v, event) -> {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    if (!holder.binding.inputEditText.hasFocus()) {
                        ((MainActivity) activity).controlEditText.select(activity, holder.binding.inputEditText);
                    }
                }
                return false;
            });
        }
    }

    private void setData(FormsHolder holder) {
        if (editable) {
            holder.binding.inputEditText.setFocusableInTouchMode(true);
            holder.binding.selectTextView.setEnabled(true);
        } else {
            holder.binding.inputEditText.setFocusableInTouchMode(false);
            holder.binding.selectTextView.setEnabled(false);
        }

        holder.binding.headerTextView.setText("عنوان");
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyDataSetChanged();
    }

    public class FormsHolder extends RecyclerView.ViewHolder {

        private SingleItemFormBinding binding;

        public FormsHolder(SingleItemFormBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}