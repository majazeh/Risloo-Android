package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
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
        return 5;
    }

//    public void setScale(ArrayList<Scale> scales) {
//        this.scales = scales;
//        notifyDataSetChanged();
//    }

    private void detector(ScalesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.createTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        }
    }

    private void listener(ScalesHolder holder) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.createSampleFragment)).widget(holder.binding.createTextView);
    }

    private void setData(ScalesHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("$Raven-9Q");
        holder.binding.nameTextView.setText("آزمون ریون کودکان (5)");
        holder.binding.editionTextView.setText("کودکان - 5");
    }

    public class ScalesHolder extends RecyclerView.ViewHolder {

        private SingleItemScaleBinding binding;

        public ScalesHolder(SingleItemScaleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}