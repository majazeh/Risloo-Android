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
        return 15;
    }

//    public void setSession(ArrayList<Session> sessions) {
//        this.sessions = sessions;
//        notifyDataSetChanged();
//    }

    private void detector(SessionsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.singleItemSession.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.singleItemSessionEditImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
        }
    }

    private void listener(SessionsHolder holder) {
        holder.binding.singleItemSession.setOnClickListener(v -> {
            holder.binding.singleItemSession.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.singleItemSession.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.sessionFragment);
        });

        holder.binding.singleItemSessionEditImageView.setOnClickListener(v -> {
            holder.binding.singleItemSessionEditImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.singleItemSessionEditImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });
    }

    private void setData(SessionsHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.singleItemSessionTopView.setVisibility(View.GONE);
        } else {
            holder.binding.singleItemSessionTopView.setVisibility(View.VISIBLE);
        }

        holder.binding.singleItemSessionSerialTextView.setText("SE9666669");
        holder.binding.singleItemSessionRoomTextView.setText("اتاق درمان فرزاد صیادی");
        holder.binding.singleItemSessionCenterTextView.setText("مرکز مشاوره خانواده");
        holder.binding.singleItemSessionCaseTextView.setText("RS966669P");
        holder.binding.singleItemSessionReferenceTextView.setText("هانیه سادات سیدمحمدی");
        holder.binding.singleItemSessionStartTimeTextView.setText("شنبه 11 بهمن 99\n ساعت 16:00");
        holder.binding.singleItemSessionDurationTextView.setText("60 دقیقه");
        holder.binding.singleItemSessionStatusTextView.setText("در انتظار تشکیل جلسه");
    }

    public class SessionsHolder extends RecyclerView.ViewHolder {

        private SingleItemSessionBinding binding;

        public SessionsHolder(SingleItemSessionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}