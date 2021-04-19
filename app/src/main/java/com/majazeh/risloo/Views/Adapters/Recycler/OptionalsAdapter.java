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

public class OptionalsAdapter extends RecyclerView.Adapter<OptionalsAdapter.OptionalsHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Optional> optionals;

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
//        Optionals optional = optionals.get(i);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return optionals.size();
        return 5;
    }

//    public void setOptional(ArrayList<Optional> optionals) {
//        this.optionals = optionals;
//        notifyDataSetChanged();
//    }

    private void detector(OptionalsHolder holder, boolean selected) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (selected)
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_blue600_ripple_gray300);
            else
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);
        } else {
            if (selected)
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_blue600);
            else
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200);
        }
    }

    private void listener(OptionalsHolder holder) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(OptionalsHolder holder) {
        holder.binding.numberTextView.setText(String.valueOf(holder.getAdapterPosition() + 1));
        holder.binding.answerTextView.setText("مطمئن نیستم");

        if (holder.getAdapterPosition() == 0) {
            detector(holder, true);

            holder.binding.containerConstraintLayout.setEnabled(true);
            holder.binding.containerConstraintLayout.setClickable(true);

            holder.binding.numberTextView.setTextColor(activity.getResources().getColor(R.color.White));
            holder.binding.numberTextView.setBackgroundResource(R.drawable.draw_oval_solid_blue600);
        } else {
            detector(holder, false);

            holder.binding.containerConstraintLayout.setEnabled(false);
            holder.binding.containerConstraintLayout.setClickable(false);

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