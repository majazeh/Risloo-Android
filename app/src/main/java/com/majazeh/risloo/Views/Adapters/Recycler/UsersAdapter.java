package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersHolder> {

    // Vars
//    private ArrayList<User> users;

    // Objects
    private Activity activity;

    public UsersAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.single_item_user, viewGroup, false);

        return new UsersHolder(view);
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
            holder.itemView.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.emailImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
            holder.mobileImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
            holder.enterImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_blue300);
            holder.editImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
        }
    }

    private void listener(UsersHolder holder) {
        holder.itemView.setOnClickListener(v -> {
            holder.itemView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.itemView.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.userFragment);
        });

        holder.emailImageView.setOnClickListener(v -> {
            holder.emailImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.emailImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.mobileImageView.setOnClickListener(v -> {
            holder.mobileImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.mobileImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.enterImageView.setOnClickListener(v -> {
            holder.enterImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.enterImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.editImageView.setOnClickListener(v -> {
            holder.editImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.editImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });
    }

    private void setData(UsersHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.topView.setVisibility(View.GONE);
        } else {
            holder.topView.setVisibility(View.VISIBLE);
        }

        holder.serialTextView.setText("IR966669");
        holder.nameTextView.setText("ریسلو");
        holder.usernameTextView.setText("baravak");
        holder.typeTextView.setText("ادمین");
        holder.statusTextView.setText("فعال");
    }

    public class UsersHolder extends RecyclerView.ViewHolder {

        private View topView;
        private TextView serialTextView, nameTextView, usernameTextView, typeTextView, statusTextView;
        private ImageView emailImageView, mobileImageView, enterImageView, editImageView;

        public UsersHolder(View view) {
            super(view);
            topView = view.findViewById(R.id.single_item_user_top_view);
            serialTextView = view.findViewById(R.id.single_item_user_serial_textView);
            nameTextView = view.findViewById(R.id.single_item_user_name_textView);
            usernameTextView = view.findViewById(R.id.single_item_user_username_textView);
            typeTextView = view.findViewById(R.id.single_item_user_type_textView);
            statusTextView = view.findViewById(R.id.single_item_user_status_textView);
            emailImageView = view.findViewById(R.id.single_item_user_email_imageView);
            mobileImageView = view.findViewById(R.id.single_item_user_mobile_imageView);
            enterImageView = view.findViewById(R.id.single_item_user_enter_imageView);
            editImageView = view.findViewById(R.id.single_item_user_edit_imageView);
        }
    }

}