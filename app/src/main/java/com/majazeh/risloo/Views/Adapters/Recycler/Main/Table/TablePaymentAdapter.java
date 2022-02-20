package com.majazeh.risloo.Views.adapters.recycler.main.Table;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.managers.DateManager;
import com.majazeh.risloo.Utils.managers.SelectionManager;
import com.majazeh.risloo.Utils.managers.StringManager;
import com.majazeh.risloo.Utils.widgets.CustomClickView;
import com.majazeh.risloo.Views.adapters.holder.main.Header.HeaderPaymentHolder;
import com.majazeh.risloo.Views.adapters.holder.main.Table.TablePaymentHolder;
import com.majazeh.risloo.databinding.HeaderItemTablePaymentBinding;
import com.majazeh.risloo.databinding.SingleItemTablePaymentBinding;
import com.mre.ligheh.Model.TypeModel.PaymentModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class TablePaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public TablePaymentAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderPaymentHolder(HeaderItemTablePaymentBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TablePaymentHolder(SingleItemTablePaymentBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderPaymentHolder) {
            setWidget((HeaderPaymentHolder) holder);
        } else if (holder instanceof TablePaymentHolder) {
            PaymentModel model = (PaymentModel) items.get(i - 1);

            listener((TablePaymentHolder) holder, model);

            setData((TablePaymentHolder) holder, model);
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

    private void setWidget(HeaderPaymentHolder holder) {
        holder.binding.leftTextView.setText(StringManager.foregroundSize(activity.getResources().getString(R.string.PaymentAdapterLeft), 11, 18, activity.getResources().getColor(R.color.coolGray500), (int) activity.getResources().getDimension(R.dimen._7ssp)));
    }

    private void listener(TablePaymentHolder holder, PaymentModel model) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(TablePaymentHolder holder, PaymentModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.leftTextView.setText(StringManager.separate(String.valueOf(model.getAmount())));

        if (model.getTreasury() != null)
            holder.binding.treasuryTextView.setText(model.getTreasury().getTitle());

        holder.binding.descriptionTextView.setText(model.getTitle());
        holder.binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDnlHHsMM(String.valueOf(model.getCreatedAt()), " "));

        if (model.getStatus().equals("expired")) {
            holder.binding.statusTextView.setText(SelectionManager.getPaymentType(activity, "fa", model.getStatus()));
            holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.coolGray700));
        } else if (model.getStatus().equals("fail")) {
            holder.binding.statusTextView.setText(SelectionManager.getPaymentType(activity, "fa", model.getStatus()));
            holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.red600));
        } else {
            holder.binding.statusTextView.setText(SelectionManager.getPaymentType(activity, "fa", model.getStatus()));
            holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.emerald600));
        }
    }

}