package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.SingleItemSample2Binding;

public class Samples2Adapter extends RecyclerView.Adapter<Samples2Adapter.Samples2Holder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Sample> samples;

    public Samples2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Samples2Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Samples2Holder(SingleItemSample2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Samples2Holder holder, int i) {
//        Samples sample = samples.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return samples.size();
        return 12;
    }

//    public void setSample(ArrayList<Sample> samples) {
//        this.samples = samples;
//        notifyDataSetChanged();
//    }

    private void detector(Samples2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
        } else {
            holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
        }
    }

    private void listener(Samples2Holder holder) {
        holder.binding.getRoot().setOnClickListener(v -> {
            holder.binding.getRoot().setClickable(false);

            // TODO : Place Code Here
        });

        holder.binding.statusTextView.setOnClickListener(v -> {
            holder.binding.statusTextView.setClickable(false);

            // TODO : Place Code Here
        });
    }

    private void setData(Samples2Holder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("$X1HQZHGT6");
        holder.binding.nameTextView.setText("آزمون ریون کودکان (5)");
        holder.binding.sessionTextView.setText("SE966669A");
        holder.binding.referenceTextView.setText("محمد حسین");
        holder.binding.statusTextView.setText("نمره دهی");
    }

    public class Samples2Holder extends RecyclerView.ViewHolder {

        private SingleItemSample2Binding binding;

        public Samples2Holder(SingleItemSample2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}