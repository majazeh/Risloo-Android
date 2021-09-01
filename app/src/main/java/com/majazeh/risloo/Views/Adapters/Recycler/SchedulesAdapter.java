package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Adapters.Holder.SchedulesHolder;
import com.majazeh.risloo.databinding.SingleItemScheduleBinding;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesHolder> {

    // Objects
    private Activity activity;

    // Widget
    private TextView countTextView, emptyTextView;

    // Vars
    private ArrayList<TypeModel> items, showingItems = new ArrayList<>();
    private long selectedTimstamp = DateManager.currentTimestamp();

    public SchedulesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SchedulesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SchedulesHolder(SingleItemScheduleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulesHolder holder, int i) {
        ScheduleModel model = (ScheduleModel) showingItems.get(i);

        detector(holder);

        listener(holder, model);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.showingItems != null)
            return showingItems.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items, TextView countTextView, TextView emptyTextView) {
        if (this.items == null)
            this.items = items;
        else
            this.items.addAll(items);

        if (countTextView != null)
            this.countTextView = countTextView;
        if (emptyTextView != null)
            this.emptyTextView = emptyTextView;

        setShowingItems();
        notifyDataSetChanged();
    }

    public void setTimestamp(long selectedTimstamp) {
        this.selectedTimstamp = selectedTimstamp;

        setShowingItems();
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            this.showingItems.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(SchedulesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(SchedulesHolder holder, ScheduleModel model) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(SchedulesHolder holder, ScheduleModel model) {
        holder.binding.timeTextView.setText("ساعت" + " " + DateManager.jalHHsMM(String.valueOf(model.getStarted_at())));
        holder.binding.durationTextView.setText(model.getDuration() + " دقیقه");
        holder.binding.nameTextView.setText(model.getRoom().getRoomManager().getName());

        if (model.isGroup_session())
            holder.binding.bulkTextView.setVisibility(View.VISIBLE);
        else
            holder.binding.bulkTextView.setVisibility(View.GONE);

        setStatus(holder, model.getStatus());

        if (model.getRoom() != null && model.getRoom().getRoomManager() != null && model.getRoom().getRoomManager().getAvatar() != null && model.getRoom().getRoomManager().getAvatar().getMedium() != null)
            setAvatar(holder, model.getRoom().getRoomManager().getAvatar().getMedium().getUrl());
        else
            setAvatar(holder, "");
    }

    private void setStatus(SchedulesHolder holder, String status) {
        holder.binding.statusTextView.setText(SelectionManager.getSessionStatus(activity, "fa", status));

        switch (status) {
            case "registration_awaiting":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Yellow500));
                break;
            case "client_awaiting":
            case "session_awaiting":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Blue500));
                break;
            case "in_session":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Green500));
                break;
            case "canceled_by_client":
            case "canceled_by_center":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Red500));
                break;
            default:
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Gray500));
                break;
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

    private void setShowingItems() {
        if (items != null) {
            if (!showingItems.isEmpty())
                showingItems.clear();

            for (int i = 0; i < items.size(); i++) {
                ScheduleModel model = (ScheduleModel) items.get(i);

                String selectedDate = DateManager.jalYYYYsMMsDD(String.valueOf(selectedTimstamp), "/");
                String modelDate = DateManager.jalYYYYsMMsDD(String.valueOf(model.getStarted_at()), "/");

                if (selectedDate.equals(modelDate))
                    showingItems.add(model);
            }

            if (showingItems.size() != 0) {
                emptyTextView.setVisibility(View.GONE);
                emptyTextView.setText("");
            } else {
                emptyTextView.setVisibility(View.VISIBLE);
                emptyTextView.setText(activity.getResources().getString(R.string.SchedulesAdapterDayEmpty));
            }

            countTextView.setText(StringManager.bracing(showingItems.size()));
        }
    }

}