package com.majazeh.risloo.Views.Adapters.Recycler.Main;

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
import com.majazeh.risloo.Views.Adapters.Holder.Main.MainNavHolder;
import com.majazeh.risloo.databinding.SingleItemMainNavBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class MainNavAdapter extends RecyclerView.Adapter<MainNavHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private int selectedPosition = 0;

    public MainNavAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MainNavHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MainNavHolder(SingleItemMainNavBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainNavHolder holder, int i) {
        TypeModel model = items.get(i);

        listener(holder);

        setData(holder, model, i);
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

    private void listener(MainNavHolder holder) {
        CustomClickView.onDelayedListener(() -> {
            ((MainActivity) activity).responseAdapter(holder.binding.nameTextView.getText().toString());
        }).widget(holder.itemView);
    }

    private void setData(MainNavHolder holder, TypeModel model, int position) {
        try {
            holder.binding.nameTextView.setText(model.object.get("title").toString());
            holder.binding.descriptionTextView.setText(model.object.get("description").toString());

            holder.binding.iconImageView.setImageResource((Integer) model.object.get("image"));

            setActive(holder, position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setActive(MainNavHolder holder, int position) {
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.draw_4sdp_solid_risloo500_ripple_risloo700);

            holder.binding.nameTextView.setTextColor(activity.getResources().getColor(R.color.White));
            holder.binding.descriptionTextView.setTextColor(activity.getResources().getColor(R.color.White));

            ImageViewCompat.setImageTintList(holder.binding.iconImageView, AppCompatResources.getColorStateList(activity, R.color.White));
        } else {
            holder.itemView.setBackgroundResource(R.drawable.draw_4sdp_solid_coolgray50_ripple_coolgray300);

            holder.binding.nameTextView.setTextColor(activity.getResources().getColor(R.color.CoolGray900));
            holder.binding.descriptionTextView.setTextColor(activity.getResources().getColor(R.color.CoolGray600));

            ImageViewCompat.setImageTintList(holder.binding.iconImageView, AppCompatResources.getColorStateList(activity, R.color.CoolGray900));
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