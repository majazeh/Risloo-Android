package com.majazeh.risloo.Views.adapters.recycler.test;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Interfaces.DiffUtilTypeModelAdapter;
import com.majazeh.risloo.Utils.Interfaces.DiffUtilTypeModelCallback;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.adapters.holder.test.TestChainHolder;
import com.majazeh.risloo.Views.activities.TestActivity;
import com.majazeh.risloo.databinding.SingleItemTestChainBinding;
import com.mre.ligheh.Model.TypeModel.ChainModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class TestChainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DiffUtilTypeModelAdapter {

    // Activity
    private final Activity activity;

    // Differ
    private final AsyncListDiffer<TypeModel> differ;

    public TestChainAdapter(@NonNull Activity activity) {
        this.activity = activity;

        differ = new AsyncListDiffer<>(this, new DiffUtilTypeModelCallback(this));
    }

    @NonNull
    @Override
    public TestChainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TestChainHolder(SingleItemTestChainBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ChainModel model = (ChainModel) differ.getCurrentList().get(i);

        setData((TestChainHolder) holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.differ.getCurrentList() != null)
            return differ.getCurrentList().size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        differ.submitList(items);
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
        if (!model.getId().equals("") && model.getId().equals(((TestActivity) activity).sampleAnswers.id)) {
            InitManager.txtColorAppearance(activity, holder.binding.titleTextView, activity.getResources().getColor(R.color.risloo500), R.style.danaDemiBold);
            InitManager.imgResTintBackground(activity, holder.binding.activeImageView, R.drawable.ic_chevron_circle_left_solid, R.color.risloo500, 0);
        } else if (!model.getStatus().equals("") && !model.getStatus().equals("seald") && !model.getStatus().equals("open")) {
            InitManager.txtColorAppearance(activity, holder.binding.titleTextView, activity.getResources().getColor(R.color.coolGray300), R.style.danaMedium);
            InitManager.imgResTintBackground(activity, holder.binding.activeImageView, R.drawable.ic_check_circle_light, R.color.coolGray300, 0);
        } else {
            InitManager.txtColorAppearance(activity, holder.binding.titleTextView, activity.getResources().getColor(R.color.coolGray500), R.style.danaMedium);
            InitManager.imgResTintBackground(activity, holder.binding.activeImageView, 0, R.color.coolGray300, R.drawable.draw_oval_solid_transparent_border_1sdp_coolgray500);
        }
    }

    @Override
    public boolean areItemsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        ChainModel oldModel = (ChainModel) oldTypeModel;
        ChainModel newModel = (ChainModel) newTypeModel;

        return newModel.getId().equals(oldModel.getId());
    }

    @Override
    public boolean areContentsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        ChainModel oldModel = (ChainModel) oldTypeModel;
        ChainModel newModel = (ChainModel) newTypeModel;

        return newModel.compareTo(oldModel);
    }

}