package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.databinding.SingleItemPictoralBinding;
import com.squareup.picasso.Picasso;

public class PictoralsAdapter extends RecyclerView.Adapter<PictoralsAdapter.PictoralsHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Pictoral> pictorals;

    public PictoralsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PictoralsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PictoralsHolder(SingleItemPictoralBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PictoralsHolder holder, int i) {
//        Pictorals pictoral = pictorals.get(i);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return pictorals.size();
        return 5;
    }

//    public void setPictoral(ArrayList<Pictoral> pictorals) {
//        this.pictorals = pictorals;
//        notifyDataSetChanged();
//    }

    private void detector(PictoralsHolder holder, boolean selected) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (selected)
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_blue600_ripple_gray300);
            else
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        } else {
            if (selected)
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_blue600);
            else
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray200);
        }
    }

    private void listener(PictoralsHolder holder) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(PictoralsHolder holder) {
        holder.binding.numberTextView.setText(String.valueOf(holder.getAdapterPosition() + 1));
        Picasso.get().load(R.color.Gray100).placeholder(R.color.Gray100).into(holder.binding.answerImageView);

        if (holder.getAdapterPosition() == 0) {
            detector(holder, true);

            holder.binding.containerConstraintLayout.setEnabled(true);
            holder.binding.containerConstraintLayout.setClickable(true);

            holder.binding.numberTextView.setTextColor(activity.getResources().getColor(R.color.White));
            holder.binding.numberTextView.setBackgroundResource(R.drawable.draw_oval_solid_blue600);
        } else {
            detector(holder, false);

            holder.binding.containerConstraintLayout.setEnabled(false);
            holder.binding.containerConstraintLayout.setClickable(false);

            holder.binding.numberTextView.setTextColor(activity.getResources().getColor(R.color.Gray800));
            holder.binding.numberTextView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray200);
        }
    }

    public class PictoralsHolder extends RecyclerView.ViewHolder {

        private SingleItemPictoralBinding binding;

        public PictoralsHolder(SingleItemPictoralBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}