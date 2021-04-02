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
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemRoomUserBinding;

public class RoomUsersAdapter extends RecyclerView.Adapter<RoomUsersAdapter.RoomUsersHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<User> users;

    public RoomUsersAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RoomUsersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomUsersHolder(SingleItemRoomUserBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomUsersHolder holder, int i) {
//        Users user = users.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return users.size();
        return 5;
    }

//    public void setUser(ArrayList<User> users) {
//        this.users = users;
//        notifyDataSetChanged();
//    }

    private void detector(RoomUsersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(RoomUsersHolder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.referenceFragment)).widget(holder.binding.getRoot());
    }

    private void setData(RoomUsersHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("GH96666DY");
        holder.binding.nameTextView.setText("محمد نخلی");
        holder.binding.mobileTextView.setText("+989905511926");
        holder.binding.typeTextView.setText("مراجع");
        holder.binding.statusTexView.setText("پذیرش شده");
        holder.binding.acceptedTextView.setText("99-12-26 10:55 ");
        holder.binding.kickedTextView.setText("99-12-26 10:55 ");
    }

    public class RoomUsersHolder extends RecyclerView.ViewHolder {

        private SingleItemRoomUserBinding binding;

        public RoomUsersHolder(SingleItemRoomUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}