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
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemRoomBinding;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> rooms;

    public RoomsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RoomsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomsHolder(SingleItemRoomBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsHolder holder, int i) {
        RoomModel room = (RoomModel) rooms.get(i);

        detector(holder);

        listener(holder, room);

        setData(holder, room);
    }

    @Override
    public int getItemCount() {
        if (this.rooms != null)
            return rooms.size();
        else
            return 0;
    }

    public void setRooms(ArrayList<TypeModel> rooms) {
        if (this.rooms == null)
            this.rooms = rooms;
        else
            this.rooms.addAll(rooms);
        notifyDataSetChanged();
    }

    public void clearRooms() {
        if (this.rooms != null) {
            this.rooms.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(RoomsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(RoomsHolder holder, RoomModel model) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.roomFragment, getExtras(model))).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(RoomsHolder holder, RoomModel model) {
        holder.binding.nameTextView.setText(model.getRoomManager().getName());
        holder.binding.typeTextView.setText(activity.getResources().getString(R.string.RoomsAdapterTypePersonalClinic));

        if (model.getRoomManager() != null && model.getRoomManager().getAvatar() != null && model.getRoomManager().getAvatar().getMedium() != null) {
            setAvatar(holder, model.getRoomManager().getAvatar().getMedium().getUrl());
        } else {
            setAvatar(holder, "");
        }
    }

    private void setAvatar(RoomsHolder holder, String url) {
        if (!url.equals("")) {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(url).placeholder(R.color.Gray50).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            holder.binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(holder.binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    private Bundle getExtras(RoomModel model) {
        Bundle extras = new Bundle();

        extras.putString("id", model.getRoomId());
        extras.putString("type", model.getRoomType());

        extras.putString("manager_id", model.getRoomManager().getUserId());
        extras.putString("manager_name", model.getRoomManager().getName());

        if (model.getRoomManager() != null && model.getRoomManager().getAvatar() != null && model.getRoomManager().getAvatar().getMedium() != null)
            extras.putString("avatar", model.getRoomManager().getAvatar().getMedium().getUrl());

        return extras;
    }

    public class RoomsHolder extends RecyclerView.ViewHolder {

        private SingleItemRoomBinding binding;

        public RoomsHolder(SingleItemRoomBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}