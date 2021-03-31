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

public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesAdapter.ProfilesHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Profile> profiles;

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
//        Profiles profile = profiles.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return profiles.size();
        return 1;
    }

//    public void setProfile(ArrayList<Profile> profiles) {
//        this.profiles = profiles;
//        notifyDataSetChanged();
//    }

    private void detector(ProfilesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(ProfilesHolder holder) {
        ClickManager.onDelayedClickListener(() -> {
            if (!((MainActivity) activity).singleton.getAvatar().equals("")) {
                IntentManager.display(activity, "", "", ((MainActivity) activity).singleton.getAvatar());
            }
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(ProfilesHolder holder) {
        Picasso.get().load(R.color.Gray100).placeholder(R.color.Gray100).into(holder.binding.avatarZoomageView);
    }

    public class ProfilesHolder extends RecyclerView.ViewHolder {

        private SingleItemProfileBinding binding;

        public ProfilesHolder(SingleItemProfileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}