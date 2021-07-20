package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.databinding.SingleItemPlatformBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class PlatformsAdapter extends RecyclerView.Adapter<PlatformsAdapter.PlatformsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> platforms;

    public PlatformsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PlatformsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlatformsHolder(SingleItemPlatformBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlatformsHolder holder, int i) {
//        PlatformModel platform = (PlatformModel) platforms.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
        if (this.platforms != null)
            return platforms.size();
        else
            return 0;
    }

    public void setPlatforms(ArrayList<TypeModel> platforms) {
        if (this.platforms == null)
            this.platforms = platforms;
        else
            this.platforms.addAll(platforms);
        notifyDataSetChanged();
    }

    public void clearPlatforms() {
        if (this.platforms != null) {
            this.platforms.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(PlatformsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(PlatformsHolder holder) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(PlatformsHolder holder) {
        holder.binding.titleTextView.setText("انتخاب به عهده مراجع (مجازی) ");
        holder.binding.valueTextView.setText("توضیح تستی");
    }

    public class PlatformsHolder extends RecyclerView.ViewHolder {

        private SingleItemPlatformBinding binding;

        public PlatformsHolder(SingleItemPlatformBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}