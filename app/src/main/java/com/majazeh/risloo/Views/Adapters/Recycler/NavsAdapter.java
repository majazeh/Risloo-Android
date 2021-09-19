package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.NavsHolder;
import com.majazeh.risloo.databinding.SingleItemNavBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class NavsAdapter extends RecyclerView.Adapter<NavsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private int selectedPosition = 0;

    public NavsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public NavsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NavsHolder(SingleItemNavBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NavsHolder holder, int i) {
        TypeModel model = items.get(i);

        detector(holder, i);

        listener(holder);

        setData(holder, model);

        setActive(holder, i);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(NavsHolder holder, int position) {
        if (selectedPosition == position)
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_4sdp_solid_blue500_ripple_blue800);
        else
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_4sdp_solid_gray100_ripple_gray300);
    }

    private void listener(NavsHolder holder) {
        CustomClickView.onDelayedListener(() -> {
            ((MainActivity) activity).responseAdapter(holder.binding.nameTextView.getText().toString());
        }).widget(holder.binding.getRoot());
    }

    private void setData(NavsHolder holder, TypeModel model) {
        try {
            holder.binding.nameTextView.setText(model.object.get("title").toString());
            holder.binding.descriptionTextView.setText(model.object.get("description").toString());

            holder.binding.iconImageView.setImageResource((Integer) model.object.get("image"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setActive(NavsHolder holder, int position) {
        if (selectedPosition == position) {
            holder.binding.nameTextView.setTextColor(activity.getResources().getColor(R.color.White));
            holder.binding.descriptionTextView.setTextColor(activity.getResources().getColor(R.color.White));

            ImageViewCompat.setImageTintList(holder.binding.iconImageView, AppCompatResources.getColorStateList(activity, R.color.White));
        } else {
            holder.binding.nameTextView.setTextColor(activity.getResources().getColor(R.color.Gray800));
            holder.binding.descriptionTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

            ImageViewCompat.setImageTintList(holder.binding.iconImageView, AppCompatResources.getColorStateList(activity, R.color.Gray800));
        }
    }

    public void setFocused(String value) {
        if (value.contains("نمونه\u200Cهای گروهی")) {
            if (((MainActivity) activity).permissoon.showUsers(((MainActivity) activity).singleton.getUserModel()) && ((MainActivity) activity).permissoon.showBulkSamples(((MainActivity) activity).singleton.getUserModel()))
                selectedPosition = 6;
            else if (((MainActivity) activity).permissoon.showBulkSamples(((MainActivity) activity).singleton.getUserModel()))
                selectedPosition = 5;
            else
                selectedPosition = 10;
        }

        else if (value.contains("نمونه") || value.contains("نمونه\u200Cها")) {
            if (((MainActivity) activity).permissoon.showUsers(((MainActivity) activity).singleton.getUserModel()))
                selectedPosition = 5;
            else
                selectedPosition = 4;
        }

        else if (value.contains("جلسه") || value.contains("جلسات"))
            selectedPosition = 2;
        else if (value.contains("مراکز درمانی"))
            selectedPosition = 1;
        else if (value.contains("اعضاء"))
            selectedPosition = 3;

        else if (value.contains("ارزیابی\u200Cها")) {
            if (((MainActivity) activity).permissoon.showUsers(((MainActivity) activity).singleton.getUserModel()))
                selectedPosition = 4;
            else
                selectedPosition = 3;
        }

        else
            selectedPosition = 0;

        notifyDataSetChanged();
    }

}