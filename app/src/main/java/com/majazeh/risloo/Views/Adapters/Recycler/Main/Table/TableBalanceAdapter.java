package com.majazeh.risloo.views.adapters.recycler.main.Table;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SpannableManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.adapters.holder.main.Header.HeaderBalanceHolder;
import com.majazeh.risloo.views.adapters.holder.main.Table.TableBalanceHolder;
import com.majazeh.risloo.databinding.HeaderItemTableBalanceBinding;
import com.majazeh.risloo.databinding.SingleItemTableBalanceBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class TableBalanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public TableBalanceAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderBalanceHolder(HeaderItemTableBalanceBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableBalanceHolder(SingleItemTableBalanceBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderBalanceHolder) {
            setWidget((HeaderBalanceHolder) holder);
        } else if (holder instanceof TableBalanceHolder) {
//            BalanceModel model = (BalanceModel) items.get(i - 1);

            listener((TableBalanceHolder) holder);

            setData((TableBalanceHolder) holder);
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
            return 5;
    }

    public int itemsCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        userSelect = false;

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

    private void setWidget(HeaderBalanceHolder holder) {
        holder.binding.amountTextView.setText(SpannableManager.foregroundColorSize(activity.getResources().getString(R.string.BalanceAdapterAmount), 10, 17, activity.getResources().getColor(R.color.coolGray500), (int) activity.getResources().getDimension(R.dimen._7ssp)));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableBalanceHolder holder) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.menuSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "پیش\u200Cفاکتور و تسویه":
                            // TODO : Place Code When Needed
                            break;
                        case "گزارشات":
                            // TODO : Place Code When Needed
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

    private void setData(TableBalanceHolder holder) {
        holder.binding.roomTextView.setText("محمد رضا سالاري فر");

        holder.binding.transactionCountTextView.setText("5");
        holder.binding.dateTextView.setText("پنجشنبه 04 آذر 0 - ساعت 16:03");

        String amount = "2000";

        if (amount.equals("0")) {
            holder.binding.amountTextView.setText(amount);
            holder.binding.amountTextView.setTextColor(activity.getResources().getColor(R.color.coolGray700));
        } else if (amount.contains("-")) {
            holder.binding.amountTextView.setText(StringManager.minusSeparate(amount));
            holder.binding.amountTextView.setTextColor(activity.getResources().getColor(R.color.red600));
        } else {
            holder.binding.amountTextView.setText(StringManager.separate(amount));
            holder.binding.amountTextView.setTextColor(activity.getResources().getColor(R.color.emerald600));
        }

        setMenu(holder);
    }

    private void setMenu(TableBalanceHolder holder) {
        ArrayList<String> items = new ArrayList<>();

        items.add(activity.getResources().getString(R.string.BalanceAdapterPayment));
        items.add(activity.getResources().getString(R.string.BalanceAdapterReports));

        items.add("");

        if (items.size() > 1) {
            holder.binding.menuGroup.setVisibility(View.VISIBLE);
            InitManager.selectCustomActionSpinner(activity, holder.binding.menuSpinner, items);
        } else {
            holder.binding.menuGroup.setVisibility(View.INVISIBLE);
        }
    }

}