package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.CentersHolder> {

    // Vars
//    private ArrayList<Room> rooms;

    // Objects
    private Activity activity;

    public RoomsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CentersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.single_item_room, viewGroup, false);

        return new CentersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CentersHolder holder, int i) {
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

    private void detector(CentersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.itemConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(CentersHolder holder) {
        holder.itemConstraintLayout.setOnClickListener(v -> {
            holder.itemConstraintLayout.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.itemConstraintLayout.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.roomFragment);
        });
    }

    private void setData(CentersHolder holder) {
        holder.nameTextView.setText("ریلسو");

        holder.charTextView.setVisibility(View.VISIBLE);
        holder.charTextView.setText(StringManager.firstChars(holder.nameTextView.getText().toString()));

        Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(holder.avatarCircleImageView);
    }

    public class CentersHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout itemConstraintLayout;
        private CircleImageView avatarCircleImageView;
        private TextView charTextView, nameTextView;

        public CentersHolder(View view) {
            super(view);
            itemConstraintLayout = view.findViewById(R.id.single_item_room_constraintLayout);
            avatarCircleImageView = view.findViewById(R.id.component_avatar_86sdp_border_white_circleImageView);
            charTextView = view.findViewById(R.id.component_avatar_86sdp_border_white_textView);
            nameTextView = view.findViewById(R.id.single_item_room_name_textView);
        }
    }

}