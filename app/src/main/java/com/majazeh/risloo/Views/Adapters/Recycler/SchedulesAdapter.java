package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.SchedulesHolder;
import com.majazeh.risloo.Views.Fragments.Index.CenterSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomSchedulesFragment;
import com.majazeh.risloo.databinding.SingleItemScheduleBinding;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Widget
    private TextView countTextView, emptyTextView;

    // Vars
    private ArrayList<TypeModel> items, showingItems = new ArrayList<>();
    public long selectedTimestamp = DateManager.currentTimestamp();

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

        initializer();

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

    public void setTimestamp(long timestamp) {
        this.selectedTimestamp = timestamp;

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

    private void initializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    private void listener(SchedulesHolder holder, ScheduleModel model) {
        CustomClickView.onClickListener(() -> {
            if (holder.binding.statusTextView.getText().toString().equals("در حال نوبت\u200Cگیری") && ((MainActivity) activity).permissoon.showReserveScheduleFragment(((MainActivity) activity).singleton.getUserModel(), model)) {

                if (current instanceof CenterSchedulesFragment)
                    model.setTreasuries(((CenterSchedulesFragment) current).treasuries);
                else if (current instanceof RoomSchedulesFragment)
                    model.setTreasuries(((RoomSchedulesFragment) current).treasuries);

                NavDirections action = NavigationMainDirections.actionGlobalReserveScheduleFragment(model);
                ((MainActivity) activity).navController.navigate(action);
            } else {
                NavDirections action = NavigationMainDirections.actionGlobalSessionFragment(model);
                ((MainActivity) activity).navController.navigate(action);
            }
        }).widget(holder.binding.getRoot());
    }

    private void setData(SchedulesHolder holder, ScheduleModel model) {
        try {
            holder.binding.timeTextView.setText("ساعت" + " " + DateManager.jalHHsMM(String.valueOf(model.getStarted_at())));
            holder.binding.durationTextView.setText(model.getDuration() + " دقیقه");
            holder.binding.nameTextView.setText(model.getRoom().getRoomManager().getName());

            if (model.getClients() != null && model.getClients().data().size() != 0) {
                holder.binding.referenceGroup.setVisibility(View.VISIBLE);
                holder.binding.referenceTextView.setText("");
                for (int i = 0; i < model.getClients().data().size(); i++) {
                    UserModel user = (UserModel) model.getClients().data().get(i);
                    if (user != null) {
                        holder.binding.referenceTextView.append(user.getName());
                        if (i != model.getClients().data().size() - 1) {
                            holder.binding.referenceTextView.append("  -  ");
                        }
                    }
                }
            } else {
                holder.binding.referenceGroup.setVisibility(View.GONE);
            }

            if (model.getFields() != null && model.getFields().length() != 0) {
                holder.binding.axisGroup.setVisibility(View.VISIBLE);
                holder.binding.axisTextView.setText("");
                for (int i = 0; i < model.getFields().length(); i++) {
                    holder.binding.axisTextView.append(model.getFields().getJSONObject(i).getString("title"));
                    if (i != model.getFields().length() - 1) {
                        holder.binding.axisTextView.append("  |  ");
                    }
                }
            } else {
                holder.binding.axisGroup.setVisibility(View.GONE);
            }

            if (model.getSession_platforms() != null && model.getSession_platforms().data().size() != 0) {
                holder.binding.platformGroup.setVisibility(View.VISIBLE);
                holder.binding.platformTextView.setText("");
                for (int i = 0; i < model.getSession_platforms().data().size(); i++) {
                    SessionPlatformModel platform = (SessionPlatformModel) model.getSession_platforms().data().get(i);
                    if (platform != null) {
                        holder.binding.platformTextView.append(platform.getTitle());
                        if (i != model.getSession_platforms().data().size() - 1) {
                            holder.binding.platformTextView.append("  |  ");
                        }
                    }
                }
            } else {
                holder.binding.platformGroup.setVisibility(View.GONE);
            }

            if (model.isGroup_session())
                holder.binding.bulkTextView.setVisibility(View.VISIBLE);
            else
                holder.binding.bulkTextView.setVisibility(View.GONE);

            setStatus(holder, model.getStatus());

            if (model.getRoom() != null && model.getRoom().getRoomManager() != null && model.getRoom().getRoomManager().getAvatar() != null && model.getRoom().getRoomManager().getAvatar().getMedium() != null)
                setAvatar(holder, model.getRoom().getRoomManager().getAvatar().getMedium().getUrl());
            else
                setAvatar(holder, "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setStatus(SchedulesHolder holder, String status) {
        holder.binding.statusTextView.setText(SelectionManager.getSessionStatus2(activity, "fa", status));

        switch (status) {
            case "registration_awaiting":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Yellow500));
                holder.binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_yellow600);
                holder.binding.statusBackground.setBackgroundResource(R.drawable.draw_2sdp_solid_yellow50);
                break;
            case "client_awaiting":
            case "session_awaiting":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Blue500));
                holder.binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_blue600);
                holder.binding.statusBackground.setBackgroundResource(R.drawable.draw_2sdp_solid_blue50);
                break;
            case "in_session":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Green500));
                holder.binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_green600);
                holder.binding.statusBackground.setBackgroundResource(R.drawable.draw_2sdp_solid_yellow50);
                break;
            case "canceled_by_client":
            case "canceled_by_center":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Red500));
                holder.binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_red600);
                holder.binding.statusBackground.setBackgroundResource(R.drawable.draw_2sdp_solid_red50);
                break;
            default:
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Gray500));
                holder.binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_gray400);
                holder.binding.statusBackground.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50);
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

                String selectedDate = DateManager.jalYYYYsMMsDD(String.valueOf(selectedTimestamp), "/");
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