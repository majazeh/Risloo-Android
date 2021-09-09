package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Adapters.Holder.ProfilesHolder;
import com.majazeh.risloo.databinding.SingleItemProfileBinding;
import com.mre.ligheh.Model.TypeModel.ProfileModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean showTitle = false;

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
        ProfileModel model = (ProfileModel) items.get(i);

        detector(holder);

        listener(holder, model);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items, boolean showTitle) {
        this.showTitle = showTitle;

        if (this.items == null)
            this.items = items;
        else
            this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(ProfilesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(ProfilesHolder holder, ProfileModel model) {
        CustomClickView.onDelayedListener(() -> {
            IntentManager.display(activity, SelectionManager.getProfileExtras(activity, "fa", getFileNameSub(model.getFile_name())), model.getUrl());
        }).widget(holder.binding.getRoot());
    }

    private void setData(ProfilesHolder holder, ProfileModel model) {
        Picasso.get().load(model.getUrl()).placeholder(R.color.Gray100).into(holder.binding.avatarImageView);

        if (showTitle) {
            holder.binding.avatarTextView.setText(SelectionManager.getProfileExtras(activity, "fa", getFileNameSub(model.getFile_name())));
            holder.binding.avatarTextView.setVisibility(View.VISIBLE);
        } else {
            holder.binding.avatarTextView.setText("");
            holder.binding.avatarTextView.setVisibility(View.GONE);
        }
    }

    private String getFileNameSub(String fileName) {
        if (fileName.contains("items_sort_key.png"))
            return "items_sort_key.png";
        else if (fileName.contains("items_sort_value.png"))
            return "items_sort_value.png";
        else if (fileName.contains(".nv.png"))
            return ".nv.png";
        else if (fileName.contains(".critical.png"))
            return ".critical.png";
        else if (fileName.contains(".sup.png"))
            return ".sup.png";
        else if (fileName.contains(".hl.png"))
            return ".hl.png";
        else if (fileName.contains(".si.png"))
            return ".si.png";
        else if (fileName.contains(".content.png"))
            return ".content.png";
        else
            return "نامغلوم";
    }

}