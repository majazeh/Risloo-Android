package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemScheduleBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesAdapter.SchedulesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> schedules;
    private String type = "";

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
//        ScheduleModel schedule = (ScheduleModel) schedules.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public void setSchedules(ArrayList<TypeModel> schedules, String type) {
        this.schedules = schedules;
        this.type = type;
        notifyDataSetChanged();
    }

    private void detector(SchedulesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(SchedulesHolder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.sessionFragment)).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(SchedulesHolder holder) {
        if (type.equals("center")) {
            holder.binding.dateTextView.setText("ساعت 04:00");
        } else {
            holder.binding.dateTextView.setText("1400-02-29 - ساعت 04:00");
        }

        holder.binding.nameTextView.setText("محمد حسن صالحی");
        holder.binding.durationTextView.setText("45" + " دقیقه");
        holder.binding.countTextView.setText("50");
        holder.binding.pointTextView.setText("موردی وجود ندارد.");
        holder.binding.statusTextView.setText("پیش نویس");

        setAvatar(holder, "");
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

    public class SchedulesHolder extends RecyclerView.ViewHolder {

        private SingleItemScheduleBinding binding;

        public SchedulesHolder(SingleItemScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}