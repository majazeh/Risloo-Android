package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.databinding.SingleItemDialogScaleBinding;

import org.json.JSONException;

import java.util.ArrayList;

public class ScalesDialogAdapter extends RecyclerView.Adapter<ScalesDialogAdapter.ScalesDialogHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<Model> scales;

    public ScalesDialogAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ScalesDialogHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ScalesDialogHolder(SingleItemDialogScaleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScalesDialogHolder holder, int i) {
        Model scale = scales.get(i);

        detector(holder);

        listener(holder, i);

        setData(holder, scale);
    }

    @Override
    public int getItemCount() {
        return scales.size();
    }

    public ArrayList<Model> getScales() {
        return scales;
    }

    public void setScales(ArrayList<Model> scales) {
        this.scales = scales;
        notifyDataSetChanged();
    }

    public void clearScales() {
        scales.clear();
        notifyDataSetChanged();
    }

    public void addScale(Model phone) {
        scales.add(phone);
        notifyDataSetChanged();
    }

    public void replaceScale(int position, Model phone) {
        scales.set(position, phone);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    private void removeScale(int position) {
        scales.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    private void detector(ScalesDialogHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);

            holder.binding.removeImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_gray50_ripple_red300);
        }
    }

    private void listener(ScalesDialogHolder holder, int position) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            removeScale(position);
        }).widget(holder.binding.removeImageView);
    }

    private void setData(ScalesDialogHolder holder, Model scale) {
        try {
            holder.binding.nameTextView.setText(scale.get("name").toString());
            holder.binding.editionTextView.setText(scale.get("edition").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ScalesDialogHolder extends RecyclerView.ViewHolder {

        private SingleItemDialogScaleBinding binding;

        public ScalesDialogHolder(SingleItemDialogScaleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}