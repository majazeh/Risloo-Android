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
        return 5;
    }

//    public void setRoom(ArrayList<Room> rooms) {
//        this.rooms = rooms;
//        notifyDataSetChanged();
//    }

    private void detector(RoomsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(RoomsHolder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.roomFragment)).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(RoomsHolder holder) {
        holder.binding.nameTextView.setText("ریلسو");

        holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
        holder.binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(holder.binding.nameTextView.getText().toString()));

        Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
    }

    public class RoomsHolder extends RecyclerView.ViewHolder {

        private SingleItemRoomBinding binding;

        public RoomsHolder(SingleItemRoomBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}