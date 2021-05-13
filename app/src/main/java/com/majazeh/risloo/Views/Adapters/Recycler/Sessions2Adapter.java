package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
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

        initializer(holder);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return sessions.size();
        return 4;
    }

//    public void setSessions(ArrayList<Session> sessions) {
//        this.sessions = sessions;
//        notifyDataSetChanged();
//    }

    private void initializer(Sessions2Holder holder) {
        InitManager.spinner(activity, holder.binding.statusSpinner, R.array.SessionStatus, "adapter");
    }

    private void detector(Sessions2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.editImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_ripple_gray300);
        }
    }

    private void listener(Sessions2Holder holder) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        holder.binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String status = parent.getItemAtPosition(position).toString();

                doWork(status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.editSessionFragment)).widget(holder.binding.editImageView);
    }

    private void setData(Sessions2Holder holder) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("SE966669A");
        holder.binding.dateTextView.setText("شنبه 11 بهمن 99 ساعت 16:00");
        holder.binding.durationTextView.setText("60 دقیقه");

        for (int i=0; i<holder.binding.statusSpinner.getCount(); i++) {
            if (holder.binding.statusSpinner.getItemAtPosition(i).toString().equalsIgnoreCase("در جلسه")) {
                holder.binding.statusSpinner.setSelection(i);
            }
        }

        if (holder.getBindingAdapterPosition() == 0) {
            setStatus(holder, true);
        } else {
            setStatus(holder, false);
        }
    }

    private void setStatus(Sessions2Holder holder, boolean enable) {
        if (enable) {
            holder.binding.statusSpinner.setEnabled(true);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                holder.binding.statusSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
            else
                holder.binding.statusSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray200);

            holder.binding.statusAngleImageView.setVisibility(View.VISIBLE);
        } else {
            holder.binding.statusSpinner.setEnabled(false);
            holder.binding.statusSpinner.setBackgroundResource(android.R.color.transparent);

            holder.binding.statusAngleImageView.setVisibility(View.GONE);
        }
    }

    private void doWork(String status) {

    }

    public class Sessions2Holder extends RecyclerView.ViewHolder {

        private SingleItemSession2Binding binding;

        public Sessions2Holder(SingleItemSession2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}