package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.SingleItemChainBinding;

public class ChainsAdapter extends RecyclerView.Adapter<ChainsAdapter.ChainsHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Chain> chains;

    public ChainsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ChainsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChainsHolder(SingleItemChainBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChainsHolder holder, int i) {
//        Chains chain = chains.get(i);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return chains.size();
        return 4;
    }

//    public void setChains(ArrayList<Chain> chains) {
//        this.chains = chains;
//        notifyDataSetChanged();
//    }

    private void setData(ChainsHolder holder) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.nameTextView.setText("آزمون سلامت عمومی (1)");

        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.nameTextView.setTextColor(activity.getResources().getColor(R.color.Blue700));
            holder.binding.nameTextView.setTextAppearance(activity, R.style.danaDemiBoldTextStyle);

            holder.binding.activeImageView.setBackgroundResource(0);
            holder.binding.activeImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_chevron_circle_left_solid, null));
            ImageViewCompat.setImageTintList(holder.binding.activeImageView, AppCompatResources.getColorStateList(activity, R.color.Blue600));
        } else {
            holder.binding.nameTextView.setTextColor(activity.getResources().getColor(R.color.Gray500));
            holder.binding.nameTextView.setTextAppearance(activity, R.style.danaMediumTextStyle);

            holder.binding.activeImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray500);
            holder.binding.activeImageView.setImageDrawable(null);
            ImageViewCompat.setImageTintList(holder.binding.activeImageView, null);
        }
    }

    public class ChainsHolder extends RecyclerView.ViewHolder {

        private SingleItemChainBinding binding;

        public ChainsHolder(SingleItemChainBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}