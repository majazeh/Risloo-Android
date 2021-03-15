package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemUserBinding;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<User> users;

    public UsersAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UsersHolder(SingleItemUserBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, int i) {
//        Users user = users.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return users.size();
        return 15;
    }

//    public void setUser(ArrayList<User> users) {
//        this.users = users;
//        notifyDataSetChanged();
//    }

    private void detector(UsersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.singleItemUser.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.emailImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
            holder.binding.mobileImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
            holder.binding.enterImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_blue300);
            holder.binding.editImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
        }
    }

    private void listener(UsersHolder holder) {
        holder.binding.singleItemUser.setOnClickListener(v -> {
            holder.binding.singleItemUser.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.singleItemUser.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.userFragment);
        });

        holder.binding.emailImageView.setOnClickListener(v -> {
            holder.binding.emailImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.emailImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.binding.mobileImageView.setOnClickListener(v -> {
            holder.binding.mobileImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.mobileImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.binding.enterImageView.setOnClickListener(v -> {
            holder.binding.enterImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.enterImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.binding.editImageView.setOnClickListener(v -> {
            holder.binding.editImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.editImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });
    }

    private void setData(UsersHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("IR966669");
        holder.binding.nameTextView.setText("ریسلو");
        holder.binding.usernameTextView.setText("baravak");
        holder.binding.typeTextView.setText("ادمین");
        holder.binding.statusTextView.setText("فعال");
    }

    public class UsersHolder extends RecyclerView.ViewHolder {

        private SingleItemUserBinding binding;

        public UsersHolder(SingleItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}