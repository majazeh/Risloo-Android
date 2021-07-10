package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Index.CenterSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomSchedulesFragment;
import com.majazeh.risloo.databinding.SingleItemWeekBinding;

import java.util.ArrayList;

public class WeeksAdapter extends RecyclerView.Adapter<WeeksAdapter.WeeksHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<Long> week;
    private long currentTimestamp = DateManager.currentTimestamp(), selectedTimestamp;
    private boolean meSelected = false;

    public WeeksAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public WeeksHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new WeeksAdapter.WeeksHolder(SingleItemWeekBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeeksHolder holder, int i) {
        long timestamp = week.get(i);

        listener(holder, timestamp);

        setData(holder, timestamp);

        setActive(holder, timestamp);
    }

    @Override
    public int getItemCount() {
        return week.size();
    }

    public void setWeek(ArrayList<Long> week) {
        this.week = week;
        notifyDataSetChanged();
    }

    private void detector(WeeksHolder holder, long timestamp) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (currentTimestamp == timestamp) {
                if (selectedTimestamp == timestamp)
                    holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_blue500_ripple_blue800);
                else
                    holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
            } else if (currentTimestamp > timestamp) {
                if (selectedTimestamp == timestamp)
                    holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_blue500_ripple_blue800);
                else
                    holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);
            } else {
                if (selectedTimestamp == timestamp)
                    holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_blue500_ripple_blue800);
                else
                    holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
            }
        } else {
            if (currentTimestamp == timestamp) {
                if (selectedTimestamp == timestamp)
                    holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_blue500);
                else
                    holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
            } else if (currentTimestamp > timestamp) {
                if (selectedTimestamp == timestamp)
                    holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_blue500);
                else
                    holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200);
            } else {
                if (selectedTimestamp == timestamp)
                    holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_blue500);
                else
                    holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
            }
        }
    }

    private void listener(WeeksHolder holder, long timestamp) {
        ClickManager.onDelayedClickListener(() -> {
            if (getCurrent() != null) {
                if (getCurrent() instanceof CenterSchedulesFragment) {
                    ((CenterSchedulesFragment) getCurrent()).responseAdapter(timestamp);
                    selectedTimestamp = timestamp;
                    meSelected = true;
                } else if (getCurrent() instanceof RoomSchedulesFragment) {
                    ((RoomSchedulesFragment) getCurrent()).responseAdapter(timestamp);
                    selectedTimestamp = timestamp;
                    meSelected = true;
                }
            }

            notifyDataSetChanged();
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(WeeksHolder holder, long timestamp) {
        holder.binding.titleTextView.setText(DateManager.jalDayName(String.valueOf(timestamp)));
        holder.binding.dateTextView.setText(DateManager.jalYYYYsMMsDD(String.valueOf(timestamp), "/"));

        if (!meSelected) {
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

            holder.binding.titleTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));
            holder.binding.dateTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));
        }
    }

    private Fragment getCurrent() {
        Fragment fragment = ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null)
            if (fragment instanceof CenterSchedulesFragment)
                return fragment;
            else if (fragment instanceof RoomSchedulesFragment)
                return fragment;

        return null;
    }

    public class WeeksHolder extends RecyclerView.ViewHolder {

        private SingleItemWeekBinding binding;

        public WeeksHolder(SingleItemWeekBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}