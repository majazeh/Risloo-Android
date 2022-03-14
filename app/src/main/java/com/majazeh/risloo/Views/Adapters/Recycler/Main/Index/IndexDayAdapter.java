package com.majazeh.risloo.views.adapters.recycler.main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.interfaces.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.main.Index.IndexDayHolder;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterSchedules;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomSchedules;
import com.majazeh.risloo.databinding.SingleItemIndexDayBinding;

import java.util.ArrayList;

public class IndexDayAdapter extends RecyclerView.Adapter<IndexDayHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<Long> timestamps;
    private boolean userSelect = false;
    public long currentTimestamp = DateManager.currentTimestamp(), selectedTimestamp;

    public IndexDayAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexDayHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexDayHolder(SingleItemIndexDayBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexDayHolder holder, int i) {
        long timestamp = timestamps.get(i);

        intializer();

        listener(holder, timestamp);

        setData(holder, timestamp);

        setActive(holder, timestamp);
    }

    @Override
    public int getItemCount() {
        return timestamps.size();
    }

    public void setTimestamps(ArrayList<Long> timestamps) {
        this.timestamps = timestamps;
        notifyDataSetChanged();
    }

    private void intializer() {
        current = ((ActivityMain) activity).fragmont.getCurrent();
    }

    private void detector(IndexDayHolder holder, long timestamp) {
        if (currentTimestamp > timestamp) {
            if (selectedTimestamp == timestamp)
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_risloo500_ripple_risloo50);
            else
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_coolgray50_border_1sdp_coolgray200_ripple_coolgray300);
        } else {
            if (selectedTimestamp == timestamp)
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_risloo500_ripple_risloo50);
            else
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);
        }
    }

    private void listener(IndexDayHolder holder, long timestamp) {
        CustomClickView.onDelayedListener(() -> {
            if (current instanceof FragmentCenterSchedules)
                ((FragmentCenterSchedules) current).responseAdapter(timestamp);
            else if (current instanceof FragmentRoomSchedules)
                ((FragmentRoomSchedules) current).responseAdapter(timestamp);

            selectedTimestamp = timestamp;
            userSelect = true;

            notifyDataSetChanged();
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexDayHolder holder, long timestamp) {
        holder.binding.titleTextView.setText(DateManager.jalND(String.valueOf(timestamp)));
        holder.binding.dateTextView.setText(DateManager.jalYYYYsMMsDD(String.valueOf(timestamp), "/"));

        if (!userSelect && currentTimestamp == timestamp) {
            selectedTimestamp = timestamp;
        }
    }

    private void setActive(IndexDayHolder holder, long timestamp) {
        if (selectedTimestamp == timestamp) {
            detector(holder, timestamp);

            holder.binding.titleTextView.setTextColor(activity.getResources().getColor(R.color.white));
            holder.binding.dateTextView.setTextColor(activity.getResources().getColor(R.color.white));
        } else {
            detector(holder, timestamp);

            holder.binding.titleTextView.setTextColor(activity.getResources().getColor(R.color.coolGray600));
            holder.binding.dateTextView.setTextColor(activity.getResources().getColor(R.color.coolGray600));
        }
    }

}