package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemScheduleBinding;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesAdapter.SchedulesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> schedules;
    private String type = "";

    // Vars
    private ArrayList<TypeModel> selectedSchedules = new ArrayList<>();
    private long selectedTimstamp = DateManager.currentTimestamp();

    public SchedulesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SchedulesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SchedulesAdapter.SchedulesHolder(SingleItemScheduleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulesHolder holder, int i) {
        ScheduleModel schedule;

        if (!type.equals("room"))
            schedule = (ScheduleModel) selectedSchedules.get(i);
         else
            schedule = (ScheduleModel) schedules.get(i);

        detector(holder);

        listener(holder, schedule);

        setData(holder, schedule);
    }

    @Override
    public int getItemCount() {
        if (!type.equals("room")) {
            if (this.selectedSchedules != null)
                return selectedSchedules.size();
            else
                return 0;
        } else {
            if (this.schedules != null)
                return schedules.size();
            else
                return 0;
        }
    }

    public void setSchedules(ArrayList<TypeModel> schedules, String type) {
        this.type = type;

        if (this.schedules == null)
            this.schedules = schedules;
        else
            this.schedules.addAll(schedules);

        if (!this.type.equals("room"))
            setSelectedSchedules();

        notifyDataSetChanged();
    }

    public void setTimestamp(long selectedTimstamp) {
        this.selectedTimstamp = selectedTimstamp;
        setSelectedSchedules();
        notifyDataSetChanged();
    }

    public void clearSchedules() {
        if (this.schedules != null) {
            this.schedules.clear();
            this.selectedSchedules.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(SchedulesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(SchedulesHolder holder, ScheduleModel model) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.sessionFragment, getExtras(model))).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(SchedulesHolder holder, ScheduleModel model) {
        try {
            if (!type.equals("room")) {
                holder.binding.dateTextView.setText(DateManager.gregorianToJalali7(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(model.getStarted_at()))));
                holder.binding.managerGroup.setVisibility(View.VISIBLE);
            } else {
                holder.binding.dateTextView.setText(DateManager.gregorianToJalali6(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(model.getStarted_at()))));
                holder.binding.managerGroup.setVisibility(View.GONE);
            }

            holder.binding.nameTextView.setText(model.getRoom().getRoomManager().getName());
            holder.binding.durationTextView.setText(model.getDuration() + " دقیقه");
            holder.binding.countTextView.setText(String.valueOf(model.getClients_number()));

            if (model.getFields() != null && model.getFields().length() != 0) {
                holder.binding.pointTextView.setText("");
                for (int i = 0; i < model.getFields().length(); i++) {
                    holder.binding.pointTextView.append(model.getFields().getJSONObject(i).getString("title"));
                    if (i != model.getFields().length() -1) {
                        holder.binding.pointTextView.append(" " + "|" + " ");
                    }
                }
            }

            if (model.isGroup_session()) {
                holder.binding.bulkTextView.setVisibility(View.VISIBLE);
            } else {
                holder.binding.bulkTextView.setVisibility(View.GONE);
            }

            holder.binding.statusTextView.setText(SelectionManager.getSessionStatus(activity, "fa", model.getStatus()));

            if (model.getRoom().getRoomManager() != null && model.getRoom().getRoomManager().getAvatar() != null && model.getRoom().getRoomManager().getAvatar().getMedium() != null) {
                setAvatar(holder, model.getRoom().getRoomManager().getAvatar().getMedium().getUrl());
            } else {
                setAvatar(holder, "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAvatar(SchedulesHolder holder, String url) {
        if (!url.equals("")) {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(url).placeholder(R.color.Gray50).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            holder.binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(holder.binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    private void setSelectedSchedules() {
        if (!selectedSchedules.isEmpty()) {
            selectedSchedules.clear();
        }

        for (int i = 0; i < schedules.size(); i++) {
            ScheduleModel model = (ScheduleModel) schedules.get(i);

            String selectedDate = DateManager.gregorianToJalali1(DateManager.dateToString("yyyy-MM-dd", DateManager.timestampToDate(selectedTimstamp)));
            String modelDate = DateManager.gregorianToJalali1(DateManager.dateToString("yyyy-MM-dd", DateManager.timestampToDate(model.getStarted_at())));

            if (selectedDate.equals(modelDate)) {
                selectedSchedules.add(model);
            }
        }
    }

    private Bundle getExtras(ScheduleModel model) {
        Bundle extras = new Bundle();
//        try {
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return extras;
    }

    public class SchedulesHolder extends RecyclerView.ViewHolder {

        private SingleItemScheduleBinding binding;

        public SchedulesHolder(SingleItemScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}