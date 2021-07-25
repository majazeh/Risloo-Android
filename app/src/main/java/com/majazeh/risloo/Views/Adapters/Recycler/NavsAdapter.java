package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemNavBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class NavsAdapter extends RecyclerView.Adapter<NavsAdapter.NavsHolder> {

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
        TypeModel item = items.get(i);

        detector(holder, i);

        listener(holder, i);

        setData(holder, item);

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
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (selectedPosition == position)
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_4sdp_solid_blue500_ripple_blue800);
            else
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_4sdp_solid_gray100_ripple_gray300);
        } else {
            if (selectedPosition == position)
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_4sdp_solid_blue500);
            else
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_4sdp_solid_gray100);
        }
    }

    private void listener(NavsHolder holder, int position) {
        ClickManager.onDelayedClickListener(() -> {
            ((MainActivity) activity).responseAdapter(holder.binding.nameTextView.getText().toString());
            setSelectedPosition(position);
        }).widget(holder.binding.getRoot());
    }

    private void setData(NavsHolder holder, TypeModel item) {
        try {
            holder.binding.nameTextView.setText(item.object.get("title").toString());
            holder.binding.descriptionTextView.setText(item.object.get("description").toString());

            holder.binding.iconImageView.setImageResource((Integer) item.object.get("image"));
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

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public class NavsHolder extends RecyclerView.ViewHolder {

        private SingleItemNavBinding binding;

        public NavsHolder(SingleItemNavBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}