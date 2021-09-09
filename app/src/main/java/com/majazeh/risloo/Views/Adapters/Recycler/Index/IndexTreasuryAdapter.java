package com.majazeh.risloo.Views.Adapters.Recycler.Index;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderTreasuryHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexTreasuryHolder;
import com.majazeh.risloo.databinding.HeaderItemIndexTreasuryBinding;
import com.majazeh.risloo.databinding.SingleItemIndexTreasuryBinding;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class IndexTreasuryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexTreasuryAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderTreasuryHolder(HeaderItemIndexTreasuryBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexTreasuryHolder(SingleItemIndexTreasuryBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderTreasuryHolder) {
            setWidget((HeaderTreasuryHolder) holder);
        } else if (holder instanceof  IndexTreasuryHolder) {
            TreasuriesModel model = (TreasuriesModel) items.get(i - 1);

            detector((IndexTreasuryHolder) holder);

            listener((IndexTreasuryHolder) holder, model);

            setData((IndexTreasuryHolder) holder, model);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        return 1;
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size() + 1;
        else
            return 0;
    }

    public int itemsCount() {
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

    private void setWidget(HeaderTreasuryHolder holder) {
        holder.binding.leftTextView.setText(StringManager.foregroundSize(activity.getResources().getString(R.string.TreasuriesFragmentLeft), 11, 14, activity.getResources().getColor(R.color.Gray500), (int) activity.getResources().getDimension(R.dimen._7ssp)));
    }

    private void detector(IndexTreasuryHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(IndexTreasuryHolder holder, TreasuriesModel model) {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalTreasuryFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.getRoot());

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalEditTreasuryFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.editImageView);
    }

    private void setData(IndexTreasuryHolder holder, TreasuriesModel model) {
        holder.binding.serialTextView.setText(model.getId());

        holder.binding.titleTextView.setText(model.getTitle());

        if (model.isCreditable())
            holder.binding.creditTextView.setText(activity.getResources().getString(R.string.TreasuriesFragmentCreditTrue));
        else
            holder.binding.creditTextView.setText(activity.getResources().getString(R.string.TreasuriesFragmentCreditFalse));

        holder.binding.leftTextView.setText(StringManager.separate(String.valueOf(model.getBalance())));
        if (model.getBalance() == 0)
            holder.binding.leftTextView.setTextColor(activity.getResources().getColor(R.color.Gray700));
        else if (String.valueOf(model.getBalance()).contains("-"))
            holder.binding.leftTextView.setTextColor(activity.getResources().getColor(R.color.Red500));
        else
            holder.binding.leftTextView.setTextColor(activity.getResources().getColor(R.color.Green600));
    }

}