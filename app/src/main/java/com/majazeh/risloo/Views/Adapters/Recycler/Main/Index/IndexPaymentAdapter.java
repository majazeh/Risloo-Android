package com.majazeh.risloo.Views.Adapters.Recycler.Main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Header.HeaderPaymentHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Index.IndexPaymentHolder;
import com.majazeh.risloo.databinding.HeaderItemIndexPaymentBinding;
import com.majazeh.risloo.databinding.SingleItemIndexPaymentBinding;
import com.mre.ligheh.Model.TypeModel.PaymentModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class IndexPaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexPaymentAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderPaymentHolder(HeaderItemIndexPaymentBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexPaymentHolder(SingleItemIndexPaymentBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderPaymentHolder) {
            setWidget((HeaderPaymentHolder) holder);
        } else if (holder instanceof  IndexPaymentHolder) {
            PaymentModel model = (PaymentModel) items.get(i - 1);

            listener((IndexPaymentHolder) holder, model);

            setData((IndexPaymentHolder) holder, model);
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
        holder.binding.leftTextView.setText(StringManager.foregroundSize(activity.getResources().getString(R.string.PaymentAdapterLeft), 11, 18, activity.getResources().getColor(R.color.CoolGray500), (int) activity.getResources().getDimension(R.dimen._7ssp)));
    }

    private void listener(IndexPaymentHolder holder, PaymentModel model) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexPaymentHolder holder, PaymentModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.leftTextView.setText(StringManager.separate(String.valueOf(model.getAmount())));

        if (model.getTreasury() != null)
            holder.binding.treasuryTextView.setText(model.getTreasury().getTitle());

        holder.binding.descriptionTextView.setText(model.getTitle());
        holder.binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDnlHHsMM(String.valueOf(model.getCreated_at()), " "));

        if (model.getStatus().equals("expired")) {
            holder.binding.statusTextView.setText(SelectionManager.getPaymentType(activity, "fa", model.getStatus()));
            holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.CoolGray700));
        } else if (model.getStatus().equals("fail")) {
            holder.binding.statusTextView.setText(SelectionManager.getPaymentType(activity, "fa", model.getStatus()));
            holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Red600));
        } else {
            holder.binding.statusTextView.setText(SelectionManager.getPaymentType(activity, "fa", model.getStatus()));
            holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Emerald600));
        }
    }

}