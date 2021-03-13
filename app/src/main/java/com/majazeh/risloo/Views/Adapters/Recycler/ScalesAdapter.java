package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class ScalesAdapter extends RecyclerView.Adapter<ScalesAdapter.ScalesHolder> {

    // Vars
//    private ArrayList<Scale> scales;

    // Objects
    private Activity activity;

    public ScalesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ScalesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.single_item_scale, viewGroup, false);

        return new ScalesHolder(view);
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
            holder.itemView.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.createImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_green300);
        }
    }

    private void listener(ScalesHolder holder) {
        holder.itemView.setOnClickListener(v -> {
            holder.itemView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.itemView.setClickable(true), 300);

//            ((MainActivity) activity).navigator(R.id.scaleFragment);
        });

        holder.createImageView.setOnClickListener(v -> {
            holder.createImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.createImageView.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.createSampleFragment);
        });
    }

    private void setData(ScalesHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.topView.setVisibility(View.GONE);
        } else {
            holder.topView.setVisibility(View.VISIBLE);
        }

        holder.serialTextView.setText("$Raven-9Q");
        holder.nameTextView.setText("آزمون ریون کودکان (5)");
        holder.editionTextView.setText("کودکان - 5");
    }

    public class ScalesHolder extends RecyclerView.ViewHolder {

        private View topView;
        private TextView serialTextView, nameTextView, editionTextView;
        private ImageView createImageView;

        public ScalesHolder(View view) {
            super(view);
            topView = view.findViewById(R.id.single_item_scale_top_view);
            serialTextView = view.findViewById(R.id.single_item_scale_serial_textView);
            nameTextView = view.findViewById(R.id.single_item_scale_name_textView);
            editionTextView = view.findViewById(R.id.single_item_scale_edition_textView);
            createImageView = view.findViewById(R.id.single_item_scale_create_imageView);
        }
    }

}