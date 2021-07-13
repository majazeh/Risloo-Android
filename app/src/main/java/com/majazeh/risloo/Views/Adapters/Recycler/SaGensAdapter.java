package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Show.SampleFragment;
import com.majazeh.risloo.databinding.SingleItemSaBinding;

import java.util.ArrayList;

public class SaGensAdapter extends RecyclerView.Adapter<SaGensAdapter.SaGensHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<String> items;
    private boolean userSelect = false, editable = false;

    public SaGensAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SaGensHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SaGensHolder(SingleItemSaBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SaGensHolder holder, int i) {
        String item = items.get(i);

        listener(holder, i);

        setData(holder, item);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
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

    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyDataSetChanged();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(SaGensHolder holder, int item) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (editable) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    if (!holder.binding.inputEditText.hasFocus()) {
                        ((MainActivity) activity).controlEditText.select(activity, holder.binding.inputEditText);
                    }
                }
            }
            return false;
        });

        holder.binding.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            Fragment current = ((MainActivity) activity).fragmont.getCurrent();

            if (current instanceof SampleFragment)
                ((SampleFragment) current).sendGen("cornometer", holder.binding.inputEditText.getText().toString());
        });

        holder.binding.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    Fragment current = ((MainActivity) activity).fragmont.getCurrent();

                    if (current instanceof SampleFragment)
                        ((SampleFragment) current).sendGen("cornometer", String.valueOf(position + 1));

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(SaGensHolder holder, String item) {
        holder.binding.headerTextView.setText(activity.getResources().getString(R.string.SampleFragmentSubGeneralTime));

        setType(holder, item);

        setClickable(holder);
    }

    private void setType(SaGensHolder holder, String item) {
        holder.binding.selectGroup.setVisibility(View.GONE);

        holder.binding.inputEditText.setVisibility(View.VISIBLE);
        holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        if (!item.equals(""))
            holder.binding.inputEditText.setText(item);
    }

    private void setClickable(SaGensHolder holder) {
        if (editable) {
            holder.binding.inputEditText.setFocusableInTouchMode(true);
            holder.binding.selectSpinner.setEnabled(true);

            holder.binding.getRoot().setAlpha((float) 1);
        } else {
            holder.binding.inputEditText.setFocusableInTouchMode(false);
            holder.binding.selectSpinner.setEnabled(false);

            holder.binding.getRoot().setAlpha((float) 0.5);
        }
    }

    public class SaGensHolder extends RecyclerView.ViewHolder {

        private SingleItemSaBinding binding;

        public SaGensHolder(SingleItemSaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}