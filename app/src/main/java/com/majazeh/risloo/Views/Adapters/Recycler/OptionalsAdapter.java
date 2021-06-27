package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.databinding.SingleItemOptionalBinding;

import java.util.ArrayList;

public class OptionalsAdapter extends RecyclerView.Adapter<OptionalsAdapter.OptionalsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<String> titles;

    public OptionalsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public OptionalsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OptionalsHolder(SingleItemOptionalBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OptionalsHolder holder, int i) {
        String title = titles.get(i);

        listener(holder, title);

        setData(holder, title);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public void setItems(ArrayList<String> titles) {
        this.titles = titles;
        notifyDataSetChanged();
    }

    private void detector(OptionalsHolder holder, boolean selected) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (selected)
                holder.itemView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_blue600_ripple_gray300);
            else
                holder.itemView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);
        } else {
            if (selected)
                holder.itemView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_blue600);
            else
                holder.itemView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200);
        }
    }

    private void listener(OptionalsHolder holder, String title) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.itemView);
    }

    private void setData(OptionalsHolder holder, String title) {
        holder.binding.numberTextView.setText(String.valueOf(holder.getBindingAdapterPosition() + 1));
        holder.binding.answerTextView.setText(title);

        setActive(holder, title);
    }

    private void setActive(OptionalsHolder holder, String title) {
        if (holder.getBindingAdapterPosition() == 0) {
            detector(holder, true);

            holder.itemView.setEnabled(true);
            holder.itemView.setClickable(true);

            holder.binding.numberTextView.setTextColor(activity.getResources().getColor(R.color.White));
            holder.binding.numberTextView.setBackgroundResource(R.drawable.draw_oval_solid_blue600);
        } else {
            detector(holder, false);

            holder.itemView.setEnabled(false);
            holder.itemView.setClickable(false);

            holder.binding.numberTextView.setTextColor(activity.getResources().getColor(R.color.Gray800));
            holder.binding.numberTextView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray200);
        }
    }

    public class OptionalsHolder extends RecyclerView.ViewHolder {

        private SingleItemOptionalBinding binding;

        public OptionalsHolder(SingleItemOptionalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}