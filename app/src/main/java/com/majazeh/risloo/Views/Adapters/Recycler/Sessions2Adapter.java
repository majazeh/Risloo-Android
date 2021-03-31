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
import com.majazeh.risloo.databinding.SingleItemSession2Binding;

public class Sessions2Adapter extends RecyclerView.Adapter<Sessions2Adapter.Sessions2Holder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Session> sessions;

    public Sessions2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Sessions2Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Sessions2Holder(SingleItemSession2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Sessions2Holder holder, int i) {
//        Sessions session = sessions.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return sessions.size();
        return 5;
    }

//    public void setSession(ArrayList<Session> sessions) {
//        this.sessions = sessions;
//        notifyDataSetChanged();
//    }

    private void detector(Sessions2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);

            holder.binding.editImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
        } else {
            holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray200);
        }
    }

    private void listener(Sessions2Holder holder) {
        holder.binding.getRoot().setOnClickListener(v -> {
            holder.binding.getRoot().setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.getRoot().setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.binding.statusTextView.setOnClickListener(v -> {
            holder.binding.statusTextView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.statusTextView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.binding.editImageView.setOnClickListener(v -> {
            holder.binding.editImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.editImageView.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.editSessionFragment);
        });
    }

    private void setData(Sessions2Holder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("SE966669A");
        holder.binding.dateTextView.setText("شنبه 11 بهمن 99 ساعت 16:00");
        holder.binding.durationTextView.setText("60 دقیقه");
        holder.binding.statusTextView.setText("در جلسه");
    }

    public class Sessions2Holder extends RecyclerView.ViewHolder {

        private SingleItemSession2Binding binding;

        public Sessions2Holder(SingleItemSession2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}