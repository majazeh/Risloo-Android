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
import com.majazeh.risloo.databinding.ComponentAvatar86sdpBorderWhiteBinding;
import com.majazeh.risloo.databinding.SingleItemCenterBinding;
import com.squareup.picasso.Picasso;

public class CentersAdapter extends RecyclerView.Adapter<CentersAdapter.CentersHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Center> centers;

    public CentersAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CentersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CentersHolder(SingleItemCenterBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CentersHolder holder, int i) {
//        Centers center = centers.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return centers.size();
        return 15;
    }

//    public void setCenter(ArrayList<Center> centers) {
//        this.centers = centers;
//        notifyDataSetChanged();
//    }

    private void detector(CentersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.singleItemCenterConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(CentersHolder holder) {
        holder.binding.singleItemCenterConstraintLayout.setOnClickListener(v -> {
            holder.binding.singleItemCenterConstraintLayout.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.singleItemCenterConstraintLayout.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.centerFragment);
        });
    }

    private void setData(CentersHolder holder) {
        holder.binding.singleItemCenterNameTextView.setText("مرکز مشاوره ریلسو");
        holder.binding.singleItemCenterUsernameTextView.setText("کلینیک شخصی");

        holder.avatarBinding.componentAvatar86sdpBorderWhiteTextView.setVisibility(View.VISIBLE);
        holder.avatarBinding.componentAvatar86sdpBorderWhiteTextView.setText(StringManager.firstChars(holder.binding.singleItemCenterNameTextView.getText().toString()));

        Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(holder.avatarBinding.componentAvatar86sdpBorderWhiteCircleImageView);
    }

    public class CentersHolder extends RecyclerView.ViewHolder {

        private SingleItemCenterBinding binding;
        private ComponentAvatar86sdpBorderWhiteBinding avatarBinding;

        public CentersHolder(SingleItemCenterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.avatarBinding = binding.singleItemCenterAvatarIncludeLayout;
        }
    }

}