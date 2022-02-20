package com.majazeh.risloo.Views.adapters.recycler.main.Table;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.holder.main.Header.HeaderTreasuryHolder;
import com.majazeh.risloo.Views.adapters.holder.main.Table.TableTreasuryHolder;
import com.majazeh.risloo.databinding.HeaderItemTableTreasuryBinding;
import com.majazeh.risloo.databinding.SingleItemTableTreasuryBinding;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class TableTreasuryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public TableTreasuryAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderTreasuryHolder(HeaderItemTableTreasuryBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableTreasuryHolder(SingleItemTableTreasuryBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderTreasuryHolder) {
            setWidget((HeaderTreasuryHolder) holder);
        } else if (holder instanceof TableTreasuryHolder) {
            TreasuriesModel model = (TreasuriesModel) items.get(i - 1);

            listener((TableTreasuryHolder) holder, model);

            setData((TableTreasuryHolder) holder, model);
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
        holder.binding.leftTextView.setText(StringManager.foregroundSize(activity.getResources().getString(R.string.TreasuryAdapterLeft), 11, 18, activity.getResources().getColor(R.color.coolGray500), (int) activity.getResources().getDimension(R.dimen._7ssp)));
    }

    private void listener(TableTreasuryHolder holder, TreasuriesModel model) {
        CustomClickView.onClickListener(() -> {
            ((MainActivity) activity).navigatoon.navigateToCreateTreasuryFragment(model);
        }).widget(holder.binding.getRoot());

        CustomClickView.onClickListener(() -> {
            ((MainActivity) activity).navigatoon.navigateToEditTreasuryFragment(model);
        }).widget(holder.binding.editImageView);
    }

    private void setData(TableTreasuryHolder holder, TreasuriesModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.titleTextView.setText(model.getTitle());

        if (model.isCreditable())
            holder.binding.creditTextView.setText(activity.getResources().getString(R.string.TreasuryAdapterCreditValid));
        else
            holder.binding.creditTextView.setText(activity.getResources().getString(R.string.TreasuryAdapterCreditInvalid));

        if (model.getBalance() == 0) {
            holder.binding.leftTextView.setText(String.valueOf(model.getBalance()));
            holder.binding.leftTextView.setTextColor(activity.getResources().getColor(R.color.coolGray700));
        } else if (String.valueOf(model.getBalance()).contains("-")) {
            holder.binding.leftTextView.setText(StringManager.minusSeparate(String.valueOf(model.getBalance())));
            holder.binding.leftTextView.setTextColor(activity.getResources().getColor(R.color.red600));
        } else {
            holder.binding.leftTextView.setText(StringManager.separate(String.valueOf(model.getBalance())));
            holder.binding.leftTextView.setTextColor(activity.getResources().getColor(R.color.emerald600));
        }

    }

}