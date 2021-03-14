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

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.SessionsHolder> {

    // Vars
//    private ArrayList<Session> sessions;

    // Objects
    private Activity activity;

    public SessionsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SessionsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.single_item_session, viewGroup, false);

        return new SessionsHolder(view);
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
            holder.itemView.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.editImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
        }
    }

    private void listener(SessionsHolder holder) {
        holder.itemView.setOnClickListener(v -> {
            holder.itemView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.itemView.setClickable(true), 300);

//            ((MainActivity) activity).navigator(R.id.sessionFragment);
        });

        holder.editImageView.setOnClickListener(v -> {
            holder.editImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.editImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });
    }

    private void setData(SessionsHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.topView.setVisibility(View.GONE);
        } else {
            holder.topView.setVisibility(View.VISIBLE);
        }

        holder.serialTextView.setText("SE9666669");
        holder.roomTextView.setText("اتاق درمان فرزاد صیادی");
        holder.centerTextView.setText("مرکز مشاوره خانواده");
        holder.caseTextView.setText("RS966669P");
        holder.referenceTextView.setText("هانیه سادات سیدمحمدی");
        holder.startTimeTextView.setText("شنبه 11 بهمن 99\n ساعت 16:00");
        holder.durationTextView.setText("60 دقیقه");
        holder.statusTextView.setText("در انتظار تشکیل جلسه");
    }

    public class SessionsHolder extends RecyclerView.ViewHolder {

        private View topView;
        private TextView serialTextView, roomTextView, centerTextView, caseTextView, referenceTextView, startTimeTextView, durationTextView, statusTextView;
        private ImageView editImageView;

        public SessionsHolder(View view) {
            super(view);
            topView = view.findViewById(R.id.single_item_session_top_view);
            serialTextView = view.findViewById(R.id.single_item_session_serial_textView);
            roomTextView = view.findViewById(R.id.single_item_session_room_textView);
            centerTextView = view.findViewById(R.id.single_item_session_center_textView);
            caseTextView = view.findViewById(R.id.single_item_session_case_textView);
            referenceTextView = view.findViewById(R.id.single_item_session_reference_textView);
            startTimeTextView = view.findViewById(R.id.single_item_session_start_time_textView);
            durationTextView = view.findViewById(R.id.single_item_session_duration_textView);
            statusTextView = view.findViewById(R.id.single_item_session_status_textView);
            editImageView = view.findViewById(R.id.single_item_session_edit_imageView);
        }
    }

}