package com.majazeh.risloo.Views.Adapters.Recycler.Test;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
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
            holder.binding.dividerView.setVisibility(View.GONE);
        else
            holder.binding.dividerView.setVisibility(View.VISIBLE);

        if (!model.getTitle().equals(""))
            holder.binding.titleTextView.setText(model.getTitle());

        setActive(holder, model);
    }

    private void setActive(TestChainHolder holder, ChainModel model) {
        if (!model.getId().equals("") && model.getId().equals(((TestActivity) activity).data.get("id"))) {
            InitManager.txtColorAppearance(activity, holder.binding.titleTextView, activity.getResources().getColor(R.color.Risloo500), R.style.danaDemiBoldTextStyle);
            InitManager.imgResTintBackground(activity, holder.binding.activeImageView, R.drawable.ic_chevron_circle_left_solid, R.color.Risloo500, 0);
        } else if (!model.getStatus().equals("") && !model.getStatus().equals("seald") && !model.getStatus().equals("open")) {
            InitManager.txtColorAppearance(activity, holder.binding.titleTextView, activity.getResources().getColor(R.color.CoolGray300), R.style.danaMediumTextStyle);
            InitManager.imgResTintBackground(activity, holder.binding.activeImageView, R.drawable.ic_check_circle_light, R.color.CoolGray300, 0);
        } else {
            InitManager.txtColorAppearance(activity, holder.binding.titleTextView, activity.getResources().getColor(R.color.CoolGray500), R.style.danaMediumTextStyle);
            InitManager.imgResTintBackground(activity, holder.binding.activeImageView, 0, R.color.CoolGray300, R.drawable.draw_oval_solid_transparent_border_1sdp_coolgray500);
        }
    }

}