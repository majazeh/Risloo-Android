package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemScaleBinding;

public class ScalesAdapter extends RecyclerView.Adapter<ScalesAdapter.ScalesHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Scale> scales;

    public ScalesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ScalesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ScalesHolder(SingleItemScaleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScalesHolder holder, int i) {
//        Scales scale = scales.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return scales.size();
        return 15;
    }

//    public void setScale(ArrayList<Scale> scales) {
//        this.scales = scales;
//        notifyDataSetChanged();
//    }

    private void detector(ScalesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.singleItemScale.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.singleItemScaleCreateTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        }
    }

    private void listener(ScalesHolder holder) {
        holder.binding.singleItemScale.setOnClickListener(v -> {
            holder.binding.singleItemScale.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.singleItemScale.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.binding.singleItemScaleCreateTextView.setOnClickListener(v -> {
            holder.binding.singleItemScaleCreateTextView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.singleItemScaleCreateTextView.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.createSampleFragment);
        });
    }

    private void setData(ScalesHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.singleItemScaleTopView.setVisibility(View.GONE);
        } else {
            holder.binding.singleItemScaleTopView.setVisibility(View.VISIBLE);
        }

        holder.binding.singleItemScaleSerialTextView.setText("$Raven-9Q");
        holder.binding.singleItemScaleNameTextView.setText("آزمون ریون کودکان (5)");
        holder.binding.singleItemScaleEditionTextView.setText("کودکان - 5");
    }

    public class ScalesHolder extends RecyclerView.ViewHolder {

        private SingleItemScaleBinding binding;

        public ScalesHolder(SingleItemScaleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}