package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Index.CenterSchedulesFragment;
import com.majazeh.risloo.databinding.SingleItemWeekBinding;

import java.util.ArrayList;

public class WeeksAdapter extends RecyclerView.Adapter<WeeksAdapter.WeeksHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<Long> week;
    private int selectedPosition = 0;
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

        listener(holder, i);

        setData(holder, timestamp, i);

        setActive(holder, i);
    }

    @Override
    public int getItemCount() {
        return week.size();
    }

    public void setWeek(ArrayList<Long> week) {
        this.week = week;
        notifyDataSetChanged();
    }

    private void detector(WeeksHolder holder, int position) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (selectedPosition == position)
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_blue500_ripple_blue800);
            else if (selectedPosition > position)
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);
            else
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        } else {
            if (selectedPosition == position)
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_blue500);
            else if (selectedPosition > position)
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200);
            else
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
        }
    }

    private void listener(WeeksHolder holder, int position) {
        ClickManager.onDelayedClickListener(() -> {
            CenterSchedulesFragment centerSchedulesFragment = (CenterSchedulesFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
            if (centerSchedulesFragment != null) {
                centerSchedulesFragment.responseAdapter();
                selectedPosition = position;
                meSelected = true;
            }

            notifyDataSetChanged();
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(WeeksHolder holder, long timestamp, int position) {
        holder.binding.titleTextView.setText(DateManager.gregorianToJalali11(DateManager.dateToString("yyyy-MM-dd", DateManager.timestampToDate(timestamp))));
        holder.binding.dateTextView.setText(DateManager.gregorianToJalali1(DateManager.dateToString("yyyy-MM-dd", DateManager.timestampToDate(timestamp))));

        if (!meSelected) {
            if (DateManager.currentTimestamp() == timestamp) {
                selectedPosition = position;
            }
        }
    }

    private void setActive(WeeksHolder holder, int position) {
        if (selectedPosition == position) {
            detector(holder, position);

            holder.binding.titleTextView.setTextColor(activity.getResources().getColor(R.color.White));
            holder.binding.dateTextView.setTextColor(activity.getResources().getColor(R.color.White));
        } else {
            detector(holder, position);

            holder.binding.titleTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));
            holder.binding.dateTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));
        }
    }

    public class WeeksHolder extends RecyclerView.ViewHolder {

        private SingleItemWeekBinding binding;

        public WeeksHolder(SingleItemWeekBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}