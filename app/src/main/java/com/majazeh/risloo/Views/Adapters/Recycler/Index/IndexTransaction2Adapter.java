package com.majazeh.risloo.Views.Adapters.Recycler.Index;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderTransaction2Holder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexTransaction2Holder;
import com.majazeh.risloo.databinding.HeaderItemIndexTransaction2Binding;
import com.majazeh.risloo.databinding.SingleItemIndexTransaction2Binding;
import com.mre.ligheh.Model.TypeModel.BillingModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class IndexTransaction2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public IndexTransaction2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderTransaction2Holder(HeaderItemIndexTransaction2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexTransaction2Holder(SingleItemIndexTransaction2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderTransaction2Holder) {
            setWidget((HeaderTransaction2Holder) holder);
        } else if (holder instanceof  IndexTransaction2Holder) {
            BillingModel model = (BillingModel) items.get(i - 1);

            listener((IndexTransaction2Holder) holder, model);

            setData((IndexTransaction2Holder) holder, model);
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

    private void setWidget(HeaderTransaction2Holder holder) {
        holder.binding.amountTextView.setText(StringManager.foregroundSize(activity.getResources().getString(R.string.Transactions2AdapterAmount), 5, 8, activity.getResources().getColor(R.color.Gray500), (int) activity.getResources().getDimension(R.dimen._7ssp)));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(IndexTransaction2Holder holder, BillingModel model) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "پرداخت":
                            // TODO : Place Code If Needed
                            break;
                        case "ویرایش":
                            // TODO : Place Code If Needed
                            break;
                    }

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(IndexTransaction2Holder holder, BillingModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.titleTextView.setText(model.getTitle());
        holder.binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDnlHHsMM(String.valueOf(model.getCreated_at()), " "));

        if (model.getCreditor() != null)
            holder.binding.creditorTextView.setText(model.getCreditor().getTitle());

        if (model.getDebtor() != null && model.getDebtor().getUserModel() != null)
            holder.binding.debtorTextView.setText(model.getDebtor().getUserModel().getName() + " - " + model.getDebtor().getTitle());
        else if (model.getDebtor() != null)
            holder.binding.debtorTextView.setText(model.getDebtor().getTitle());

        holder.binding.amountTextView.setText(StringManager.separate(String.valueOf(model.getAmount())));
        holder.binding.statusTextView.setText(SelectionManager.getBillType(activity, "fa", model.getType()));

        setMenu(holder);
    }

    private void setMenu(IndexTransaction2Holder holder) {
        ArrayList<String> items = new ArrayList<>();

        items.add(activity.getResources().getString(R.string.Transactions2AdapterPay));
        items.add(activity.getResources().getString(R.string.Transactions2AdapterEdit));
        items.add("");

        InitManager.actionCustomSpinner(activity, holder.binding.menuSpinner, items);
    }

}