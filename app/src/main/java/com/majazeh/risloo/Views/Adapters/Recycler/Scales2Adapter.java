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
import com.majazeh.risloo.databinding.SingleItemScale2Binding;

public class Scales2Adapter extends RecyclerView.Adapter<Scales2Adapter.Scales2Holder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Scale> scales;

    public Scales2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Scales2Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Scales2Holder(SingleItemScale2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Scales2Holder holder, int i) {
//        Scales scale = scales.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return scales.size();
        return 4;
    }

//    public void setScales(ArrayList<Scale> scales) {
//        this.scales = scales;
//        notifyDataSetChanged();
//    }

    private void detector(Scales2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(Scales2Holder holder) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(Scales2Holder holder) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("$Raven-9Q");
        holder.binding.nameTextView.setText("آزمون ریون کودکان (5)");
        holder.binding.editionTextView.setText("کودکان - 5");
    }

    public class Scales2Holder extends RecyclerView.ViewHolder {

        private SingleItemScale2Binding binding;

        public Scales2Holder(SingleItemScale2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}