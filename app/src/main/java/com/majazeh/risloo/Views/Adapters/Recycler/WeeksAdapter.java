package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.WeeksHolder;
import com.majazeh.risloo.Views.Fragments.Index.CenterSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomSchedulesFragment;
import com.majazeh.risloo.databinding.SingleItemWeekBinding;

import java.util.ArrayList;

public class WeeksAdapter extends RecyclerView.Adapter<WeeksHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<Long> timestamps;
    public long currentTimestamp = DateManager.currentTimestamp(), selectedTimestamp;
    private boolean userSelect = false;

    public WeeksAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public WeeksHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new WeeksHolder(SingleItemWeekBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeeksHolder holder, int i) {
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
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    private void detector(WeeksHolder holder, long timestamp) {
        if (currentTimestamp == timestamp) {
            if (selectedTimestamp == timestamp)
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_blue500_ripple_blue800);
            else
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        } else if (currentTimestamp > timestamp) {
            if (selectedTimestamp == timestamp)
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_blue500_ripple_blue800);
            else
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);
        } else {
            if (selectedTimestamp == timestamp)
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_blue500_ripple_blue800);
            else
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(WeeksHolder holder, long timestamp) {
        CustomClickView.onDelayedListener(() -> {
            if (current instanceof CenterSchedulesFragment) {
                ((CenterSchedulesFragment) current).responseAdapter(timestamp);
                selectedTimestamp = timestamp;
                userSelect = true;
            }

            if (current instanceof RoomSchedulesFragment) {
                ((RoomSchedulesFragment) current).responseAdapter(timestamp);
                selectedTimestamp = timestamp;
                userSelect = true;
            }

            notifyDataSetChanged();
        }).widget(holder.binding.getRoot());
    }

    private void setData(WeeksHolder holder, long timestamp) {
        holder.binding.titleTextView.setText(DateManager.jalDayName(String.valueOf(timestamp)));
        holder.binding.dateTextView.setText(DateManager.jalYYYYsMMsDD(String.valueOf(timestamp), "/"));

        if (!userSelect) {
            if (currentTimestamp == timestamp) {
                selectedTimestamp = timestamp;
            }
        }
    }

    private void setActive(WeeksHolder holder, long timestamp) {
        if (selectedTimestamp == timestamp) {
            detector(holder, timestamp);

            holder.binding.titleTextView.setTextColor(activity.getResources().getColor(R.color.White));
            holder.binding.dateTextView.setTextColor(activity.getResources().getColor(R.color.White));
        } else {
            detector(holder, timestamp);

            holder.binding.titleTextView.setTextColor(activity.getResources().getColor(R.color.CoolGray600));
            holder.binding.dateTextView.setTextColor(activity.getResources().getColor(R.color.CoolGray600));
        }
    }

}