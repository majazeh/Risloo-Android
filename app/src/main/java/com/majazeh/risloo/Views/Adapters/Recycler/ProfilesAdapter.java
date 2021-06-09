package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemProfileBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesAdapter.ProfilesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<String> profiles;

    public ProfilesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProfilesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProfilesHolder(SingleItemProfileBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilesHolder holder, int i) {
        String profile = profiles.get(i);

        detector(holder);

        listener(holder, profile);

        setData(holder, profile);
    }

    @Override
    public int getItemCount() {
        if (this.profiles != null)
            return profiles.size();
        else
            return 0;
    }

    public void setProfiles(ArrayList<String> profiles) {
        if (this.profiles == null)
            this.profiles = profiles;
        else
            this.profiles.addAll(profiles);
        notifyDataSetChanged();
    }

    public void clearProfiles() {
        if (this.profiles != null) {
            this.profiles.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(ProfilesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(ProfilesHolder holder, String profile) {
        ClickManager.onDelayedClickListener(() -> {
            if (!((MainActivity) activity).singleton.getAvatar().equals("")) {
                IntentManager.display(activity, "", "", profile);
            }
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(ProfilesHolder holder, String profile) {
        Picasso.get().load(profile).placeholder(R.color.Gray100).into(holder.binding.avatarZoomageView);
    }

    public class ProfilesHolder extends RecyclerView.ViewHolder {

        private SingleItemProfileBinding binding;

        public ProfilesHolder(SingleItemProfileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}