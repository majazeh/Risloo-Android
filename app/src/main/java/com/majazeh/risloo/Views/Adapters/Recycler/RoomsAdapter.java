package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemRoomBinding;
import com.squareup.picasso.Picasso;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomsHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Room> rooms;

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
//        Rooms room = rooms.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return rooms.size();
        return 15;
    }

//    public void setRoom(ArrayList<Room> rooms) {
//        this.rooms = rooms;
//        notifyDataSetChanged();
//    }

    private void detector(RoomsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.singleItemRoomConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(RoomsHolder holder) {
        holder.binding.singleItemRoomConstraintLayout.setOnClickListener(v -> {
            holder.binding.singleItemRoomConstraintLayout.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.singleItemRoomConstraintLayout.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.roomFragment);
        });
    }

    private void setData(RoomsHolder holder) {
        holder.binding.singleItemRoomNameTextView.setText("ریلسو");

        holder.binding.singleItemRoomAvatarCircleImageView.componentAvatar86sdpBorderWhiteTextView.setVisibility(View.VISIBLE);
        holder.binding.singleItemRoomAvatarCircleImageView.componentAvatar86sdpBorderWhiteTextView.setText(StringManager.firstChars(holder.binding.singleItemRoomNameTextView.getText().toString()));

        Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(holder.binding.singleItemRoomAvatarCircleImageView.componentAvatar86sdpBorderWhiteCircleImageView);
    }

    public class RoomsHolder extends RecyclerView.ViewHolder {

        private SingleItemRoomBinding binding;

        public RoomsHolder(SingleItemRoomBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}