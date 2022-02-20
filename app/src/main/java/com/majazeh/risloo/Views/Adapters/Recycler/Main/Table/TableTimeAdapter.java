package com.majazeh.risloo.Views.adapters.recycler.main.Table;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.Views.adapters.holder.main.Header.HeaderTimeHolder;
import com.majazeh.risloo.Views.adapters.holder.main.Table.TableTimeHolder;
import com.majazeh.risloo.databinding.HeaderItemTableTimeBinding;
import com.majazeh.risloo.databinding.SingleItemTableTimeBinding;
import com.mre.ligheh.Model.TypeModel.BillingModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class TableTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public TableTimeAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderTimeHolder(HeaderItemTableTimeBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableTimeHolder(SingleItemTableTimeBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderTimeHolder) {
            setWidget((HeaderTimeHolder) holder);
        } else if (holder instanceof TableTimeHolder) {
            BillingModel model = (BillingModel) items.get(i - 1);

            listener((TableTimeHolder) holder, model);

            setData((TableTimeHolder) holder, model);
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

    private void setWidget(HeaderTimeHolder holder) {
        holder.binding.amountTextView.setText(StringManager.foregroundSize(activity.getResources().getString(R.string.TimeAdapterAmount), 5, 12, activity.getResources().getColor(R.color.coolGray500), (int) activity.getResources().getDimension(R.dimen._7ssp)));
    }

    private void listener(TableTimeHolder holder, BillingModel model) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(TableTimeHolder holder, BillingModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.titleTextView.setText(model.getTitle());

        if (model.getCreditor() != null)
            holder.binding.creditorTextView.setText(model.getCreditor().getTitle());

        if (model.getDebtor() != null && model.getDebtor().getUser() != null)
            holder.binding.debtorTextView.setText(model.getDebtor().getUser().getName() + " - " + model.getDebtor().getTitle());
        else if (model.getDebtor() != null)
            holder.binding.debtorTextView.setText(model.getDebtor().getTitle());

        holder.binding.amountTextView.setText(StringManager.separate(String.valueOf(model.getAmount())));
    }

}