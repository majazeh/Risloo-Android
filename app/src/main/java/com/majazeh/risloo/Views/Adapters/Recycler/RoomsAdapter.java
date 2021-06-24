package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Show.CenterFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Show.DashboardFragmentDirections;
import com.majazeh.risloo.Views.Fragments.Show.ReferenceFragmentDirections;
import com.majazeh.risloo.databinding.SingleItemRoomBinding;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

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

        listener(holder, i);

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

    private void listener(RoomsHolder holder, int position) {
        ClickManager.onClickListener(() -> {
            switch (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId()) {
                case R.id.dashboardFragment: {
                    NavDirections action = DashboardFragmentDirections.actionDashboardFragmentToRoomFragment("room", rooms.get(position));
                    ((MainActivity) activity).navController.navigate(action);
                } break;
                case R.id.centerFragment: {
                    NavDirections action = CenterFragmentDirections.actionCenterFragmentToRoomFragment("room", rooms.get(position));
                    ((MainActivity) activity).navController.navigate(action);
                } break;
                case R.id.referenceFragment: {
                    NavDirections action = ReferenceFragmentDirections.actionReferenceFragmentToRoomFragment("room", rooms.get(position));
                    ((MainActivity) activity).navController.navigate(action);
                } break;
            }
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(RoomsHolder holder, RoomModel model) {
        holder.binding.nameTextView.setText(model.getRoomManager().getName());
        holder.binding.typeTextView.setText(activity.getResources().getString(R.string.RoomsAdapterTypePersonalClinic));

        if (model.getRoomManager() != null && model.getRoomManager().getAvatar() != null && model.getRoomManager().getAvatar().getMedium() != null)
            setAvatar(holder, model.getRoomManager().getAvatar().getMedium().getUrl());
        else
            setAvatar(holder, "");
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

    public class RoomsHolder extends RecyclerView.ViewHolder {

        private SingleItemRoomBinding binding;

        public RoomsHolder(SingleItemRoomBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}