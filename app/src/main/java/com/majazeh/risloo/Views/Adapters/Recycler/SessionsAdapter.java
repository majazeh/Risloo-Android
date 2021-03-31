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
import com.majazeh.risloo.databinding.SingleItemSessionBinding;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.SessionsHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Session> sessions;

    public SessionsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SessionsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SessionsHolder(SingleItemSessionBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SessionsHolder holder, int i) {
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

    private void detector(SessionsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.editImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
        }
    }

    private void listener(SessionsHolder holder) {
        holder.binding.getRoot().setOnClickListener(v -> {
            holder.binding.getRoot().setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.getRoot().setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.sessionFragment);
        });

        holder.binding.editImageView.setOnClickListener(v -> {
            holder.binding.editImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.editImageView.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.editSessionFragment);
        });
    }

    private void setData(SessionsHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("SE9666669");
        holder.binding.roomTextView.setText("اتاق درمان فرزاد صیادی");
        holder.binding.centerTextView.setText("مرکز مشاوره خانواده");
        holder.binding.caseTextView.setText("RS966669P");
        holder.binding.referenceTextView.setText("هانیه سادات سیدمحمدی");
        holder.binding.startTimeTextView.setText("شنبه 11 بهمن 99\n ساعت 16:00");
        holder.binding.durationTextView.setText("60 دقیقه");
        holder.binding.statusTextView.setText("در انتظار تشکیل جلسه");
    }

    public class SessionsHolder extends RecyclerView.ViewHolder {

        private SingleItemSessionBinding binding;

        public SessionsHolder(SingleItemSessionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}