package com.majazeh.risloo.Views.adapters.recycler.main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.holder.main.Index.IndexScheduleHolder;
import com.majazeh.risloo.Views.Fragments.Main.Index.CenterSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomSchedulesFragment;
import com.majazeh.risloo.databinding.SingleItemIndexScheduleBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class IndexScheduleAdapter extends RecyclerView.Adapter<IndexScheduleHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Widget
    private TextView countTextView, emptyTextView;

    // Vars
    private ArrayList<TypeModel> items, showingItems = new ArrayList<>();
    public long selectedTimestamp = DateManager.currentTimestamp();

    public IndexScheduleAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexScheduleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexScheduleHolder(SingleItemIndexScheduleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexScheduleHolder holder, int i) {
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

    private void listener(IndexScheduleHolder holder, ScheduleModel model) {
        CustomClickView.onClickListener(() -> {
            if (holder.binding.statusTextView.getText().toString().equals("در حال نوبت\u200Cگیری") && ((MainActivity) activity).permissoon.showCenterSchedulesFragmentReserveSchedule(((MainActivity) activity).singleton.getUserModel(), model)) {

                if (current instanceof CenterSchedulesFragment)
                    model.setTreasuries(((CenterSchedulesFragment) current).treasuries);
                else if (current instanceof RoomSchedulesFragment)
                    model.setTreasuries(((RoomSchedulesFragment) current).treasuries);

                ((MainActivity) activity).navigatoon.navigateToReserveScheduleFragment(model);
            } else {
                ((MainActivity) activity).navigatoon.navigateToSessionFragment(model);
            }
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexScheduleHolder holder, ScheduleModel model) {
        holder.binding.timeTextView.setText("ساعت" + " " + DateManager.jalHHsMM(String.valueOf(model.getStartedAt())));
        holder.binding.durationTextView.setText(model.getDuration() + " دقیقه");
        holder.binding.nameTextView.setText(model.getRoom().getManager().getName());

        setClients(holder, model.getClients());

        setFields(holder, model.getFields());

        setPlatforms(holder, model.getSessionPlatforms());

        setGroupSession(holder, model.isGroupSession());

        setStatus(holder, model.getStatus());

        if (model.getRoom() != null && model.getRoom().getManager() != null && model.getRoom().getManager().getAvatar() != null && model.getRoom().getManager().getAvatar().getMedium() != null)
            setAvatar(holder, model.getRoom().getManager().getAvatar().getMedium().getUrl());
        else
            setAvatar(holder, "");
    }

    private void setClients(IndexScheduleHolder holder, List clients) {
        if (clients != null && clients.data().size() != 0) {
            holder.binding.referenceGroup.setVisibility(View.VISIBLE);
            holder.binding.referenceTextView.setText("");

            for (int i = 0; i < clients.data().size(); i++) {
                UserModel user = (UserModel) clients.data().get(i);

                if (user != null) {
                    holder.binding.referenceTextView.append(user.getName());
                    if (i != clients.data().size() - 1)
                        holder.binding.referenceTextView.append("  -  ");

                }
            }
        } else {
            holder.binding.referenceGroup.setVisibility(View.GONE);
            holder.binding.referenceTextView.setText("");
        }
    }

    private void setFields(IndexScheduleHolder holder, JSONArray fields) {
        if (fields != null && fields.length() != 0) {
            holder.binding.axisGroup.setVisibility(View.VISIBLE);
            holder.binding.axisTextView.setText("");

            try {
                for (int i = 0; i < fields.length(); i++) {
                    holder.binding.axisTextView.append(fields.getJSONObject(i).getString("title"));
                    if (i != fields.length() - 1)
                        holder.binding.axisTextView.append("  |  ");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            holder.binding.axisGroup.setVisibility(View.GONE);
            holder.binding.axisTextView.setText("");
        }
    }

    private void setPlatforms(IndexScheduleHolder holder, List platforms) {
        if (platforms != null && platforms.data().size() != 0) {
            holder.binding.platformGroup.setVisibility(View.VISIBLE);
            holder.binding.platformTextView.setText("");

            for (int i = 0; i < platforms.data().size(); i++) {
                SessionPlatformModel platform = (SessionPlatformModel) platforms.data().get(i);

                if (platform != null) {
                    holder.binding.platformTextView.append(platform.getTitle());
                    if (i != platforms.data().size() - 1)
                        holder.binding.platformTextView.append("  |  ");

                }
            }
        } else {
            holder.binding.platformGroup.setVisibility(View.GONE);
            holder.binding.platformTextView.setText("");
        }
    }

    private void setGroupSession(IndexScheduleHolder holder, boolean isGroupSession) {
        if (isGroupSession)
            holder.binding.bulkTextView.setVisibility(View.VISIBLE);
        else
            holder.binding.bulkTextView.setVisibility(View.GONE);
    }

    private void setStatus(IndexScheduleHolder holder, String status) {
        holder.binding.statusTextView.setText(SelectionManager.getSessionStatus2(activity, "fa", status));

        switch (status) {
            case "registration_awaiting":
            case "registration":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.amber500));
                holder.binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_amber600);
                holder.binding.statusBackground.setBackgroundResource(R.drawable.draw_2sdp_solid_amber50);
                break;
            case "client_awaiting":
            case "session_awaiting":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.risloo500));
                holder.binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_risloo500);
                holder.binding.statusBackground.setBackgroundResource(R.drawable.draw_2sdp_solid_risloo50);
                break;
            case "in_session":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.emerald500));
                holder.binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_emerald600);
                holder.binding.statusBackground.setBackgroundResource(R.drawable.draw_2sdp_solid_emerald50);
                break;
            case "canceled_by_client":
            case "canceled_by_center":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.red500));
                holder.binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_red600);
                holder.binding.statusBackground.setBackgroundResource(R.drawable.draw_2sdp_solid_red50);
                break;
            default:
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.coolGray500));
                holder.binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_coolgray400);
                holder.binding.statusBackground.setBackgroundResource(R.drawable.draw_2sdp_solid_coolgray50);
                break;
        }
    }

    private void setAvatar(IndexScheduleHolder holder, String url) {
        if (!url.equals("")) {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(url).placeholder(R.color.coolGray100).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            holder.binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(holder.binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    private void setShowingItems() {
        if (items != null) {
            if (!showingItems.isEmpty())
                showingItems.clear();

            for (int i = 0; i < items.size(); i++) {
                ScheduleModel model = (ScheduleModel) items.get(i);

                String selectedDate = DateManager.jalYYYYsMMsDD(String.valueOf(selectedTimestamp), "/");
                String modelDate = DateManager.jalYYYYsMMsDD(String.valueOf(model.getStartedAt()), "/");

                if (selectedDate.equals(modelDate))
                    showingItems.add(model);
            }

            if (showingItems.size() != 0) {
                emptyTextView.setVisibility(View.GONE);
                emptyTextView.setText("");
            } else {
                emptyTextView.setVisibility(View.VISIBLE);
                emptyTextView.setText(activity.getResources().getString(R.string.ScheduleAdapterDayEmpty));
            }

            countTextView.setText(StringManager.bracing(showingItems.size()));
        }
    }

}