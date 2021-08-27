package com.majazeh.risloo.Views.Adapters.Recycler.Test;

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
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Test.TestChainHolder;
import com.majazeh.risloo.databinding.SingleItemTestChainBinding;
import com.mre.ligheh.Model.TypeModel.ChainModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class TestChainAdapter extends RecyclerView.Adapter<TestChainHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public TestChainAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public TestChainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TestChainHolder(SingleItemTestChainBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestChainHolder holder, int i) {
        ChainModel model = (ChainModel) items.get(i);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
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

    private void setData(TestChainHolder holder, ChainModel model) {
        if (holder.getBindingAdapterPosition() == 0)
            holder.binding.topView.setVisibility(View.GONE);
        else
            holder.binding.topView.setVisibility(View.VISIBLE);

        holder.binding.nameTextView.setText(model.getTitle());

        setActive(holder, model);
    }

    private void setActive(TestChainHolder holder, ChainModel model) {
        if (model.getId().equals(((TestActivity) activity).data.get("id"))) {
            holder.binding.nameTextView.setTextColor(activity.getResources().getColor(R.color.Blue700));
            holder.binding.nameTextView.setTextAppearance(activity, R.style.danaDemiBoldTextStyle);

            holder.binding.activeImageView.setBackgroundResource(0);
            holder.binding.activeImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_chevron_circle_left_solid, null));
            ImageViewCompat.setImageTintList(holder.binding.activeImageView, AppCompatResources.getColorStateList(activity, R.color.Blue600));
        } else if (!model.getStatus().equals("seald") && !model.getStatus().equals("open")) {
            holder.binding.nameTextView.setTextColor(activity.getResources().getColor(R.color.Gray300));
            holder.binding.nameTextView.setTextAppearance(activity, R.style.danaMediumTextStyle);

            holder.binding.activeImageView.setBackgroundResource(0);
            holder.binding.activeImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_check_circle_light, null));
            ImageViewCompat.setImageTintList(holder.binding.activeImageView, AppCompatResources.getColorStateList(activity, R.color.Gray300));
        } else {
            holder.binding.nameTextView.setTextColor(activity.getResources().getColor(R.color.Gray500));
            holder.binding.nameTextView.setTextAppearance(activity, R.style.danaMediumTextStyle);

            holder.binding.activeImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray500);
            holder.binding.activeImageView.setImageDrawable(null);
            ImageViewCompat.setImageTintList(holder.binding.activeImageView, null);
        }
    }

}