package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemPlatformBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class PlatformsAdapter extends RecyclerView.Adapter<PlatformsAdapter.PlatformsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> platforms;
    private HashMap data, header;

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

        initializer(holder);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
        if (this.platforms != null)
            return platforms.size();
        else
            return 4;
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

    private void initializer(PlatformsHolder holder) {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());
    }

    private void detector(PlatformsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);

            holder.binding.editImageView.setBackgroundResource(R.drawable.draw_oval_solid_gray50_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(PlatformsHolder holder) {
        ClickManager.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.containerConstraintLayout);

        ClickManager.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.editImageView);

        holder.binding.sessionCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                doWork("1", "checkbox");
            else
                doWork("", "checkbox");
        });

        holder.binding.availableSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                doWork("on", "switch");

                holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOn));
                holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Green700));
                holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_green50_border_1sdp_gray200);
            } else {
                doWork("", "switch");

                holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
                holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Gray600));
                holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
            }
        });
    }

    private void setData(PlatformsHolder holder) {
        holder.binding.titleTextView.setText("تماس صوتی آنلاین");
    }

    private void doWork(String value, String method) {
        // TODO : Place Code When Needed
    }

    public class PlatformsHolder extends RecyclerView.ViewHolder {

        private SingleItemPlatformBinding binding;

        public PlatformsHolder(SingleItemPlatformBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}